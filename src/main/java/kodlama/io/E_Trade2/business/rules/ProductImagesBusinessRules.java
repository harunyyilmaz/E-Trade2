package kodlama.io.E_Trade2.business.rules;

import kodlama.io.E_Trade2.core.utilities.exceptions.BusinessException;
import kodlama.io.E_Trade2.dataBase.abstracts.ProductImageRepository;
import kodlama.io.E_Trade2.dataBase.abstracts.ProductsRepository;
import kodlama.io.E_Trade2.dtos.requests.CreateProductImageRequest;
import kodlama.io.E_Trade2.dtos.requests.UpdateProductImageRequest;
import kodlama.io.E_Trade2.entities.concretes.Product;
import kodlama.io.E_Trade2.entities.concretes.ProductImage;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ProductImagesBusinessRules {

    private ProductImageRepository productImageRepository;
    private ProductsRepository productsRepository;

    // productId'ye ait bir resim olup olmadığını kontrol eder.
    //Eğer resim varsa, bir BusinessException fırlatarak işlem durdurulur.
    public void checkIfProductImagesIdExists(Long productId) {
        if (productImageRepository.existsByProductId(productId)) {
            throw new BusinessException("This product already has an associated image");
        }
    }

    // Ürün ID'sini doğrular ve varsa ürünü döndürür, aksi takdirde iş kuralları hatası fırlatır.
    public void validateAndGetProduct(Long productId) {
         this.productsRepository.findById(productId)
                .orElseThrow(() -> new BusinessException("Product not found with ID "));
    }

    // Ürün adını doğrular ve varsa ürünü döndürür, aksi takdirde iş kuralları hatası fırlatır.
    public void validateAndGetProductByName(String productName) {
        productsRepository.findByName(productName)
                .orElseThrow(() -> new BusinessException("Product not found with name "));
    }

    // Güncelleme isteğinin geçerliliğini kontrol eder. ID'nin var olup olmadığını kontrol eder ve gerekirse iş kuralları hatası fırlatır.
    public void validateUpdateRequest(UpdateProductImageRequest updateProductImageRequest) {
        if (updateProductImageRequest.getId() == null) {
            throw new BusinessException("ID is required for update");
        }
    }

    //Resim boyutu
    public void validateImageSize(Long size) {
        Long maxSize = 5 * 1024 * 1024L;
        if (size > maxSize) {
            throw new BusinessException("Image size exceeds the maximum allowed size of 5MB");
        }
    }

    public void checkIfImageActive(ProductImage productImage) {
        if (!productImage.getIsActive()) {
            throw new BusinessException("Product image is not active");
        }
    }

    //Görselin genislik ve yükseklik degerlerini dogrulama
    public void validateImageDimensions(Integer width, Integer height) {
        if (width <= 0 || height <= 0) {
            throw new BusinessException("Image dimensions must be positive");
        }
    }
}
