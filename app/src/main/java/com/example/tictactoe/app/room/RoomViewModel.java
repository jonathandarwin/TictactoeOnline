package com.example.tictactoe.app.room;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.util.Log;

import com.example.tictactoe.common.APIHandler;
import com.example.tictactoe.common.APIHelper;
import com.example.tictactoe.model.Invite;
import com.example.tictactoe.model.Room;
import com.example.tictactoe.model.SESSION;
import com.example.tictactoe.model.User;
import com.example.tictactoe.repository.RoomRepository;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

import java.util.ArrayList;
import java.util.List;

public class RoomViewModel extends ViewModel {
    private Context context;
    private RoomRepository repo;

    public RoomViewModel(Context context){
        this.context = context;
        repo = RoomRepository.getInstance();
    }

    public String joinRoom(){
        User user = new User();
        user.setEmail(SESSION.email);
        user.setName(SESSION.name);
        return repo.joinRoom(user);

    }

    public LiveData<List<User>> getRoomUser(){
        final MutableLiveData<List<User>> result = new MutableLiveData<>();
        repo.getRoomUser(new APIHandler() {
            @Override
            public void onSuccess(DataSnapshot dataSnapshot) {
                List<User> listUser = new ArrayList<>();
                for(DataSnapshot item : dataSnapshot.getChildren()){
                    User user = item.getValue(User.class);
                    if(!user.getEmail().equals(SESSION.email)){
                        listUser.add(user);
                    }
                }
                result.setValue(listUser);
            }

            @Override
            public void onError(DatabaseError databaseError) {
                result.setValue(new ArrayList<User>());
            }
        });

        return result;
    }

    public Invite setInvitation(User user){
        Invite invite = new Invite();
        invite.setSender(SESSION.email);
        invite.setSenderName(SESSION.name);
        invite.setReceiver(user.getEmail());
        invite.setReceiverName(user.getName());
        invite.setResponded(0);
        invite.setAccepted(0);
        return invite;
    }

    public String sendInvitation(Invite invite){
        return repo.sendInvitation(invite);
    }

    public LiveData<Invite> checkInvitation(){
        final MutableLiveData<Invite> result = new MutableLiveData<>();
        repo.checkInvitation(new APIHandler() {
            @Override
            public void onSuccess(DataSnapshot dataSnapshot) {
                for(DataSnapshot item : dataSnapshot.getChildren()){
                    Invite invite = item.getValue(Invite.class);
                    invite.setKey(item.getKey());
                    Log.d("masuksiniga", "invite key : " + item.getKey());
                    Log.d("masuksiniga", "invite is responded : " + invite.getResponded());
                    if(invite.getReceiver().equals(SESSION.email) && invite.getResponded() == 0){
                        result.setValue(invite);
                    }
                }
            }

            @Override
            public void onError(DatabaseError databaseError) {
                APIHelper.LOG(APIHelper.ERROR_TYPE, databaseError.getMessage());
            }
        });
        return result;
    }

    public boolean deleteRoomUser(String key){
        if(repo.deleteRoomUser(key))
            return true;
        return false;
    }

    public boolean deleteInvitation(String key){
        if(repo.deleteInvitation(key))
            return true;
        return false;
    }

    public LiveData<Invite> listenInvitationKey(String key){
        final MutableLiveData<Invite> result = new MutableLiveData<>();
        repo.listenInvitationKey(key, new APIHandler() {
            @Override
            public void onSuccess(DataSnapshot dataSnapshot) {
                Invite invite = dataSnapshot.getValue(Invite.class);
                result.setValue(invite);
            }

            @Override
            public void onError(DatabaseError databaseError) {
                APIHelper.LOG(APIHelper.ERROR_TYPE, databaseError.getMessage());
            }
        });
        return result;
    }

    public boolean updateAccepted(String key, int value){
        if(repo.updateAccepted(key, value))
            return true;
        return false;
    }
}
