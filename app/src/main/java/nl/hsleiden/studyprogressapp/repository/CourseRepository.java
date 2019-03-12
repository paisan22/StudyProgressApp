package nl.hsleiden.studyprogressapp.repository;

import android.arch.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.Executor;

import javax.inject.Inject;
import javax.inject.Singleton;

import nl.hsleiden.studyprogressapp.database.DAOs.CourseDAO;
import nl.hsleiden.studyprogressapp.database.Models.Course;

@Singleton
public class CourseRepository {

    private final CourseDAO courseDAO;
    private final Executor executor;

    @Inject
    public CourseRepository(CourseDAO courseDAO, Executor executor) {
        this.courseDAO = courseDAO;
        this.executor = executor;
    }

    public LiveData<List<Course>> getAllCourses() {
        return courseDAO.getAllCourses();
    }
}
