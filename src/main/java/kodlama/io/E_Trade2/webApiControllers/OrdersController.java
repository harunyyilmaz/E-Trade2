package kodlama.io.E_Trade2.webApiControllers;

import jakarta.validation.Valid;
import kodlama.io.E_Trade2.business.abstracts.OrderService;
import kodlama.io.E_Trade2.dtos.requests.CreateOrderRequest;
import kodlama.io.E_Trade2.dtos.requests.UpdateOrderRequest;
import kodlama.io.E_Trade2.dtos.responses.GetAllOrderResponse;
import kodlama.io.E_Trade2.dtos.responses.GetByIdOrderResponse;
import kodlama.io.E_Trade2.enums.OrderStatus;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@AllArgsConstructor
public class OrdersController {

    private OrderService orderService;

    @GetMapping
    public List<GetAllOrderResponse> getAll(){
        return this.orderService.getAll();
    }
    @GetMapping("/{id}")
    public GetByIdOrderResponse getById(@PathVariable Long id){
        return this.orderService.getById(id);
    }
    @PostMapping
    public void add(@RequestBody @Valid CreateOrderRequest createOrderRequest){
        this.orderService.add(createOrderRequest);
    }
    @PutMapping
    public void update(@RequestBody @Valid UpdateOrderRequest updateOrderRequest){
        this.orderService.update(updateOrderRequest);
    }
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){
        this.orderService.delete(id);
    }

    @PutMapping("/cancel")
    public void cancelOrder(@RequestParam Long orderId ,@RequestParam Long userId){
        this.orderService.cancelOrder(orderId,userId);
    }

    @PutMapping("/status")
    public void updateOrderStatus(@RequestParam Long orderId ,@RequestBody OrderStatus orderStatus){
        this.orderService.updateOrderStatus(orderId,orderStatus);
    }

}
