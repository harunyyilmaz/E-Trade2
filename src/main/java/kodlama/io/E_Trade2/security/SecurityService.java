package kodlama.io.E_Trade2.security;
import kodlama.io.E_Trade2.dataBase.abstracts.UserRepository;
import kodlama.io.E_Trade2.entities.concretes.Role;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@AllArgsConstructor
public class SecurityService implements UserDetailsService {
    // bu sinif kullanici bilgilerini dogrulama ve kullanici rolleri ve izinleri yonetme
    //UserDetailsService : Spring Scrty de kullanicinin bilgilerini yuklemek icin kullanilan ara birim.
    private UserRepository userRepository;


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        //Bu metot belirli bir e-posta adresine sahip kullaniciyi veritabanindan bulur ve kullanicinin
        //kimlik bilgilerini ve rollerini(yetkilerini) yükler.
        //Bu metodun amaci uygulamaya giris yapmaya calisan bir kullanicinin kimlik bilgilerni dogrulamak
        //ve ona uygun rolleri tanimlamaktir.

        Optional<kodlama.io.E_Trade2.entities.concretes.User> user = userRepository.findByEmail(email);

        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        //Spring Scrty cercevesinde kullanilir kullanicinin sahip oldugu rolleri temsil eder.

        if (user.isEmpty()){
            throw new UsernameNotFoundException("Could not find user with email" + email);
        }else{
            for (Role role : user.get().getRoles()){
                grantedAuthorities.add(new SimpleGrantedAuthority("Role_" + role.getName().toUpperCase()));
            }
        }
        return new User(email,user.get().getPassword(),grantedAuthorities);
        //User sinifi => bu sinif Spring Scrty nin kullanici kimlik bilgilerinin(kullanici adi-sifre)
        //ve yetkileri(rolleri) saklamak icin kullanilan bir siniftir.
    }

}