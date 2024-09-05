package kodlama.io.E_Trade2.entities.concretes;

import jakarta.persistence.*;
import kodlama.io.E_Trade2.base.BaseEntity;
import kodlama.io.E_Trade2.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Order extends BaseEntity {

    @Column(name = "orderNumber")
    private int orderNumber;
    @Column(name = "totalPrice",nullable = false)
    private BigDecimal totalPrice;
    @Column(name = "shippingMethod")
    private String shippingMethod;
    @Column(name = "trackingNumber")
    private String trackingNumber;
    @Column(name = "notes")
    private String notes;
    @Column(name = "city")
    private String city;

    @Enumerated(EnumType.STRING)
    @Column(name = "orderStatus" , nullable = false)
    private OrderStatus orderStatus;

    @OneToMany(mappedBy = "order" ,fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private List<OrderItem> orderItems;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;


}
