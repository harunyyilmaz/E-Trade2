package kodlama.io.E_Trade2.business.rules;

import kodlama.io.E_Trade2.core.utilities.exceptions.BusinessException;
import kodlama.io.E_Trade2.dataBase.abstracts.CustomersRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class CustomerBusinessRules {

    private CustomersRepository customersRepository;

    public void checkIfCustomerUserNameExists(String name){
        if (this.customersRepository.existsByUserName(name)){
            throw new BusinessException("Customer username already exists");
        }
    }
}
