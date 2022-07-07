package com.example.MyLrsAppFinal.repository;

import com.example.MyLrsAppFinal.Models.Lrs_routes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface LrsRoutesRepository extends JpaRepository<Lrs_routes,Long> {
    @Procedure(procedureName = "IndexingLinetEvent")
    void indexingLineEvent();



    @Query(value="select * from lrs_routes r where r.route_name=?1 ", nativeQuery = true)
     public Lrs_routes findByName(String name);


    @Query(value="select * from lrs_routes r where r.route_name=?1 ", nativeQuery = true)
     public List<Lrs_routes> findByNames(String name);


    @Query(value="select validatRoute(:mesure, :voie1,:route_name1) from dual", nativeQuery = true)
     public Long getRoute(@Param("route_name1") String route_name1,@Param("mesure") double mesure,@Param("voie1") int voie1);


    @Procedure(procedureName = "IndexingPointEvent")
    void indexingPointEvent();


    @Procedure(procedureName = "IndexingLrs")
    void indexing();



    @Procedure(procedureName = "addNewRoute")
    void procNewSegmentSep(String routeJson,String name1,Double voie, Double reference_id);


    @Procedure(procedureName = "procNewaddNewRef")
    void procNewaddNewRef(String route_name1,Double pkd1,Double pkf1,int voie1,String geometry1,int reference_id1);


    @Procedure(procedureName = "addSectioncsv")
    void procaddSectioncsv(String routeJson,int  reference1 ,int voie1,String route_name1);

    @Procedure(procedureName = "procNewaddNewRefVerify")
    void procNewaddNewRefVerify(String route_name1,Double pkd1,Double pkf1,int voie1,String geometry1,Long province1,int reference_id1);

    @Procedure(procedureName = "editpkdPkf")
    void changePKByAttproc(Double pkd1,Double pkf1,String route1, Double pkd2,Double pkf2,int voie1);


    @Procedure(procedureName = "deleteByINfoOfRoute")
    void deleteByAttproc(Double pkd1,Double pkf1,String route1,int voie1);

    @Procedure(procedureName = "editpkdPkfById")
    void changePKByIDproc(Long id1,Double pkd2,Double pkf2);

    @Procedure(procedureName = "deleteSectionById")
    void deleteByIDproc(Long id1);

    @Query(value = "select getcurrentvideoJson(:mesure, :voie1,:route_name1) from dual" , nativeQuery = true )
    String getPositionfeature(@Param("mesure") Double mesure,@Param("voie1") int voie1,@Param("route_name1") String route_name1);

    @Query(value = "select getPdMesure(:route , :pointx,:pointY,:voie1) from dual" , nativeQuery = true )
    Long getPositionMesure(@Param("route") String route,@Param("pointx") Double x,@Param("pointY") Double y,@Param("voie1") int voie1);

    @Modifying
    @Query(value = "update lrs_routes set ROUTE_NAME = :name where ROUTE_NAME = :name1" , nativeQuery = true )
    Long changeName(@Param("name") String name,@Param("name1") String name1);


    @Query( value ="select * from TABLE (bboxFunctionProvince(:name2))", nativeQuery = true)
    List<Double> getBBox(@Param("name2") String name2);

    @Query( value ="select distinct route_name from lrs_routes", nativeQuery = true)
    List<String> getAllROuteNames();

}
