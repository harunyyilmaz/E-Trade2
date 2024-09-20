package kodlama.io.E_Trade2.dtos.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateCommentRequest {

    @NotNull
    private Long id;
    @NotNull
    private Long productId;
    @NotNull
    private Long customerId;
    @NotBlank
    private String content;
}
