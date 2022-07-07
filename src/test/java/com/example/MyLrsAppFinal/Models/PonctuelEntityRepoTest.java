package com.example.MyLrsAppFinal.Models;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

@SpringBootTest
class PonctuelEntityRepoTest {

    private LinearEntityRepo linearEntityRepo;
@Autowired
    PonctuelEntityRepoTest(LinearEntityRepo linearEntityRepo) {
        this.linearEntityRepo = linearEntityRepo;
    }

    @Test
    public void test() throws IOException {
        String query = "select Distinct route_name from linear_event r";
        System.out.println(query);
       this.linearEntityRepo.getdistinctValuesproc(query).forEach(System.out::println);



    }


}