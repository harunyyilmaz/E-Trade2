package kodlama.io.E_Trade2.webApiControllers;

import kodlama.io.E_Trade2.business.abstracts.UserService;
import kodlama.io.E_Trade2.dtos.requests.LoginRequest;
import kodlama.io.E_Trade2.entities.concretes.User;
import kodlama.io.E_Trade2.security.JwtUtil;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthorizationController {

    private AuthenticationManager authenticationManager;
    private JwtUtil jwtUtil;
    private UserService userService;

    @PostMapping("/login")
    public Map<String,Object> loginHandler(@RequestBody LoginRequest body){
        //Genellikle (e-posta ve password) iceren bir nesne istegin gövdesinden alir ve bir map döner.
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(body.getEmail(),body.getPassword());
        // Kullanicinin kimlik bilgilerini (email ve password) iceren bir
        // "UsernamePasswordAuthenticationToken" nesnesi olusturulur.


        authenticationManager.authenticate(authenticationToken);
        //AuthenticationManager kullanarak verilen kimlik bilgileri ile kullanici dogrular eger
        //kimlik bilgileri gecerliyse,dogrulama basarili olur.aksi halde istisna firlatir.

        String token = jwtUtil.generateToken(body.getEmail());
        // kullanici e-postasini kullanarak bir token olusturur.

        Map<String,Object> authorizationMap = new HashMap<>();
        authorizationMap.put("jwt-token", token);
        // Jwt tokenini "jwt-token" anahtarı altında haritaya ekler.
        return authorizationMap; // olusturulan JWT tokenini iceren haritayi döner.
    }
    /*ÖZETLE:
    Bu sinif, kullanicilarin kimlik dogrulama islemlerini gerceklestirdikten sonra
    JWT token'i olusturarak yanit olarak döner.

     */
    //abc


    @PostMapping("register")
    public ResponseEntity<Boolean> loginHandler(User user){
        String defaultRoleName="ROLE_NAME";
        userService.saveUserByRole(user,defaultRoleName);

        return new ResponseEntity<>(Boolean.TRUE, HttpStatus.OK);
    }
}
