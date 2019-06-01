package com.example.tictactoe.app.play;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.example.tictactoe.R;
import com.example.tictactoe.common.BaseActivity;
import com.example.tictactoe.databinding.PlayActivityBinding;

public class PlayActivity extends BaseActivity<PlayViewModel, PlayActivityBinding> {
    public PlayActivity(){
        super(PlayViewModel.class, R.layout.play_activity);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
