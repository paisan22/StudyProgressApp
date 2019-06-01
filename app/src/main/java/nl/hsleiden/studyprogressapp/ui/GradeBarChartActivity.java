package nl.hsleiden.studyprogressapp.ui;

import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.ValueFormatter;

import java.util.ArrayList;
import java.util.List;

import nl.hsleiden.studyprogressapp.R;
import nl.hsleiden.studyprogressapp.database.Models.Course;
import nl.hsleiden.studyprogressapp.databinding.ActivityGradeBarChartBinding;
import nl.hsleiden.studyprogressapp.menu.MenuHandler;
import nl.hsleiden.studyprogressapp.viewModels.GradeBarChartActivityViewModel;
import nl.hsleiden.studyprogressapp.viewModels.GradeBarChartActivityViewModelFactory;

public class GradeBarChartActivity extends AppCompatActivity {

    private GradeBarChartActivityViewModelFactory gradeBarChartActivityViewModelFactory;
    private BarChart barChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Toepassing van Data binding om boilerplate code te voorkomen.
        ActivityGradeBarChartBinding barChartBinding =
                DataBindingUtil.setContentView(this, R.layout.activity_grade_bar_chart);

        //toolbar setup
        setSupportActionBar(barChartBinding.gradeBarChartToolbar);

        gradeBarChartActivityViewModelFactory = new GradeBarChartActivityViewModelFactory(this);

        GradeBarChartActivityViewModel viewModel = ViewModelProviders
                .of(this, gradeBarChartActivityViewModelFactory)
                .get(GradeBarChartActivityViewModel.class);

        viewModel.getAllCourses().observe(this, list -> {
            barChart = barChartBinding.barChart;
            setData(list);
        });
    }

    private void setData(List<Course> list) {

        // get all possible years
        List<Integer> studyYears = getPossibleYears(list);

        // count ects per year
        ArrayList<TotalECTSPerYear> totalECTSPerYears = getECTSPerYear(list, studyYears);

        // make bar-entries based on years and put in the ects
        List<BarEntry> barEntries = createBarEntries(totalECTSPerYears);

        // adding bar-entries to the data-set
        BarDataSet barDataSet = new BarDataSet(barEntries, "Aantal ECTS per jaar");

        // creating a valueFormatter for setting the year-labels on the axis
        ValueFormatter valueFormatter = getXLabels(studyYears);
        XAxis xAxis = barChart.getXAxis();
        xAxis.setGranularity(1F);
        xAxis.setValueFormatter(valueFormatter);

        // adding the data-set to the BarData which holds al the data for the chart.
        BarData barData = new BarData(barDataSet);
        barData.setBarWidth(0.9f);

        // adding the data and some styling to the chart.
        barChart.setDrawGridBackground(false);
        barChart.setDrawBorders(false);
        barChart.setData(barData);
        barChart.setFitBars(true);

        Description description = new Description();
        description.setText("");
        barChart.setDescription(description);

        // set max value for the yaxis
        YAxis axisLeft = barChart.getAxisLeft();
        axisLeft.setAxisMaximum(60);

        // finally refresh the bar-chart so the provided data is drawn
        barChart.invalidate();
    }

    private ValueFormatter getXLabels(List<Integer> studyYears) {
        return new ValueFormatter() {
            @Override
            public String getAxisLabel(float value, AxisBase axis) {
                return studyYears.get((int) value).toString();
            }
        };
    }

    private List<BarEntry> createBarEntries(ArrayList<TotalECTSPerYear> totalECTSPerYears) {
        List<BarEntry> barEntries = new ArrayList<>();

        for (int i = 0; i < totalECTSPerYears.size(); i++) {
            barEntries.add(new BarEntry(i, totalECTSPerYears.get(i).ects));
        }
        return barEntries;
    }

    private ArrayList<TotalECTSPerYear> getECTSPerYear(List<Course> list, List<Integer> studyYears) {

        ArrayList<TotalECTSPerYear> totalECTSPerYears = new ArrayList<>();

        // repeat this for each year
        for (int year : studyYears) {
            // sum for each subject the ects if the year is equal to the current year
            int ects = 0;
            for (Course course : list) {
                if ((course.getStudyYear() == year) && (course.getGrade() >= 5.5)) {
                    ects += course.getEcts();
                }
            }
            totalECTSPerYears.add(new TotalECTSPerYear(year, ects));
        }
        return totalECTSPerYears;
    }

    private List<Integer> getPossibleYears(List<Course> list) {
        List<Integer> studyYears = new ArrayList<>();

        for (Course course : list) {
            if (!studyYears.contains(course.getStudyYear())) {
                studyYears.add(course.getStudyYear());
            }
        }
        return studyYears;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.my_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        MenuHandler.handle(this,item);
        return super.onOptionsItemSelected(item);
    }

    class TotalECTSPerYear {
        private int year;
        private int ects;

        public TotalECTSPerYear(int year, int ects) {
            this.year = ects;
            this.ects = ects;
        }
    }
}
