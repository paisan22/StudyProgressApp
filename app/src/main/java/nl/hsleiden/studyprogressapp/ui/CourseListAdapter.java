package nl.hsleiden.studyprogressapp.ui;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.List;

import nl.hsleiden.studyprogressapp.R;
import nl.hsleiden.studyprogressapp.database.Models.Course;

public class CourseListAdapter extends RecyclerView.Adapter<CourseListAdapter.MyViewHolder> {

    private List<Course> courseList;
    private final Context context;

    public CourseListAdapter(Context context) {
        this.context = context;
    }

    public void setCourses(List<Course> courses) {
        this.courseList = courses;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View listItem = LayoutInflater.from(context).inflate(R.layout.course_list_item, viewGroup, false);

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

            TextView title = (TextView) itemView.findViewById(R.id.course_list_item_title);
            TextView ec = (TextView) itemView.findViewById(R.id.course_list_item_ec);
            CheckBox done = (CheckBox) itemView.findViewById(R.id.course_list_item_done);

            title.setText(course.getTitle());
            ec.setText(String.valueOf(course.getECs()));
            done.setChecked(course.isDone());
        }
    }
}
