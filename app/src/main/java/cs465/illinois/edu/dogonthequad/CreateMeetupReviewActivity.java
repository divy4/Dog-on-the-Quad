package cs465.illinois.edu.dogonthequad;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;

public class CreateMeetupReviewActivity extends CreateMeetupActivity implements View.OnClickListener {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initialize(R.layout.activity_create_meetup_review);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.cancel_button) {
            confirmCancel();
        } else {
            super.onClick(view);
        }
    }

    private void confirmCancel() {
        new AlertDialog.Builder(this)
            .setTitle("Cancel meetup?")
            .setMessage("Are you sure you want to cancel this meetup?")
            .setPositiveButton(R.string.cancel_meetup, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    clearPreviousActivities();
                    finish();
                }})
            .setNegativeButton(R.string.continue_review, null).show();
    }
}
