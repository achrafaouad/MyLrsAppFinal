package com.example.MyLrsAppFinal.repository;

import com.example.MyLrsAppFinal.Models.Ponctuel_Events;
import com.example.MyLrsAppFinal.Models.ponctuelEventWithGeometry;
import com.example.MyLrsAppFinal.service.LrsService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;


@SpringBootTest
class PonctuelEventReposiotoryTest {

    private PonctuelEventReposiotory ponctuelEventReposiotory;
    private final LrsService lrsService;

  @Autowired
    PonctuelEventReposiotoryTest(PonctuelEventReposiotory ponctuelEventReposiotory, LrsService lrsService) {
        this.ponctuelEventReposiotory = ponctuelEventReposiotory;
      this.lrsService = lrsService;
  }


    @Test
    public void test(){
        List<ponctuelEventWithGeometry> newestGeometry = new ArrayList<>();
        String request = "select  * from ponctuel_Events where voie = 1 and event_type_id = 1";

        for( Ponctuel_Events ver : this.lrsService.queryPonctuelData(request)){
            ponctuelEventWithGeometry dd = ponctuelEventWithGeometry.builder().id(ver.getId()).route_name(ver.getRoute_name()).pkEvent(ver.getPkEvent()).voie(ver.getVoie()).event_name(ver.getEvent_type().getName()).route_id(ver.getRoute().getRoute_id()).build();
            String varr = this.ponctuelEventReposiotory.getPointJson(dd.getId());
            dd.setJsond(varr);
            newestGeometry.add(dd);
        }
        newestGeometry.forEach(System.out::println);

    }
}