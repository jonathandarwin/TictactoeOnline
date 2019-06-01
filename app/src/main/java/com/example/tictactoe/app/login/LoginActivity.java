package com.example.tictactoe.app.login;

import android.arch.lifecycle.Observer;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Toast;

import com.example.tictactoe.R;
import com.example.tictactoe.app.room.RoomActivity;
import com.example.tictactoe.app.signup.SignUpActivity;
import com.example.tictactoe.common.BaseActivity;
import com.example.tictactoe.databinding.LoginActivityBinding;
import com.example.tictactoe.model.User;
import com.example.tictactoe.util.ToastUtil;

public class LoginActivity extends BaseActivity<LoginViewModel, LoginActivityBinding>
        implements View.OnClickListener{

    public LoginActivity(){
        super(LoginViewModel.class, R.layout.login_activity);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setListener();
        getBinding().setViewModel(new User());
    }

    @Override
    public void onClick(View v) {
        if(v.equals(getBinding().btnLogin)){
            final User user = getBinding().getViewModel();
            if(getViewModel().validateUser(user)){
                getViewModel().getUser(user).observe(this, new Observer<Boolean>() {
                    @Override
                    public void onChanged(@Nullable Boolean aBoolean) {
                        if(aBoolean){
                             gotoIntent(RoomActivity.class, true);
                        }
                        else{
                            ToastUtil.make(LoginActivity.this, "Incorrect email / password");
                        }
                    }
                });
            }
            else{
                ToastUtil.make(LoginActivity.this, "Please input username / password");
            }
        }
        else if (v.equals(getBinding().txtSignup)){
            gotoIntent(SignUpActivity.class, false);
        }
    }

    private void setListener(){
        getBinding().btnLogin.setOnClickListener(this);
        getBinding().txtSignup.setOnClickListener(this);
    }
}
