package Domain;

import java.io.Serializable;
import java.util.Objects;

public class User implements Serializable {
    private int idUser;
    private String username;
    private String password;
    private int score;

    public User(String username, String password,int score) {
        this.username = username;
        this.password = password;
        this.score=score;
    }

    public User() {
    }

    public User(int idUser, String username, String password, int score) {
        this.idUser = idUser;
        this.username = username;
        this.password = password;
        this.score = score;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
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

    public void setScore(int score) {
        this.score = score;
    }

    public int getScore() {
        return score;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return  username.equals(user.username) &&
                password.equals(user.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, password);
    }
}
