package nl.hsleiden.studyprogressapp.ui;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.card.MaterialCardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import nl.hsleiden.studyprogressapp.R;
import nl.hsleiden.studyprogressapp.database.Models.Course;

public class CourseListAdapter extends RecyclerView.Adapter<CourseListAdapter.MyViewHolder> implements Filterable {

    private List<Course> courseList;
    private List<Course> courseListFull;


    public CourseListAdapter() {
    }

    @Override
    public Filter getFilter() {
        return courseFilter;
    }

    // wordt op een background thread uitgevoerd zodat de UI niet freezed
    private Filter courseFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Course> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(courseListFull);
            } else {
                int pattern = Integer.parseInt(constraint.toString());

                filteredList.addAll(
                        courseListFull
                                .stream()
                                .filter(course -> course.getStudyYear() == pattern)
                                .collect(Collectors.toList()));
            }
            FilterResults filterResults = new FilterResults();
            filterResults.values = filteredList;
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            courseList.clear();
            try {
                courseList.addAll((List) results.values);
            } catch (NullPointerException e) {
                e.printStackTrace();
                courseList.addAll(courseListFull);
            }

            notifyDataSetChanged();
        }
    };

    public void setCourses(List<Course> courses) {
        this.courseList = courses;
        this.courseListFull = new ArrayList<>(courses);
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

            if (course.getGrade() < 5.5) {
                MaterialCardView cardView;
                cardView = (MaterialCardView) itemView;
                cardView.setCardBackgroundColor(itemView.getContext().getResources().getColor(R.color.color_warning));
            }

            TextView name = (TextView) itemView.findViewById(R.id.subject_list_item_name);
            TextView grade = (TextView) itemView.findViewById(R.id.subject_list_item_grade);
            TextView studyYear = (TextView) itemView.findViewById(R.id.subject_list_item_year);

            TextView required = (TextView) itemView.findViewById(R.id.subject_list_item_required);
            if(course.isRequired()) {
                required.setText("Verplicht: Ja");
            } else {
                required.setText("Verplicht: Nee");
            }


            name.setText(course.getName());
            grade.setText("Cijfer: " + String.valueOf(course.getGrade()));
            studyYear.setText("Jaar: " + String.valueOf(course.getStudyYear()));

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(), SubjectDetailsActivity.class);
                    intent.putExtra("course", course);
                    v.getContext().startActivity(intent);
                }
            });
        }
    }
}
