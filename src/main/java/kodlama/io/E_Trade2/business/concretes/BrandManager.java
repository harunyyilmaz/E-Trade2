package kodlama.io.E_Trade2.business.concretes;

import kodlama.io.E_Trade2.business.abstracts.BrandService;
import kodlama.io.E_Trade2.core.utilities.mappers.ModelMapperService;
import kodlama.io.E_Trade2.dataBase.abstracts.BrandRepository;
import kodlama.io.E_Trade2.dtos.requests.CreateBrandRequest;
import kodlama.io.E_Trade2.dtos.requests.UpdateBrandRequest;
import kodlama.io.E_Trade2.dtos.responses.GetAllBrandsResponse;
import kodlama.io.E_Trade2.dtos.responses.GetAllProductResponse;
import kodlama.io.E_Trade2.dtos.responses.GetByIdBrandResponse;
import kodlama.io.E_Trade2.entities.concretes.Brand;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class BrandManager implements BrandService {

    private BrandRepository brandRepository;
    private ModelMapperService modelMapperService;

    @Override
    public Set<GetAllBrandsResponse> getAll() {

        List<Brand> brands= this.brandRepository.findAll();
        Set<GetAllBrandsResponse> getAllProductResponses = brands.stream()
                .map(brand -> {
                    GetAllBrandsResponse getAllBrandsResponse = new GetAllBrandsResponse();
                    getAllBrandsResponse.setName(brand.getName());
                    getAllBrandsResponse.setId(brand.getId());
                    getAllBrandsResponse.setCountry(brand.getCountry());
                    getAllBrandsResponse.setDescription(brand.getDescription());
                    getAllBrandsResponse.setContactEmail(brand.getContactEmail());
                    getAllBrandsResponse.setCreateAt(brand.getCreateAt());
                    getAllBrandsResponse.setPhoneNumber(brand.getPhoneNumber());
                    getAllBrandsResponse.setUpdateDate(brand.getUpdateDate());

                    if (brand.getProducts()!=null && !brand.getProducts().isEmpty()){
                        getAllBrandsResponse.setProductId(brand.getProducts().get(0).getId());
                    }//Bu tür kontrol yapma zorunluluğu, Java'nın null güvenliği ile ilgilidir
                    // ve boş (veya null) bir listeyle çalışırken NullPointerException gibi
                    // hataları önlemeyi amaçlar.
                    return getAllBrandsResponse;
                }).collect(Collectors.toSet());

        return getAllProductResponses;
    }

    @Override
    public GetByIdBrandResponse getById(Long id) {
        return null;
    }

    @Override
    public void add(CreateBrandRequest createBrandRequest) {

    }

    @Override
    public void update(UpdateBrandRequest updateBrandRequest) {

    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public void addProductToBrand(Long brandId, CreateBrandRequest createBrandRequest) {

    }

    @Override
    public void removeProductFromBrand(Long brandId, Long productId) {

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
