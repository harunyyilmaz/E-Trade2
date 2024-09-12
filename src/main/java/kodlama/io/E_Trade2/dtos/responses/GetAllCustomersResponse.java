package kodlama.io.E_Trade2.dtos.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetAllCustomersResponse {

    private Long id;
    private LocalDateTime createAt;
    private LocalDateTime updateDate;
    private String firstName;
    private String lastName;
    private String userName;
    private String email;
    private int age;
    private int brithYear;
}
