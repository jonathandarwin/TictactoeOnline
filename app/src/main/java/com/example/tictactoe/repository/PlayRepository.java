package com.example.tictactoe.repository;

import android.support.annotation.NonNull;

import com.example.tictactoe.common.APIHandler;
import com.example.tictactoe.common.APIHelper;
import com.example.tictactoe.model.Play;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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
            reference.child("play").child(play.getKey()).setValue(play);
        }
        catch(Exception e){
            APIHelper.LOG(APIHelper.ERROR_TYPE, e.getMessage());
            return false;
        }
        return true;
    }

    public void listenToKey(final APIHandler listener, String key){
        reference.child("play").child(key).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listener.onSuccess(dataSnapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                listener.onError(databaseError);
            }
        });
    }
}
