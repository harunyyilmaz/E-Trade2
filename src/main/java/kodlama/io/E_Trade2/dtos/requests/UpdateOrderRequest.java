package kodlama.io.E_Trade2.dtos.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import kodlama.io.E_Trade2.entities.concretes.OrderItem;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.parameters.P;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateOrderRequest {

    @NotNull
    @NotBlank
    private Long id;
    @NotNull
    @NotBlank
    private Long userId;
    @NotNull
    @NotBlank
    private int orderNumber;

    private String shippingMethod;

    @Size(max = 200)
    private String notes;
    @NotNull
    @NotBlank
    private List<OrderItem> orderItems;
}
