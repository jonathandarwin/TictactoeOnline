package com.example.tictactoe.model;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.example.tictactoe.BR;

import java.io.Serializable;

public class User extends BaseObservable implements Serializable {
    public String email;
    public String password;
    public String name;

    @Bindable
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
        notifyPropertyChanged(BR.email);
    }

    @Bindable
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
        notifyPropertyChanged(BR.password);
    }

    @Bindable
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        notifyPropertyChanged(BR.name);
    }
}
