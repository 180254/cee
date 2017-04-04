package pl.cee.model;

import java.io.Serializable;

public class State implements Serializable {

    private User user;

    public State() {
    }

    public State(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}

