package kodlama.io.E_Trade2.entities.concretes;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import kodlama.io.E_Trade2.base.BaseEntity;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "users")
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class User extends BaseEntity {

    @Column(name = "firstName")
    @JsonProperty("firstName")
    private String firstName;
    @Column(name = "lastName")
    @JsonProperty("lastName")
    private String lastName;
    @Column(name = "userName", unique = true , nullable = false)
    @JsonProperty("userName")
    private String userName;
    @Column(name = "email",unique = true , nullable = false)
    @JsonProperty("email")
    private String email;
    @Column(name = "password",unique = true , nullable = false)
    @JsonProperty("password")
    private String password;
    @Column(name = "age")
    @JsonProperty("age")
    private int age;
    @Column(name = "birthYear")
    @JsonProperty("birthYear")
    private int birthYear;
    @Column(name = "isEnable")
    @JsonProperty("isEnable")
    private boolean isEnable;

    @ManyToMany(fetch = FetchType.EAGER , cascade = {CascadeType.MERGE , CascadeType.PERSIST})
    @JoinTable(name = "user_role",
            joinColumns = @JoinColumn (name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    //BaseEntity özelliklerini ayarlamayı gerektiren özel bir kullanım senaryosu yoksa gereksizdir.
    //Yeni bir User nesnesi oluşturulurken sadece temel bilgilerin ayarlanması gerektiği durumlar.
    public User(Long id, LocalDateTime updateDate, LocalDateTime createAt) {
        super(id, updateDate, createAt);
    }

    // Eğer User sınıfını oluştururken id, updateDate, createdAt dışında kalan tüm alanları da
    // sıklıkla ayarlamanız gerekiyorsa, tek yapıcı olarak aşağıdaki yapıcıyı kullanmak
    // yeterli olacaktır:
    public User(@JsonProperty("id") Long id,
                @JsonProperty("updateDate") LocalDateTime updateDate,
                @JsonProperty("createAt") LocalDateTime createAt,
                @JsonProperty("firstName") String firstName,
                @JsonProperty("lastName") String lastName,
                @JsonProperty("userName") String userName,
                @JsonProperty("email") String email,
                @JsonProperty("password") String password,
                @JsonProperty("age") int age,
                @JsonProperty("birthYear") int birthYear,
                @JsonProperty("isEnable") boolean isEnable,
                @JsonProperty("roles") Set<Role> roles) {
        super(id, updateDate, createAt);
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.age = age;
        this.birthYear = birthYear;
        this.isEnable = isEnable;
        this.roles=roles;
    }

    public User() {

    }
}
