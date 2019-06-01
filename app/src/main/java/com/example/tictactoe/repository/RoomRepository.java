package com.example.tictactoe.repository;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.tictactoe.common.APIHandler;
import com.example.tictactoe.common.APIHelper;
import com.example.tictactoe.model.Invite;
import com.example.tictactoe.model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class RoomRepository {
    private static RoomRepository instance;
    private DatabaseReference reference;

    public RoomRepository(){
        reference = FirebaseDatabase.getInstance().getReference();
    }

    public static RoomRepository getInstance(){
        if(instance == null)
            instance = new RoomRepository();
        return instance;
    }

    public String joinRoom(User user){
        String key = "";
        try{
            key = reference.child("room").push().getKey();
            reference.child("room").child(key).setValue(user);
        }
        catch(Exception e){
            APIHelper.LOG(APIHelper.ERROR_TYPE, e.toString());
        }
        return key;
    }

    public void getRoomUser(final APIHandler listener){
        reference.child("room").addValueEventListener(new ValueEventListener() {
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

    public String sendInvitation(Invite invite){
        String key = "";
        try{
            key = reference.child("invite").push().getKey();
            reference.child("invite").child(key).setValue(invite);
        }
        catch(Exception e){
            APIHelper.LOG(APIHelper.ERROR_TYPE, e.toString());
        }
        return key;
    }

    public void checkInvitation(final APIHandler listener){
        reference.child("invite").addValueEventListener(new ValueEventListener() {
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

    public boolean deleteRoomUser(String key){
        try{
            reference.child("room").child(key).removeValue();
        }
        catch(Exception e){
            APIHelper.LOG(APIHelper.ERROR_TYPE, e.toString());
            return false;
        }
        return true;
    }

    public boolean deleteInvitation(String key){
        try{
            reference.child("invite").child(key).child("responded").setValue(1);
        }
        catch(Exception e){
            APIHelper.LOG(APIHelper.ERROR_TYPE, e.toString());
            return false;
        }
        return true;
    }

    public void listenInvitationKey(String key, final APIHandler listener){
        reference.child("invite").child(key).addValueEventListener(new ValueEventListener() {
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

    public boolean updateAccepted(String key, int value){
        try{
            reference.child("invite").child(key).child("accepted").setValue(value);
        }
        catch(Exception e){
            APIHelper.LOG(APIHelper.ERROR_TYPE, e.toString());
            return false;
        }
        return true;
    }
}
