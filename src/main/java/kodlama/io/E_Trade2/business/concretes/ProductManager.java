package kodlama.io.E_Trade2.business.concretes;

import kodlama.io.E_Trade2.business.abstracts.ProductService;
import kodlama.io.E_Trade2.business.rules.ProductBusinessRules;
import kodlama.io.E_Trade2.core.utilities.exceptions.BusinessException;
import kodlama.io.E_Trade2.core.utilities.mappers.ModelMapperService;
import kodlama.io.E_Trade2.dataBase.abstracts.CategoriesRepository;
import kodlama.io.E_Trade2.dataBase.abstracts.CustomersRepository;
import kodlama.io.E_Trade2.dataBase.abstracts.ProductsRepository;
import kodlama.io.E_Trade2.dtos.requests.CreateProductRequest;
import kodlama.io.E_Trade2.dtos.requests.UpdateProductFavoriteRequest;
import kodlama.io.E_Trade2.dtos.requests.UpdateProductRequest;
import kodlama.io.E_Trade2.dtos.responses.GetAllProductResponse;
import kodlama.io.E_Trade2.dtos.responses.GetByIdProductResponse;
import kodlama.io.E_Trade2.entities.concretes.Category;
import kodlama.io.E_Trade2.entities.concretes.Customer;
import kodlama.io.E_Trade2.entities.concretes.Product;
import kodlama.io.E_Trade2.entities.concretes.FavoriteProduct;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ProductManager implements ProductService {
    private ProductsRepository productsRepository;
    private ModelMapperService modelMapperService;
    private ProductBusinessRules productBusinessRules;
    private CategoriesRepository categoriesRepository;
    private CustomersRepository customersRepository;

    @Override
    public Set<GetAllProductResponse> getAll() {
        List<Product> products = this.productsRepository.findAll();
        Set<GetAllProductResponse> getAllProductResponses = products.stream()
                .map(product -> this.modelMapperService.forResponse()
                        .map(product, GetAllProductResponse.class)).collect(Collectors.toSet());
        return getAllProductResponses;
    }

    @Override
    public List<GetAllProductResponse> getProductsByPriceRange(double minPrice, double maxPrice) {
        this.productBusinessRules.getProductsInPriceRange(minPrice, maxPrice);

        List<Product> products = this.productsRepository.findAll();
        List<GetAllProductResponse> getAllProductResponses = products.stream()
                .map(product -> this.modelMapperService.forResponse()
                        .map(product, GetAllProductResponse.class)).collect(Collectors.toList());
        return getAllProductResponses;
    }

    @Override
    public GetByIdProductResponse getById(Long id) {
        Product product = this.productsRepository.findById(id).orElseThrow();
        GetByIdProductResponse getByIdProductResponse = this.modelMapperService.forResponse().map(product, GetByIdProductResponse.class);
        return getByIdProductResponse;
    }

    @Override
    public void add(CreateProductRequest createProductRequest) {

        this.productBusinessRules.checkIfProductNameExists(createProductRequest.getName());

        Product product = this.modelMapperService.forRequest().map(createProductRequest, Product.class);
        this.productsRepository.save(product);
    }

    @Override
    public void update(UpdateProductRequest updateProductRequest) {
        /* ModelMapper kullandim.
        Product product = this.modelMapperService.forRequest().map(updateProductRequest,Product.class);
        this.productsRepository.save(product);
         */

        Product product = this.productsRepository.findById(updateProductRequest.getId())
                .orElseThrow(() -> new BusinessException("Product not found with ID"));

        Optional.ofNullable(updateProductRequest.getName()).ifPresent(product::setName);
        Optional.ofNullable(updateProductRequest.getDescriptions()).ifPresent(product::setDescriptions);
        Optional.of(updateProductRequest.getPrice()).filter(price -> price > 0).ifPresent(product::setPrice);
        Optional.of(updateProductRequest.getQuantity()).filter(quantity -> quantity > 0).ifPresent(product::setQuantity);

        Optional.ofNullable(updateProductRequest.getCategoryName()).ifPresent(categoryName -> {
            Category category = this.categoriesRepository.findByName(categoryName)
                    .orElseThrow(() -> new BusinessException("Category not found with name"));
            product.setCategory(category);
        });

        Optional.ofNullable(updateProductRequest.getCategoryId()).ifPresent(categoryId -> {
            Category category = this.categoriesRepository.findById(categoryId)
                    .orElseThrow(() -> new BusinessException("Category not found with id"));
            product.setCategory(category);
        });

        productsRepository.save(product);

    }

    @Override
    public void delete(Long id) {
        this.productsRepository.deleteById(id);
    }

    @Override
    public List<GetAllProductResponse> getProductsByBrandId(Long brandId) {
        return List.of();
    }

    @Override
    public List<GetAllProductResponse> getProductsByBrandName(String brandName) {
        return List.of();
    }

}
