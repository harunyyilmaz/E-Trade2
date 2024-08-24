package kodlama.io.E_Trade2.dtos.responses;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class GetAllCategoriesResponse {

    private Long id;
    private String name;
    private String descriptions;

}
