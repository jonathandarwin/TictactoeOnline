package com.example.tictactoe.common;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.tictactoe.model.User;

public class Auth {

    SharedPreferences preference;

    public Auth(Context context){
        preference = context.getSharedPreferences("tictactoe", Context.MODE_PRIVATE);
    }

    public void saveData(User user){
        SharedPreferences.Editor editor = preference.edit();
        editor.putString("email", user.getEmail());
        editor.putString("password", user.getPassword());
        editor.putString("name", user.getName());
        editor.apply();
    }

    public User loadData(){
        User user = new User();
        user.setEmail(preference.getString("email",""));
        user.setPassword(preference.getString("password", ""));
        user.setName(preference.getString("name", ""));

        return user;
    }

    public boolean isLoggedIn(){
        String email = preference.getString("email", "");
        return !email.equals("") ? true : false;
    }
}
