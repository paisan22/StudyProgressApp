package nl.hsleiden.studyprogressapp.repository;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Room;
import android.content.Context;

import java.util.List;

import javax.inject.Singleton;

import nl.hsleiden.studyprogressapp.database.AppDatabase;
import nl.hsleiden.studyprogressapp.database.DAOs.CourseDAO;
import nl.hsleiden.studyprogressapp.database.Models.Course;

@Singleton
public class CourseRepository {

    private String DB_NAME = "course_db";
    private CourseDAO courseDAO;

    public CourseRepository(Context context) {

        this.courseDAO = Room.databaseBuilder(context, AppDatabase.class, DB_NAME)
                .build()
                .getCourseDAO();
    }

    public LiveData<List<Course>> getAllCourses() {
        return this.courseDAO.getAllCourses();
    }

    public void insertAllCourses(List<Course> courses) {

        this.courseDAO.insertAllCourses((Course) courses);
    }

    public void insertCourse(Course course) {
        courseDAO.insertCourse(course);
    }
}