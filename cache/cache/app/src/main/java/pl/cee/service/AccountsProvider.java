package pl.cee.service;

import pl.cee.model.Credentials;
import pl.cee.model.User;

public interface AccountsProvider {

    User getUser(Credentials credentials);
}
