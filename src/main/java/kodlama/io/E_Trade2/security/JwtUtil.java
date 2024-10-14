package kodlama.io.E_Trade2.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secret;

    public String generateToken(String email) throws IllegalArgumentException,JWTCreationException{
        return JWT.create()
                .withSubject("User Details")
                .withClaim("email" , email)
                .withIssuedAt(new Date())
                .withIssuer("ALLIANZ")
                .sign(Algorithm.HMAC256(secret));
    }

    public String validateTokenAndRetrieveSubject(String token) throws JWTVerificationException{
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(secret))
                .withSubject("User Details")
                .withIssuer("ALLIANZ")
                .build();

        DecodedJWT jwt = verifier.verify(token);
        return jwt.getClaim("email").asString();
    }






    /*
    @Value("${jwt.secret}")
    private String secret;

    public String generateToken(String email) throws IllegalArgumentException, JWTCreationException{
        return JWT.create()
                .withSubject("User Details")// Token icerigine baslik ekler.
                .withClaim("email",email)// Tokena özel bir veri(claim) ekler.Burada e-posta ekledik.
                .withIssuedAt(new Date())//Token'nin verildigi tarihi ekler
                .withIssuer("ALLIANZ")//Tokeni kimin olusturdugunu belirtir.
                .sign(Algorithm.HMAC256(secret));//Token'i olusturdugumuz gizli anahtarla imzalar.Bu tokenin güveligini saglar.
    }

    public String validateTokenAndRetrieveSubject(String token) throws JWTVerificationException{
        //Bu metot verilen tokeni dogrular ve icerisindeki e-posta adresini alir.
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(secret))
                .withSubject("User Details") // JWT konusunu kontrol eder.
                .withIssuer("ALLIANZ")//JWT'nin yayincisini kontrol eder.
                .build();//Bu satir jwtVerifier nesnesini tamamlar ve dogrulayici olusturur.(verifier nesnesi olusturur)

        DecodedJWT jwt = verifier.verify(token);
        //DecodedJWT gibi siniflar, token'nin sifrelenmis icerigini cözer.
        //Genellikle JWT'yi cözümleyip dogrulamak ve icindeki verilere erismek icin kullanilir.
        return jwt.getClaim("email").asString();
        //Token icersindeki e-posta adresini cikarir ve döndürür.
        //BU satirda dogrulanan Jwt icindeki "email" iddiasinin degerini cikarir ve bir java String olarak döndürür.
    }   // Yani bu fonksiyon JWT icindeki e-posta adresini alir ve geri döndürür

     */
}
