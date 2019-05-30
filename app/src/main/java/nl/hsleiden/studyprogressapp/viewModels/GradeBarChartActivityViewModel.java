package nl.hsleiden.studyprogressapp.viewModels;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import java.util.List;

import nl.hsleiden.studyprogressapp.database.Models.Course;
import nl.hsleiden.studyprogressapp.repository.CourseRepository;

public class GradeBarChartActivityViewModel extends ViewModel {

    private CourseRepository courseRepository;
    private LiveData<List<Course>> courses;

    public GradeBarChartActivityViewModel(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
        this.courses = this.courseRepository.getAllCourses();
    }

    public LiveData<List<Course>> getAllCourses() {
        return courses;
    }
}
