package com.example.MyLrsAppFinal.Models;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class LinearEntityRepoTest {

    @Autowired
    private LinearEntityRepo linearEntityRepo;


    @Test
    void getdata(){
        List<Linear_Event> resultList = this.linearEntityRepo.QueryData("SELECT * FROM Linear_Event");

        resultList.forEach(System.out::println);
    }

}