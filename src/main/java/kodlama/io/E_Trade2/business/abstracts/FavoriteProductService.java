package kodlama.io.E_Trade2.business.abstracts;

import kodlama.io.E_Trade2.dtos.requests.CreateFavoriteProductRequest;
import kodlama.io.E_Trade2.dtos.requests.UpdateFavoriteProductRequest;
import kodlama.io.E_Trade2.dtos.responses.GetAllFavoriteProductResponse;
import kodlama.io.E_Trade2.dtos.responses.GetByIdFavoriteProductResponse;

import java.util.List;


public interface FavoriteProductService {

    List<GetAllFavoriteProductResponse> getAll();
    List<GetAllFavoriteProductResponse> getAllCustomerById(Long customerId);
    GetByIdFavoriteProductResponse getById(Long id);
    void add(CreateFavoriteProductRequest createFavoriteProductRequest);
    void update(UpdateFavoriteProductRequest updateFavoriteProductRequest);
    void delete(Long id);
}
