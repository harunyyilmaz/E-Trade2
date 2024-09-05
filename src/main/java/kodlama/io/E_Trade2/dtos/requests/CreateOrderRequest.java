package kodlama.io.E_Trade2.dtos.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import kodlama.io.E_Trade2.entities.concretes.OrderItem;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateOrderRequest {


    @NotNull
    @NotBlank
    private Long userId;
    @NotNull
    @NotBlank
    private int orderNumber;
    private String shippingMethod;
    @NotNull
    @NotBlank
    private BigDecimal totalPrice;
    @Size(max = 200)
    private String notes;
    private String city;
    @NotNull
    @NotBlank
    private List<OrderItem> orderItems;

}
