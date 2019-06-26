package com.example.tictactoe.app.play;

import android.arch.lifecycle.Observer;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import com.example.tictactoe.R;
import com.example.tictactoe.app.room.RoomActivity;
import com.example.tictactoe.common.BaseActivity;
import com.example.tictactoe.databinding.PlayActivityBinding;
import com.example.tictactoe.model.Play;
import com.example.tictactoe.model.Room;
import com.example.tictactoe.model.User;

import java.util.ArrayList;
import java.util.List;

public class PlayActivity extends BaseActivity<PlayViewModel, PlayActivityBinding>
            implements View.OnClickListener{

    private static final String YOU_WIN = "You Win!";
    private static final String YOU_LOSE = "You Lose!";
    private static final String DRAW = "Draw...";

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
        User user = loadUserData();

        resetGame();

        if(key.equals(RoomActivity.CODE_BOT)){
            play.setPlayer1(user);
            play.setPlayer2(getViewModel().makeBot());

            getBinding().setViewModel(play);

            checkTurn();
        }
        else{
            User userIntent = (User) getIntent().getExtras().getSerializable("player");

            play.setKey(key);

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

        getBinding().btnPlayAgain.setOnClickListener(this);
        getBinding().btnQuit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        // VALIDATE TURN AND VALIDATE BUTTON
        if(v.equals(getBinding().btnPlayAgain)){
            if(key.equals(RoomActivity.CODE_BOT)){
                resetGame();
            }
            else{
                String text = loadUserData().getName() + " wants to play again.";
                if(playerNumber == 1){
                    play.setPlayer2Message(text);
                }
                else{
                    play.setPlayer1Message(text);
                }

                int playAgain = play.getPlayAgain() + 1;
                play.setPlayAgain(playAgain);
                if(playAgain == 2){
                    resetGame();
                }

                getViewModel().insertPlay(play);
            }
        }
        else if(v.equals(getBinding().btnQuit)){
            if(!key.equals(RoomActivity.CODE_BOT)){
                String text = loadUserData().getName() + " has left the game.";
                if(playerNumber == 1){
                    play.setPlayer2Message(text);
                }
                else if(playerNumber == 2){
                    play.setPlayer1Message(text);
                }

                getViewModel().insertPlay(play);
            }

            gotoIntent(RoomActivity.class, null, true);
        }
        else{
            // BOX
            if(key.equals(RoomActivity.CODE_BOT)){
                if(winner == 0){
                    // PLAYER MOVE
                    int boxPosition = getViewModel().getBoxPosition(getResources().getResourceEntryName(v.getId()));
                    play.getListBox().set(boxPosition-1, getResources().getColor(R.color.colorRed));

                    // UDPATE VIEW
                    turn = 2;
                    checkTurn();
                    getBinding().setViewModel(play);
                    checkWinner();

                    if(winner == 0){
                        // BOT MOVE
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                getViewModel().moveBot(play);

                                // UDPATE VIEW
                                turn = 1;
                                checkTurn();
                                getBinding().setViewModel(play);
                                checkWinner();
                            }
                        }, 300);
                    }
                }
            }
            else{
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
                    checkTurn();

                    getBinding().setViewModel(response);

                    checkWinner();

                    // SET MESSAGE
                    if(playerNumber == 1 && !play.getPlayer1Message().equals("")){
                        getBinding().txtWaiting.setText(play.getPlayer1Message());
                    }
                    else if (playerNumber == 2 && !play.getPlayer2Message().equals("")){
                        getBinding().txtWaiting.setText(play.getPlayer2Message());
                    }
                }
            }
        });
    }

    private void resetGame(){
        winner = 0;
        initListBox();
        play.setPlayAgain(0);
        play.setPlayer1Message("");
        play.setPlayer2Message("");
        turn = 1;
        play.setTurn(turn);
        checkTurn();
        getBinding().llWin.setVisibility(View.INVISIBLE);
        getBinding().txtWaiting.setText("");
    }

    private void checkWinner(){
        winner = getViewModel().checkBox(play);
        if(winner != 0){
            getBinding().llWin.setVisibility(View.VISIBLE);

            // DRAW
            if(winner == 3){
                getBinding().txtWinner.setText(DRAW);
                getBinding().txtWinner.setTextColor(getResources().getColor(R.color.colorBrown));
            }
            else if(winner == playerNumber){
                getBinding().txtWinner.setText(YOU_WIN);
                getBinding().txtWinner.setTextColor(getResources().getColor(R.color.colorGreen));
            }
            else {
                getBinding().txtWinner.setText(YOU_LOSE);
                getBinding().txtWinner.setTextColor(getResources().getColor(R.color.colorRed));
            }
        }
        else{
            getBinding().llWin.setVisibility(View.INVISIBLE);
        }
    }

    private void checkTurn(){
        if(turn == 1){
            getBinding().turn1.setVisibility(View.VISIBLE);
            getBinding().turn2.setVisibility(View.GONE);
        }
        else if (turn == 2){
            getBinding().turn1.setVisibility(View.GONE);
            getBinding().turn2.setVisibility(View.VISIBLE);
        }
    }
}