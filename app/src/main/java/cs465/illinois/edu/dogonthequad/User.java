package cs465.illinois.edu.dogonthequad;

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

    public User(UUID id, String name, ArrayList<UUID> dogs) {
        this.mId = id;
        this.mName = name;
        this.mDogs = dogs;
    }

}
