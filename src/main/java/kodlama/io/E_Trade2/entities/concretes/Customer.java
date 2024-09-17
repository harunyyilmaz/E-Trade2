package kodlama.io.E_Trade2.entities.concretes;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import kodlama.io.E_Trade2.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "customers")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Customer extends BaseEntity {

    @Column(name = "firstName")
    private String firstName;
    @Column(name = "lastName")
    private String lastName;
    @Column(name = "userName" , unique = true)
    private String userName;
    @Column(name = "password",unique = true)
    private String password;
    @Column(name = "email", unique = true)
    private String email;
    @Column(name = "age")
    private int age;
    @Column(name = "birthYear")
    private int birthYear;

    @OneToMany(mappedBy = "customer", fetch = FetchType.LAZY , cascade = CascadeType.ALL)
    private List<FavoriteProduct> productFavorites;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonIgnore
    @JoinColumn(name = "product_id")
    private Product product;

    @OneToMany(mappedBy = "customer", fetch = FetchType.LAZY ,cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Address> addresses;



}
