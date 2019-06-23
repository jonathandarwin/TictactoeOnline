package com.example.tictactoe.app.room;

import android.arch.lifecycle.Observer;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;

import com.example.tictactoe.R;
import com.example.tictactoe.app.play.PlayActivity;
import com.example.tictactoe.common.BaseActivity;
import com.example.tictactoe.databinding.RoomActivityBinding;
import com.example.tictactoe.model.Invite;
import com.example.tictactoe.model.SESSION;
import com.example.tictactoe.model.User;
import com.example.tictactoe.util.ToastUtil;
import java.util.ArrayList;
import java.util.List;

public class RoomActivity extends BaseActivity<RoomViewModel, RoomActivityBinding> {

    RoomAdapter adapter;
    List<User> listUser;
    String key;
    String invitationKey;

    public RoomActivity(){
        super(RoomViewModel.class, R.layout.room_activity);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initAdapter();
    }

    @Override
    protected void onResume() {
        super.onResume();
        key = getViewModel().joinRoom();
        getViewModel().getRoomUser().observe(this, new Observer<List<User>>() {
            @Override
            public void onChanged(@Nullable List<User> users) {
                listUser.clear();
                if(users.size() > 0){
                    listUser.addAll(users);
                }
                adapter.notifyDataSetChanged();
            }
        });
        getViewModel().checkInvitation().observe(this, new Observer<Invite>() {
            @Override
            public void onChanged(@Nullable Invite invite) {
                final String invitationKey = invite.getKey();
                AlertDialog.Builder dialog = createDialogConfirmation("Invitation from " + invite.getSenderName(), "You got invitation from " + invite.getSender());
                Log.d("masuksiniga", "masuk CHECK INVITATION");
                dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.d("masuksiniga", "masuk YES");
                        deleteInvitation(invitationKey);
                        getViewModel().updateAccepted(invitationKey, 1);
                        Bundle bundle = new Bundle();
                        bundle.putInt("player", 2);
                        gotoIntent(PlayActivity.class, bundle,true);
                    }
                });

                dialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deleteInvitation(invitationKey);
                        Log.d("masuksiniga", "masuk NO");
                    }
                });

                dialog.create().show();
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        getViewModel().deleteRoomUser(key);
    }

    private void initAdapter(){
        listUser = new ArrayList<>();
        adapter = new RoomAdapter(this, listUser, new AdapterButton() {
            @Override
            public void onClick(User user) {
                Invite invite = getViewModel().setInvitation(user);
                invitationKey = getViewModel().sendInvitation(invite);
                if(!invitationKey.equals("")){
                    ToastUtil.make(RoomActivity.this, "Send Invitation Success");
                    listenInvitationKey();
                }
                else{
                    ToastUtil.make(RoomActivity.this, "Send Invitation Failed");
                }
            }
        });
        getBinding().recyclerView.setHasFixedSize(true);
        getBinding().recyclerView.setLayoutManager(new LinearLayoutManager(this));
        getBinding().recyclerView.setAdapter(adapter);
    }

    public void listenInvitationKey(){
        getViewModel().listenInvitationKey(invitationKey).observe(this, new Observer<Invite>() {
            @Override
            public void onChanged(@Nullable Invite invite) {
                // IF RESPONDED AND ACCEPTED
                if(invite.getResponded() == 1 && invite.getAccepted() == 1){
                    // GO TO PLAY
                    Bundle bundle = new Bundle();
                    bundle.putInt("player", 1);
                    gotoIntent(PlayActivity.class, bundle, true);
                }
            }
        });
    }

    private void deleteInvitation(String invitationKey){
        getViewModel().deleteInvitation(invitationKey);
    }

    public interface AdapterButton{
        void onClick(User user);
    }
}
