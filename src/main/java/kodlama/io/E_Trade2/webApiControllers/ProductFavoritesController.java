package kodlama.io.E_Trade2.webApiControllers;

import jakarta.validation.Valid;
import kodlama.io.E_Trade2.business.abstracts.FavoriteProductService;
import kodlama.io.E_Trade2.dtos.requests.CreateFavoriteProductRequest;
import kodlama.io.E_Trade2.dtos.requests.UpdateFavoriteProductRequest;
import kodlama.io.E_Trade2.dtos.responses.GetAllFavoriteProductResponse;
import kodlama.io.E_Trade2.dtos.responses.GetByIdFavoriteProductResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping
@AllArgsConstructor
public class ProductFavoritesController {

    private FavoriteProductService favoriteProductService;

    @GetMapping
    public List<GetAllFavoriteProductResponse> getAll() {
        return this.favoriteProductService.getAll();
    }

    @GetMapping("{id}")
    public GetByIdFavoriteProductResponse getById(@PathVariable Long id) {
        return this.favoriteProductService.getById(id);
    }

    @GetMapping("/customer/{id}")
    public List<GetAllFavoriteProductResponse> getAllCustomerById(@PathVariable Long customerId) {
        return this.favoriteProductService.getAllCustomerById(customerId);
    }

    @PostMapping
    public ResponseEntity<Void> add(@RequestBody @Valid CreateFavoriteProductRequest createFavoriteProductRequest) {
        this.favoriteProductService.add(createFavoriteProductRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();

    }

    @PutMapping
    public ResponseEntity<Void> update(@RequestBody @Valid UpdateFavoriteProductRequest updateFavoriteProductRequest){
        this.favoriteProductService.update(updateFavoriteProductRequest);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> delete(@PathVariable Long id){
        this.favoriteProductService.delete(id);
        return ResponseEntity.noContent().build();
    }
}