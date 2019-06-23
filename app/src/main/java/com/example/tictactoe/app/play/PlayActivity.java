package com.example.tictactoe.app.play;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.example.tictactoe.R;
import com.example.tictactoe.common.BaseActivity;
import com.example.tictactoe.databinding.PlayActivityBinding;
import com.example.tictactoe.model.Play;
import com.example.tictactoe.model.SESSION;
import com.example.tictactoe.model.User;

public class PlayActivity extends BaseActivity<PlayViewModel, PlayActivityBinding>
            implements View.OnClickListener{

    Play play;

    public PlayActivity(){
        super(PlayViewModel.class, R.layout.play_activity);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        play = new Play();
        User userIntent = (User) getIntent().getExtras().getSerializable("player");
        User user = new User();
        user.setEmail(SESSION.email);
        user.setName(SESSION.name);
        if(getIntent().getExtras().getInt("playerNumber") == 1){
            // INSERT PLAY GAME
            play.setPlayer1(user);
            play.setPlayer2(userIntent);
            getViewModel().insertPlay(play);
        }
        else if (getIntent().getExtras().getInt("playerNumber") == 2){
            play.setPlayer1(userIntent);
            play.setPlayer2(user);
        }
        getBinding().setViewModel(play);
    }

    @Override
    protected void setListener() {
        super.setListener();
        getBinding().box1.setOnClickListener(this);
        getBinding().box2.setOnClickListener(this);
        getBinding().box3.setOnClickListener(this);
        getBinding().box4.setOnClickListener(this);
        getBinding().box5.setOnClickListener(this);
        getBinding().box6.setOnClickListener(this);
        getBinding().box7.setOnClickListener(this);
        getBinding().box8.setOnClickListener(this);
        getBinding().box9.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.equals(getBinding().box1)){

        }
        else if (v.equals(getBinding().box2)){

        }
        else if (v.equals(getBinding().box2)){

        }
        else if (v.equals(getBinding().box3)){

        }
        else if (v.equals(getBinding().box4)){

        }
        else if (v.equals(getBinding().box5)){

        }
        else if (v.equals(getBinding().box6)){

        }
        else if (v.equals(getBinding().box7)){

        }
        else if (v.equals(getBinding().box8)){

        }
        else if (v.equals(getBinding().box9)){

        }
    }


}
