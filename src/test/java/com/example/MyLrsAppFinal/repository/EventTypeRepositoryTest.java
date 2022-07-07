package com.example.MyLrsAppFinal.repository;

import com.example.MyLrsAppFinal.Models.Event_params;
import com.example.MyLrsAppFinal.service.LrsService;
import com.example.MyLrsAppFinal.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.List;

@SpringBootTest
class EventTypeRepositoryTest {

    private LrsService lrsService;
    private UserService userService;
    private EventTypeRepository eventTypeRepository;

    @Autowired
    public EventTypeRepositoryTest(LrsService lrsService1, UserService userService, EventTypeRepository eventTypeRepository) {
        this.lrsService = lrsService1;
        this.userService = userService;
        this.eventTypeRepository = eventTypeRepository;
    }


    @Test
    public void show(){
        List<Event_params> tt = this.lrsService.getParams("accident");
        tt.forEach(System.out::println);
    }


    @Test
    public void test() throws IOException {
        this.userService.getUsers().forEach(System.out::println);

    }

}