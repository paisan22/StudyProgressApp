package nl.hsleiden.studyprogressapp.viewModels;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import java.util.List;

import nl.hsleiden.studyprogressapp.database.Models.Course;
import nl.hsleiden.studyprogressapp.repository.CourseRepository;
import nl.hsleiden.studyprogressapp.webservice.Webservice;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Zorgt dat de data behouden wordt wanneer de activity state wijzigd.
 */
public class MainActivityViewModel extends ViewModel {

    private CourseRepository courseRepository;
    private LiveData<List<Course>> courses;

    private static final String TAG = "MainActivityViewModel";

    public MainActivityViewModel(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
        this.courses = this.courseRepository.getAllCourses();
    }

    public void filterCoursesByStudyYear(int studyYear) {
        this.courses = this.courseRepository.getAllCoursesByStudyYear(studyYear);
    }

    public void resetCoursesFromWebservice() {

        this.courseRepository.deleteAllCoures();

        Call<List<Course>> courseRequest = new Webservice().getCourseRequest();

        courseRequest.enqueue(new Callback<List<Course>>() {

            @Override
            public void onResponse(Call<List<Course>> call, Response<List<Course>> response) {

                if(!response.isSuccessful()) {
                    Log.d(TAG, "onResponse: " + response.code());
                }

                // for setting the study year for each subject.
                // TODO: remove for production environment
                List<Course> courseList = setStudyYearForEachSubject(response.body());

                courseRepository.insertAllCourses(courseList);
            }

            /**
             * Data seeding for setting study year, only for development purpose.
             * @param courses
             * @return
             */
            private List<Course> setStudyYearForEachSubject(List<Course> courses) {

                int count = 1;
                for (int i = 0; i < courses.size();i++) {
                    switch (count) {
                        case 1: {
                            courses.get(i).setStudyYear(2014);
                            count++;
                            break;
                        }
                        case 2: {
                            courses.get(i).setStudyYear(2015);
                            count++;
                            break;
                        }
                        case 3: {
                            courses.get(i).setStudyYear(2016);
                            count++;
                            break;
                        }
                        case 4: {
                            courses.get(i).setStudyYear(2017);
                            count = 1;
                            break;
                        }
                    }
                }
                return courses;
            }

            @Override
            public void onFailure(Call<List<Course>> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.getMessage());
            }
        });
    }

    public LiveData<List<Course>> getAllCourses() {
        return courses;
    }

    public void insertCourse(Course course) {
        courseRepository.insertCourse(course);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
    }

}
