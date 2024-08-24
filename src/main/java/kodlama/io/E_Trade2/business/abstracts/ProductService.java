package kodlama.io.E_Trade2.business.abstracts;

import kodlama.io.E_Trade2.dtos.requests.CreateProductRequest;
import kodlama.io.E_Trade2.dtos.requests.UpdateProductRequest;
import kodlama.io.E_Trade2.dtos.responses.GetAllProductResponse;
import kodlama.io.E_Trade2.dtos.responses.GetByIdProductResponse;

import java.util.List;
import java.util.Set;

public interface ProductService{

    Set<GetAllProductResponse> getAll();
    List<GetAllProductResponse> getProductsByPriceRange(double minPrice , double maxPrice);
    GetByIdProductResponse getById(Long id);
    void add(CreateProductRequest createProductRequest);
    void update(UpdateProductRequest updateProductRequest);
    void delete(Long id);
}
