package com.example.MyLrsAppFinal.service;

import com.example.MyLrsAppFinal.Models.MyUser;
import com.example.MyLrsAppFinal.Models.Role;

import java.util.List;

public interface UserService {

    MyUser saveUser(MyUser user);
    MyUser save(MyUser user);
    Role saveRole(Role Role);
    void addRoleToUser(String username,String rolName);
    MyUser getUser(String username);
    List<MyUser> getUsers();

}
