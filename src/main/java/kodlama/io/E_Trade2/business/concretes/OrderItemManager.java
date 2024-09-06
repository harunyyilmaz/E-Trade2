package kodlama.io.E_Trade2.business.concretes;

import kodlama.io.E_Trade2.business.abstracts.OrderItemService;
import kodlama.io.E_Trade2.business.rules.OrderBusinessRules;
import kodlama.io.E_Trade2.business.rules.OrderItemBusinessRules;
import kodlama.io.E_Trade2.core.utilities.exceptions.BusinessException;
import kodlama.io.E_Trade2.core.utilities.mappers.ModelMapperService;
import kodlama.io.E_Trade2.dataBase.abstracts.OrderItemRepository;
import kodlama.io.E_Trade2.dataBase.abstracts.OrderRepository;
import kodlama.io.E_Trade2.dataBase.abstracts.ProductsRepository;
import kodlama.io.E_Trade2.dtos.requests.CreateOrderItemRequest;
import kodlama.io.E_Trade2.dtos.requests.UpdateOrderItemRequest;
import kodlama.io.E_Trade2.dtos.responses.GetAllOrderItemResponse;
import kodlama.io.E_Trade2.dtos.responses.GetByIdOrderItemResponse;
import kodlama.io.E_Trade2.entities.concretes.Order;
import kodlama.io.E_Trade2.entities.concretes.OrderItem;
import kodlama.io.E_Trade2.entities.concretes.Product;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class OrderItemManager implements OrderItemService {

    private OrderItemRepository orderItemRepository;
    private ModelMapperService modelMapperService;
    private ProductsRepository productsRepository;
    private OrderRepository orderRepository;
    private OrderItemBusinessRules orderItemBusinessRules;

    @Override
    public List<GetAllOrderItemResponse> getAll() {

        List<OrderItem> orderItems = this.orderItemRepository.findAll();
        List<GetAllOrderItemResponse> getAllOrderItemResponses = orderItems.stream()
                .map(orderItem -> {
                    GetAllOrderItemResponse orderItemResponse = new GetAllOrderItemResponse();
                    orderItemResponse.setOrderId(orderItem.getId());
                    orderItemResponse.setQuantity(orderItem.getQuantity());
                    orderItemResponse.setUnitPrice(orderItem.getUnitPrice());
                    orderItemResponse.setProductId(orderItem.getProduct().getId());
                    return orderItemResponse;
                }).collect(Collectors.toList());
        return getAllOrderItemResponses;
    }

    @Override
    public GetByIdOrderItemResponse getById(Long id) {

        OrderItem orderItem = orderItemRepository.findById(id)
                .orElseThrow(() -> new BusinessException("OrderItem not found with id" + id));

        GetByIdOrderItemResponse getByIdOrderItemResponse = new GetByIdOrderItemResponse();
        getByIdOrderItemResponse.setProductId(orderItem.getProduct().getId());
        getByIdOrderItemResponse.setProductName(orderItem.getProduct().getName());
        getByIdOrderItemResponse.setQuantity(orderItem.getQuantity());
        getByIdOrderItemResponse.setOrderId(orderItem.getOrder().getId());
        getByIdOrderItemResponse.setUnitPrice(orderItem.getUnitPrice());

        return getByIdOrderItemResponse;
    }

    @Override
    public void add(CreateOrderItemRequest createOrderItemRequest) {

        this.orderItemBusinessRules.validateQuantityOrderItem(createOrderItemRequest);
        this.orderItemBusinessRules.validateUnitPriceOrderItem(createOrderItemRequest);

        OrderItem orderItem = new OrderItem();
        orderItem.setQuantity(createOrderItemRequest.getQuantity());
        orderItem.setUnitPrice(createOrderItemRequest.getUnitPrice());

        Product product = this.productsRepository.findByName(createOrderItemRequest.getProductName())
                .orElseThrow(()-> new BusinessException("Product not found name"));
        orderItem.setProduct(product);

        Order order = this.orderRepository.findByOrderNumber(createOrderItemRequest.getOrderNumber())
                .orElseThrow(()-> new BusinessException("Order number not found"));

        orderItem.setOrder(order);

        orderItemRepository.save(orderItem);

    }

    @Override
    public void update(UpdateOrderItemRequest updateOrderItemRequest) {

        OrderItem orderItem = new OrderItem();
        orderItem.setQuantity(updateOrderItemRequest.getQuantity());
        orderItem.setUnitPrice(updateOrderItemRequest.getUnitPrice());

        Product product = this.productsRepository.findByName(updateOrderItemRequest.getProductName())
                .orElseThrow(()-> new BusinessException("Product not found with name"));
        orderItem.setProduct(product);

        Order order = this.orderRepository.findByOrderNumber(updateOrderItemRequest.getOrderNumber())
                .orElseThrow(()-> new BusinessException("Order not found with number"));
        orderItem.setOrder(order);

        orderItemRepository.save(orderItem);
    }


    @Override
    public void delete(Long id) {
        this.orderItemRepository.deleteById(id);

    }

    @Override
    public void updateQuantity(Long orderItemId, int quantity) {
        OrderItem orderItem = this.orderItemRepository.findById(orderItemId)
                .orElseThrow(()-> new BusinessException("OrderItem not found with ID" + orderItemId));
        orderItem.setQuantity(quantity);

        orderItemRepository.save(orderItem);

    }
}
