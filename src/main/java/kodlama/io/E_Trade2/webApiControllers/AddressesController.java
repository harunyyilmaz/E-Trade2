package kodlama.io.E_Trade2.webApiControllers;

import jakarta.validation.Valid;
import kodlama.io.E_Trade2.business.abstracts.AddressService;
import kodlama.io.E_Trade2.dtos.requests.CreateAddressRequest;
import kodlama.io.E_Trade2.dtos.requests.UpdateAddressRequest;
import kodlama.io.E_Trade2.dtos.responses.GetAllAddressResponse;
import kodlama.io.E_Trade2.dtos.responses.GetByIdAddressResponse;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/addresses")
@AllArgsConstructor
public class AddressesController {

    private AddressService addressService;

    @GetMapping
    public List<GetAllAddressResponse> getAll() {
        return this.addressService.getAll();
    }

    @GetMapping("/{id}")
    public GetByIdAddressResponse getById(@PathVariable Long id) {
        return this.addressService.getById(id);
    }
    @PostMapping
    public void add(@Valid @RequestBody CreateAddressRequest createAddressRequest) {
        this.addressService.add(createAddressRequest);
    }

    @PutMapping("/{id}")
    public void update(@PathVariable Long customerId, @RequestBody @Valid UpdateAddressRequest updateAddressRequest) {
        this.addressService.update(customerId,updateAddressRequest);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){
        this.addressService.delete(id);
    }

    @GetMapping("/getByCustomerId")
    public List<GetAllAddressResponse> getByCustomerId(@PathVariable Long customerId){
        return this.addressService.getByCustomerId(customerId);
    }

    @GetMapping("city")
    public List<GetAllAddressResponse> findByCity(@RequestParam String city){
        return this.addressService.findByCity(city);
    }
}
