package kodlama.io.E_Trade2.business.abstracts;

import kodlama.io.E_Trade2.dtos.requests.CreateProductImageRequest;
import kodlama.io.E_Trade2.dtos.requests.UpdateProductImageRequest;
import kodlama.io.E_Trade2.dtos.responses.GetByIdProductImageResponse;
import kodlama.io.E_Trade2.entities.concretes.ProductImage;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ProductImageService {

    /**
     * Görsel ekler.
     */
    ProductImage addProductImage(CreateProductImageRequest createProductImageRequest);
    /**
     * Görseli günceller.
     */
    ProductImage updateProductImage(Long id, UpdateProductImageRequest updateProductImageRequest);
    /**
     * Görseli ID'ye göre siler.
     */
    void deleteProductImage(Long id);
    /**
     * ID'ye göre bir görseli getirir.
     */
    Optional<ProductImage> getProductImageById(Long id);

    Optional<GetByIdProductImageResponse> getById(Long id);
    /**
     * Ürüne ait tüm görselleri getirir.
     */
    List<ProductImage> getProductImagesByProductId(Long productId);
    /**
     * Tüm aktif görselleri getirir.
     */
    List<ProductImage> getAllActiveProductImages();
    /**
     * Görsel URL'sine göre bir görseli getirir.
     */
    Optional<ProductImage> getProductImageByUrl(String url);
    /**
     * Belirli bir tarih aralığında yüklenen görselleri getirir.
     */
    List<ProductImage> getProductImagesByUploadDateRange(LocalDateTime startDate, LocalDateTime endDate);

    //Bir den fazla foto.

    void update(UpdateProductImageRequest updateProductImageRequest);

    List<ProductImage> addMultipleProductImages(List<CreateProductImageRequest> createProductImageRequests);

}
