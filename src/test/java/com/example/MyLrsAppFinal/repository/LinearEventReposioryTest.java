package com.example.MyLrsAppFinal.repository;

import com.example.MyLrsAppFinal.Models.*;
import com.example.MyLrsAppFinal.service.LrsService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@SpringBootTest
class LinearEventReposioryTest {
    private EventTypeRepository eventTypeRepository;
    private LinearEventReposiory linearEventReposiory;
    private LrsService lrsService;
    private PonctuelEventReposiotory ponctuelEventReposiotory;

    @Autowired
    LinearEventReposioryTest(EventTypeRepository eventTypeRepository, LinearEventReposiory linearEventReposiory, LrsService lrsService, PonctuelEventReposiotory ponctuelEventReposiotory) {
        this.eventTypeRepository = eventTypeRepository;
        this.linearEventReposiory = linearEventReposiory;
        this.lrsService = lrsService;
        this.ponctuelEventReposiotory = ponctuelEventReposiotory;
    }



    @Test
    public void get(){
        List <EventType> evt = this.eventTypeRepository.findAll();
        List<String> eventsName = new ArrayList<>();
        for(EventType tt: evt){
            eventsName.add(tt.getName());
        }

        Date dt=new Date();
        Integer year=dt.getYear();
        Double somme = 0D;
        List<Datagraph> dss = new ArrayList<>();
        for(String t : eventsName){
            Datagraph ds = new Datagraph();
            ds.setName(t);
            for(var i = 1;i<=12;i++){
                for (Ponctuel_Events ponctuel_events : this.ponctuelEventReposiotory.findAll()) {
                    if(ponctuel_events.getDate_ajoute() != null){
                        if((ponctuel_events.getDate_ajoute().getMonth()) == i && ponctuel_events.getDate_ajoute().getYear() == year){
                            if(ponctuel_events.getEvent_type().getName().equals(t)){
                                somme = somme +  1;
                            }
                        }
                    }
                }
                if(somme == 0 ){
                    somme =null;
                }
                ds.getData().add(somme);
                somme = 0D;
            }

            dss.add(ds);
        }
        System.out.println(dss);

    }


}