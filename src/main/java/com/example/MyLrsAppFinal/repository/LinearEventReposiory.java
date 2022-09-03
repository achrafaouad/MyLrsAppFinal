package com.example.MyLrsAppFinal.repository;

import com.example.MyLrsAppFinal.Models.Linear_Event;
import com.example.MyLrsAppFinal.Models.Ponctuel_Events;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.sql.Clob;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Repository
public interface LinearEventReposiory extends JpaRepository<Linear_Event,Long> {



    @Procedure(procedureName = "drowSegments")
    void procInsertEvent(Double pkd,Double pkf, String route_name1);

    //todo
    @Procedure(procedureName = "VerifydrowSegments")
    void verifySegment(Double pkDebut,Double pkFin, String route_name1,int voie1);

    @Procedure(procedureName = "addSegmentToRoute")
    void procAddSegmentToRoute(String routeJson);


    @Procedure(procedureName = "drowSegmentsByOffset")
    void procInsertLineEvent(Double pkDebut, Double pkFin, String route_name1, int voie1, String c1, String c2, String c3, Double d1, Double d2, Double d3, Date t1,Date t2,Date t3,Long Event_type_id);



    @Query( value ="select * from test144()", nativeQuery = true)
    List<String> getGeojsonValues();

    @Query( value ="select * from ofssetDrawPrev(:pkDebut,:pkFin,:route_name1,:voie1)", nativeQuery = true)
    List<String> getGeojsonValuesdrowPolygon(@Param("pkDebut") Double pkDebut ,@Param("pkFin")Double pkFin ,@Param("route_name1")String route_name1 ,@Param("voie1") int voie1);





    @Query( value ="select * from Table(MyIntersectionsLines(:thematique1,:thematique2))", nativeQuery = true)
    List<Map<String,?>> QueryData(@Param("thematique1") Double thematique1 ,@Param("thematique2")Double thematique2);



 @Query( value ="select * from Table(MyIntersections(:thematique1,:thematique2))", nativeQuery = true)
    List<Map<String,?>> QueryDataLP(@Param("thematique1") Double thematique1 ,@Param("thematique2")Double thematique2);


    @Query( value ="select jsonLine(:id2) from dual", nativeQuery = true)
    Clob getLineJson(@Param("id2") double id2);



    //todo

    @Procedure(procedureName = "insertLineByCoordEvent")
    void procNewlinesSep(Double pointx1,Double pointY1,Double pointx2,Double pointY2,String c1,String c2,String c3,Double d1,Double d2,Double d3,Date t1,Date t2,Date t3,Long event_type_id);


    @Procedure(procedureName = "insertLineByCoordEvent2")
    void procNewlinesSep2(Double pointx1,Double pointY1,Double pointx2,Double pointY2,String c1,String c2,String c3,Double d1,Double d2,Double d3,Date t1,Date t2,Date t3,String image,Long event_type_id);


    @Query( value ="select * from table(getLineEventJson(:id2))", nativeQuery = true)
    List<String> getLineEventJson(@Param("id2") double id2);


    @Query( value ="select * from linear_event where event_type_id = :id2", nativeQuery = true)
    List<Linear_Event> QueryData(@Param("id2") double id2);


    @Query( value ="select * from linear_event r WHERE r.date_ajoute BETWEEN (CURRENT_TIMESTAMP - 10) AND (CURRENT_TIMESTAMP)", nativeQuery = true)
    List<Linear_Event> findBetweenTwoDates();

}
