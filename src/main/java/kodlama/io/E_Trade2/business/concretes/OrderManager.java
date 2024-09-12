package kodlama.io.E_Trade2.business.concretes;

import kodlama.io.E_Trade2.business.abstracts.OrderService;
import kodlama.io.E_Trade2.business.rules.OrderBusinessRules;
import kodlama.io.E_Trade2.core.utilities.exceptions.BusinessException;
import kodlama.io.E_Trade2.core.utilities.exceptions.OrderCancellationException;
import kodlama.io.E_Trade2.core.utilities.exceptions.OrderNotFoundException;
import kodlama.io.E_Trade2.core.utilities.mappers.ModelMapperService;
import kodlama.io.E_Trade2.dataBase.abstracts.OrderRepository;
import kodlama.io.E_Trade2.dataBase.abstracts.UserRepository;
import kodlama.io.E_Trade2.dtos.requests.CreateOrderRequest;
import kodlama.io.E_Trade2.dtos.requests.UpdateOrderRequest;
import kodlama.io.E_Trade2.dtos.responses.GetAllOrderResponse;
import kodlama.io.E_Trade2.dtos.responses.GetByIdOrderResponse;
import kodlama.io.E_Trade2.entities.concretes.Order;
import kodlama.io.E_Trade2.entities.concretes.OrderItem;
import kodlama.io.E_Trade2.entities.concretes.User;
import kodlama.io.E_Trade2.enums.OrderStatus;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
@AllArgsConstructor
@Service
public class OrderManager implements OrderService {

    private OrderRepository orderRepository;
    private UserRepository userRepository;
    private ModelMapperService modelMapperService;
    private OrderBusinessRules orderBusinessRules;


    @Override
    public List<GetAllOrderResponse> getAll() {

        List<Order> orders = this.orderRepository.findAll();
        List<GetAllOrderResponse> getAllOrderResponses = orders.stream()
                .map(order -> {
                    GetAllOrderResponse response = new GetAllOrderResponse();
                    response.setId(order.getId());
                    response.setOrderItems(order.getOrderItems());
                    response.setOrderNumber(order.getOrderNumber());
                    response.setNotes(order.getNotes());
                    response.setCreateAt(order.getCreateAt());
                    response.setShippingMethod(order.getShippingMethod());
                    response.setTotalPrice(order.getTotalPrice());
                    response.setTrackingNumber(order.getTrackingNumber());
                    response.setUserId(order.getUser() != null ? order.getUser().getId() : null);
                    return response;
                }).collect(Collectors.toList());
        return getAllOrderResponses;
    }

    @Override
    public GetByIdOrderResponse getById(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Order not found with id: "));

        GetByIdOrderResponse getByIdOrderResponse = new GetByIdOrderResponse();
        getByIdOrderResponse.setId(order.getId());
        getByIdOrderResponse.setCreateAt(order.getCreateAt());
        getByIdOrderResponse.setOrderItems(order.getOrderItems());
        getByIdOrderResponse.setUserId(order.getUser().getId());
        getByIdOrderResponse.setTotalPrice(order.getTotalPrice());
        getByIdOrderResponse.setOrderNumber(order.getOrderNumber());
        getByIdOrderResponse.setShippingMethod(order.getShippingMethod());
        getByIdOrderResponse.setTrackingNumber(order.getTrackingNumber());
        getByIdOrderResponse.setNotes(order.getNotes());
        getByIdOrderResponse.setCity(order.getCity());

        return getByIdOrderResponse;

    }

    @Override
    public void add(CreateOrderRequest createOrderRequest) {

        this.orderBusinessRules.checkIfOrderNumberExists(createOrderRequest.getOrderNumber());

       Order order = new Order();
       order.setOrderNumber(createOrderRequest.getOrderNumber());
       order.setShippingMethod(createOrderRequest.getShippingMethod());
       order.setCity(createOrderRequest.getCity());
       order.setNotes(createOrderRequest.getNotes());
       order.setTotalPrice(createOrderRequest.getTotalPrice());

       User user = this.userRepository.findById(createOrderRequest.getUserId())
               .orElseThrow(() -> new IllegalArgumentException("Invalid User ID"));
       order.setUser(user);

       List<OrderItem> createOrderItems = createOrderRequest.getOrderItems().stream()
                       .map(orderItemRequest -> {
                           OrderItem orderItem = new OrderItem();
                           orderItem.setId(orderItemRequest.getId());
                           orderItem.setUnitPrice(orderItemRequest.getUnitPrice());
                           orderItem.setQuantity(orderItemRequest.getQuantity());
                           orderItem.setProduct(orderItemRequest.getProduct());
                           orderItem.setOrder(order);//OrderItem ile Order nesnesi arasindaki iliskiyi kuruyoruz.
                           return orderItem;
                       }).collect(Collectors.toList());
       order.setOrderItems(createOrderItems);

       orderRepository.save(order);

    }


    @Override
    public void update(UpdateOrderRequest updateOrderRequest) {
        Order order = new Order();
        order.setOrderNumber(updateOrderRequest.getOrderNumber());
        order.setShippingMethod(updateOrderRequest.getShippingMethod());
        order.setNotes(updateOrderRequest.getNotes());

        User user = this.userRepository.findById(updateOrderRequest.getUserId())
                .orElseThrow(()-> new IllegalArgumentException("User not found ID"));
        order.setUser(user);

        List<OrderItem> updateOrderItems = updateOrderRequest.getOrderItems().stream()
                .map(orderItemRequest -> {
                    OrderItem orderItem = new OrderItem();
                    orderItem.setId(orderItemRequest.getId());
                    orderItem.setUnitPrice(orderItemRequest.getUnitPrice());
                    orderItem.setQuantity(orderItemRequest.getQuantity());
                    orderItem.setProduct(orderItemRequest.getProduct());
                    orderItem.setOrder(order); // Güncellenen order ile iliskilendirilir.
                    return orderItem;
                }).collect(Collectors.toList());
        order.setOrderItems(updateOrderItems);

        orderRepository.save(order);
    }

    @Override
    public void delete(Long id) {
        this.orderRepository.deleteById(id);
    }

    @Override
    public void cancelOrder(Long orderId , Long userId) {


        //Siparis bul
        Order order = this.orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException("Not found order ID"));

        //Yorum satirina aldigimiz kodlari daha temiz hale getirdik.
        this.orderBusinessRules.checkIfUserAuthorized(order,userId);
        this.orderBusinessRules.checkIfOrderCanBeCancelled(order,orderId);
        /*
        //Kullanici dogrulamsi
        if (order.getUser()== null || !order.getUser().getId().equals(userId)){
            throw new OrderNotFoundException("User with ID" + userId + "is not authorized to cancel this order") ;
        }
        //Iptal kisitlamalarini kontrol et.
        if (order.getOrderStatus() != OrderStatus.PENDING){
            throw new OrderCancellationException("Order with ID" + orderId + "cannot be cancelled");
        }

         */
        //Siparisin durumunu iptal olarak ayarla.
        order.setOrderStatus(OrderStatus.CANCELED);
        orderRepository.save(order);
    }

    @Override
    public void updateOrderStatus(Long orderId, OrderStatus orderStatus) {
        Order order = this.orderRepository.findById(orderId)
                .orElseThrow(()-> new OrderNotFoundException("Order not found"));
        order.setOrderStatus(orderStatus);
        orderRepository.save(order);
    }
}
