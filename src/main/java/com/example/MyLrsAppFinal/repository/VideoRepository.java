package com.example.MyLrsAppFinal.repository;

import com.example.MyLrsAppFinal.Models.Symbology;
import com.example.MyLrsAppFinal.Models.Video;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface VideoRepository  extends JpaRepository<Video,Long> {


    @Query(value="select *  from video where ROUTE_ID = :id And voie = :voie", nativeQuery = true)
    List<Video> queryByRoute(@Param("id") double id,@Param("voie") double voie);
}
