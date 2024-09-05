package kodlama.io.E_Trade2.business.abstracts;

import kodlama.io.E_Trade2.dtos.requests.CreateOrderRequest;
import kodlama.io.E_Trade2.dtos.requests.UpdateOrderRequest;
import kodlama.io.E_Trade2.dtos.responses.GetAllOrderResponse;
import kodlama.io.E_Trade2.dtos.responses.GetByIdOrderResponse;
import kodlama.io.E_Trade2.enums.OrderStatus;

import java.util.List;

public interface OrderService {

    List<GetAllOrderResponse> getAll();
    GetByIdOrderResponse getById(Long id);
    void add(CreateOrderRequest createOrderRequest);
    void update(UpdateOrderRequest updateOrderRequest);
    void delete(Long id);
    void cancelOrder(Long orderId , Long userId); //Siparis iptal etmek
    void updateOrderStatus(Long orderId , OrderStatus orderStatus); // Siparisin durumunu güncellemek.
    // Siparişin güncel durumunu belirtir. OrderStatus muhtemelen bir enum veya başka bir türde
    // bir sınıf olup, siparişin alındı, hazırlanıyor, gönderildi, teslim edildi gibi durumları
    // temsil eder.
 }
