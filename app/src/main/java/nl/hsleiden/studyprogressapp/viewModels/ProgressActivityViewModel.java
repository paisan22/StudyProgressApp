package nl.hsleiden.studyprogressapp.viewModels;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import java.util.List;

import nl.hsleiden.studyprogressapp.database.Models.Course;
import nl.hsleiden.studyprogressapp.repository.CourseRepository;

public class ProgressActivityViewModel extends ViewModel {

    private static final String TAG = "ProgressActivityViewMod";

    private LiveData<List<Course>> courses;

    private final int MAX_ECTS = 240;
    private final double MIN_GRADE = 5.5;

    private int current_ECTS_total = 0;
    private int current_ECTS = 0;


    public ProgressActivityViewModel(CourseRepository courseRepository) {
        this.courses = courseRepository.getAllCourses();
    }

    public int getMAX_ECTS() {
        return MAX_ECTS;
    }

    public int getCurrent_ECTS() {
        return this.current_ECTS;
    }

    public void incrementCurrentECTS() {
        this.current_ECTS++;
    }

    public void setCurrent_ECTS(int progress) {
        this.current_ECTS = progress;
    }

    public LiveData<List<Course>> getCourses() {
        return courses;
    }

    public int getCurrent_ECTS_total() {
        return current_ECTS_total;
    }

    public void setCurrent_ECTS_total(List<Course> courses) {

        this.current_ECTS_total = courses.stream()
                .filter(c -> c.getGrade() >= this.MIN_GRADE)
                .mapToInt(Course::getEcts)
                .sum();
    }
}
