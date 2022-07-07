package com.example.MyLrsAppFinal.repository;

import com.example.MyLrsAppFinal.Models.Province;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.Clob;

public interface ProvinceRepository extends JpaRepository<Province,Long> {


    @Query( value ="select jsonProvince(:name2) from dual", nativeQuery = true)
    Clob getJson(@Param("name2") String name2);

    public Province findProvinceByName(String name);

}
