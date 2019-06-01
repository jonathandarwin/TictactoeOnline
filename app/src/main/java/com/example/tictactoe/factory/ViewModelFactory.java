package com.example.tictactoe.factory;


import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.content.Context;
import android.support.annotation.NonNull;

import com.example.tictactoe.app.login.LoginActivity;
import com.example.tictactoe.app.login.LoginViewModel;
import com.example.tictactoe.app.play.PlayActivity;
import com.example.tictactoe.app.play.PlayViewModel;
import com.example.tictactoe.app.room.RoomActivity;
import com.example.tictactoe.app.room.RoomViewModel;
import com.example.tictactoe.app.signup.SignUpActivity;
import com.example.tictactoe.app.signup.SignUpViewModel;

public class ViewModelFactory implements ViewModelProvider.Factory {

    private Context context;

    public ViewModelFactory(Context context){
        this.context = context;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if(context instanceof LoginActivity){
            return (T) new LoginViewModel(context);
        }
        if(context instanceof SignUpActivity){
            return (T) new SignUpViewModel(context);
        }
        if(context instanceof RoomActivity){
            return (T) new RoomViewModel(context);
        }
        if(context instanceof PlayActivity){
            return (T) new PlayViewModel(context);
        }
        return null;
    }
}
