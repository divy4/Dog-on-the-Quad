package cs465.illinois.edu.dogonthequad;

import android.os.Bundle;
import android.widget.RadioGroup;

import cs465.illinois.edu.dogonthequad.DataModels.SocialLevel;

public class CreateMeetupSocialLevelActivity extends CreateMeetupActivity implements RadioGroup.OnCheckedChangeListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initialize(R.layout.activity_create_meetup_social_level);

        RadioGroup rg = findViewById(R.id.radio_group);
        rg.setOnCheckedChangeListener(this);

        switch(mMeetup.mSocialLevel) {
            case Low:
                rg.check(R.id.low_button);
                break;
            case Medium:
                rg.check(R.id.medium_button);
                break;
            case High:
                rg.check(R.id.high_button);
                break;

        }
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
