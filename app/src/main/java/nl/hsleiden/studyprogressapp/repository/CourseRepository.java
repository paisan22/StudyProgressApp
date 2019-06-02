package nl.hsleiden.studyprogressapp.repository;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Room;
import android.content.Context;

import java.util.List;

import javax.inject.Singleton;

import nl.hsleiden.studyprogressapp.database.AppDatabase;
import nl.hsleiden.studyprogressapp.database.DAOs.CourseDAO;
import nl.hsleiden.studyprogressapp.database.Models.Course;

/**
 * Verantwoordelijk om de taken naar de dao te delegeren en deze NIET op de main thread te laten
 * uitvoeren. Op deze manier blijft de UI intact.
 */
@Singleton
public class CourseRepository {

    private String DB_NAME = "course_db";

    private CourseDAO courseDAO;

    public CourseRepository(Context context) {

        this.courseDAO = Room.databaseBuilder(context, AppDatabase.class, DB_NAME)
                .build()
                .getCourseDAO();
    }

    public void deleteAllCoures() {

        new Thread(() -> courseDAO.deleteAll()).start();
    }

    public LiveData<List<Course>> getAllCourses() {
        return this.courseDAO.getAllCourses();
    }

    public LiveData<List<Course>> getAllCoursesByStudyYear(int studyYear) {
        return this.courseDAO.getAllCoursesByStudyYear(studyYear);
    }

    public void insertAllCourses(List<Course> courses) {

        new Thread(() -> courseDAO.insertAll(courses)).start();
    }

    public void insertCourse(Course course) {
        courseDAO.insertCourse(course);
    }

    public void updateCourse(Course course) {
        new Thread(() -> courseDAO.updateCourse(course)).start();
    }

    public void deleteCourse(Course course) {
        new Thread(() -> courseDAO.deleteCourse(course)).start();
    }
}
