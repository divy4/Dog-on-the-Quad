package cs465.illinois.edu.dogonthequad;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;

public class CreateMeetupEndTimeActivity extends CreateMeetupActivity implements TimePicker.OnTimeChangedListener {

    TimePicker mTimePicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initialize(R.layout.activity_create_meetup_end_time);

        mTimePicker = findViewById(R.id.time_picker);
        mTimePicker.setOnTimeChangedListener(this);

        Calendar cal = Calendar.getInstance();

        if(mMeetup.mEndTime == null){
            cal.setTime(new Date());
            cal.add(Calendar.MINUTE, 30);
        } else {
            cal.setTime(mMeetup.mEndTime);
        }


        mTimePicker.setHour(cal.get(Calendar.HOUR_OF_DAY));
        mTimePicker.setMinute(cal.get(Calendar.MINUTE));

        handleTimeChange(cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE));
    }

    @Override
    public void onTimeChanged(TimePicker timePicker, int i, int i1) {

        handleTimeChange(i, i1);
    }

    protected void handleTimeChange(int i, int i1) {
        Calendar now = Calendar.getInstance();
        now.setTime(new Date());

        Calendar changed = Calendar.getInstance();
        changed.setTime(new Date());

        changed.set(Calendar.HOUR_OF_DAY, i);
        changed.set(Calendar.MINUTE, i1);
        if(now.get(Calendar.HOUR_OF_DAY) > changed.get(Calendar.HOUR_OF_DAY)){
            changed.add(Calendar.DATE, 1);
        }

        mMeetup.mEndTime = changed.getTime();
    }
}
