package kodlama.io.E_Trade2.business.concretes;

import jakarta.persistence.EntityNotFoundException;
import kodlama.io.E_Trade2.business.abstracts.ProductFavoriteService;
import kodlama.io.E_Trade2.core.utilities.exceptions.BusinessException;
import kodlama.io.E_Trade2.core.utilities.mappers.ModelMapperService;
import kodlama.io.E_Trade2.dataBase.abstracts.CustomersRepository;
import kodlama.io.E_Trade2.dataBase.abstracts.ProductFavoriteRepository;
import kodlama.io.E_Trade2.dataBase.abstracts.ProductsRepository;
import kodlama.io.E_Trade2.dtos.requests.CreateProductFavoriteRequest;
import kodlama.io.E_Trade2.dtos.requests.UpdateProductFavoriteRequest;
import kodlama.io.E_Trade2.dtos.responses.GetAllProductFavoriteResponse;
import kodlama.io.E_Trade2.dtos.responses.GetByIdProductFavoriteResponse;
import kodlama.io.E_Trade2.entities.concretes.Customer;
import kodlama.io.E_Trade2.entities.concretes.Product;
import kodlama.io.E_Trade2.entities.concretes.ProductFavorite;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ProductFavoriteManager implements ProductFavoriteService {

    private ProductFavoriteRepository productFavoriteRepository;
    private CustomersRepository customersRepository;
    private ProductsRepository productsRepository;
    private ModelMapperService modelMapperService;

    @Override
    public List<GetAllProductFavoriteResponse> getAll() {
        List<ProductFavorite> productFavorites = this.productFavoriteRepository.findAll();
        List<GetAllProductFavoriteResponse> getAllProductFavoriteResponses = productFavorites.stream()
                .map(productFavorite -> {
                    GetAllProductFavoriteResponse response = new GetAllProductFavoriteResponse();
                    response.setId(productFavorite.getId());
                    response.setProductName(productFavorite.getProduct().getName());
                    response.setCustomerId(productFavorite.getCustomer().getId());
                    response.setCustomerName(productFavorite.getCustomer().getFirstName());
                    return response;
                }).collect(Collectors.toList());
        return getAllProductFavoriteResponses;
    }

    @Override
    public List<GetAllProductFavoriteResponse> getAllCustomerById(Long customerId) {
        Customer customer = this.customersRepository.findById(customerId)
                .orElseThrow(() -> new BusinessException("Customer not found with id"));

        List<ProductFavorite> productFavorites = this.productFavoriteRepository.findByCustomerId(customerId);

        List<GetAllProductFavoriteResponse> responses = productFavorites.stream()
                .map(productFavorite -> this.modelMapperService.forResponse()
                        .map(productFavorite, GetAllProductFavoriteResponse.class))
                .collect(Collectors.toList());
        return responses;
    }

    @Override
    public GetByIdProductFavoriteResponse getById(Long id) {

        ProductFavorite productFavorite = this.productFavoriteRepository.findById(id).orElseThrow();

        GetByIdProductFavoriteResponse getByIdProductFavoriteResponse = new GetByIdProductFavoriteResponse();
        getByIdProductFavoriteResponse.setProductId(productFavorite.getId());
        getByIdProductFavoriteResponse.setCustomerName(productFavorite.getCustomer().getFirstName());
        getByIdProductFavoriteResponse.setCustomerId(productFavorite.getCustomer().getId());
        getByIdProductFavoriteResponse.setProductName(productFavorite.getProduct().getName());
        getByIdProductFavoriteResponse.setProductId(productFavorite.getProduct().getId());

        return getByIdProductFavoriteResponse;
    }

    @Override
    public void add(CreateProductFavoriteRequest createProductFavoriteRequest) {

        Product product = this.productsRepository.findById(createProductFavoriteRequest.getProductId())
                .orElseThrow(() -> new EntityNotFoundException("Product not found with id"));

        Customer customer = this.customersRepository.findById(createProductFavoriteRequest.getCustomerId())
                .orElseThrow(() -> new EntityNotFoundException("Customer not found with id"));

        ProductFavorite productFavorite = new ProductFavorite();
        productFavorite.setProduct(product);
        productFavorite.setCustomer(customer);

        this.productFavoriteRepository.save(productFavorite);

    }

    @Override
    public void update(UpdateProductFavoriteRequest updateProductFavoriteRequest) {

        Product product = this.productsRepository.findById(updateProductFavoriteRequest.getCustomerId())
                .orElseThrow(() -> new EntityNotFoundException("product not found with id"));

        Customer customer = this.customersRepository.findById(updateProductFavoriteRequest.getCustomerId())
                .orElseThrow(() -> new EntityNotFoundException("customer not found with id"));

        ProductFavorite productFavorite = new ProductFavorite();
        productFavorite.setCustomer(customer);
        productFavorite.setProduct(product);

        this.productFavoriteRepository.save(productFavorite);

    }

    @Override
    public void delete(Long id) {
        this.productFavoriteRepository.deleteById(id);
    }
}
