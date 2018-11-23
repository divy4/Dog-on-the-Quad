package cs465.illinois.edu.dogonthequad;

import android.os.Bundle;
import android.view.View;

public class CreateMeetupReviewActivity extends CreateMeetupActivity implements View.OnClickListener {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initialize(R.layout.activity_create_meetup_review);
    }
}
