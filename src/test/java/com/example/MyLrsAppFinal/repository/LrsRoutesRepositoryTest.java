package com.example.MyLrsAppFinal.repository;

import com.example.MyLrsAppFinal.Models.Lrs_routes;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class LrsRoutesRepositoryTest {
    @Autowired
    private LrsRoutesRepository lrsRoutesRepository;

    @Test
    public void giv(){

        System.out.println(this.lrsRoutesRepository.findById(1564L));
    }

}