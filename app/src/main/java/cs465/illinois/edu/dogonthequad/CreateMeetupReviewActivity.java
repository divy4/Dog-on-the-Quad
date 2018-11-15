package cs465.illinois.edu.dogonthequad;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class CreateMeetupReviewActivity extends CreateMeetupActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initialize(R.layout.activity_create_meetup_review, null);
    }
}
