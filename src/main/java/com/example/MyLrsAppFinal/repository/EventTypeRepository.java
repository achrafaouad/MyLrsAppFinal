package com.example.MyLrsAppFinal.repository;

import com.example.MyLrsAppFinal.Models.EventType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface EventTypeRepository extends JpaRepository<EventType,Long> {


    @Query(value="select * from event_type t where t.name = ?1" , nativeQuery = true)
    EventType findEventTypeName(String eventName);

    @Query(value="select t.name from event_type t " , nativeQuery = true)
    List<String> indEventsTypeName();


    @Query(value="delete from event_type where id = :id ", nativeQuery = true)
    void deleteByMyid(@Param("id") double id);

}
