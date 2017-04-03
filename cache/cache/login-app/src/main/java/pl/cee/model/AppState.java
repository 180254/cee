package pl.cee.model;

import java.io.Serializable;

public class AppState implements Serializable {

    private UserInfo userInfo;

    public AppState() {
    }

    public AppState(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }
}

