package com.example.tictactoe.app.signup;

import android.arch.lifecycle.ViewModel;
import android.content.Context;

import com.example.tictactoe.model.User;
import com.example.tictactoe.repository.UserRepository;
import com.example.tictactoe.util.ToastUtil;

public class SignUpViewModel extends ViewModel {

    private Context context;
    private UserRepository repo;

    public SignUpViewModel(Context context){
        this.context = context;
        repo = UserRepository.getInstance();
    }

    public void insertUser(User user){
        if(repo.insertUser(user)){
            ToastUtil.make(context, "Register Success");
        }
    }
}