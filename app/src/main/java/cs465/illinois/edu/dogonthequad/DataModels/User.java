package cs465.illinois.edu.dogonthequad.DataModels;

import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

public class User {

    public UUID mId;
    public String mName;
    public ArrayList<UUID> mDogs;
    public Date mUserSince;
    public Integer mMeetupsCreated;
    public Integer mMeetupsAttended;
    public Integer mDogsPetted;
    public Integer mPeopleMet;

    public User() {
        this.mId = UUID.randomUUID();
        this.mName = "";
        this.mDogs = new ArrayList<UUID>();
        this.mUserSince = new Date();
        this.mMeetupsCreated = 5;
        this.mMeetupsAttended = 15;
        this.mDogsPetted = 18;
        this.mPeopleMet = 43;
    }

    public User(String name,
                UUID mId,
                ArrayList<UUID> dogs,
                Date userSince,
                int meetupsCreated,
                int meetupsAttended,
                int dogsPetted,
                int peopleMet) {
        this.mId = mId;
        this.mName = name;
        this.mDogs = dogs;
        this.mUserSince = userSince;
        this.mMeetupsCreated = meetupsCreated;
        this.mMeetupsAttended = meetupsAttended;
        this.mDogsPetted = dogsPetted;
        this.mPeopleMet = peopleMet;
    }
}
