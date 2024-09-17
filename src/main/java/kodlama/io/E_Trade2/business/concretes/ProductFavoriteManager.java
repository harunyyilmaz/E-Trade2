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
import kodlama.io.E_Trade2.entities.concretes.FavoriteProduct;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
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
        List<FavoriteProduct> productFavorites = this.productFavoriteRepository.findAll();
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

        List<FavoriteProduct> productFavorites = this.productFavoriteRepository.findByCustomerId(customerId);

        List<GetAllProductFavoriteResponse> responses = productFavorites.stream()
                .map(productFavorite -> this.modelMapperService.forResponse()
                        .map(productFavorite, GetAllProductFavoriteResponse.class))
                .collect(Collectors.toList());
        return responses;
    }

    @Override
    public GetByIdProductFavoriteResponse getById(Long id) {

        FavoriteProduct favoriteProduct = this.productFavoriteRepository.findById(id).orElseThrow();

        GetByIdProductFavoriteResponse getByIdProductFavoriteResponse = new GetByIdProductFavoriteResponse();
        getByIdProductFavoriteResponse.setProductId(favoriteProduct.getId());
        getByIdProductFavoriteResponse.setCustomerName(favoriteProduct.getCustomer().getFirstName());
        getByIdProductFavoriteResponse.setCustomerId(favoriteProduct.getCustomer().getId());
        getByIdProductFavoriteResponse.setProductName(favoriteProduct.getProduct().getName());
        getByIdProductFavoriteResponse.setProductId(favoriteProduct.getProduct().getId());

        return getByIdProductFavoriteResponse;
    }

    @Override
    public void add(CreateProductFavoriteRequest createProductFavoriteRequest) {

        Product product = this.productsRepository.findById(createProductFavoriteRequest.getProductId())
                .orElseThrow(() -> new EntityNotFoundException("Product not found with id"));

        Customer customer = this.customersRepository.findById(createProductFavoriteRequest.getCustomerId())
                .orElseThrow(() -> new EntityNotFoundException("Customer not found with id"));

        FavoriteProduct favoriteProduct = new FavoriteProduct();
        favoriteProduct.setProduct(product);
        favoriteProduct.setCustomer(customer);

        this.productFavoriteRepository.save(favoriteProduct);

    }


    @Override
    public void update(UpdateProductFavoriteRequest updateProductFavoriteRequest) {

        Product product = this.productsRepository.findById(updateProductFavoriteRequest.getCustomerId())
                .orElseThrow(() -> new EntityNotFoundException("product not found with id"));

        Customer customer = this.customersRepository.findById(updateProductFavoriteRequest.getCustomerId())
                .orElseThrow(() -> new EntityNotFoundException("customer not found with id"));

        FavoriteProduct favoriteProduct = new FavoriteProduct();
        favoriteProduct.setCustomer(customer);
        favoriteProduct.setProduct(product);

        this.productFavoriteRepository.save(favoriteProduct);

    }

    @Override
    public void delete(Long id) {
        this.productFavoriteRepository.deleteById(id);
    }
}
