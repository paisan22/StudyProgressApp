package nl.hsleiden.studyprogressapp.viewModels;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import java.util.List;

import nl.hsleiden.studyprogressapp.database.Models.Course;
import nl.hsleiden.studyprogressapp.repository.CourseRepository;

/**
 * Zorgt dat de data behouden wordt wanneer de activity state wijzigd.
 */
public class MainActivityViewModel extends ViewModel {

    private CourseRepository courseRepository;
    private LiveData<List<Course>> courses;

    public MainActivityViewModel(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
        this.courses = this.courseRepository.getAllCourses();

//        createData();

    }

    public LiveData<List<Course>> getAllCourses() {
        return courses;
    }

    public void insertCourse(Course course) {
        courseRepository.insertCourse(course);
    }

    public void createData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
//                courseRepository.insertCourse(new Course("IOPR1",3,7.5,"Programmeren",1));
                courseRepository.insertCourse(new Course("INET",3,1,"Netwerken",2));
                courseRepository.insertCourse(new Course("SLB",3,1,"Begeleiding",4));
            }
        }).start();
    }

    @Override
    protected void onCleared() {
        super.onCleared();
    }
}
