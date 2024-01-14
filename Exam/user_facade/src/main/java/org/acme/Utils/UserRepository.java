package org.acme.Utils;

import org.acme.Interfaces.IUserRepository;
import org.acme.Models.User;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class UserRepository implements IUserRepository {
    HashMap<String, User> users = new HashMap<>();
    private String getUserID(){
        return String.valueOf(users.size() +1);
    }
    @Override
    public void addUser(User user) {
        users.put(getUserID(), user);
    }

    @Override
    public User getUser(String userID) {
        return users.get(userID);
    }

    public List<User> getUsers() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getPayments'");
    }

    @Override
    public void removeUser(String userID) {
    }
}
