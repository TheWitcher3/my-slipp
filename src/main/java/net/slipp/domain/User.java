package net.slipp.domain;

import javax.persistence.*;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false, length = 20, unique = true)
    private String userId;
    private String password;
    private String name;
    private String email;

    @Override
    public String toString() {
        return "User{" +
                "userId='" + userId + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                '}';
    }

    public void updateUser(User user) {
        this.userId = user.userId;
        this.password = user.password;
        this.email = user.email;
        this.name = user.name;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public Long getId() {
        return id;
    }

    public boolean matchPassword(String newPassword) {
        if(newPassword == null) {
            return false;
        }
        return newPassword.equals(password);
    }

    public boolean matchId(String newId) {
        if(newId == null) {
            return false;
        }
        return newId.equals(id);
    }

    public String getUserId() {
        return userId;
    }
}
