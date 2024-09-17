package kodlama.io.E_Trade2.webApiControllers;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.validation.Valid;
import kodlama.io.E_Trade2.business.abstracts.ProductImageService;
import kodlama.io.E_Trade2.dtos.requests.CreateProductImageRequest;
import kodlama.io.E_Trade2.dtos.requests.UpdateProductImageRequest;
import kodlama.io.E_Trade2.dtos.responses.GetByIdProductImageResponse;
import kodlama.io.E_Trade2.entities.concretes.ProductImage;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/productImages")
@AllArgsConstructor
public class ProductImagesController {

    private ProductImageService productImageService;

    @PostMapping
    public ProductImage addProductImage(@RequestBody @Valid CreateProductImageRequest createProductImageRequest) {
        return this.productImageService.addProductImage(createProductImageRequest);
    }

    @PutMapping("/{id}")
    public ProductImage updateProductImage(@PathVariable Long id, @RequestBody @Valid UpdateProductImageRequest updateProductImageRequest) {
        return this.productImageService.updateProductImage(id, updateProductImageRequest);
    }

    @DeleteMapping("/{id}")
    public void deleteProductImage(@PathVariable Long id) {
        this.productImageService.deleteProductImage(id);
    }

    @GetMapping("/{id}")
    public Optional<ProductImage> getProductImageById(@PathVariable Long id) {
        return this.productImageService.getProductImageById(id);
    }

    @GetMapping("/details/{id}")
    public Optional<GetByIdProductImageResponse> getById(@PathVariable Long id) {
        return this.productImageService.getById(id);
    }

    @GetMapping("/byProductId")
    public List<ProductImage> getProductImagesByProductId(@RequestParam Long productId) {
        return this.productImageService.getProductImagesByProductId(productId);
    }

    @GetMapping("/activeProductImages")
    public List<ProductImage> getAllActiveProductImages(){
            return this.productImageService.getAllActiveProductImages();
        }

    @GetMapping("/byUrl")
    public Optional<ProductImage> getProductImageByUrl(String url) {
        return this.productImageService.getProductImageByUrl(url);
    }

    @GetMapping("/byDateRange")
    public List<ProductImage> getProductImagesByUploadDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        return this.productImageService.getProductImagesByUploadDateRange(startDate, endDate);
    }

    @PutMapping
    public void update(@RequestBody @Valid UpdateProductImageRequest updateProductImageRequest) {
        this.productImageService.update(updateProductImageRequest);
    }

    @GetMapping("/addMultiple")
    public List<ProductImage> addMultipleProductImages(List<CreateProductImageRequest> createProductImageRequests){
        return this.productImageService.addMultipleProductImages(createProductImageRequests);
    }

}
