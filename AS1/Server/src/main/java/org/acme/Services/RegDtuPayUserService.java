package org.acme.Services;

import org.acme.Models.DtuPayUser;
import org.acme.Models.Trade;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RegDtuPayUserService {
    List<DtuPayUser> userList = new ArrayList<>(Arrays.asList(
            new DtuPayUser(1, "Yingli","cprYingli","bankAccount_Yingli"),
            new DtuPayUser(2,"Tama","cprTama","bankAccount_Tama"),
            new DtuPayUser(3,"Jiahe","cprJiahe","bankAccount_Jiahe"),
            new DtuPayUser(4,"Siyuan","cprSiyuan","bankAccount_Siyuan"),
            new DtuPayUser(5,"Xinyi","cprXinyi","bankAccount_Xinyi")
    ));
    public List<DtuPayUser> getAllDtuPayUser(){
        return userList;
    }
    public int addNewDtuPayUser(DtuPayUser newUser) {
        int nextId = userList.size() + 1;
        newUser.setId(nextId);
        userList.add(newUser);
        return nextId;
    }
}
