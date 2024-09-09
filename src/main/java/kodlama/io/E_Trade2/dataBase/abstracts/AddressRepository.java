package kodlama.io.E_Trade2.dataBase.abstracts;

import kodlama.io.E_Trade2.entities.concretes.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address,Long> {
}
