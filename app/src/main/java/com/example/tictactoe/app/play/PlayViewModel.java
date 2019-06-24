package com.example.tictactoe.app.play;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.v4.graphics.ColorUtils;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.tictactoe.R;
import com.example.tictactoe.common.APIHandler;
import com.example.tictactoe.common.APIHelper;
import com.example.tictactoe.model.Play;
import com.example.tictactoe.repository.PlayRepository;
import com.google.android.gms.common.internal.ResourceUtils;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

import java.util.List;

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

    public void checkBox(Play play){
        List<Integer> box = play.getListBox();
    }
}
