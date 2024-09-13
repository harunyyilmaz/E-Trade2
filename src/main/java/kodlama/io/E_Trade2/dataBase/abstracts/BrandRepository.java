package kodlama.io.E_Trade2.dataBase.abstracts;

import kodlama.io.E_Trade2.entities.concretes.Brand;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.Set;

public interface BrandRepository extends JpaRepository<Brand,Long> {

    Optional<Brand> findByName(String name);

}
