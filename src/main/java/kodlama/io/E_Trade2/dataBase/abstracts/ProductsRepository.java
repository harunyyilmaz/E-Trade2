package kodlama.io.E_Trade2.dataBase.abstracts;

import kodlama.io.E_Trade2.entities.concretes.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductsRepository extends JpaRepository<Product,Long> {

    boolean existsByName(String name);

    List<Product> findByPriceBetween(double minPrice , double maxPrice);
}
