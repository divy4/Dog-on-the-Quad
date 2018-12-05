package cs465.illinois.edu.dogonthequad;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ExpandableListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cs465.illinois.edu.dogonthequad.DataModels.API;
import cs465.illinois.edu.dogonthequad.DataModels.Dog;
import cs465.illinois.edu.dogonthequad.DataModels.User;

public class DogOwnerProfileActivity extends Activity {

    private ExpandableListAdapter listAdapter;
    private ExpandableListView expListView;
    private List<String> listDataHeader;
    private HashMap<String, List<String>> listDataChild;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dog_owner_profile);

        // get the listview
        expListView = (ExpandableListView) findViewById(R.id.lvExp);

        // preparing list data
        prepareListData();

        listAdapter = new ExpandableListAdapter(this, listDataHeader, listDataChild);

        // setting list adapter
        expListView.setAdapter(listAdapter);

        User mUser = API.getCurrentUser();
        TextView view = findViewById(R.id.user_since);
        view.setText(mUser.mUserSince.toString());
        view = findViewById(R.id.meetups_created);
        view.setText(mUser.mMeetupsCreated.toString());
        view = findViewById(R.id.meetups_attended);
        view.setText(mUser.mMeetupsAttended.toString());
        view = findViewById(R.id.dogs_petted);
        view.setText(mUser.mDogsPetted.toString());
        view = findViewById(R.id.people_met);
        view.setText(mUser.mPeopleMet.toString());
    }

    private void prepareListData() {
        ArrayList<Dog> dogs = API.getDogs();
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();
        for (Dog dog : dogs) {
            listDataHeader.add(dog.mName);
            listDataChild.put(dog.mName, dog.badges);
        }
    }
}
