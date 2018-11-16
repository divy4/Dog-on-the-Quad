package cs465.illinois.edu.dogonthequad;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;

public class CreateMeetupSelectDogsActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_meetup_select_dogs);


        ArrayList<String> dogs = new ArrayList<>();
        dogs.add("Fido");
        ArrayAdapter<String> dogAdapter = new ArrayAdapter<>(this, R.layout.select_dog_list_item, dogs);
//        final ListView dogList = (ListView)findViewById(R.id.DogList);
//        dogList.setAdapter(dogAdapter);
    }
}
