package kodlama.io.E_Trade2.dataBase.abstracts;

import kodlama.io.E_Trade2.entities.concretes.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomersRepository extends JpaRepository<Customer,Long> {

    boolean existsByUserName(String name);

    Optional<Customer> findByFirstName(String name);

    /*
    Eğer Repository'de Optional<Customer> yerine doğrudan Customer döndüren bir yöntem
    tanımladıysanız, orElseThrow() metodunu kullanamazsınız. Çünkü orElseThrow(),
     sadece Optional türündeki nesnelerde kullanılabilir. Optional, bir değerin var
     olup olmadığını kontrol etmenizi sağlar.
     */
}
