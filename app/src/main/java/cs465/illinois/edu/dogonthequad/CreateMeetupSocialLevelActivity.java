package cs465.illinois.edu.dogonthequad;

import android.app.Activity;
import android.os.Bundle;
import android.widget.RadioGroup;

public class CreateMeetupSocialLevelActivity extends CreateMeetupActivity implements RadioGroup.OnCheckedChangeListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initialize(R.layout.activity_create_meetup_social_level);

        RadioGroup rg = findViewById(R.id.radio_group);
        rg.setOnCheckedChangeListener(this);

        rg.check(R.id.low_button);
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
        switch (i) {
            case R.id.low_button:
                mMeetup.mSocialLevel = SocialLevel.Low;
                break;
            case R.id.medium_button:
                mMeetup.mSocialLevel = SocialLevel.Medium;
                break;
            case R.id.high_button:
                mMeetup.mSocialLevel = SocialLevel.High;
                break;
        }
    }
}