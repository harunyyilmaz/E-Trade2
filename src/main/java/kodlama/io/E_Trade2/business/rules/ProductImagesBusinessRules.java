package kodlama.io.E_Trade2.business.rules;

import kodlama.io.E_Trade2.core.utilities.exceptions.BusinessException;
import kodlama.io.E_Trade2.dataBase.abstracts.ProductImageRepository;
import kodlama.io.E_Trade2.dataBase.abstracts.ProductsRepository;
import kodlama.io.E_Trade2.dtos.requests.UpdateProductImageRequest;
import kodlama.io.E_Trade2.entities.concretes.Product;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ProductImagesBusinessRules {

    private ProductImageRepository productImageRepository;
    private ProductsRepository productsRepository;

    // Veritabanında verilen productId'ye sahip bir ProductImage olup olmadığını kontrol eder.
    public void checkIfProductImagesIdExists(Long productId){
        if (productImageRepository.existsByProductId(productId)){
            throw new BusinessException("kadbc");
        }
    }

    // Ürün ID'sini doğrular ve varsa ürünü döndürür, aksi takdirde iş kuralları hatası fırlatır.
    public Product validateAndGetProduct(Long productId) {
        return productsRepository.findById(productId)
                .orElseThrow(() -> new BusinessException("Product not found with ID "));
    }

    // Ürün adını doğrular ve varsa ürünü döndürür, aksi takdirde iş kuralları hatası fırlatır.
    public Product validateAndGetProductByName(String productName) {
        return productsRepository.findByName(productName)
                .orElseThrow(() -> new BusinessException("Product not found with name "));
    }

    // Güncelleme isteğinin geçerliliğini kontrol eder. ID'nin var olup olmadığını kontrol eder ve gerekirse iş kuralları hatası fırlatır.
    public void validateUpdateRequest(UpdateProductImageRequest updateProductImageRequest) {
        if (updateProductImageRequest.getId() == null) {
            throw new BusinessException("ID is required for update");
        }
    }
}
