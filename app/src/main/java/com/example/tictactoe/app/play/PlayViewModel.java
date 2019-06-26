package com.example.tictactoe.app.play;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.support.v4.graphics.ColorUtils;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.tictactoe.R;
import com.example.tictactoe.common.APIHandler;
import com.example.tictactoe.common.APIHelper;
import com.example.tictactoe.model.Play;
import com.example.tictactoe.model.User;
import com.example.tictactoe.repository.PlayRepository;
import com.google.android.gms.common.internal.ResourceUtils;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PlayViewModel extends ViewModel {

    private Context context;
    private PlayRepository repo;
    private List<String> listName;

    public PlayViewModel(Context context){
        this.context = context;
        repo = PlayRepository.getInstance();
        listName = new ArrayList<>();
        listName.add("Joe");
        listName.add("Nathan");
        listName.add("Bob");
        listName.add("Eli");
        listName.add("Bill");
        listName.add("Charlie");
    }

    public boolean insertPlay(Play play){
        return repo.insertPlay(play) ? true : false;
    }

    public LiveData<Play> listenToKey(String key){
        final MutableLiveData<Play> result = new MutableLiveData<>();
        repo.listenToKey(new APIHandler() {
            @Override
            public void onSuccess(DataSnapshot dataSnapshot) {
                Play play = dataSnapshot.getValue(Play.class);
                result.setValue(play);
            }

            @Override
            public void onError(DatabaseError databaseError) {
                APIHelper.LOG(APIHelper.ERROR_TYPE, databaseError.getMessage());
                result.setValue(new Play());
            }
        }, key);
        return result;
    }

    public boolean validateButton(Play play, int id){
        return play.getListBox().get(id-1) == context.getResources().getColor(R.color.colorBrown) ? true : false;
    }

    public int getBoxPosition(String id){
        return Integer.parseInt(id.substring(3));
    }

    public int checkBox(Play play){
        List<Integer> box = play.getListBox();
        int boxNumber = -1;
        int filled = 0;

        // CHECK ROW
        for (int i=0; i<9; i+=3){
            if((box.get(i).intValue() == box.get(i+1).intValue() && box.get(i+1).intValue() == box.get(i+2).intValue())){
                return checkWinner(box.get(i).intValue());
            }

            // CHECK DIAGONAL
            switch(i){
                case 0:
                    if((box.get(i).intValue() == box.get(i+4).intValue() && box.get(i+4).intValue() == box.get(i+8).intValue())){
                        return checkWinner(box.get(i).intValue());
                    }
                    break;
                case 6:
                    if((box.get(i).intValue() == box.get(i-2).intValue() && box.get(i-2).intValue() == box.get(i-4).intValue())){
                        return checkWinner(box.get(i).intValue());
                    }
                    break;
            }
        }

        // CHECK COLUMN
        for (int i=0; i<3; i++){
            if((box.get(i).intValue() == box.get(i+3).intValue() && box.get(i+3).intValue() == box.get(i+6).intValue())){
                return checkWinner(box.get(i).intValue());
            }
        }

        // CHECK IF ALL THE BOX IS FILLED WITH COLOR
        for (int i=0; i<play.getListBox().size(); i++){
            if(play.getListBox().get(i).intValue() != context.getResources().getColor(R.color.colorBrown)){
                filled++;
            }
        }

        if(filled == 9){
            return 3;
        }

        return 0;
    }


    private int checkWinner(int box){
        if(box == context.getResources().getColor(R.color.colorRed)){
            return 1;
        }
        else if (box == context.getResources().getColor(R.color.colorBlue)){
            return 2;
        }
        return 0;
    }

    public User makeBot(){
        User user = new User();
        user.setEmail("BOT");
        user.setName(generateBotName());
        return user;
    }

    private String generateBotName(){
        Random random = new Random();
        return "BOT " + listName.get(random.nextInt(6));
    }

    public Play moveBot(final Play play){
        Random random = new Random();
        while(true){
            int i = random.nextInt(9);
            if(play.getListBox().get(i) == context.getResources().getColor(R.color.colorBrown)){
                play.getListBox().set(i, context.getResources().getColor(R.color.colorBlue));
                return play;
            }
        }
    }
}
