package cs465.illinois.edu.dogonthequad;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.gson.Gson;
import static cs465.illinois.edu.dogonthequad.MapActivity.MEETUP_KEY;

public class CreateMeetupReviewActivity extends CreateMeetupActivity implements View.OnClickListener {
    Button mConfirm;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initialize(R.layout.activity_create_meetup_review);

        mConfirm = findViewById(R.id.confirm_button);
        mConfirm.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.confirm_button){
            Intent intent = new Intent(this, MeetupInProgressActivity.class);
            String json = new Gson().toJson(mMeetup);
            intent.putExtra(MEETUP_KEY, json);
            startActivity(intent);
        } else {
            super.onClick(view);
        }
    }
}
