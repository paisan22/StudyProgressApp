package nl.hsleiden.studyprogressapp.ui;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import nl.hsleiden.studyprogressapp.R;
import nl.hsleiden.studyprogressapp.database.Models.Course;

public class CourseListAdapter extends RecyclerView.Adapter<CourseListAdapter.MyViewHolder> {

    private List<Course> courseList;

    public CourseListAdapter(List<Course> courseList) {
        this.courseList = courseList;
    }

    public void setCourses(List<Course> courses) {
        this.courseList = courses;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View listItem = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.course_list_item, viewGroup, false);

        MyViewHolder myViewHolder = new MyViewHolder(listItem);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        Course course = courseList.get(i);
        myViewHolder.setData(course);
    }

    @Override
    public int getItemCount() {
        return courseList == null ? 0: courseList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        public void setData(Course course) {

            TextView name = (TextView) itemView.findViewById(R.id.course_list_item_name);
            TextView ects = (TextView) itemView.findViewById(R.id.course_ects);
            TextView grade = (TextView) itemView.findViewById(R.id.course_grade);

            name.setText(course.getName());
            ects.setText(String.valueOf(course.getEcts()));
            grade.setText(String.valueOf(course.getGrade()));
        }
    }
}
