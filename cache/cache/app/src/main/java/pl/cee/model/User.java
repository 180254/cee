package pl.cee.model;

import java.io.Serializable;

public class User implements Serializable {

    private String nickname;

    public User() {
    }

    public User(String nickname) {
        this.nickname = nickname;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
}
