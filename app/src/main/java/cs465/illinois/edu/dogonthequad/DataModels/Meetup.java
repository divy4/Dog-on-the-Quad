package cs465.illinois.edu.dogonthequad.DataModels;


import com.google.android.gms.maps.model.LatLng;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import cs465.illinois.edu.dogonthequad.DataModels.SocialLevel;

public class Meetup {

    public MeetupState mState;

    public LatLng mLocation;

    public SocialLevel mSocialLevel;

    public Date mEndTime;

    public List<UUID> mDogs; //have a table of users and a table of mDogs, use ID to get dog

    public UUID mUser;
    
    public String mPhoto;
}

