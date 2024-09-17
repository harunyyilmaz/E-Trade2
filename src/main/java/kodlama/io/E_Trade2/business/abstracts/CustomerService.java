package kodlama.io.E_Trade2.business.abstracts;

import kodlama.io.E_Trade2.dtos.requests.CreateCustomerRequest;
import kodlama.io.E_Trade2.dtos.requests.UpdateCustomerRequest;
import kodlama.io.E_Trade2.dtos.responses.GetAllCustomersResponse;
import kodlama.io.E_Trade2.dtos.responses.GetByIdCustomersResponse;

import java.util.List;
import java.util.Set;

public interface CustomerService {

    Set<GetAllCustomersResponse> getAll();

    GetByIdCustomersResponse getById(Long id);

    void add(CreateCustomerRequest createCustomerRequest);

    void update(UpdateCustomerRequest updateCustomerRequest);

    void delete(Long id);

    void updateFavoriteProducts(Long customerId, List<Long> productsIds);
}
