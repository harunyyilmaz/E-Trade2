package kodlama.io.E_Trade2.entities.concretes;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import kodlama.io.E_Trade2.base.BaseEntity;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "roles")
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Role extends BaseEntity {

    @Column(name = "name",unique = true)
    @JsonProperty("name")
    private String name;
    @Column(name = "description")
    @JsonProperty("description")
    private String description;

    @ManyToMany(mappedBy = "roles",cascade = {CascadeType.MERGE,CascadeType.PERSIST})
    private Set<User> users=new HashSet<>();

    public Role(@JsonProperty("id") Long id,
                @JsonProperty("updateDate") LocalDateTime updateDate,
                @JsonProperty("createAt") LocalDateTime createAt,
                @JsonProperty("name") String name,
                @JsonProperty("description") String description,
                @JsonProperty("users") Set<User> users){
        super(id, updateDate, createAt);
        this.name = name;
        this.description = description;
        this.users=users;
    }
    public Role(){

    }
}
