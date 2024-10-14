package kodlama.io.E_Trade2.business.abstracts;

import kodlama.io.E_Trade2.dtos.requests.CreateCartItemRequest;
import kodlama.io.E_Trade2.dtos.requests.UpdateCartItemRequest;
import kodlama.io.E_Trade2.dtos.responses.GetAllCartItemResponse;
import kodlama.io.E_Trade2.dtos.responses.GetByIdCartItemResponse;
import kodlama.io.E_Trade2.entities.concretes.CartItem;
import kodlama.io.E_Trade2.enums.CartItemStatus;

import java.math.BigDecimal;
import java.util.List;

public interface CartItemService {

    List<GetAllCartItemResponse> getAll();

    GetByIdCartItemResponse getById(Long id);

    void add(CreateCartItemRequest createCartItemRequest);

    void update(UpdateCartItemRequest updateCartItemRequest);

    void delete(Long id);

    //ürün sayisini arttirma - azaltma
    void quantity(Long cartItemId, int newQuantity);

    void removeCartItem(Long cartId, Long cartItemId);

    List<GetAllCartItemResponse> getCartItemsByPriceRange(Long cartId, BigDecimal minPrice, BigDecimal maxPrice);

    List<GetAllCartItemResponse> getCartItemsByStatus(CartItemStatus cartItemStatus);

    void applyDiscount(CartItem cartItem, BigDecimal discountRate);
}
