package com.example.MyLrsAppFinal.Models;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;


@Repository
public class PonctuelEntityRepo {
    @Autowired
    private EntityManager entity;


    public List<Ponctuel_Events> QueryData(String data){
        List<Ponctuel_Events> resultList = new ArrayList<>();
        try {
            resultList = entity.createNativeQuery(data, Ponctuel_Events.class).getResultList();
        } catch (Exception e) {
        }
        return resultList;
    }


    public List<Ponctuel_Events> queryPonctuelDataForMap(String data){
        List<Ponctuel_Events> resultList = new ArrayList<>();
        try {
            resultList = entity.createNativeQuery(data, Ponctuel_Events.class).getResultList();
        } catch (Exception e) {
        }
        return resultList;
    }


      public List<String> getdistinctValuesproc(String data){
        List<String> resultList = new ArrayList<>();
        try {
            resultList = entity.createNativeQuery(data).getResultList();
            System.out.println("salim");
            System.out.println(resultList);
        } catch (Exception e) {
        }
        return resultList;
    }
}
