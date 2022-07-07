package com.example.MyLrsAppFinal.service;

import com.example.MyLrsAppFinal.Models.MyUser;
import com.example.MyLrsAppFinal.Models.Role;
import com.example.MyLrsAppFinal.repository.RoleRepo;

import com.example.MyLrsAppFinal.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserServiceImplimentation implements UserService, UserDetailsService {
    private final UserRepo userRepo;
    private final RoleRepo roleRepo;
    private final BCryptPasswordEncoder passwordEncoder;


    //tell spring how to find a user and return user of spring security
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        MyUser user = userRepo.findByUsername(username);
        if(user == null){
            log.info("user not found   ");
            throw new UsernameNotFoundException("user not found   ");
        }else{
            log.info("user  found   ");
        }

        Collection<SimpleGrantedAuthority> auth = new ArrayList<>();
        user.getRoles().forEach(role -> {
            auth.add(new SimpleGrantedAuthority(role.getName()));
        });

        return new org.springframework.security.core.userdetails.User(user.getUsername(),user.getPassword(),auth);
    }




    @Override
    public MyUser saveUser(MyUser user) {
        log.info("save user to database");
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepo.save(user);
    }

    @Override
    public MyUser save(MyUser user) {
        return this.userRepo.save(user);
    }

    @Override
    public Role saveRole(Role role) {
        log.info("save role  to database");
        return roleRepo.save(role);
    }

    @Override
    public void addRoleToUser(String username, String rolName) {
        MyUser myUser = userRepo.findByUsername(username);
        Role role = roleRepo.findByName(rolName);
        log.info("adding  role {} to user {}",rolName,username);
        myUser.getRoles().add(role);

    }

    @Override
    public MyUser getUser(String username) {
        log.info("fetching   user {}",username);
        return userRepo.findByUsername(username);
    }

    @Override
    public List<MyUser> getUsers() {
        Role rr = new Role(1L,"simple_user");
        List<MyUser> test = new ArrayList<>();
        for(MyUser u : userRepo.findAll()){
            if(u.getRoles().contains(rr)){
                test.add(u);
            }

        }
        return test;
    }


}