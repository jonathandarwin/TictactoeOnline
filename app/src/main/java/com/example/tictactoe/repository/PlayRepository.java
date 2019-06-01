package com.example.tictactoe.repository;

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
}
