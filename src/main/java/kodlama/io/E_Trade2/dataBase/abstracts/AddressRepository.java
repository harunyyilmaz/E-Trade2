package kodlama.io.E_Trade2.dataBase.abstracts;

import kodlama.io.E_Trade2.dtos.responses.GetAllAddressResponse;
import kodlama.io.E_Trade2.entities.concretes.Address;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AddressRepository extends JpaRepository<Address,Long> {

   List<Address> findByCity(String city);

   boolean existsByPostalCode(String postalCode);

   boolean existsByCountry(String country);

   boolean existsByCityAndCountry(String city,String country);

}
