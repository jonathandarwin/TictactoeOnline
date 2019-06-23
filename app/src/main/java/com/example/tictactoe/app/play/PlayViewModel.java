package com.example.tictactoe.app.play;

import android.arch.lifecycle.ViewModel;
import android.content.Context;

import com.example.tictactoe.model.Play;
import com.example.tictactoe.repository.PlayRepository;

public class PlayViewModel extends ViewModel {

    private Context context;
    private PlayRepository repo;

    public PlayViewModel(Context context){
        this.context = context;
        repo = PlayRepository.getInstance();
    }

    public boolean insertPlay(Play play){
        return repo.insertPlay(play) ? true : false;
    }
}
