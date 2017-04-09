package pl.cee.service;

import pl.cee.model.Credentials;
import pl.cee.model.User;

import java.util.HashMap;
import java.util.Map;

public class AccountsProviderImpl implements AccountsProvider {

    private final Map<Credentials, User> accounts;

    public AccountsProviderImpl() {
        this.accounts = new HashMap<>(3);
        this.accounts.put(new Credentials("admin", "admin"), new User("administrator"));
        this.accounts.put(new Credentials("student", "student"), new User("student"));
        this.accounts.put(new Credentials("mr", "bean"), new User("Mr. Bean"));
    }

    @Override
    public User getUser(Credentials credentials) {
        return accounts.get(credentials);
    }
}
