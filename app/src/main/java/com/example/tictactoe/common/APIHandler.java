package com.example.tictactoe.common;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

public interface APIHandler {
    void onSuccess(DataSnapshot dataSnapshot);
    void onError(DatabaseError databaseError);
}
