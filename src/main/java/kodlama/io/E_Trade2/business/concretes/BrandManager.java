package kodlama.io.E_Trade2.business.concretes;

import kodlama.io.E_Trade2.business.abstracts.BrandService;
import kodlama.io.E_Trade2.core.utilities.exceptions.BusinessException;
import kodlama.io.E_Trade2.core.utilities.mappers.ModelMapperService;
import kodlama.io.E_Trade2.dataBase.abstracts.BrandRepository;
import kodlama.io.E_Trade2.dataBase.abstracts.ProductsRepository;
import kodlama.io.E_Trade2.dtos.requests.CreateBrandRequest;
import kodlama.io.E_Trade2.dtos.requests.UpdateBrandRequest;
import kodlama.io.E_Trade2.dtos.responses.GetAllBrandsResponse;
import kodlama.io.E_Trade2.dtos.responses.GetAllProductResponse;
import kodlama.io.E_Trade2.dtos.responses.GetByIdBrandResponse;
import kodlama.io.E_Trade2.entities.concretes.Brand;
import kodlama.io.E_Trade2.entities.concretes.Customer;
import kodlama.io.E_Trade2.entities.concretes.Product;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class BrandManager implements BrandService {

    private BrandRepository brandRepository;
    private ModelMapperService modelMapperService;
    private ProductsRepository productsRepository;

    @Override
    public Set<GetAllBrandsResponse> getAll() {

        List<Brand> brands = this.brandRepository.findAll();
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

                    if (brand.getProducts() != null && !brand.getProducts().isEmpty()) {
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
        Brand brand = this.brandRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Brad not found with id"));

        GetByIdBrandResponse getByIdBrandResponse = new GetByIdBrandResponse();
        getByIdBrandResponse.setName(brand.getName());
        getByIdBrandResponse.setDescription(brand.getDescription());
        getByIdBrandResponse.setCountry(brand.getCountry());
        getByIdBrandResponse.setPhoneNumber(brand.getPhoneNumber());
        getByIdBrandResponse.setContactEmail(brand.getContactEmail());
        getByIdBrandResponse.setCreateAt(brand.getCreateAt());
        getByIdBrandResponse.setUpdateDate(brand.getUpdateDate());

        if (brand.getProducts() != null && !brand.getProducts().isEmpty()) {
            getByIdBrandResponse.setProductId(brand.getProducts().get(0).getId());
        }
        return getByIdBrandResponse;
    }

    @Override
    public void add(CreateBrandRequest createBrandRequest) {

        Brand brand = new Brand();
        brand.setName(createBrandRequest.getName());
        brand.setCountry(createBrandRequest.getCountry());
        brand.setDescription(createBrandRequest.getDescription());
        brand.setPhoneNumber(createBrandRequest.getPhoneNumber());
        brand.setContactEmail(createBrandRequest.getContactEmail());

        this.brandRepository.save(brand);
    }

    @Override
    public void update(UpdateBrandRequest updateBrandRequest) {
        Brand brand = this.brandRepository.findById(updateBrandRequest.getId())
                .orElseThrow(() -> new BusinessException("Brand not found with id"));


        Optional.ofNullable(updateBrandRequest.getName()).ifPresent(brand::setName);
        Optional.ofNullable(updateBrandRequest.getDescription()).ifPresent(brand::setDescription);
        Optional.ofNullable(updateBrandRequest.getPhoneNumber()).ifPresent(brand::setPhoneNumber);
        Optional.ofNullable(updateBrandRequest.getContactEmail()).ifPresent(brand::setContactEmail);
        Optional.ofNullable(updateBrandRequest.getCountry()).ifPresent(brand::setCountry);

        this.brandRepository.save(brand);
    }

    @Override
    public void delete(Long id) {

        this.brandRepository.deleteById(id);

    }

    @Override
    public void updateProductToBrand(Long productId, UpdateBrandRequest updateBrandRequest) {

        Product product = this.productsRepository.findById(productId)
                .orElseThrow(() -> new BusinessException("Product not found with id" + productId));


        Brand brand = this.brandRepository.findById(updateBrandRequest.getId())
                .orElseThrow(() -> new BusinessException("Brand not found with id" + updateBrandRequest.getId()));

        product.setBrand(brand);

        this.productsRepository.save(product);
    }

    @Override
    public void removeProductFromBrand(Long brandId, Long productId) {
        Product product = this.productsRepository.findById(productId)
                .orElseThrow(() -> new BusinessException("Product not found with id"));

        Brand brand = this.brandRepository.findById(brandId)
                .orElseThrow(() -> new BusinessException("Brand not found with id"));

        if (brand.equals(product.getBrand())) {
            //Ürünün markasini kaldir
            product.setBrand(null);
            //Markanin ürün listesinden ürünü kaldir
            brand.getProducts().remove(product);

            this.brandRepository.save(brand);
            this.productsRepository.save(product);
        } else {
            throw new BusinessException("Product is not associated with this brand.");
        }
    }

    @Override
    public List<GetAllProductResponse> getProductsByBrandId(Long brandId) {

        Brand brand = this.brandRepository.findById(brandId)
                .orElseThrow(() -> new BusinessException("Brand not found with id" + brandId));

        List<Product> products = brand.getProducts();

        List<GetAllProductResponse> getAllProductResponses = products.stream()
                .map(product -> {
                    GetAllProductResponse getAllProductResponse = new GetAllProductResponse();
                    getAllProductResponse.setId(product.getId());
                    getAllProductResponse.setName(product.getName());
                    getAllProductResponse.setQuantity(product.getQuantity());
                    getAllProductResponse.setCreateAt(product.getCreateAt());
                    getAllProductResponse.setDescriptions(product.getDescriptions());
                    getAllProductResponse.setCategoryId(product.getCategory().getId());
                    getAllProductResponse.setCategoryName(product.getCategory().getName());
                    getAllProductResponse.setPrice(product.getPrice());
                    getAllProductResponse.setUpdateDate(product.getUpdateDate());

                    List<Long> orderIds = product.getOrderItems().stream()
                            .map(orderItem -> orderItem.getOrder().getId())
                            .distinct() // ayni siparis id lerini tekrarlamamis oluyoruz.
                            .collect(Collectors.toList());
                    getAllProductResponse.setOrderId(orderIds);

                    Set<Long> customerIds = product.getCustomers().stream()
                            .map(Customer::getId)

                            .collect(Collectors.toSet());
                    getAllProductResponse.setCustomerId(customerIds);

                    return getAllProductResponse;
                }).collect(Collectors.toList());
        return getAllProductResponses;
    }

    @Override
    public List<GetAllProductResponse> getProductsByBrandName(String brandName) {
        Brand brand = this.brandRepository.findByName(brandName)
                .orElseThrow(() -> new BusinessException("Brand not found with name"));

        List<Product> products = brand.getProducts();
        List<GetAllProductResponse> getAllProductResponses = products.stream()
                .map(product -> this.modelMapperService.forResponse().map(product,GetAllProductResponse.class))
                .collect(Collectors.toList());

        return getAllProductResponses;
    }
}
