package pos.examples.soap.stateless.Model;


import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;

@Data
@AllArgsConstructor
@Entity
@Table(name="user")
public class User {
    @Id
    @GeneratedValue(strategy =  GenerationType.AUTO)
    private Integer id;

    @NotNull
    private String first_name;

    @NotNull
    private String last_name;

    @NotNull
    private Integer age;

    public User(){
    }

    public User(String first_name, String last_name, Integer age){
        this.first_name = first_name;
        this.last_name = last_name;
        this.age = age;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}
