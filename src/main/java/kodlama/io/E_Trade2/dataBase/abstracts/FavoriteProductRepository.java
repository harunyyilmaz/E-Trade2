package kodlama.io.E_Trade2.dataBase.abstracts;

import kodlama.io.E_Trade2.entities.concretes.FavoriteProduct;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FavoriteProductRepository extends JpaRepository<FavoriteProduct, Long> {

    List<FavoriteProduct> findByCustomerId(Long customerId);

    boolean existsByProductId(Long productId);

    boolean existsByProductIdAndCustomerId(Long customerId, Long productId);
}
