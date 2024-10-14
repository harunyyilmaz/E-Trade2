package kodlama.io.E_Trade2.business.abstracts;

import kodlama.io.E_Trade2.dtos.requests.CreateCartItemRequest;
import kodlama.io.E_Trade2.dtos.requests.CreateCartRequest;
import kodlama.io.E_Trade2.dtos.requests.UpdateCartRequest;
import kodlama.io.E_Trade2.dtos.responses.GetAllCartResponse;
import kodlama.io.E_Trade2.dtos.responses.GetByIdCartResponse;
import kodlama.io.E_Trade2.entities.concretes.Cart;
import kodlama.io.E_Trade2.enums.CartStatus;

import java.util.List;

public interface CartService {

    List<GetAllCartResponse> getAll();

    GetByIdCartResponse getById(Long id);

    void add(CreateCartRequest createCartRequest);

    void update(UpdateCartRequest updateCartRequest);

    void delete(Long id);

    void cancelCart(Long cartId,CartStatus cartStatus);

    void clearCart(Cart cart);

    GetByIdCartResponse getByCustomerId(Long customerId);

    void addItemToCart(Long cartId, CreateCartItemRequest createCartItemRequest);

    void removeItemFromCart(Long cartId, Long cartItemId);

    void calculateCartTotal(Long cartId);

    GetByIdCartResponse getActiveCartByCustomerId(Long customerId, CartStatus cartStatus);

    // Sepet Durumunu Güncelleme
    // Sepetin durumunu (örneğin, aktif, tamamlanmış, iptal edilmiş) güncellemek için bir metot:
    void updateCartStatus(Long cartId, CartStatus status);

    //Sepetteki Ürün Sayısını Getirme
    //Sepette kaç adet ürün bulunduğunu öğrenmek için:
    int getCartItemCount(Long cartId);



    /*
    public Order checkoutCart(Long cartId) {
    Cart cart = cartRepository.findById(cartId)
            .orElseThrow(() -> new NoSuchElementException("Cart not found with ID: " + cartId));

    // Sipariş oluşturmak için gerekli işlemler
    Order order = new Order();
    order.setUser(cart.getCustomer().getUser());
    order.setTotalPrice(calculateCartTotal(cartId));
    order.setOrderStatus(OrderStatus.PENDING);
    order.setOrderItems(cart.getCartItems());

    orderRepository.save(order);

    // Sepeti temizle
    clearCart(cartId);

    return order;
     */



}
