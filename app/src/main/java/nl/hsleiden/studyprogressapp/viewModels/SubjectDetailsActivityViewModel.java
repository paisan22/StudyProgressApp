package nl.hsleiden.studyprogressapp.viewModels;

import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.content.Intent;

import nl.hsleiden.studyprogressapp.database.Models.Course;
import nl.hsleiden.studyprogressapp.repository.CourseRepository;
import nl.hsleiden.studyprogressapp.ui.MainActivity;

public class SubjectDetailsActivityViewModel extends ViewModel {

    private CourseRepository courseRepository;
    private Course course;

    public SubjectDetailsActivityViewModel(CourseRepository courseRepository, Course course) {
        this.courseRepository = courseRepository;
        this.course = course;
    }

    public void updateCourse(Course course, Context context) {

        courseRepository.updateCourse(course);

        Intent intent = new Intent(context,MainActivity.class);
        context.startActivity(intent);
    }

    public Course getCourse() {
        return this.course;
    }
}
