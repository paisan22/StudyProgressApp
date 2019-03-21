package nl.hsleiden.studyprogressapp.viewModels;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.content.Context;
import android.support.annotation.NonNull;

import nl.hsleiden.studyprogressapp.database.Models.Course;
import nl.hsleiden.studyprogressapp.repository.CourseRepository;

public class SubjectDetailsActivityViewModelFactory implements ViewModelProvider.Factory {

    private CourseRepository courseRepository;
    private Course course;

    public SubjectDetailsActivityViewModelFactory(Context context, Course course) {
        this.courseRepository = new CourseRepository(context);
        this.course = course;
    }

    @NonNull
    @Override
    @SuppressWarnings(value = "unchecked")
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if(modelClass.isAssignableFrom(SubjectDetailsActivityViewModel.class)) {
            return (T) new SubjectDetailsActivityViewModel(courseRepository, course);
        } else {
            throw new IllegalArgumentException("Unknown class");
        }
    }
}
