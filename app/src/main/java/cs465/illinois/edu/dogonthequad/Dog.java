package cs465.illinois.edu.dogonthequad;

import java.util.ArrayList;
import java.util.UUID;

public class Dog {

    // TODO add other stuff
    public UUID mId;
    public String mName;
    public ArrayList<String> mBadges;

    public Dog() {
        this.mId = UUID.randomUUID();
        this.mName = "";
        this.mBadges = new ArrayList<String>();
    }

    public Dog(UUID id, String name, ArrayList<String> badges) {
        this.mId = id;
        this.mName = name;
        this.mBadges = badges;
    }
}
