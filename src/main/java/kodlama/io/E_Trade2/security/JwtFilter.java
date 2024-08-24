package kodlama.io.E_Trade2.security;

import com.auth0.jwt.exceptions.JWTVerificationException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component //Bu sinifin bir SPRING oldugunu ve SPRING cercevesi tarafindan yönetildigini gosterir.
@AllArgsConstructor
public class JwtFilter extends OncePerRequestFilter {
    //JwtFilter Amaci =>Gelen Http isteklerinin basliklarda gecerli bir JWT olup olmadigini kontrol
    //etmek ve dogrulamak.

    private JwtUtil jwtUtil;
    private SecurityService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String autHeader = request.getHeader("Authorization");

        if (autHeader !=null && !autHeader.isBlank() && autHeader.startsWith("Bearer")){
            //Bu kosul saglaniyorsa jwt icerir ve asagida jwt olusturuyoruz.

            String jwt = autHeader.substring(7);
            // 7. karakterden sonraki kisim tokenin kendisidir.
            if (jwt == null || jwt.isBlank()){
                response.sendError(HttpServletResponse.SC_BAD_REQUEST,"Invalid JWT TOKEN in Bearer Header");
            }else {
                try {
                    // JWT'yi doğrulama ve ayrıştırma işlemi burada yapılır
                    // Örneğin: Token geçerliliğini kontrol etmek, kullanıcı bilgilerini çıkarmak vb.

                    String email = jwtUtil.validateTokenAndRetrieveSubject(jwt);
                    //Bu JWT'yi dogrular ve icindeki kullanici kimligini (örn= e-posta adresini) cikarir.
                    //Bu yönetim "JWTVerificationException" firlatabilir, bu daJWT nin gecersiz
                    // oldugunu gosterir.

                    UserDetails userDetails = userDetailsService.loadUserByUsername(email);
                    //Kullanicinin veritabanindan kullanici detaylarini alir yani bilgileri yükler.
                                                                                                                                    // Spring Security'nin yetkilendirme kararlarını verirken kullanacağı rolleri belirtir.
                    UsernamePasswordAuthenticationToken authToken= new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
                    //Spring Scrty de kullanicinin kimlik dogrulama ve yetkilendirme bilgilerini temsil eder.
                    //Bu sınıf, genellikle kullanıcı adı ve parola ile kimlik doğrulaması yaparken veya
                    // bir kullanıcıyı manuel olarak güvenlik bağlamına yerleştirirken kullanılır.

                    SecurityContextHolder.getContext().setAuthentication(authToken);//Güvenlik baglamina Tokeni ayarla.
                    /*
                    SecurityContextHolder Sınıfı:
                    SecurityContextHolder,
                    Spring Security'de güvenlik bağlamını (SecurityContext) tutmak için kullanılan bir
                    sınıftır. Güvenlik bağlamı, şu anki kullanıcının kimlik doğrulama ve yetkilendirme
                    bilgilerini içerir.

                    getContext():
                    Bu metot, mevcut SecurityContext nesnesini döndürür. Bu bağlam, oturumun (session)
                    süresi boyunca kullanıcının güvenlik bilgilerini taşır.

                    setAuthentication(authToken):
                    Bu metot, SecurityContext'in kimlik doğrulama (Authentication) bilgilerini ayarlar.
                     Burada, yukarıda oluşturulan authToken bu bağlama yerleştirili


                     */
                }catch (JWTVerificationException exception){
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST,"Invalid JWT Token");
                    //Gönderilen Jwt nin gecersiz oldugunu ve islem yapilamayacagini gösterir.
                }
                filterChain.doFilter(request,response);
            }
        }
    }
}
