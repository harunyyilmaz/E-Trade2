package kodlama.io.E_Trade2.business.concretes;

import jakarta.persistence.EntityNotFoundException;
import kodlama.io.E_Trade2.business.abstracts.FavoriteProductService;
import kodlama.io.E_Trade2.business.rules.FavoriteProductBusinessRules;
import kodlama.io.E_Trade2.core.utilities.exceptions.BusinessException;
import kodlama.io.E_Trade2.core.utilities.mappers.ModelMapperService;
import kodlama.io.E_Trade2.dataBase.abstracts.CustomersRepository;
import kodlama.io.E_Trade2.dataBase.abstracts.FavoriteProductRepository;
import kodlama.io.E_Trade2.dataBase.abstracts.ProductsRepository;
import kodlama.io.E_Trade2.dtos.requests.CreateFavoriteProductRequest;
import kodlama.io.E_Trade2.dtos.requests.UpdateFavoriteProductRequest;
import kodlama.io.E_Trade2.dtos.responses.GetAllFavoriteProductResponse;
import kodlama.io.E_Trade2.dtos.responses.GetByIdFavoriteProductResponse;
import kodlama.io.E_Trade2.entities.concretes.Customer;
import kodlama.io.E_Trade2.entities.concretes.Product;
import kodlama.io.E_Trade2.entities.concretes.FavoriteProduct;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class FavoriteProductManager implements FavoriteProductService {

    private FavoriteProductRepository favoriteProductRepository;
    private CustomersRepository customersRepository;
    private ProductsRepository productsRepository;
    private ModelMapperService modelMapperService;
    private FavoriteProductBusinessRules favoriteProductBusinessRules;

    @Override
    public List<GetAllFavoriteProductResponse> getAll() {
        List<FavoriteProduct> productFavorites = this.favoriteProductRepository.findAll();
        List<GetAllFavoriteProductResponse> getAllProductFavoriteResponses = productFavorites.stream()
                .map(productFavorite -> {
                    GetAllFavoriteProductResponse response = new GetAllFavoriteProductResponse();
                    response.setId(productFavorite.getId());
                    response.setProductName(productFavorite.getProduct().getName());
                    response.setCustomerId(productFavorite.getCustomer().getId());
                    response.setCustomerName(productFavorite.getCustomer().getFirstName());
                    return response;
                }).collect(Collectors.toList());
        return getAllProductFavoriteResponses;
    }
    @Override
    public List<GetAllFavoriteProductResponse> getAllCustomerById(Long customerId) {
        Customer customer = this.customersRepository.findById(customerId)
                .orElseThrow(() -> new BusinessException("Customer not found with id"));

        List<FavoriteProduct> productFavorites = this.favoriteProductRepository.findByCustomerId(customerId);

        List<GetAllFavoriteProductResponse> responses = productFavorites.stream()
                .map(productFavorite -> this.modelMapperService.forResponse()
                        .map(productFavorite, GetAllFavoriteProductResponse.class))
                .collect(Collectors.toList());
        return responses;
    }

    @Override
    public GetByIdFavoriteProductResponse getById(Long id) {

        FavoriteProduct favoriteProduct = this.favoriteProductRepository.findById(id).orElseThrow();

        GetByIdFavoriteProductResponse getByIdFavoriteProductResponse = new GetByIdFavoriteProductResponse();
        getByIdFavoriteProductResponse.setProductId(favoriteProduct.getId());
        getByIdFavoriteProductResponse.setCustomerName(favoriteProduct.getCustomer().getFirstName());
        getByIdFavoriteProductResponse.setCustomerId(favoriteProduct.getCustomer().getId());
        getByIdFavoriteProductResponse.setProductName(favoriteProduct.getProduct().getName());
        getByIdFavoriteProductResponse.setProductId(favoriteProduct.getProduct().getId());

        return getByIdFavoriteProductResponse;
    }

    @Override
    public void add(CreateFavoriteProductRequest createFavoriteProductRequest) {

        //Ürün  zaten favorilere eklenmis mi.
        this.favoriteProductBusinessRules.checkIfProductAlreadyFavorite(
                createFavoriteProductRequest.getCustomerId(),
                createFavoriteProductRequest.getProductId()
        );

        Product product = this.productsRepository.findById(createFavoriteProductRequest.getProductId())
                .orElseThrow(() -> new EntityNotFoundException("Product not found with id"));

        Customer customer = this.customersRepository.findById(createFavoriteProductRequest.getCustomerId())
                .orElseThrow(() -> new EntityNotFoundException("Customer not found with id"));

        FavoriteProduct favoriteProduct = new FavoriteProduct();
        favoriteProduct.setProduct(product);
        favoriteProduct.setCustomer(customer);

        this.favoriteProductRepository.save(favoriteProduct);

    }


    @Override
    public void update(UpdateFavoriteProductRequest updateFavoriteProductRequest) {

        this.favoriteProductBusinessRules.checkIfFavoriteProductExits(updateFavoriteProductRequest.getProductId());

        Product product = this.productsRepository.findById(updateFavoriteProductRequest.getCustomerId())
                .orElseThrow(() -> new EntityNotFoundException("product not found with id"));

        Customer customer = this.customersRepository.findById(updateFavoriteProductRequest.getCustomerId())
                .orElseThrow(() -> new EntityNotFoundException("customer not found with id"));

        FavoriteProduct favoriteProduct = new FavoriteProduct();
        favoriteProduct.setCustomer(customer);
        favoriteProduct.setProduct(product);

        this.favoriteProductRepository.save(favoriteProduct);

    }

    @Override
    public void delete(Long id) {
        //Favori ürün var mi.
        this.favoriteProductBusinessRules.checkIfFavoriteProductExits(id);
        this.favoriteProductRepository.deleteById(id);
    }
}
