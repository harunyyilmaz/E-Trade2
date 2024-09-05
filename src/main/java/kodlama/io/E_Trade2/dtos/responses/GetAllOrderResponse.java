package kodlama.io.E_Trade2.dtos.responses;

import kodlama.io.E_Trade2.entities.concretes.OrderItem;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetAllOrderResponse {

    private Long id;
    private LocalDateTime createAt;
    private Long userId;
    private List<OrderItem> orderItems;
    private int orderNumber;
    private BigDecimal totalPrice;
    private String shippingMethod;
    private String trackingNumber;
    private String notes;

}
