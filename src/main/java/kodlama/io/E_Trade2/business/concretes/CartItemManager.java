package kodlama.io.E_Trade2.business.concretes;

import kodlama.io.E_Trade2.business.abstracts.CartItemService;
import kodlama.io.E_Trade2.business.rules.CartItemBusinessRules;
import kodlama.io.E_Trade2.core.utilities.mappers.ModelMapperService;
import kodlama.io.E_Trade2.dataBase.abstracts.CartItemRepository;
import kodlama.io.E_Trade2.dataBase.abstracts.CartRepository;
import kodlama.io.E_Trade2.dataBase.abstracts.ProductsRepository;
import kodlama.io.E_Trade2.dtos.requests.CreateCartItemRequest;
import kodlama.io.E_Trade2.dtos.requests.UpdateCartItemRequest;
import kodlama.io.E_Trade2.dtos.responses.GetAllCartItemResponse;
import kodlama.io.E_Trade2.dtos.responses.GetByIdCartItemResponse;
import kodlama.io.E_Trade2.entities.concretes.Cart;
import kodlama.io.E_Trade2.entities.concretes.CartItem;
import kodlama.io.E_Trade2.entities.concretes.Product;
import kodlama.io.E_Trade2.enums.CartItemStatus;
import kodlama.io.E_Trade2.enums.CartStatus;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CartItemManager implements CartItemService {

    private CartItemRepository cartItemRepository;
    private ProductsRepository productsRepository;
    private CartRepository cartRepository;
    private ModelMapperService modelMapperService;
    private CartItemBusinessRules cartItemBusinessRules;

    @Override
    public List<GetAllCartItemResponse> getAll() {

        List<CartItem> cartItems = this.cartItemRepository.findAll();

        List<GetAllCartItemResponse> getAllCartItemResponses = cartItems.stream()
                .map(cartItem -> {
                    GetAllCartItemResponse response = new GetAllCartItemResponse();
                    response.setId(cartItem.getId());
                    response.setPrice(cartItem.getPrice());
                    response.setProductId(cartItem.getProduct().getId());
                    response.setQuantity(cartItem.getQuantity());
                    response.setProductName(cartItem.getProduct().getName());
                    return response;
                }).collect(Collectors.toList());
        return getAllCartItemResponses;
    }

    @Override
    public GetByIdCartItemResponse getById(Long id) {

        CartItem cartItem = this.cartItemRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Cart item not found with Id"));

        GetByIdCartItemResponse response = new GetByIdCartItemResponse();
        response.setId(cartItem.getId());
        response.setProductId(cartItem.getProduct().getId());
        response.setProductName(cartItem.getProduct().getName());
        response.setQuantity(cartItem.getQuantity());
        response.setPrice(cartItem.getPrice());

        return response;
    }

    @Override
    public void add(CreateCartItemRequest createCartItemRequest) {

        CartItem cartItem = new CartItem();
        cartItem.setId(createCartItemRequest.getCartId());
        cartItem.setQuantity(createCartItemRequest.getQuantity());
        cartItem.setPrice(createCartItemRequest.getPrice());
        cartItem.setCreateAt(LocalDateTime.now());

        Product product = this.productsRepository.findById(createCartItemRequest.getProductId())
                .orElseThrow(() -> new NoSuchElementException("Product not found with ID"));
        cartItem.setProduct(product);

        Cart cart = this.cartRepository.findById(createCartItemRequest.getCartId())
                .orElseThrow(() -> new NoSuchElementException("Cart not found with ID"));
        cartItem.setCart(cart);

        cartItem.setCartItemStatus(CartItemStatus.ADDED);

        this.cartItemRepository.save(cartItem);
    }

    @Override
    public void update(UpdateCartItemRequest updateCartItemRequest) {

        CartItem cartItem = new CartItem();
        cartItem.setId(updateCartItemRequest.getId());
        cartItem.setPrice(updateCartItemRequest.getPrice());
        cartItem.setQuantity(updateCartItemRequest.getQuantity());


        this.cartItemRepository.save(cartItem);
    }

    @Override
    public void delete(Long id) {
        CartItem cartItem = this.cartItemRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Cart item not found with ID"));
        this.cartItemRepository.deleteById(id);
    }

    @Override
    public void quantity(Long cartItemId, int newQuantity) {
        CartItem cartItem = this.cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new NoSuchElementException("Cart item not found with ID"));

        cartItem.setQuantity(newQuantity);

        //Durumu belirle ve CartItem nesnesine ata.
        CartItemStatus status = this.cartItemBusinessRules.determineCartItemStatus(newQuantity);
        cartItem.setCartItemStatus(status);

        this.cartItemRepository.save(cartItem);
    }


    @Override
    public void removeCartItem(Long cartId, Long cartItemId) {


        Cart cart = this.cartRepository.findById(cartId)
                .orElseThrow(() -> new NoSuchElementException("Cart not found with ID"));

        CartItem cartItem = this.cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new NoSuchElementException("Cart item not found with ID"));

        if (!cart.getCartItems().contains(cartItem)) {
            throw new IllegalArgumentException("This cart item does not belong to the specified cart.");
        }

        cartItem.setCartItemStatus(CartItemStatus.REMOVED);

        //Sepetten kaldir.
        cart.getCartItems().remove(cartItem);
        //Ürünü veritabanindan kaldir.
        this.cartItemRepository.delete(cartItem);

        //Sepetin son durumunu güncelle.
        this.cartRepository.save(cart);

    }

    @Override
    public List<GetAllCartItemResponse> getCartItemsByPriceRange(Long cartId, BigDecimal minPrice, BigDecimal maxPrice) {

        Cart cart = this.cartRepository.findById(cartId)
                .orElseThrow(() -> new NoSuchElementException("Cart not found with id"));

        List<CartItem> cartItems = cart.getCartItems().stream()
                .filter(cartItem -> {
                    BigDecimal itemPrice = cartItem.getProduct().getPrice();
                    return itemPrice.compareTo(minPrice) >= 0 && itemPrice.compareTo(maxPrice) <= 0;
                }).collect(Collectors.toList());

        List<GetAllCartItemResponse> responseList = cartItems.stream()
                .map(cartItem -> this.modelMapperService.forResponse().map(cartItem, GetAllCartItemResponse.class))
                .collect(Collectors.toList());

        return responseList;
    }

    @Override
    public List<GetAllCartItemResponse> getCartItemsByStatus(CartItemStatus cartItemStatus) {

        List<CartItem> cartItems = this.cartItemRepository.findAll();

        List<GetAllCartItemResponse> getAllCartItemResponses = cartItems.stream()
                .filter(cartItem -> cartItem.getCartItemStatus() == cartItemStatus)
                .map(cartItem -> {
                    GetAllCartItemResponse response = new GetAllCartItemResponse();
                    response.setId(cartItem.getId());
                    response.setPrice(cartItem.getPrice());
                    response.setQuantity(cartItem.getQuantity());
                    response.setProductId(cartItem.getProduct().getId());
                    response.setProductName(cartItem.getProduct().getName());
                    return response;
                })
                .collect(Collectors.toList());

        return getAllCartItemResponses;
    }

    @Override
    public void applyDiscount(CartItem cartItem, BigDecimal discountRate) {


        if (discountRate.compareTo(BigDecimal.ZERO) < 0 || discountRate.compareTo(BigDecimal.ONE) > 0) {
            throw new IllegalArgumentException("Discount rate must be between 0 and 1");
        }

        //Ürün fiyatini getir.
        BigDecimal originalPrice = cartItem.getProduct().getPrice();

        //Indirimli fiyati hesapla
        BigDecimal discountAmount = originalPrice.multiply(discountRate);
        BigDecimal discountedPrice = originalPrice.subtract(discountAmount);

        //Indirimli fiyati sepet ögesine uygula
        cartItem.setPrice(discountedPrice);

        this.cartItemRepository.save(cartItem);
    }
}
