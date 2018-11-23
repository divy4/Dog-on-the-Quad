package cs465.illinois.edu.dogonthequad;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ExpandableListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
    }

    private void prepareListData() {
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();

        listDataHeader.add("Betty");
        listDataHeader.add("Rover");

        List<String> bettyBadges = new ArrayList<String>();
        bettyBadges.add("High Jumper");
        bettyBadges.add("Ear Wagger");
        bettyBadges.add("Loud Barker");

        List<String> roverBadges = new ArrayList<String>();
        roverBadges.add("Tail Chaser");
        roverBadges.add("Calm doggo");
        roverBadges.add("sploot");

        listDataChild.put(listDataHeader.get(0), bettyBadges); // Header, Child data
        listDataChild.put(listDataHeader.get(1), roverBadges);
    }
}
