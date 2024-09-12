package kodlama.io.E_Trade2.entities.concretes;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import kodlama.io.E_Trade2.base.BaseEntity;
import kodlama.io.E_Trade2.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "brands")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Brand extends BaseEntity {

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "country")
    private String country;

    @Column(name = "contact")
    private String contactEmail;

    @Column(name = "phoneNumber")
    private String phoneNumber;

    @Enumerated( EnumType.STRING)
    @Column(name = "orderStatus" , nullable = false)
    private OrderStatus orderStatus;

    @OneToMany(mappedBy = "brand", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Product> products;
}
