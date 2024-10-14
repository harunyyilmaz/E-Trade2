package kodlama.io.E_Trade2.business.rules;

import kodlama.io.E_Trade2.dataBase.abstracts.CartItemRepository;
import kodlama.io.E_Trade2.entities.concretes.CartItem;
import kodlama.io.E_Trade2.entities.concretes.Product;
import kodlama.io.E_Trade2.enums.CartItemStatus;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
@AllArgsConstructor
public class CartItemBusinessRules {

    private CartItemRepository cartItemRepository;


    public void validateCartItemProductMatch(Long cartItemId, Long productId) {
        CartItem cartItem = this.cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new NoSuchElementException("Sepet öğesi bulunamadı. ID: " + cartItemId));

        Product product = cartItem.getProduct();
        if (!product.getId().equals(productId)) {
            throw new IllegalArgumentException("Bu sepet öğesine ait ürün bulunamadı. Geçersiz ürün ID: " + productId);
        }
    }

    public CartItemStatus determineCartItemStatus(int quantity) {
        if (quantity == 0) {
            return CartItemStatus.OUT_OF_STOCK; // Miktar sıfırsa OUT_OF_STOCK
        } else {
            return CartItemStatus.PENDING; // Aksi takdirde PENDING
        }
    }
}
