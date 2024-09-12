package kodlama.io.E_Trade2.business.abstracts;

import kodlama.io.E_Trade2.dtos.requests.CreateBrandRequest;
import kodlama.io.E_Trade2.dtos.requests.UpdateBrandRequest;
import kodlama.io.E_Trade2.dtos.responses.GetAllBrandsResponse;
import kodlama.io.E_Trade2.dtos.responses.GetByIdBrandResponse;

import java.util.List;

public interface BrandService {

    List<GetAllBrandsResponse> getAll();

    GetByIdBrandResponse getById(Long id);

    void add(CreateBrandRequest createBrandRequest);

    void update(UpdateBrandRequest updateBrandRequest);

    void delete(Long id);
}
