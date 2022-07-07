package com.example.MyLrsAppFinal.repository;

import com.example.MyLrsAppFinal.Models.LinearEntityRepo;
import com.example.MyLrsAppFinal.Models.Linear_Event;
import com.example.MyLrsAppFinal.Models.Ponctuel_Events;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface PonctuelEventReposiotory extends JpaRepository<Ponctuel_Events,Long> {


    @Procedure(procedureName = "locate_pointProcedure_Verify")
    void verifyPoint(Double event_position, String route_name1,int voie1);

    @Procedure(procedureName = "locate_pointProcedure")
    void procInsertEvent(Double event_position, String route_name1, int voie1, String c1, String c2, String c3, Double d1, Double d2, Double d3, Date t1, Date t2, Date t3, Long Event_type_id);

    @Query( value ="select  locate_pointProcedurePrev(:event_position,:route_name1,:voie1) from dual", nativeQuery = true)
    List<String> getGeojsonValuesdrowPoint(@Param("event_position") Double event_position , @Param("route_name1")String route_name1 , @Param("voie1") int voie1);

    @Procedure(procedureName = "insertByCoordEvent")
    void procNewPointSep(Double pointx,Double pointY,String c1,String c2,String c3,Double d1,Double d2,Double d3,Date t1,Date t2,Date t3,Long event_type_id);

   @Procedure(procedureName = "insertByCoordEvent2")
    void procNewPointSep2(Double pointx,Double pointY,String c1,String c2,String c3,Double d1,Double d2,Double d3,Date t1,Date t2,Date t3,String image,Long event_type_id);


    @Query( value ="select * from Table(MyIntersectionPOintToPoint(:thematique1,:thematique2))", nativeQuery = true)
            List<Map<String,?>> QueryDataS(@Param("thematique1") Double thematique1 , @Param("thematique2")Double thematique2);



  @Query( value ="select * from Table(MyIntersectionPOintToPointP(:thematique1,:thematique2;:pkEv))", nativeQuery = true)
            List<Map<String,?>> QueryDataP(@Param("thematique1") Double thematique1 , @Param("thematique2")Double thematique2 ,@Param("pkEv")Double pkEv );


@Query( value ="select jsonPoint(:id2) from dual", nativeQuery = true)
           String getPointJson(@Param("id2") double id2);

@Query( value ="select * from table(getPointEventJson(:id2))", nativeQuery = true)
           List<String> getPointEventJson(@Param("id2") double id2);


        @Query( value ="select * from ", nativeQuery = true)
           List<String> getdistinctValuesproc(@Param("id2") double id2);



    @Query( value ="select * from ponctuel_events where event_type_id = :id2", nativeQuery = true)
    List<Ponctuel_Events> QueryData(@Param("id2") double id2);

    @Query( value ="select * from ponctuel_events r WHERE r.date_ajoute BETWEEN (CURRENT_TIMESTAMP - 10) AND (CURRENT_TIMESTAMP)", nativeQuery = true)
    List<Ponctuel_Events> findBetweenTwoDates();



}

