package com.example.tictactoe.app.play;

import android.arch.lifecycle.ViewModel;
import android.content.Context;

public class PlayViewModel extends ViewModel {

    private Context context;


    public PlayViewModel(Context context){
        this.context = context;
    }
}
