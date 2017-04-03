package pl.cee.model;

import java.io.Serializable;

public class UserInfo implements Serializable {

    private String nickname;

    public UserInfo() {
    }

    public UserInfo(String nickname) {
        this.nickname = nickname;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
}
