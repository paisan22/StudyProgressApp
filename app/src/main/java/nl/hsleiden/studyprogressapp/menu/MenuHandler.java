package nl.hsleiden.studyprogressapp.menu;

import android.content.Context;
import android.content.Intent;
import android.view.MenuItem;

import nl.hsleiden.studyprogressapp.R;
import nl.hsleiden.studyprogressapp.ui.MainActivity;
import nl.hsleiden.studyprogressapp.ui.ProgressActivity;

public class MenuHandler {

    public static void handle (Context context, MenuItem menuItem) {

        switch (menuItem.getItemId()) {
            case R.id.menu_home: {
                context.startActivity(new Intent(context,MainActivity.class));
                break;
            }
            case R.id.menu_progress_ects: {
                context.startActivity(new Intent(context, ProgressActivity.class));
                break;
            }
        }
    }
}
