package kodlama.io.E_Trade2.business.abstracts;

import kodlama.io.E_Trade2.dataBase.abstracts.CategoriesRepository;
import kodlama.io.E_Trade2.dtos.requests.CreateProductFavoriteRequest;
import kodlama.io.E_Trade2.dtos.requests.UpdateAddressRequest;
import kodlama.io.E_Trade2.dtos.requests.UpdateProductFavoriteRequest;
import kodlama.io.E_Trade2.dtos.responses.GetAllProductFavoriteResponse;
import kodlama.io.E_Trade2.dtos.responses.GetByIdProductFavoriteResponse;

import java.util.List;


public interface ProductFavoriteService {

    List<GetAllProductFavoriteResponse> getAll();
    List<GetAllProductFavoriteResponse> getAllCustomerById(Long customerId);
    GetByIdProductFavoriteResponse getById(Long id);
    void add(CreateProductFavoriteRequest createProductFavoriteRequest);
    void update(UpdateProductFavoriteRequest updateProductFavoriteRequest);
    void delete(Long id);
}
