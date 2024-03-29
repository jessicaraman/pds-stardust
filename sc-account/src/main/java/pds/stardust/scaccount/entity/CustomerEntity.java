package pds.stardust.scaccount.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.data.annotation.Id;

/**
 * CustomerEntity : represents a logged customer
 */
@ApiModel(description = "Details about customer")
public class CustomerEntity {

    @Id
    @ApiModelProperty(notes = "The unique id of customer")
    private Integer id;
    @ApiModelProperty(notes = "The customer's name")
    private String name;
    @ApiModelProperty(notes = "The customer's surname")
    private String surname;
    @ApiModelProperty(notes = "The customer's image")
    private String image;
    @ApiModelProperty(notes = "The customer's username")
    private String username;
    @ApiModelProperty(notes = "The customer's password")
    private String password;
    @ApiModelProperty(notes = "The customer's token")
    private String token;

    public CustomerEntity(Integer id, String name, String surname, String image, String username, String password, String token) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.image = image;
        this.username = username;
        this.password = password;
        this.token = token;
    }

    @Override
    public String toString() {
        return "CustomerEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", image='" + image + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", token='" + token + '\'' +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
