package kodlama.io.E_Trade2.webApiControllers;

import jakarta.validation.Valid;
import kodlama.io.E_Trade2.business.abstracts.ProductFavoriteService;
import kodlama.io.E_Trade2.dtos.requests.CreateProductFavoriteRequest;
import kodlama.io.E_Trade2.dtos.requests.UpdateProductFavoriteRequest;
import kodlama.io.E_Trade2.dtos.responses.GetAllProductFavoriteResponse;
import kodlama.io.E_Trade2.dtos.responses.GetByIdProductFavoriteResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping
@AllArgsConstructor
public class ProductFavoritesController {

    private ProductFavoriteService productFavoriteService;

    @GetMapping
    public List<GetAllProductFavoriteResponse> getAll() {
        return this.productFavoriteService.getAll();
    }

    @GetMapping("{id}")
    public GetByIdProductFavoriteResponse getById(@PathVariable Long id) {
        return this.productFavoriteService.getById(id);
    }

    @GetMapping("/customer/{id}")
    public List<GetAllProductFavoriteResponse> getAllCustomerById(@PathVariable Long customerId) {
        return this.productFavoriteService.getAllCustomerById(customerId);
    }

    @PostMapping
    public ResponseEntity<Void> add(@RequestBody @Valid CreateProductFavoriteRequest createProductFavoriteRequest) {
        this.productFavoriteService.add(createProductFavoriteRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();

    }

    @PutMapping
    public ResponseEntity<Void> update(@RequestBody @Valid UpdateProductFavoriteRequest updateProductFavoriteRequest){
        this.productFavoriteService.update(updateProductFavoriteRequest);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> delete(@PathVariable Long id){
        this.productFavoriteService.delete(id);
        return ResponseEntity.noContent().build();
    }
}