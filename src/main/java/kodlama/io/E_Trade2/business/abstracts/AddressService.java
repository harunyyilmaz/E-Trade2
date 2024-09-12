package kodlama.io.E_Trade2.business.abstracts;

import kodlama.io.E_Trade2.dtos.requests.CreateAddressRequest;
import kodlama.io.E_Trade2.dtos.requests.UpdateAddressRequest;
import kodlama.io.E_Trade2.dtos.responses.GetAllAddressResponse;
import kodlama.io.E_Trade2.dtos.responses.GetByIdAddressResponse;

import java.util.List;
import java.util.Set;

public interface AddressService {

    List<GetAllAddressResponse> getAll();

    GetByIdAddressResponse getById(Long id);

    void add(CreateAddressRequest createAddressRequest);

    void update(Long customerId, UpdateAddressRequest updateAddressRequest);

    void delete(Long id);

    List<GetAllAddressResponse> getByCustomerId(Long customerId);

    List<GetAllAddressResponse> findByCity(String city);
}
