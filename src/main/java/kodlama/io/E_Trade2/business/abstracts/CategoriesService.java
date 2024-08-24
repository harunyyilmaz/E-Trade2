package kodlama.io.E_Trade2.business.abstracts;

import kodlama.io.E_Trade2.dtos.requests.CreateCategoriesRequest;
import kodlama.io.E_Trade2.dtos.requests.UpdateCategoriesRequest;
import kodlama.io.E_Trade2.dtos.responses.GetAllCategoriesResponse;
import kodlama.io.E_Trade2.dtos.responses.GetByIdCategoriesResponse;

import java.util.Set;

public interface CategoriesService {

    Set<GetAllCategoriesResponse> getAll();
    GetByIdCategoriesResponse getById(Long id);
    void add(CreateCategoriesRequest createCategoriesRequest);
    void update(UpdateCategoriesRequest updateCategoriesRequest);
    void delete(Long id);
}
