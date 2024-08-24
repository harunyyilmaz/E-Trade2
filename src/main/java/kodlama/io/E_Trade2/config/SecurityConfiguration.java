package kodlama.io.E_Trade2.config;

import jakarta.servlet.http.HttpServletResponse;
import kodlama.io.E_Trade2.dataBase.abstracts.UserRepository;
import kodlama.io.E_Trade2.security.JwtFilter;
import kodlama.io.E_Trade2.security.SecurityService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfiguration {

    private UserRepository userRepository;
    private JwtFilter filter;
    private SecurityService uds;

    private static final String[] AUTH_WHITELIST = {
            "/auth/**",
            "/swagger-ui/**",
            "v3/api-docs/**",
            "/configuration/security",
            "/swagger-ui.html",
            "/webjars/**",
            "/v3/api-docs/**",
            "/api/public/**",
            "/api/public/authenticate",
            "/actuator/*",
            "/swagger-ui/**",
            "/tax",
            "/tax/**",
            "/category/**",
            "/category",
    };

    private static final String[] USER_AUTH_WHITELIST = {
            "/user",
            "/customer",
            "/product",
            "/categories"
    };
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {


        http.headers(headers -> headers.frameOptions(frameOptions -> frameOptions.disable()))
                .csrf(csrf -> csrf.disable()) //CSRF korumasi devre disi kalir.
                .httpBasic(httpBasic -> httpBasic.disable())//HTTP Basic Authenticationi devre disi birakir.
                .cors(cors -> cors.configurationSource(request -> { //farkli kaynaklardan gelen http isteklerine erisim izinlerini kontrol eder.
                    CorsConfiguration configuration = new CorsConfiguration();
                    configuration.setAllowedOrigins(List.of("*"));//tüm kaynaklaradan gelen isteklere izin verir.(herhangi bir api ye erisim izni.)
                    configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE"));//izin verilen http yöntemlerini belirtir.
                    configuration.setAllowedHeaders(List.of("*"));//Tüm basliklara izin verir.
                    configuration.setExposedHeaders(List.of("Content - Disposition"));//istemci tarafindan erisilebilcek basliklari belirtir(Content-Disposition) basligi.
                    return configuration;
                    //bu kod SpringSecurity yapilandirmasini özellestirir ve belirli güvenlik
                    //özelliklerini devre disi birakir.Her ayar,güvenlik özelliklerinin nasil
                    // davranacagini ve hangi tür istekleri kabul edecegini belirler.
                }))
                .authorizeHttpRequests(authz -> authz //yetkilendirme yapilandirmasi icin kullanilan bir lambda
                        .requestMatchers("AUTH-WHITELIST").permitAll()
                        .requestMatchers("/user/**", "/categories/", "/product/**").hasRole("USER")
                        .requestMatchers("/customer/**", "/categories/**", "/product/**").hasRole("CUSTOMER")
                        .requestMatchers("/**").hasRole("ADMIN")
                        .requestMatchers("USER_AUTH_WHITELIST").hasAuthority("ROLE_ADMIN")
                        //hasAuthority daha genel nir yetkilendirme kontrolü saglar.
                        //Bu kod SpringSecuriy deHTTP isteklerini yetkilendirme yapilandirmasini saglar.
                        //URL desenlerine bagli olarak hangi rollerin veya yetkilerin gerekli oldugunu belirler.
                )
                .userDetailsService(uds)//kullanici detaylarini yüklemek icin(uds) nesnesi
                // veritabanindan veya baska bir kaynaktan kullanici bilgilerini getirebilir.
                .exceptionHandling(exception->exception //kimlik dogrulama hatalarini nasil isleyeceginizi belirlemek icin kullanilam bir(lambda) fonksiyonudur.
                                                        //örnegin yetkisiz erisim denemeleri nasil isleyecegimizi belirlememize olanak tanir.
                        .authenticationEntryPoint((request, response, authException) ->
                                response.sendError(HttpServletResponse.SC_UNAUTHORIZED,"Unauthorizet")
                                //Bu kod parcasi SpringSecurity nin kullanici detaylarini yüklemek ve
                                //yetkilendirme hatalarini islemek icin yapilandirilmistir.
                        )
                )
                //OTURUM YÖNETIMI : Bu kod SpringSecurity de oturum yönetimi ve özel filtre ekleme islemlerini yapilandirir.
                .sessionManagement(session -> session//(sessıon) parametresı oturum yönetimi yapilandirmasi icin bir lambda fonksiyonudur.
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS));//oturum yönetimi politikasi "STATELESS"
        //olarak ayarlanmis bu her istegin bagimsiz oldugu ve sunucu tarafindan oturum bilgiisi tutlmadigi anlamina gelir


        //ÖZEL FILTRE EKLE :
        http.addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);
        //Filter, usernamePasswordAuthFilter dan önce calisir.Bu genellikle özel kimlik dogrulama
        //veya yetkilendirme mantigini eklemek icin kullanilir.

        //YAPILANDIRMA TAMAMLANMASI:
        return http.build();//HttpSecurity yapilandirmasini tamamlar ve "SecurityFilterChain" nesnesni olarak döndürür.
        //Bu nesne,SpringSecurity tarafindan HTTP isteklerine güvenlik filtrelerinin uygulanmasini saglar.
        //Bu yapilandirmalar,uygulamanin güvenlik polistikasini tanimlar ve HTTP isteklerinin nasil islenecegini belirler.

    }
    //Asagidaki bu iki metot uygulamanin güvenlik yapilandirmasini olusturur ve kullanici kimlik dogrulama
    //sifreleme islemlerini güvenli hale getirir.
    @Bean
    public PasswordEncoder passwordEncoder(){//Sifrenin sifrelenmesi icin ve dogrulanmasi icin kullanilan arayüzdür.
        return new BCryptPasswordEncoder();//Bu satir "BCryptPasswordEncoder" sinifinin örnegini döner.
        //Bu sifreleri sifreler,sifrenin kirilmasini zorlastirir.
    }

    @Bean//"AuthenticationManager" : Bu uygulamanin kimlik dogrulama islemleri icin gecerli bean'i olsuturulur.
    //AuthenticationConfiguration >:Mevcut kimlik dogrulama yapilandirmalarini iceren bir siniftir.
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception{
        return authenticationConfiguration.getAuthenticationManager();//Bu satir authConfig nesnesinden bir authManager döner.
    }
}
