package cs465.illinois.edu.dogonthequad;

import android.content.Context;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.PorterDuff;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

import com.mikhaellopez.circularimageview.CircularImageView;

import java.util.ArrayList;
import java.util.Collections;
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

        if(mMeetup.mDogs == null)
            mMeetup.mDogs = new ArrayList<UUID>();

        Button nextButton = (Button) findViewById(R.id.meetup_next_button);
        DogListAdapter dogAdapter = new DogListAdapter(this, R.layout.select_dog_list_item, API.getDogs(), nextButton);


        final ListView dogList = (ListView)findViewById(R.id.DogList);
        dogList.setAdapter(dogAdapter);




    }

    private class DogListAdapter extends ArrayAdapter<Dog>  {

        List<Dog> mDogs;
        ArrayList<Boolean> mIncluded;
        Context mContext;
        Button mNextButton;

        public DogListAdapter(@NonNull Context context, int resource, @NonNull List<Dog> objects, Button nextButton) {
            super(context, resource, objects);
            mDogs = objects;
            mIncluded = new ArrayList<>(Collections.nCopies(mDogs.size(), false));
            for (UUID doggo : mMeetup.mDogs){
                for (int i = 0; i < mDogs.size(); i++){
                    if (mDogs.get(i).mId.equals(doggo)) {
                        mIncluded.set(i, true);
                    }
                }
            }
            mContext = context;
            mNextButton = nextButton;
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

            CircularImageView circleImageView = listItem.findViewById(R.id.profile_picture);
            circleImageView.setImageResource(R.drawable.placeholder_dog_profile);

            CheckBox box = listItem.findViewById(R.id.dog_list_checkbox);
            box.setChecked(mIncluded.get(position));
            box.setTag(d);

            View.OnClickListener listener = view -> {
                Log.d("CLICKED", "CLICKED");
                if(mIncluded.get(position)){
                    mMeetup.mDogs.remove(((Dog) box.getTag()).mId);
                    box.setChecked(false);
                    mIncluded.set(position, false);
                } else {
                    mMeetup.mDogs.add(((Dog) box.getTag()).mId);
                    box.setChecked(true);
                    mIncluded.set(position, true);

                }
                updateButton();
            };

            box.setOnClickListener(listener);
            circleImageView.setOnClickListener(listener);
            text.setOnClickListener(listener);
            listItem.setOnClickListener(listener);
            return listItem;
        }


        public void updateButton() {
            mNextButton.setEnabled(mMeetup.mDogs.size() > 0);

//            if (mMeetup.mDogs.size() == 0) {
//                mNextButton.setEnabled(false);
//                mNextButton.getBackground().setColorFilter(Color.GRAY, PorterDuff.Mode.MULTIPLY);
//            } else {
//                mNextButton.setEnabled(true);
//                mNextButton.getBackground().setColorFilter(mNextButtonColorFilter);
//            }

        }
    }
}
