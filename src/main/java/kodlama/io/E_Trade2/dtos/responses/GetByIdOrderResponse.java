package kodlama.io.E_Trade2.dtos.responses;

import kodlama.io.E_Trade2.entities.concretes.OrderItem;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class GetByIdOrderResponse {

    private Long id;
    private LocalDateTime createAt;
    private List<OrderItem> orderItems;
    private Long userId;
    private BigDecimal totalPrice;
    private int orderNumber;
    private String shippingMethod;
    private String trackingNumber;
    private String notes;
    private String city;
}
