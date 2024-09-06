package kodlama.io.E_Trade2.business.rules;

import kodlama.io.E_Trade2.dataBase.abstracts.OrderItemRepository;
import kodlama.io.E_Trade2.dtos.requests.CreateOrderItemRequest;
import kodlama.io.E_Trade2.entities.concretes.OrderItem;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@AllArgsConstructor
public class OrderItemBusinessRules {

    private OrderItemRepository orderItemRepository;


    public void validateQuantityOrderItem(CreateOrderItemRequest createOrderItemRequest){
        if (createOrderItemRequest.getQuantity()<0){
            throw new IllegalArgumentException("Quantity must be greater than zero");
        }
    }

    public void validateUnitPriceOrderItem(CreateOrderItemRequest createOrderItemRequest){
        if (createOrderItemRequest.getUnitPrice().compareTo(BigDecimal.ZERO) <= 0){
            throw new IllegalArgumentException("Unit price must be greater than zero");
        }
    }
}
/*
                       IllegalArgumentException:
   Java'da bulunan bir runtime exception (çalışma zamanı istisnası) türüdür.
 Genellikle, bir metoda geçirilen argümanların beklenmeyen veya geçersiz olduğu durumlarda
 kullanılır.

 */