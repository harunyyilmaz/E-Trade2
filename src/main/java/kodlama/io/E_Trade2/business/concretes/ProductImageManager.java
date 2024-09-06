package kodlama.io.E_Trade2.business.concretes;

import kodlama.io.E_Trade2.business.abstracts.ProductImageService;
import kodlama.io.E_Trade2.business.rules.ProductImagesBusinessRules;
import kodlama.io.E_Trade2.core.utilities.exceptions.BusinessException;
import kodlama.io.E_Trade2.core.utilities.mappers.ModelMapperService;
import kodlama.io.E_Trade2.dataBase.abstracts.ProductImageRepository;
import kodlama.io.E_Trade2.dataBase.abstracts.ProductsRepository;
import kodlama.io.E_Trade2.dtos.requests.CreateProductImageRequest;
import kodlama.io.E_Trade2.dtos.requests.UpdateProductImageRequest;
import kodlama.io.E_Trade2.dtos.responses.GetByIdProductImageResponse;
import kodlama.io.E_Trade2.entities.concretes.Product;
import kodlama.io.E_Trade2.entities.concretes.ProductImage;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ProductImageManager implements ProductImageService {

    private ProductImageRepository productImageRepository;
    private ModelMapperService modelMapperService;
    private ProductsRepository productsRepository;
    private ProductImagesBusinessRules productImagesBusinessRules;

    @Override
    public ProductImage addProductImage(CreateProductImageRequest createProductImageRequest) {

        //Asagidaki businessrules=Yeni bir ürün resmi eklerken, ürünün geçerli olup olmadığını
        // kontrol etmek için kullanılabi
        this.productImagesBusinessRules.validateAndGetProduct(createProductImageRequest.getProductId());

        ProductImage productImage = new ProductImage();
        productImage.setUrl(createProductImageRequest.getUrl());
        productImage.setThumbnailUrl(createProductImageRequest.getThumbnailUrl());
        productImage.setWidth(createProductImageRequest.getWidth());
        productImage.setHeight(createProductImageRequest.getHeight());
        productImage.setTitle(createProductImageRequest.getTitle());
        productImage.setAltText(createProductImageRequest.getAltText());
        productImage.setSize(createProductImageRequest.getSize());
        productImage.setFormat(createProductImageRequest.getFormat());
        productImage.setIsActive(createProductImageRequest.getIsActive());
        Product product = this.productsRepository.findById(createProductImageRequest.getProductId())
                .orElseThrow(() -> new BusinessException("Product not found with ID"));
        productImage.setProduct(product);

        this.productImageRepository.save(productImage);
        return productImage;
    }


    @Override
    public List<ProductImage> addMultipleProductImages(List<CreateProductImageRequest> createProductImageRequests) {
        List<ProductImage> productImages = new ArrayList<>();

        for (CreateProductImageRequest request : createProductImageRequests) {
            ProductImage productImage = addProductImage(request);
            productImages.add(productImage);
        }

        return productImages;
    }


    @Override
    public ProductImage updateProductImage(Long id, UpdateProductImageRequest updateProductImageRequest) {

//Ürün adını doğrulamak için kullanılabilir. (Ürün adını güncelleme sırasında kullanıyorsan.)
        this.productImagesBusinessRules.validateAndGetProductByName(updateProductImageRequest.getProductName());

        //Güncelleme isteginin gecerliligini kontrol etmek icin
        this.productImagesBusinessRules.validateUpdateRequest(updateProductImageRequest);


        ProductImage productImage = this.productImageRepository.findById(id)
                .orElseThrow(() -> new BusinessException("ProductImage not found"));
        /*NNNOTTTTTT:
        productImage.setUrl(updateProductImageRequest.getUrl()); Bu sekilde devam edersek her seferinde tüm alanlari güncellemek gerekecektir.

         */
        Optional.ofNullable(updateProductImageRequest.getUrl()).ifPresent(productImage::setUrl);
        Optional.ofNullable(updateProductImageRequest.getThumbnailUrl()).ifPresent(productImage::setThumbnailUrl);
        Optional.ofNullable(updateProductImageRequest.getTitle()).ifPresent(productImage::setTitle);
        Optional.ofNullable(updateProductImageRequest.getAltText()).ifPresent(productImage::setAltText);
        Optional.ofNullable(updateProductImageRequest.getWidth()).ifPresent(productImage::setWidth);
        Optional.ofNullable(updateProductImageRequest.getHeight()).ifPresent(productImage::setHeight);
        Optional.ofNullable(updateProductImageRequest.getId()).ifPresent(productImage::setId);
        Optional.ofNullable(updateProductImageRequest.getSize()).ifPresent(productImage::setSize);
        Optional.ofNullable(updateProductImageRequest.getFormat()).ifPresent(productImage::setFormat);
        Optional.ofNullable(updateProductImageRequest.getIsActive()).ifPresent(productImage::setIsActive);
        Optional.ofNullable(updateProductImageRequest.getProductName()).ifPresent(productName -> {
            Product product = this.productsRepository.findByName(productName)
                    .orElseThrow(() -> new BusinessException("Product not found with name"));
            productImage.setProduct(product);
            productImageRepository.save(productImage);
        });
        return productImage;
    }

    @Override
    public void deleteProductImage(Long id) {
        this.productImagesBusinessRules.checkIfProductImagesIdExists(id);
        this.productImageRepository.deleteById(id);
    }

    @Override
    public Optional<ProductImage> getProductImageById(Long id) {
        Optional<ProductImage> productImage = this.productImageRepository.findById(id);
        return productImage;
    }

    @Override
    public Optional<GetByIdProductImageResponse> getById(Long id) {


        ProductImage productImage = this.productImageRepository.findById(id)
                .orElseThrow(() -> new BusinessException("ProductImage not found with ID" + id));

        GetByIdProductImageResponse getByIdProductImageResponse = new GetByIdProductImageResponse();
        getByIdProductImageResponse.setId(productImage.getId());
        getByIdProductImageResponse.setUrl(productImage.getUrl());
        getByIdProductImageResponse.setThumbnailUrl(productImage.getThumbnailUrl());
        getByIdProductImageResponse.setUploadDate(productImage.getUploadDate());
        getByIdProductImageResponse.setTitle(productImage.getTitle());
        getByIdProductImageResponse.setAltText(productImage.getAltText());
        getByIdProductImageResponse.setWidth(productImage.getWidth());
        getByIdProductImageResponse.setHeight(productImage.getHeight());
        getByIdProductImageResponse.setSize(productImage.getSize());
        getByIdProductImageResponse.setFormat(productImage.getFormat());
        getByIdProductImageResponse.setIsActive(productImage.getIsActive());
        getByIdProductImageResponse.setProductId(productImage.getProduct().getId());

        return Optional.of(getByIdProductImageResponse);
    }


    @Override
    public List<ProductImage> getProductImagesByProductId(Long productId) {
        List<ProductImage> productImages = this.productImageRepository.findByProductId(productId);
        return productImages;
    }

    @Override
    public List<ProductImage> getAllActiveProductImages() {
        return this.productImageRepository.findByIsActiveTrue();
    }

    @Override
    public Optional<ProductImage> getProductImageByUrl(String url) {
        return this.productImageRepository.findByUrl(url);
    }

    @Override
    public List<ProductImage> getProductImagesByUploadDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        return this.productImageRepository.findByUploadDateBetween(startDate, endDate);
    }

    @Override
    public void update(UpdateProductImageRequest updateProductImageRequest) {
        ProductImage productImage = new ProductImage();
        productImage.setId(updateProductImageRequest.getId());
        productImage.setUrl(updateProductImageRequest.getUrl());
        productImage.setThumbnailUrl(updateProductImageRequest.getThumbnailUrl());
        productImage.setTitle(updateProductImageRequest.getTitle());
        productImage.setAltText(updateProductImageRequest.getAltText());
        productImage.setWidth(updateProductImageRequest.getWidth());
        productImage.setHeight(updateProductImageRequest.getHeight());
        productImage.setSize(updateProductImageRequest.getSize());
        productImage.setFormat(updateProductImageRequest.getFormat());
        productImage.setIsActive(updateProductImageRequest.getIsActive());

        Product product = this.productsRepository.findById(updateProductImageRequest.getId())
                .orElseThrow(() -> new BusinessException("Product not found ID"));
        productImage.setProduct(product);

        productImageRepository.save(productImage);

    }


}
