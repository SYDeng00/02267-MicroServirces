package org.acme.Services;

import org.acme.Models.User;
import org.acme.Utils.Helper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserService {
    List<User> userList = new ArrayList<>();

    public List<User> getAllCustomers(){
        return userList;
    }
    public boolean getCustomer(String bankAcc){
        Optional<User> opCustomers = userList.stream().filter(t->t.getBankAccount().equals(bankAcc)).findFirst();
        if(opCustomers.isPresent()){
            return true;
        }
        else{
            return false;
        }
    }
    public int addNewCustomer(User newUser) {

        if (getCustomer(newUser.getBankAccount())){
            int nextId = userList.size() + 1;
            newUser.setId(String.valueOf(nextId));
            userList.add(newUser);
            return nextId;
        }
        else {
            return -1;
        }
    }

    public List<User> getAllMerchants() {
        List<User> merchantAll = userList.stream().filter(t->t.getUserType().equals(Helper.USER_TYPE_MERCHANT)).toList();
        return merchantAll;
    }
}
