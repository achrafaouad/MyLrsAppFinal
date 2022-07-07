package com.example.MyLrsAppFinal.Models;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

@Repository
public class LinearEntityRepo {
    @Autowired
    private EntityManager entity;


    public List<Linear_Event> QueryData(String data){
        List<Linear_Event> resultList = new ArrayList<>();
        try {
            resultList = entity.createNativeQuery(data, Linear_Event.class).getResultList();
        } catch (Exception e) {
        }
        return resultList;
    }

    public List<String> getdistinctValuesproc(String data){

        System.out.println(data);
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
