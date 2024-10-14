package kodlama.io.E_Trade2.webApiControllers;

import jakarta.validation.Valid;
import kodlama.io.E_Trade2.business.abstracts.CartService;
import kodlama.io.E_Trade2.dtos.requests.CreateCartItemRequest;
import kodlama.io.E_Trade2.dtos.requests.CreateCartRequest;
import kodlama.io.E_Trade2.dtos.requests.UpdateCartRequest;
import kodlama.io.E_Trade2.dtos.responses.GetAllCartResponse;
import kodlama.io.E_Trade2.dtos.responses.GetByIdCartResponse;
import kodlama.io.E_Trade2.entities.concretes.Cart;
import kodlama.io.E_Trade2.enums.CartStatus;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/carts")
@AllArgsConstructor
public class CartsController {

    private CartService cartService;

    @GetMapping
    public List<GetAllCartResponse> getAll() {
        return this.cartService.getAll();
    }

    @GetMapping("{id}")
   public GetByIdCartResponse getById(@PathVariable Long id){
        return this.cartService.getById(id);
   }

    @PostMapping
    ResponseEntity<Void> add(@Valid @RequestBody CreateCartRequest createCartRequest) {
        this.cartService.add(createCartRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping
    ResponseEntity<Void> update(@RequestBody @Valid UpdateCartRequest updateCartRequest) {
        this.cartService.update(updateCartRequest);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    ResponseEntity<Void> delete(@PathVariable Long id) {
        this.cartService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{cartId}/cancel")
    ResponseEntity<Void> cancelCart(@PathVariable Long cartId, @RequestParam CartStatus cartStatus) {
        this.cartService.cancelCart(cartId, cartStatus);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/clear")
    ResponseEntity<Void> clearCart(@RequestBody @Valid Cart cart) {
        this.cartService.clearCart(cart);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{customerId}")
    GetByIdCartResponse getByCustomerId(@PathVariable Long customerId) {
        return this.cartService.getByCustomerId(customerId);
    }

    @PostMapping("/{cartId}/items")
    ResponseEntity<Void> addItemToCart(@PathVariable Long cartId, @RequestBody @Valid CreateCartItemRequest createCartItemRequest) {
        this.cartService.addItemToCart(cartId, createCartItemRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/{cartId}/items/{cartItemId}")
    ResponseEntity<Void> removeItemFromCart(@PathVariable Long cartId, @PathVariable Long cartItemId) {
        this.cartService.removeItemFromCart(cartId, cartItemId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{cartId}/total")
    ResponseEntity<Void> calculateCartTotal(@PathVariable Long cartId) {
        this.cartService.calculateCartTotal(cartId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/customer/{customerId}/active")
    GetByIdCartResponse getActiveCartByCustomerId(@PathVariable Long customerId, @RequestParam CartStatus cartStatus) {
        return this.cartService.getActiveCartByCustomerId(customerId, cartStatus);
    }

    @PutMapping("/{cartId}/status")
    ResponseEntity<Void> updateCartStatus(@PathVariable Long cartId, @RequestParam CartStatus cartStatus) {
        this.cartService.updateCartStatus(cartId, cartStatus);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{cartId}/item-count")
    int getCartItemCount(@PathVariable Long cartId) {
        return this.cartService.getCartItemCount(cartId);
    }
}
