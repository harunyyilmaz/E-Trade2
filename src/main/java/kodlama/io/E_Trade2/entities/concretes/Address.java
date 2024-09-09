package kodlama.io.E_Trade2.entities.concretes;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import kodlama.io.E_Trade2.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "addresses")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Address extends BaseEntity {


    @Column(name = "streetAddress")
    private String streetAddress;
    @Column(name = "city")
    private String city;
    @Column(name = "state")
    private String state; // eyalet, il, bölge
    @Column(name = "postalCode")
    private String postalCode;
    @Column(name = "country")
    private String country;

    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_Id")
    @JsonIgnore
    private Customer customer;


}
