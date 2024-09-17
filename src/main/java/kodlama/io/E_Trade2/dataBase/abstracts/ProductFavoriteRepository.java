package kodlama.io.E_Trade2.dataBase.abstracts;

import kodlama.io.E_Trade2.entities.concretes.ProductFavorite;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductFavoriteRepository extends JpaRepository<ProductFavorite,Long>{

    List<ProductFavorite> findByCustomerId(Long customerId);
}
