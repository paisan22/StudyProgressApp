package nl.hsleiden.studyprogressapp.viewModels;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import java.util.List;

import javax.inject.Inject;

import nl.hsleiden.studyprogressapp.database.Models.Course;
import nl.hsleiden.studyprogressapp.repository.CourseRepository;

public class MainActivityViewModel extends ViewModel {

    private final CourseRepository repository;
    private final LiveData<List<Course>> courses;

    @Inject
    public MainActivityViewModel(CourseRepository repository) {
        this.repository = repository;
        courses = repository.getAllCourses();
    }

    public LiveData<List<Course>> getAllCourses() {
        return this.courses;
    }
}
