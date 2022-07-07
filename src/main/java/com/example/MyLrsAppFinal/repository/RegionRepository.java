package com.example.MyLrsAppFinal.repository;

import com.example.MyLrsAppFinal.Models.EventType;
import com.example.MyLrsAppFinal.Models.Region;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RegionRepository extends JpaRepository<Region,Long> {
}
