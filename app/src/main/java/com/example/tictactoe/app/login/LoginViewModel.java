package com.example.tictactoe.app.login;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Context;

import com.example.tictactoe.common.APIHandler;
import com.example.tictactoe.common.Auth;
import com.example.tictactoe.model.User;
import com.example.tictactoe.repository.UserRepository;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

public class LoginViewModel extends ViewModel {
    private Context context;
    UserRepository repo;

    public LoginViewModel(Context context){
        this.context = context;
        repo = UserRepository.getInstance();
    }

    public boolean validateUser(User user){
        if(user.getEmail().equals(null) || user.getEmail().trim().equals("") ||
            user.getPassword().equals(null) || user.getPassword().trim().equals(""))
            return false;
        return true;
    }

    public LiveData<Boolean> getUser(final User user){
        final MutableLiveData<Boolean> result = new MutableLiveData<>();
        repo.getUser(user, new APIHandler() {
            @Override
            public void onSuccess(DataSnapshot dataSnapshot) {
                Boolean bool = new Boolean(false);
                for(DataSnapshot item : dataSnapshot.getChildren()){
                    User data = item.getValue(User.class);
                    if(data.getPassword().equals(user.getPassword())){
                        Auth auth = new Auth(context);
                        auth.saveData(data);
                        bool = true;
                    }
                    else{
                        bool = false;
                    }
                    break;
                }
                result.postValue(bool);
            }

            @Override
            public void onError(DatabaseError databaseError) {

                result.postValue(false);
            }
        });
        return result;
    }
}
