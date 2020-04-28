package com.pds.pgmapp.model;

/**
 * Customer Entity
 */
public class CustomerEntity {

    private String id;
    private String name;
    private String surname;
    private String image;
    private String username;
    private String password;
    private String token;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    @Override
    public String toString() {
        return "CustomerEntity{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", image='" + image + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", token='" + token + '\'' +
                '}';
    }

    public CustomerEntity() {

    }

    public CustomerEntity(String id, String name, String surname, String image, String username, String password, String token) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.image = image;
        this.username = username;
        this.password = password;
        this.token = token;
    }
}
