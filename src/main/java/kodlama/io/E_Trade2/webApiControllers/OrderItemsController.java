package kodlama.io.E_Trade2.webApiControllers;


import jakarta.validation.Valid;
import kodlama.io.E_Trade2.business.abstracts.OrderItemService;
import kodlama.io.E_Trade2.dtos.requests.CreateOrderItemRequest;
import kodlama.io.E_Trade2.dtos.requests.UpdateOrderItemRequest;
import kodlama.io.E_Trade2.dtos.responses.GetAllOrderItemResponse;
import kodlama.io.E_Trade2.dtos.responses.GetByIdOrderItemResponse;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orderItems")
@AllArgsConstructor
public class OrderItemsController {

    private OrderItemService orderItemService;

    @GetMapping
    public List<GetAllOrderItemResponse> getAll() {
        return this.orderItemService.getAll();
    }

    @GetMapping("/{id}")
    public GetByIdOrderItemResponse getById(@PathVariable Long id) {
        return this.orderItemService.getById(id);
    }

    @PostMapping
    public void add(@RequestBody @Valid CreateOrderItemRequest createOrderItemRequest) {
        this.orderItemService.add(createOrderItemRequest);
    }

    @PutMapping
    public void update(@RequestBody @Valid UpdateOrderItemRequest updateOrderItemRequest) {
        this.orderItemService.update(updateOrderItemRequest);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        this.orderItemService.delete(id);
    }
    @PutMapping("/updateQuantity")
    public void updateQuantity(@RequestParam Long orderItemId,@PathVariable int quantity) {
        this.orderItemService.updateQuantity(orderItemId, quantity);
    }

}
