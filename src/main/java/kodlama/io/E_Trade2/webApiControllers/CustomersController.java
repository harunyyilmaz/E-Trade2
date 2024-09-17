package kodlama.io.E_Trade2.webApiControllers;

import jakarta.validation.Valid;
import kodlama.io.E_Trade2.business.abstracts.CustomerService;
import kodlama.io.E_Trade2.dtos.requests.CreateCustomerRequest;
import kodlama.io.E_Trade2.dtos.requests.UpdateCustomerRequest;
import kodlama.io.E_Trade2.dtos.responses.GetAllCustomersResponse;
import kodlama.io.E_Trade2.dtos.responses.GetByIdCustomersResponse;
import kodlama.io.E_Trade2.dtos.responses.GetByIdProductResponse;
import kodlama.io.E_Trade2.entities.concretes.Product;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/customers")
@AllArgsConstructor
public class CustomersController {

    private CustomerService customerService;

    @GetMapping
    public Set<GetAllCustomersResponse> getAll() {
        return this.customerService.getAll();
    }

    @GetMapping("/{id}")
    public GetByIdCustomersResponse getById(@PathVariable Long id) {
        return this.customerService.getById(id);
    }

    @PostMapping
    public void add(@RequestBody @Valid CreateCustomerRequest createCustomerRequest) {
        this.customerService.add(createCustomerRequest);
    }

    @PutMapping
    public void update(@RequestBody @Valid UpdateCustomerRequest updateCustomerRequest) {
        this.customerService.update(updateCustomerRequest);
    }

    @DeleteMapping
    public void delete(@PathVariable Long id) {
        this.customerService.delete(id);
    }

    @PutMapping("/{customerId}/favorites")
    public ResponseEntity<Void> updateProductFavorites(@PathVariable Long customerId, @RequestBody List<Long> productIds) {
        this.customerService.updateFavoriteProducts(customerId, productIds);
        return ResponseEntity.noContent().build();
    }
}
