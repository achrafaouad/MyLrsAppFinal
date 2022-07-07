package com.example.MyLrsAppFinal.repository;

import com.example.MyLrsAppFinal.Models.CoordinateSynoptique;
import com.example.MyLrsAppFinal.Models.DataSynoptique;
import com.example.MyLrsAppFinal.Models.EventType;
import com.example.MyLrsAppFinal.Models.Linear_Event;
import com.example.MyLrsAppFinal.service.LrsService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;


@SpringBootTest
class LinearEventReposioryTest {
    private EventTypeRepository eventTypeRepository;
    private LinearEventReposiory linearEventReposiory;
    private LrsService lrsService;

    @Autowired
    LinearEventReposioryTest(EventTypeRepository eventTypeRepository, LinearEventReposiory linearEventReposiory, LrsService lrsService) {
        this.eventTypeRepository = eventTypeRepository;
        this.linearEventReposiory = linearEventReposiory;
        this.lrsService = lrsService;
    }



    @Test
    public void get(){
            int year = 2022;
            String rtName = "Route1";
            int voie = 1;
            List <EventType> evt = this.eventTypeRepository.findAll();
            List<String> eventsName = new ArrayList<>();
            for(EventType tt: evt){
                eventsName.add(tt.getName());
            }


      List<DataSynoptique> dss = new ArrayList<>();

            for(String t : eventsName){
                DataSynoptique ds = new DataSynoptique();
                  ds.setName(t);
                    for (Linear_Event linear_event : this.linearEventReposiory.findAll()) {
                        if((linear_event.getDate_ajoute().getYear()+1900) == year){

                        if(linear_event.getVoie() == voie){

                        if(linear_event.getRoute_name().equals(rtName)){
                            System.out.println("hana dkhelt l rtName");
                        if(linear_event.getEvent_type().getName().equals(t)){
                            CoordinateSynoptique crrd = new CoordinateSynoptique();
                            crrd.setX(t);
                            crrd.setY(List.of(linear_event.getPkd(),linear_event.getPkf()));
                            ds.getData().add(crrd);

                    }
                    }
                    }
                    }
                }
                    dss.add(ds);
            }


            System.out.println(dss);


    }


}