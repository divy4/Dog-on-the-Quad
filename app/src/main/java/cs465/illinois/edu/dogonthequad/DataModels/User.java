package cs465.illinois.edu.dogonthequad.DataModels;

import java.util.UUID;

public class User {

    //TODO add other stuff

    public UUID mId = UUID.randomUUID();

    public UUID getId() {
        return mId;
    }

}
