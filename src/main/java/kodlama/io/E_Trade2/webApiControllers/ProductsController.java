package kodlama.io.E_Trade2.webApiControllers;

import jakarta.validation.Valid;
import kodlama.io.E_Trade2.business.abstracts.ProductService;
import kodlama.io.E_Trade2.dtos.requests.CreateProductRequest;
import kodlama.io.E_Trade2.dtos.requests.UpdateProductRequest;
import kodlama.io.E_Trade2.dtos.responses.GetAllProductResponse;
import kodlama.io.E_Trade2.dtos.responses.GetByIdProductResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/api/products")
@AllArgsConstructor
public class ProductsController {

    private ProductService productService;

    @GetMapping
    public Set<GetAllProductResponse> getAll() {
        return this.productService.getAll();
    }

    @GetMapping("/{id}")
    public GetByIdProductResponse getById(@PathVariable Long id) {
        return this.productService.getById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void add(@RequestBody @Valid CreateProductRequest createProductRequest) {
        this.productService.add(createProductRequest);
    }

    @PutMapping
    public void update(@RequestBody @Valid UpdateProductRequest updateProductRequest) {
        this.productService.update(updateProductRequest);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        this.productService.delete(id);
    }
}
