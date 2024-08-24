package kodlama.io.E_Trade2.webApiControllers;

import kodlama.io.E_Trade2.business.abstracts.CustomerService;
import kodlama.io.E_Trade2.dtos.requests.CreateCustomerRequest;
import kodlama.io.E_Trade2.dtos.requests.UpdateCustomerRequest;
import kodlama.io.E_Trade2.dtos.responses.GetAllCustomersResponse;
import kodlama.io.E_Trade2.dtos.responses.GetByIdCustomersResponse;
import kodlama.io.E_Trade2.dtos.responses.GetByIdProductResponse;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("api/customers")
@AllArgsConstructor
public class CustomersController {

    private CustomerService customerService;

    @GetMapping
    public Set<GetAllCustomersResponse> getAll(){
        return this.customerService.getAll();
    }
    @GetMapping("/{id}")
    public GetByIdCustomersResponse getById(Long id){
        return this.customerService.getById(id);
    }
    @PostMapping
    public void add(CreateCustomerRequest createCustomerRequest){
        this.customerService.add(createCustomerRequest);
    }
    @PutMapping
    public void update(UpdateCustomerRequest updateCustomerRequest){
        this.customerService.update(updateCustomerRequest);
    }
    @DeleteMapping
    public void delete(Long id){
        this.customerService.delete(id);
    }
}
