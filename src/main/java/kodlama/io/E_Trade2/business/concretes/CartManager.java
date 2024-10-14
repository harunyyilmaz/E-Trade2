package kodlama.io.E_Trade2.business.concretes;

import kodlama.io.E_Trade2.business.abstracts.CartService;
import kodlama.io.E_Trade2.dataBase.abstracts.CartItemRepository;
import kodlama.io.E_Trade2.dataBase.abstracts.CartRepository;
import kodlama.io.E_Trade2.dataBase.abstracts.CustomersRepository;
import kodlama.io.E_Trade2.dataBase.abstracts.ProductsRepository;
import kodlama.io.E_Trade2.dtos.requests.CreateCartItemRequest;
import kodlama.io.E_Trade2.dtos.requests.CreateCartRequest;
import kodlama.io.E_Trade2.dtos.requests.UpdateCartRequest;
import kodlama.io.E_Trade2.dtos.responses.GetAllCartResponse;
import kodlama.io.E_Trade2.dtos.responses.GetByIdCartResponse;
import kodlama.io.E_Trade2.entities.concretes.Cart;
import kodlama.io.E_Trade2.entities.concretes.CartItem;
import kodlama.io.E_Trade2.entities.concretes.Customer;
import kodlama.io.E_Trade2.entities.concretes.Product;
import kodlama.io.E_Trade2.enums.CartStatus;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CartManager implements CartService {

    private CartRepository cartRepository;
    private CustomersRepository customersRepository;
    private ProductsRepository productsRepository;
    private CartItemRepository cartItemRepository;

    @Override
    public List<GetAllCartResponse> getAll() {
        List<Cart> carts = this.cartRepository.findAll();

        List<GetAllCartResponse> getAllCartResponses = carts.stream()
                .map(cart -> {
                    GetAllCartResponse getAllCartResponse = new GetAllCartResponse();
                    getAllCartResponse.setId(cart.getId());
                    Optional.ofNullable(cart.getCustomer())
                            .map(Customer::getId)
                            .ifPresent(getAllCartResponse::setCustomerId);
                    //getAllCartResponse.setCustomerId(Optional.ofNullable(cart.getCustomer())
                    //      .map(Customer::getId) // Eğer customer null değilse, getId() metodunu çağır
                    //    .orElse(null)); // Aksi takdirde null ayarla

                    getAllCartResponse.setTotalPrice(cart.getTotalPrice());
                    getAllCartResponse.setCreateAt(cart.getCreateAt());
                    return getAllCartResponse;
                }).collect(Collectors.toList());
        return getAllCartResponses;
    }

    @Override
    public GetByIdCartResponse getById(Long id) {

        Cart cart = this.cartRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Cart not found with ID"));

        GetByIdCartResponse getByIdCartResponse = new GetByIdCartResponse();
        Optional.ofNullable(cart.getTotalPrice()).ifPresent(getByIdCartResponse::setTotalPrice);
        Optional.ofNullable(cart.getCreateAt()).ifPresent(getByIdCartResponse::setCreateAt);
        Optional.ofNullable(cart.getCustomer())
                .map(Customer::getId)
                .ifPresent(getByIdCartResponse::setCustomerId);
        return getByIdCartResponse;
    }

    @Override
    public void add(CreateCartRequest createCartRequest) {

        Cart cart = new Cart();
        cart.setTotalPrice(createCartRequest.getTotalPrice());
        Customer customer = this.customersRepository.findById(createCartRequest.getCustomerId())
                .orElseThrow(() -> new NoSuchElementException("Customer not found with ID"));
        cart.setCustomer(customer);
        this.cartRepository.save(cart);

    }

    @Override
    public void update(UpdateCartRequest updateCartRequest) {

        Cart cart = new Cart();
        Optional.ofNullable(updateCartRequest.getTotalPrice()).ifPresent(cart::setTotalPrice);
        this.cartRepository.save(cart);
    }

    @Override
    public void delete(Long id) {
        Cart cart = this.cartRepository.findById(id)
                .orElseThrow(()-> new NoSuchElementException("Cart not found with ID"));

        if(cart.getCartStatus() != CartStatus.EMPTY){
            throw new IllegalArgumentException("Only empty carts can be deleted");
        }
        this.cartRepository.deleteById(id);
    }

    @Override
    public void cancelCart(Long cartId, CartStatus cartStatus) {
        Cart cart = this.cartRepository.findById(cartId)
                .orElseThrow(() -> new NoSuchElementException("Cart not found with ID"));

        // Durumun güncellenmesine izin verilen bir kontrol
        if (cartStatus == CartStatus.CANCELLED && cart.getCartStatus() != CartStatus.ACTIVE) {
            throw new IllegalArgumentException("Only active carts can be cancelled.");
        }

        cart.setCartStatus(cartStatus);
        this.cartRepository.save(cart);
    }


    @Override
    //Sepet ögelerini temizlemek
    public void clearCart(Cart cart) {
        cart.getCartItems().clear();
        cart.setTotalPrice(BigDecimal.ZERO);
        this.cartRepository.save(cart);
    }

    @Override
    public GetByIdCartResponse getByCustomerId(Long customerId) {
        Cart cart = this.cartRepository.findByCustomerId(customerId)
                .orElseThrow(() -> new NoSuchElementException("Customer not found with Id"));
        GetByIdCartResponse getByIdCartResponse = new GetByIdCartResponse();
        getByIdCartResponse.setCreateAt(cart.getCreateAt());
        Optional.ofNullable(cart.getCustomer())
                .map(Customer::getId)
                .ifPresent(getByIdCartResponse::setCustomerId);
        getByIdCartResponse.setTotalPrice(cart.getTotalPrice());
        return getByIdCartResponse;
    }

    @Override
    public void addItemToCart(Long cartId, CreateCartItemRequest createCartItemRequest) {

        Cart cart = this.cartRepository.findById(cartId)
                .orElseThrow(() -> new NoSuchElementException("Cart not found with id"));

        if (cart.getCartStatus() != CartStatus.ACTIVE) {
            throw new IllegalArgumentException("Items can only be added to active carts.");
        }

        Product product = this.productsRepository.findById(createCartItemRequest.getProductId())
                .orElseThrow(() -> new NoSuchElementException("Product not found with ID"));

        CartItem cartItem = new CartItem();
        cartItem.setQuantity(createCartItemRequest.getQuantity());
        cartItem.setPrice(createCartItemRequest.getPrice());
        cartItem.setCreateAt(LocalDateTime.now());
        cartItem.setProduct(product);
        cartItem.setCart(cart);

        //Cart nesnesine cartItem eklemis olur.
        cart.getCartItems().add(cartItem);
        this.cartRepository.save(cart);
    }

    @Override
    public void removeItemFromCart(Long cartId, Long cartItemId) {

        Cart cart = this.cartRepository.findById(cartId)
                .orElseThrow(() -> new NoSuchElementException("Cart not found with id"));

        // Sepet durumunu kontrol et
        if (cart.getCartStatus() != CartStatus.ACTIVE) {
            throw new IllegalArgumentException("Items can only be removed from active carts.");
        }

        CartItem cartItem = this.cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new NoSuchElementException("CartItem not found with ID"));

        // CartItem'in sepete ait olup olmadığını kontrol et
        if (!cart.getCartItems().contains(cartItem)) {
            throw new IllegalArgumentException("CartItem does not belong to the specified Cart");
        }

        // CartItem'i sepetten kaldır
        cart.getCartItems().remove(cartItem);

        // CartItem'i veritabanından sil ve güncellenmiş sepeti kaydet
        this.cartItemRepository.delete(cartItem);
        this.cartRepository.save(cart);
    }
        /*
        if (cart.equals(cartItem.getCart())){
            cartItem.setCart(null);
            cart.getCartItems().remove(cartItem);

            this.cartRepository.save(cart);
            this.cartItemRepository.save(cartItem);
        }else {
            throw new BusinessException("");
        }
         */

    @Override
    public void calculateCartTotal(Long cartId) {
        Cart cart = this.cartRepository.findById(cartId)
                .orElseThrow(() -> new NoSuchElementException("Cart not found with ID"));

        double total = cart.getCartItems().stream()
                .mapToDouble(cartItem -> cartItem.getPrice().doubleValue() * cartItem.getQuantity())
                .sum();
    }

    @Override
    public GetByIdCartResponse getActiveCartByCustomerId(Long customerId,CartStatus cartStatus) {
        Cart cart = this.cartRepository.findByCustomerId(customerId)
                .orElseThrow(()-> new NoSuchElementException("Cart not found customer ID"));

        if (cart.getCartStatus()!= CartStatus.ACTIVE){
            throw new IllegalArgumentException("Cart is not ACTIVE");
        }

        Customer customer = cart.getCustomer();
        if (customer == null || customer.getId() == null) {
            throw new IllegalStateException("Customer information is missing for the cart.");
        }

        cart.setCartStatus(cartStatus);
        this.cartRepository.save(cart);

        GetByIdCartResponse getByIdCartResponse = new GetByIdCartResponse();
        getByIdCartResponse.setCartId(cart.getId());
        getByIdCartResponse.setCartStatus(cart.getCartStatus());
        getByIdCartResponse.setTotalPrice(cart.getTotalPrice());
        getByIdCartResponse.setCustomerId(cart.getCustomer().getId());
        getByIdCartResponse.setCreateAt(LocalDateTime.now());

        return getByIdCartResponse;
    }

    @Override
    public void updateCartStatus(Long cartId, CartStatus cartStatus) {

        Cart cart = this.cartRepository.findById(cartId)
                .orElseThrow(() -> new NoSuchElementException("Cart not found with ID"));

        if(cart.getCartStatus() == CartStatus.COMPLETED){
            throw new IllegalArgumentException("Cannot update status of a completed cart.");
        }
        //Sepetin durumunu güncelle
        cart.setCartStatus(cartStatus);//cartStatus durumunu kullanici secer ona göre atanir
        cartRepository.save(cart);
    }

    @Override
    public int getCartItemCount(Long cartId) {
        return 0;
    }
}
