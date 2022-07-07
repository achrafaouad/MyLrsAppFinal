package com.example.MyLrsAppFinal.repository;

import com.example.MyLrsAppFinal.Models.EventType;
import com.example.MyLrsAppFinal.Models.Event_params;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface EventParamsRepository extends JpaRepository<Event_params,Long> {


    List<Event_params> findByEventType(EventType eventType);


    @Query(value="delete from event_params where event_id = :id ", nativeQuery = true)
    void deleteByMyid(@Param("id") double id);

}
