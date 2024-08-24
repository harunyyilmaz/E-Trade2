package kodlama.io.E_Trade2.business.abstracts;

import kodlama.io.E_Trade2.entities.concretes.User;

public interface UserService {

    void saveUserByRole(User user , String roleName);
}
