package com.example.athina.database_plan;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface PlanDao {

    @Query("SELECT * FROM `Plan`")
    List<Plan> getAllPlans();

    @Insert
    void insertFeature(Plan... plans);

    @Delete
    void delete(Plan plan);
}
