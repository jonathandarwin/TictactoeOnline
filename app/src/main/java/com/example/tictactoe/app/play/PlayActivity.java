package com.example.tictactoe.app.play;

import android.arch.lifecycle.Observer;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.tictactoe.R;
import com.example.tictactoe.common.BaseActivity;
import com.example.tictactoe.databinding.PlayActivityBinding;
import com.example.tictactoe.model.Play;
import com.example.tictactoe.model.SESSION;
import com.example.tictactoe.model.User;

import java.util.ArrayList;
import java.util.List;

public class PlayActivity extends BaseActivity<PlayViewModel, PlayActivityBinding>
            implements View.OnClickListener{

    private static final String YOU_WIN = "You Win!";
    private static final String YOU_LOSE = "You Lose!";

    Play play;
    int playerNumber = 0;
    String key;
    int turn = 1;
    int winner = 0;

    public PlayActivity(){
        super(PlayViewModel.class, R.layout.play_activity);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        play = new Play();
        playerNumber = getIntent().getExtras().getInt("playerNumber");
        key = getIntent().getExtras().getString("key");

        User userIntent = (User) getIntent().getExtras().getSerializable("player");
        User user = new User();
        user.setEmail(SESSION.email);
        user.setName(SESSION.name);

        play.setKey(key);
        play.setTurn(1);
        initListBox();
        if(playerNumber == 1){
            // INSERT PLAY GAME
            play.setPlayer1(user);
            play.setPlayer2(userIntent);
            getViewModel().insertPlay(play);
        }
        else if (playerNumber == 2){
            play.setPlayer1(userIntent);
            play.setPlayer2(user);
        }
        getBinding().setViewModel(play);

        listenToKey();
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
        // VALIDATE TURN AND VALIDATE BUTTON
        if(winner == 0 && turn == playerNumber && getViewModel().validateButton(play, getViewModel().getBoxPosition(getResources().getResourceEntryName(v.getId())))){
            // UPDATE BOX
            int boxPosition = getViewModel().getBoxPosition(getResources().getResourceEntryName(v.getId()));
            play.getListBox().set(boxPosition-1, getResources().getColor(turn == 1 ? R.color.colorRed : R.color.colorBlue));

            // UPDATE TURN - FOR PLAYER
            turn = playerNumber == 1 ? 2 : 1;
            play.setTurn(turn);

            getViewModel().insertPlay(play);
        }
    }

    private void initListBox(){
        List<Integer> listBox = new ArrayList<>();
        for (int i=0; i<9; i++){
            listBox.add(getResources().getColor(R.color.colorBrown));
        }
        play.setListBox(listBox);
    }

    private void listenToKey(){
        getViewModel().listenToKey(key).observe(this, new Observer<Play>() {
            @Override
            public void onChanged(@Nullable Play response) {
                play = response;
                if(response != null){
                    // UPDATE TURN - FOR OPPONENT
                    turn = response.getTurn();
                    if(response.getTurn() == 1){
                        getBinding().turn1.setVisibility(View.VISIBLE);
                        getBinding().turn2.setVisibility(View.GONE);
                    }
                    else if (response.getTurn() == 2){
                        getBinding().turn1.setVisibility(View.GONE);
                        getBinding().turn2.setVisibility(View.VISIBLE);
                    }

                    getBinding().setViewModel(response);

                    winner = getViewModel().checkBox(play);
                    if(winner != 0){
                        if(winner == playerNumber){
                            getBinding().txtWinner.setText(YOU_WIN);
                            getBinding().txtWinner.setTextColor(getResources().getColor(R.color.colorGreen));
                        }
                        else {
                            getBinding().txtWinner.setText(YOU_LOSE);
                            getBinding().txtWinner.setTextColor(getResources().getColor(R.color.colorRed));
                        }
                    }
                }
            }
        });
    }



}