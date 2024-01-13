package org.acme.Interfaces;

import org.acme.Models.User;

import java.util.List;
import java.util.UUID;

public interface IUserRepository {
    void addUser(User user);
    User getUser(String userID);
    List<User> getUsers();
    void removeUser(String userID);

}
