package nl.hsleiden.studyprogressapp.database.DAOs;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import nl.hsleiden.studyprogressapp.database.Models.Course;

@Dao
public interface CourseDAO {

    @Query("SELECT * FROM course")
    LiveData<List<Course>> getAllCourses();

    @Insert
    void insertAllCourses(Course... courses);

    @Query("SELECT COUNT(id) FROM course")
    int getNumberOfRows();

}
