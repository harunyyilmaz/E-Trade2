package kodlama.io.E_Trade2.webApiControllers;

import jakarta.validation.Valid;
import kodlama.io.E_Trade2.business.abstracts.BrandService;
import kodlama.io.E_Trade2.dtos.requests.CreateBrandRequest;
import kodlama.io.E_Trade2.dtos.requests.UpdateBrandRequest;
import kodlama.io.E_Trade2.dtos.responses.GetAllBrandsResponse;
import kodlama.io.E_Trade2.dtos.responses.GetAllProductResponse;
import kodlama.io.E_Trade2.dtos.responses.GetByIdBrandResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/brands")
@AllArgsConstructor
public class BrandsController {

    private BrandService brandService;

    @GetMapping
    public Set<GetAllBrandsResponse> getAll() {
        return this.brandService.getAll();
    }

    @GetMapping("/{id}")
    public GetByIdBrandResponse getById(@PathVariable Long id) {
        return this.brandService.getById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Void>add(@RequestBody @Valid CreateBrandRequest createBrandRequest) {
            this.brandService.add(createBrandRequest);
            return ResponseEntity.status(HttpStatus.CREATED).build();//201 created döndürür.
    }

    @PutMapping
    public ResponseEntity<Void> update(@RequestBody @Valid UpdateBrandRequest updateBrandRequest) {
        this.brandService.update(updateBrandRequest);
        return ResponseEntity.ok().build();//200 ok döndürür.
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        this.brandService.delete(id);
        return ResponseEntity.noContent().build();//204 no content döndürür.
    }

    @GetMapping("/products/{brandId}")
    public List<GetAllProductResponse> getProductsByBrandId(@PathVariable Long brandId) {
        return this.brandService.getProductsByBrandId(brandId);
    }

    @GetMapping("/products/brandName")
    public List<GetAllProductResponse> getProductsByBrandName(@RequestParam String brandName) {
        return this.brandService.getProductsByBrandName(brandName);
    }

    @PutMapping("{brandId}/update/{productId}")
    public ResponseEntity<Void> updateProductToBrand(@PathVariable Long productId, @RequestBody @Valid UpdateBrandRequest updateBrandRequest) {
        this.brandService.updateProductToBrand(productId, updateBrandRequest);
        return ResponseEntity.ok().build();//200 OK döndürür.
    }

    @DeleteMapping("/{brandId}/remove/{productId}")
    public ResponseEntity<Void> removeProductFromBrand(@PathVariable Long brandId, @PathVariable Long productId) {
        this.brandService.removeProductFromBrand(brandId, productId);
        return ResponseEntity.noContent().build();//204 no content döndürür.
    }
}
