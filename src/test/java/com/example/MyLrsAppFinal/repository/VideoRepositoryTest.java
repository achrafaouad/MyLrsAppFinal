package com.example.MyLrsAppFinal.repository;

import com.example.MyLrsAppFinal.Models.Lrs_routes;
import com.example.MyLrsAppFinal.Models.Video;
import com.example.MyLrsAppFinal.service.LrsService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SpringBootTest
class VideoRepositoryTest {

    private LrsService lrsService;
    private EventTypeRepository eventTypeRepository;
    private VideoRepository videoRepository;
    private LrsRoutesRepository lrsRoutesRepository;

    @Autowired
    public VideoRepositoryTest(LrsService lrsService1, EventTypeRepository eventTypeRepository, VideoRepository videoRepository, LrsRoutesRepository lrsRoutesRepository) {
        this.lrsService = lrsService1;
        this.eventTypeRepository = eventTypeRepository;
        this.videoRepository = videoRepository;
        this.lrsRoutesRepository = lrsRoutesRepository;
    }


    @Test
    public void test()  {

        List<Lrs_routes> lrss = this.lrsRoutesRepository.findByNames("Route1");
        List<Long> routes_id = new ArrayList<>();
        List<Video> videos = new ArrayList<>();

        for (Lrs_routes lee : lrss){
            routes_id.add(lee.getRoute_id());
        }


        for (Long id : routes_id){
            List<Video> vv =  this.videoRepository.queryByRoute(id,1);
            for(Video v : vv){
                videos.add(v);
                System.out.println(v);
            }
        }

        videos.forEach(System.out::println);

    }

}