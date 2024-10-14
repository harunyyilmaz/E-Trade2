package kodlama.io.E_Trade2.webApiControllers;

import jakarta.validation.Valid;
import kodlama.io.E_Trade2.business.abstracts.CartItemService;
import kodlama.io.E_Trade2.dataBase.abstracts.CartItemRepository;
import kodlama.io.E_Trade2.dtos.requests.CreateCartItemRequest;
import kodlama.io.E_Trade2.dtos.requests.UpdateCartItemRequest;
import kodlama.io.E_Trade2.dtos.responses.GetAllCartItemResponse;
import kodlama.io.E_Trade2.dtos.responses.GetByIdCartItemResponse;
import kodlama.io.E_Trade2.entities.concretes.CartItem;
import kodlama.io.E_Trade2.enums.CartItemStatus;
import lombok.AllArgsConstructor;
import org.springframework.data.querydsl.QPageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/cartItems")
@AllArgsConstructor
public class CartItemsController {

    private CartItemService cartItemService;

    @GetMapping
    public List<GetAllCartItemResponse> getAll() {
        return this.cartItemService.getAll();
    }

    @GetMapping("{id}")
    public GetByIdCartItemResponse getById(@PathVariable Long id) {
        return this.cartItemService.getById(id);
    }

    @PostMapping
    public ResponseEntity<Void> add(@RequestBody @Valid CreateCartItemRequest createCartItemRequest) {
        this.cartItemService.add(createCartItemRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping
    public ResponseEntity<Void> update(@RequestBody @Valid UpdateCartItemRequest updateCartItemRequest) {
        this.cartItemService.update(updateCartItemRequest);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        this.cartItemService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/quantity")
    public ResponseEntity<Void> quantity(@PathVariable Long cartItemId, @PathVariable int newQuantity) {
        this.cartItemService.quantity(cartItemId, newQuantity);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/remove")
    public ResponseEntity<Void> removeCartItem(@PathVariable Long cartId, @PathVariable Long cartItemId) {
        this.cartItemService.removeCartItem(cartId, cartItemId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(("/cartItems/{cartId}/{minPrice}/{maxPrice}"))
    public List<GetAllCartItemResponse> getCartItemsByPriceRange(@RequestParam Long cartId, @RequestParam BigDecimal minPrice, @RequestParam BigDecimal maxPrice) {
        return this.cartItemService.getCartItemsByPriceRange(cartId, minPrice, maxPrice);
    }

    @GetMapping("/status")
    public List<GetAllCartItemResponse> getCartItemsByStatus(@RequestParam CartItemStatus cartItemStatus) {
        return this.cartItemService.getCartItemsByStatus(cartItemStatus);
    }

    @PutMapping("/{cartItemId}/apply-discount")
    public ResponseEntity<Void> applyDiscount(CartItem cartItem, BigDecimal discountRate) {
        this.cartItemService.applyDiscount(cartItem, discountRate);
        return ResponseEntity.ok().build();
    }

}
