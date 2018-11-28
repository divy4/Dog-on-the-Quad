package cs465.illinois.edu.dogonthequad.DataModels;

import java.util.ArrayList;
import java.util.UUID;

public class User {

    // TODO add other stuff
    public UUID mId;
    public String mName;
    public ArrayList<UUID> mDogs;

    public User() {
        this.mId = UUID.randomUUID();
        this.mName = "";
        this.mDogs = new ArrayList<UUID>();
    }

    public User(String name, UUID mId, ArrayList<UUID> dogs) {
        this.mId = mId;
        this.mName = name;
        this.mDogs = dogs;
    }
}
