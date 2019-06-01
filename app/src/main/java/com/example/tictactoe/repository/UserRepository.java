package com.example.tictactoe.repository;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.example.tictactoe.common.APIHandler;
import com.example.tictactoe.common.APIHelper;
import com.example.tictactoe.model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UserRepository {

    public static UserRepository instance;
    private DatabaseReference reference;

    public UserRepository(){
        reference = FirebaseDatabase.getInstance().getReference();
    }

    public static UserRepository getInstance(){
        if(instance == null)
            instance = new UserRepository();
        return instance;
    }

    public boolean insertUser(User user){
        try{
            APIHelper.LOG(APIHelper.REQUEST_TYPE, "masuk sini");
            reference.child("user").push().setValue(user);
        }
        catch(Exception e){
            APIHelper.LOG(APIHelper.ERROR_TYPE, e.toString());
            return false;
        }
        return true;
    }

    public LiveData<Boolean> getUser(final User user, final APIHandler listener){
        final MutableLiveData<Boolean> result = new MutableLiveData<>();
        reference.child("user").orderByChild("email").equalTo(user.getEmail())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        listener.onSuccess(dataSnapshot);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        listener.onError(databaseError);
                    }
                });
        return result;
    }
}
