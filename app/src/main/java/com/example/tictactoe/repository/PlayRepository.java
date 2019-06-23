package com.example.tictactoe.repository;

import com.example.tictactoe.common.APIHelper;
import com.example.tictactoe.model.Play;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class PlayRepository {

    private static PlayRepository instance;
    private DatabaseReference reference;

    public PlayRepository(){
        reference = FirebaseDatabase.getInstance().getReference();
    }

    public static PlayRepository getInstance(){
        if(instance == null)
            instance = new PlayRepository();
        return instance;
    }

    public boolean insertPlay(Play play){
        try{
            String key = reference.child("play").push().getKey();
            reference.child("play").child(key).setValue(play);
        }
        catch(Exception e){
            APIHelper.LOG(APIHelper.ERROR_TYPE, e.getMessage());
            return false;
        }
        return true;
    }
}
