package cs465.illinois.edu.dogonthequad;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class CreateMeetupPhotoActivity extends CreateMeetupActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initialize(R.layout.activity_create_meetup_photo);
    }
}
