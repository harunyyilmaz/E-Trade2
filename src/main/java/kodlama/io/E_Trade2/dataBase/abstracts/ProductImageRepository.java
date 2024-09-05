package kodlama.io.E_Trade2.dataBase.abstracts;

import kodlama.io.E_Trade2.entities.concretes.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ProductImageRepository extends JpaRepository<ProductImage,Long>{

    List<ProductImage>  findByProductId(Long productId);

    List<ProductImage> findByIsActiveTrue();

    Optional<ProductImage> findByUrl(String url);

    List<ProductImage> findByUploadDateBetween(LocalDateTime startDate , LocalDateTime endDate);

}
