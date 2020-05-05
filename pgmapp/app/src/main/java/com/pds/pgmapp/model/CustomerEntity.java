package com.pds.pgmapp.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Customer Entity
 */
public class CustomerEntity implements Parcelable {

    public static final Creator<CustomerEntity> CREATOR = new Creator<CustomerEntity>() {
        @Override
        public CustomerEntity createFromParcel(Parcel in) {
            return new CustomerEntity(in);
        }

        @Override
        public CustomerEntity[] newArray(int size) {
            return new CustomerEntity[size];
        }
    };
    private Integer id;
    private String name;
    private String surname;
    private String image;
    private String username;
    private String password;
    private String token;

    public CustomerEntity() {

    }

    public CustomerEntity(Integer id, String name, String surname, String image, String username, String password, String token) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.image = image;
        this.username = username;
        this.password = password;
        this.token = token;
    }

    protected CustomerEntity(Parcel in) {
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readInt();
        }
        name = in.readString();
        surname = in.readString();
        image = in.readString();
        username = in.readString();
        password = in.readString();
        token = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (id == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(id);
        }
        dest.writeString(name);
        dest.writeString(surname);
        dest.writeString(image);
        dest.writeString(username);
        dest.writeString(password);
        dest.writeString(token);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
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
}
