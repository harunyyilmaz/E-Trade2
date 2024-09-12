package kodlama.io.E_Trade2.business.abstracts;

import kodlama.io.E_Trade2.dtos.requests.CreateBrandRequest;
import kodlama.io.E_Trade2.dtos.requests.UpdateBrandRequest;
import kodlama.io.E_Trade2.dtos.responses.GetAllBrandsResponse;
import kodlama.io.E_Trade2.dtos.responses.GetAllProductResponse;
import kodlama.io.E_Trade2.dtos.responses.GetByIdBrandResponse;

import java.util.List;
import java.util.Set;

public interface BrandService {

    Set<GetAllBrandsResponse> getAll();

    GetByIdBrandResponse getById(Long id);

    void add(CreateBrandRequest createBrandRequest);

    void update(UpdateBrandRequest updateBrandRequest);

    void delete(Long id);

    void addProductToBrand(Long brandId, CreateBrandRequest createBrandRequest);

    void removeProductFromBrand(Long brandId, Long productId);

    List<GetAllProductResponse> getProductsByBrandId(Long brandId);

    List<GetAllProductResponse> getProductsByBrandName(String brandName);

}
