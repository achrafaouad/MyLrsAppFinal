package com.example.MyLrsAppFinal.repository;

import com.example.MyLrsAppFinal.Models.EventType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

@SpringBootTest
class EventParamsRepositoryTest {
    private EventParamsRepository eventParamsRepository;

    @Autowired
    EventParamsRepositoryTest(EventParamsRepository eventParamsRepository) {
        this.eventParamsRepository = eventParamsRepository;
    }






}