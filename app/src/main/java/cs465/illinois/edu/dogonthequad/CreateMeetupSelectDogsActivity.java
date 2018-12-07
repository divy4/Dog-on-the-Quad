package cs465.illinois.edu.dogonthequad;

import android.content.Context;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.PorterDuff;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import cs465.illinois.edu.dogonthequad.DataModels.API;
import cs465.illinois.edu.dogonthequad.DataModels.Dog;
import cs465.illinois.edu.dogonthequad.DataModels.User;

public class CreateMeetupSelectDogsActivity extends CreateMeetupActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initialize(R.layout.activity_create_meetup_select_dogs);

        Button nextButton = (Button) findViewById(R.id.meetup_next_button);
        nextButton.setEnabled(false);
        DogListAdapter dogAdapter = new DogListAdapter(this, R.layout.select_dog_list_item, API.getDogs(), nextButton);

        final ListView dogList = (ListView)findViewById(R.id.DogList);
        dogList.setAdapter(dogAdapter);

        mMeetup.mDogs = new ArrayList<UUID>();
    }

    private class DogListAdapter extends ArrayAdapter<Dog> implements View.OnClickListener {

        List<Dog> mDogs;
        Context mContext;
        Button mNextButton;
        ColorFilter mNextButtonColorFilter;

        public DogListAdapter(@NonNull Context context, int resource, @NonNull List<Dog> objects, Button nextButton) {
            super(context, resource, objects);
            mDogs = objects;
            mContext = context;
            mNextButton = nextButton;
            mNextButtonColorFilter = mNextButton.getBackground().getColorFilter();
            updateButton();
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            Dog d = mDogs.get(position);
            View listItem = convertView;
            if(listItem == null)
                listItem = LayoutInflater.from(mContext).inflate(R.layout.select_dog_list_item,parent,false);
            TextView text = listItem.findViewById(R.id.dog_list_name);
            text.setText(d.mName);

            CheckBox box = listItem.findViewById(R.id.dog_list_checkbox);
            box.setTag(d);
            box.setOnClickListener(this);
            return listItem;
        }

        @Override
        public void onClick(View view) {
            if(view.getId() == R.id.dog_list_checkbox){
                CheckBox box = (CheckBox) view;
                if(box.isChecked()){
                    mMeetup.mDogs.add(((Dog) box.getTag()).mId);
                } else {
                    mMeetup.mDogs.remove(((Dog) box.getTag()).mId);
                }
                updateButton();
            }
        }

        public void updateButton() {
            mNextButton.setEnabled(mMeetup.mDogs.size() > 0);
            if (mMeetup.mDogs.size() == 0) {
                mNextButton.setEnabled(false);
                mNextButton.getBackground().setColorFilter(Color.GRAY, PorterDuff.Mode.MULTIPLY);
            } else {
                mNextButton.setEnabled(true);
                mNextButton.getBackground().setColorFilter(mNextButtonColorFilter);
            }

        }
    }
}
