package com.example.MyLrsAppFinal.repository;

import com.example.MyLrsAppFinal.Models.MyUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepo extends JpaRepository<MyUser,Long> {
    MyUser findByUsername(String username);

}
