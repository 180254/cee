package pl.cee.service;

import pl.cee.model.Credentials;
import pl.cee.model.User;

import java.util.Map;

public interface AccountsProvider {

    Map<Credentials, User> getAccounts();

    User getUser(Credentials credentials);
}
