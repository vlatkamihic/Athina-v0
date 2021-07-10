package com.example.athina.database_profile;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.athina.database_plan.Plan;

import java.util.List;

@Dao
public interface FeatureDao {

    @Query("SELECT * FROM `Feature`")
    List<Feature> getAllFeatures();

    @Insert
    void insertFeature(Feature... features);

    @Delete
    void delete(Feature feature);

}
