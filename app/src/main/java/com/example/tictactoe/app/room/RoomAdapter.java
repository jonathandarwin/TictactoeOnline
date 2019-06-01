package com.example.tictactoe.app.room;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tictactoe.R;
import com.example.tictactoe.databinding.ListRoomItemBinding;
import com.example.tictactoe.model.User;

import java.util.List;

public class RoomAdapter extends RecyclerView.Adapter<RoomAdapter.ViewHolder> {

    private Context context;
    private List<User> listUser;
    private RoomActivity.AdapterButton listener;

    public RoomAdapter(Context context, List<User> listUser, RoomActivity.AdapterButton listener){
        this.context = context;
        this.listUser = listUser;
        this.listener = listener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ListRoomItemBinding binding;
        public ViewHolder(ListRoomItemBinding binding){
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    @NonNull
    @Override
    public RoomAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        ListRoomItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.list_room_item, viewGroup, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RoomAdapter.ViewHolder viewHolder, int i) {
        final User user = listUser.get(i);
        viewHolder.binding.setViewModel(user);
        viewHolder.binding.btnInvite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick(user);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listUser.size();
    }
}
