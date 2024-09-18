package kodlama.io.E_Trade2.business.rules;

import kodlama.io.E_Trade2.core.utilities.exceptions.BusinessException;
import kodlama.io.E_Trade2.dataBase.abstracts.BrandRepository;
import kodlama.io.E_Trade2.dtos.requests.UpdateBrandRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class BrandBusinessRules {

    private BrandRepository brandRepository;

    public void checkIfBrandNameExists(String brandName) {
        if (this.brandRepository.existsByName(brandName)) {
            throw new BusinessException("Brand name already exists");
        }
    }

    public void validateUpdateRequest(UpdateBrandRequest updateBrandRequest) {
        if (updateBrandRequest.getId() == null) {
            throw new BusinessException("Id is required for update");
        }
    }

    public  void checkIfBrandExists(Long brandId){
       this.brandRepository.findById(brandId)
               .orElseThrow(()-> new BusinessException("Brand not found with id"));
    }

    // Örnek: Telefon numarasının 10 haneli olmasını kontrol et
    public void checkIfPhoneNumberIsValid(String phoneNumber) {
        if (phoneNumber == null || !phoneNumber.matches("\\d{10}")) {
            throw new BusinessException("Invalid phone number format");
        }
    }

}
