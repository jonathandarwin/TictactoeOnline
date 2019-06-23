package com.example.tictactoe.app.signup;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.example.tictactoe.R;
import com.example.tictactoe.common.BaseActivity;
import com.example.tictactoe.databinding.SignupActivityBinding;
import com.example.tictactoe.model.User;

public class SignUpActivity extends BaseActivity<SignUpViewModel, SignupActivityBinding>
        implements View.OnClickListener
{

    public SignUpActivity(){
        super(SignUpViewModel.class, R.layout.signup_activity);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setListener();
        getBinding().setViewModel(new User());
    }

    @Override
    public void onClick(View v) {
        if(v.equals(getBinding().btnSignup)){
            User user = getBinding().getViewModel();
            getViewModel().insertUser(user);
            finish();
        }
    }

    @Override
    protected void setListener() {
        super.setListener();
        getBinding().btnSignup.setOnClickListener(this);
    }
}
