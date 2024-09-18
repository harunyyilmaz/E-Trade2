package kodlama.io.E_Trade2.business.rules;

import kodlama.io.E_Trade2.core.utilities.exceptions.BusinessException;
import kodlama.io.E_Trade2.dataBase.abstracts.AddressRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AddressBusinessRules {

    private AddressRepository addressRepository;

    public void checkIfPostalCodeAddressExists(String postalCode) {
        if (this.addressRepository.existsByPostalCode(postalCode)) {
            throw new BusinessException("Street address already exists");
        }
    }

    public void checkIfAddressExists(Long id) {
        if (!this.addressRepository.existsById(id)) {
            throw new BusinessException("Address does not exist");
        }
    }

    public void validateCountryAndCity(String country, String city) {
        //Ülkenin veritabanin mevcut olup olmadigini kontrol eder.
        if (this.addressRepository.existsByCountry(country)) {
            throw new BusinessException("Country does not exist in the database");
        }
        // sadece şehir adı değil, şehrin ilgili ülkeye ait olup olmadığı da kontrol edilir.
        if (this.addressRepository.existsByCityAndCountry(city, country)) {
            throw new BusinessException("City does not exist in the database for country");
        }
    }
    /*
    validateCountryAndCity: Eğer bu metodun amacı, verilen ülke ve şehrin geçerliliğini kontrol etmekse
    (yani, bir işlem gerçekleştirilmeden önce bu değerlerin doğruluğunu sağlamaksa), validate kullanmak
    daha uygun olur.

checkIfCountryAndCityExist: Eğer bu metod, sadece ülkenin ve şehrin var olup olmadığını
 kontrol ediyorsa ve bu kontrol, başka bir işlem için bir ön koşul değilse, checkIf kullanmak
  daha anlamlı olabilir.
     */

}
