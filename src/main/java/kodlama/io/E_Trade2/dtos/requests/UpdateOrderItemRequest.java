package kodlama.io.E_Trade2.dtos.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateOrderItemRequest {

    @NotNull
    @NotBlank
    private Long id;
    @NotNull
    @NotBlank
    private int quantity;
    @NotNull
    @NotBlank
    private BigDecimal unitPrice;
    @NotNull
    @NotBlank
    private int orderNumber;
    @NotNull
    @NotBlank
    private String productName;
}
