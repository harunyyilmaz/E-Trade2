package kodlama.io.E_Trade2.dtos.requests;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateProductFavoriteRequest {

    @NotNull
    private Long customerId;
    @NotNull
    private Long productId;
}
