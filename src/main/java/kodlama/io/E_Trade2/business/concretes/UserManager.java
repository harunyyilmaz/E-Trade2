package kodlama.io.E_Trade2.business.concretes;

import kodlama.io.E_Trade2.business.abstracts.UserService;
import kodlama.io.E_Trade2.dataBase.abstracts.RoleRepository;
import kodlama.io.E_Trade2.dataBase.abstracts.UserRepository;
import kodlama.io.E_Trade2.entities.concretes.Role;
import kodlama.io.E_Trade2.entities.concretes.User;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@AllArgsConstructor
public class UserManager implements UserService {

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;

    @Override
    public void saveUserByRole(User user, String roleName) {

        user.setPassword(this.passwordEncoder.encode(user.getPassword()));
        Set<Role> roles = new HashSet<>();
        Role role = this.roleRepository.findByName(roleName);

        if (role == null){
            role = new Role();
            role.setName(roleName);
            role.setDescription(roleName + "description");
            role = this.roleRepository.save(role);
        }
        roles.add(role);
        user.setRoles(roles);
        userRepository.save(user);
    }
}
