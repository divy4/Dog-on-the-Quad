package cs465.illinois.edu.dogonthequad.DataModels;

import java.util.ArrayList;
import java.util.UUID;

public class Dog {

    // TODO add other stuff
    public UUID mId;
    public String mName;
    public ArrayList<String> badges;

    public Dog() {
        this.mId = UUID.randomUUID();
        this.mName = "";
        this.badges = new ArrayList<String>();
    }

    public Dog(String name, UUID id, ArrayList<String> badges) {
        this.mName = name;
        this.mId = id;
        this.badges = badges;
    }
}
