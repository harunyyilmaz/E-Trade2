package kodlama.io.E_Trade2.dataBase.abstracts;

import kodlama.io.E_Trade2.entities.concretes.Brand;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BrandRepository extends JpaRepository<Brand,Integer> {

}
