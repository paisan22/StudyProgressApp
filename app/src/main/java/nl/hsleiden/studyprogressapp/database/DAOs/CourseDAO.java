package nl.hsleiden.studyprogressapp.database.DAOs;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import nl.hsleiden.studyprogressapp.database.Models.Course;

@Dao
public interface CourseDAO {

    @Query("SELECT * FROM course")
    LiveData<List<Course>> getAllCourses();

    @Query("DELETE FROM course")
    void deleteAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<Course> courses);

    @Insert
    void insertCourse(Course course);

    @Query("SELECT COUNT(id) FROM course")
    int getNumberOfRows();

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateCourse(Course course);

    @Query("SELECT * FROM course WHERE studyYear = :studyYear")
    public abstract LiveData<List<Course>> getAllCoursesByStudyYear(int studyYear);
}
