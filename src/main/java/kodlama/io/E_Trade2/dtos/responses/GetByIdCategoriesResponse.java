package kodlama.io.E_Trade2.dtos.responses;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Data
@Getter
@Setter
public class GetByIdCategoriesResponse {

    private LocalDateTime createAt;
    private LocalDateTime updateDate;
    private Long id;
    private String name;
    private String descriptions;
}
