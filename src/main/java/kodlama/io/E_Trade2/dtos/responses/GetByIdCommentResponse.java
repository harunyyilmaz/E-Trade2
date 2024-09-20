package kodlama.io.E_Trade2.dtos.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetByIdCommentResponse {

    private Long id;
    private String productName;
    private String customerName;
    private LocalDateTime createAt;
    private String content;
}
