package kodlama.io.E_Trade2.business.concretes;

import kodlama.io.E_Trade2.business.abstracts.CustomerService;
import kodlama.io.E_Trade2.business.rules.CustomerBusinessRules;
import kodlama.io.E_Trade2.core.utilities.exceptions.BusinessException;
import kodlama.io.E_Trade2.core.utilities.mappers.ModelMapperService;
import kodlama.io.E_Trade2.dataBase.abstracts.CustomersRepository;
import kodlama.io.E_Trade2.dataBase.abstracts.ProductsRepository;
import kodlama.io.E_Trade2.dtos.requests.CreateCustomerRequest;
import kodlama.io.E_Trade2.dtos.requests.UpdateCustomerRequest;
import kodlama.io.E_Trade2.dtos.responses.GetAllCustomersResponse;
import kodlama.io.E_Trade2.dtos.responses.GetByIdCustomersResponse;
import kodlama.io.E_Trade2.entities.concretes.Customer;
import kodlama.io.E_Trade2.entities.concretes.Product;
import kodlama.io.E_Trade2.entities.concretes.FavoriteProduct;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CustomerManager implements CustomerService {

    private CustomersRepository customersRepository;
    private ModelMapperService modelMapperService;
    private CustomerBusinessRules customerBusinessRules;
    private ProductsRepository productsRepository;

    @Override
    public Set<GetAllCustomersResponse> getAll() {
        List<Customer> customers = this.customersRepository.findAll();
        Set<GetAllCustomersResponse> getAllCustomersResponses = customers.stream()
                .map(customer -> this.modelMapperService.forResponse()
                        .map(customer, GetAllCustomersResponse.class)).collect(Collectors.toSet());
        return getAllCustomersResponses;
    }

    @Override
    public GetByIdCustomersResponse getById(Long id) {
        Customer customer = this.customersRepository.findById(id).orElseThrow();
        GetByIdCustomersResponse getByIdCustomersResponse = this.modelMapperService.forResponse()
                .map(customer,GetByIdCustomersResponse.class);
        return getByIdCustomersResponse;
    }

    @Override
    public void add(CreateCustomerRequest createCustomerRequest) {

        this.customerBusinessRules.checkIfCustomerUserNameExists(createCustomerRequest.getUserName());

        Customer customer = this.modelMapperService.forRequest().map(createCustomerRequest,Customer.class);
        this.customersRepository.save(customer);
    }

    @Override
    public void update(UpdateCustomerRequest updateCustomerRequest) {
        Customer customer = this.modelMapperService.forRequest().map(updateCustomerRequest,Customer.class);
        this.customersRepository.save(customer);

    }

    @Override
    public void delete(Long id) {
        this.customersRepository.deleteById(id);
    }

    @Override
    public void updateFavoriteProducts(Long customerId, List<Long> productsIds) {
        //Müsteri ve ürünler yüklendi.
        Customer customer = this.customersRepository.findById(customerId)
                .orElseThrow(() -> new BusinessException("Customer not found with ID"));

        List<Product> products = this.productsRepository.findAllById(productsIds);

        //ID ye göre getirdigimiz müsterinin mevcut favorilerini temizledik.
        customer.getProductFavorites().clear();

        //Müsterinin favori ürünlerini güncelle
        for (Product product : products) {
            FavoriteProduct favoriteProduct = new FavoriteProduct();
            favoriteProduct.setCustomer(customer);
            favoriteProduct.setProduct(product);
            customer.getProductFavorites().add(favoriteProduct);
        }
        this.customersRepository.save(customer);
    }
}
