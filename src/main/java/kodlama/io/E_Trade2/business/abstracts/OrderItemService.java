package kodlama.io.E_Trade2.business.abstracts;

import kodlama.io.E_Trade2.dtos.requests.CreateOrderItemRequest;
import kodlama.io.E_Trade2.dtos.requests.UpdateOrderItemRequest;
import kodlama.io.E_Trade2.dtos.responses.GetAllOrderItemResponse;
import kodlama.io.E_Trade2.dtos.responses.GetByIdOrderItemResponse;

import java.util.List;

public interface OrderItemService {

    List<GetAllOrderItemResponse> getAll();
    GetByIdOrderItemResponse getById(Long id);
    void add(CreateOrderItemRequest createOrderItemRequest);
    void update(UpdateOrderItemRequest updateOrderItemRequest);
    void delete(Long id);
    void updateQuantity(Long orderItemId , int quantity); //Belirli bir orderItem miktarini günceller.


}
