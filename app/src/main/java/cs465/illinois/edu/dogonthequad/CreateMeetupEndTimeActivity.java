package cs465.illinois.edu.dogonthequad;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TimePicker;

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
        cal.setTime(new Date());
        cal.add(Calendar.MINUTE, 30);


        mTimePicker.setHour(cal.get(Calendar.HOUR));
        mTimePicker.setMinute(cal.get(Calendar.MINUTE));
    }

    @Override
    public void onTimeChanged(TimePicker timePicker, int i, int i1) {
        timePicker.

    }
}
