package kodlama.io.E_Trade2.business.rules;

import kodlama.io.E_Trade2.core.utilities.exceptions.BusinessException;
import kodlama.io.E_Trade2.core.utilities.exceptions.OrderCancellationException;
import kodlama.io.E_Trade2.core.utilities.exceptions.OrderNotFoundException;
import kodlama.io.E_Trade2.dataBase.abstracts.OrderRepository;
import kodlama.io.E_Trade2.entities.concretes.Order;
import kodlama.io.E_Trade2.enums.OrderStatus;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@AllArgsConstructor
@Service
public class OrderBusinessRules {

    private OrderRepository orderRepository;

    // 1. asama kural yazarken OrderBusinessRules sinifi olusturduk.
    // 2. asama da Repoya gidipi metot yazacaz existsByOrderNumber => jpa özel keywords olusturur.
    // örnek findByName
    //sonra RuntimeExceptions yerine özel BusinessExceptions sinifi olusturuyoruz mesaj döndürmesi icin.
    public void checkIfOrderNumberExists(int orderNumber) {
        if (this.orderRepository.existsByOrderNumber(orderNumber)) {
            throw new BusinessException("Order number already exists");
        }
    }
    //Siparisi bul.
    public void checkIfOrderExists(Long orderId) {
        if (!this.orderRepository.existsById(orderId)) {
            throw new OrderNotFoundException("Not found Order ID " + orderId);
        }
    }

    //Kullanici dogrulamasi
    public void checkIfUserAuthorized(Order order, Long userId) {
        if (order.getUser() == null || !order.getUser().getId().equals(userId)) {
            throw new OrderNotFoundException("User with ID " + userId + " is not authorized to cancel this order");
        }
    }

    //Iptal kisitlamalarini kontrol et
    public void checkIfOrderCanBeCancelled(Order order, Long orderId) {
        if (order.getOrderStatus() != OrderStatus.PENDING) {
            throw new OrderCancellationException("Order with ID " + orderId + " cannot be cancelled");
        }

    }

}
