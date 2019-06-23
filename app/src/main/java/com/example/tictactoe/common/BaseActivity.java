package com.example.tictactoe.common;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;

import com.example.tictactoe.factory.ViewModelFactory;
import com.google.firebase.FirebaseApp;

public class BaseActivity<VM extends ViewModel, DataBinding extends ViewDataBinding> extends AppCompatActivity {
    VM viewModel;
    DataBinding binding;
    Class<VM> vm;
    int layout;

    public BaseActivity(){

    }

    public BaseActivity(Class<VM> vm, int layout){
        this.vm = vm;
        this.layout = layout;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseApp.initializeApp(this);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        viewModel = ViewModelProviders.of(this, new ViewModelFactory(this)).get(vm);
        binding = DataBindingUtil.setContentView(this, layout);
        setListener();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if(getCurrentFocus() != null){
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
        return super.dispatchTouchEvent(ev);
    }

    protected void setListener(){

    }

    protected VM getViewModel(){
        return viewModel;
    }

    protected DataBinding getBinding(){
        return binding;
    }

    protected void gotoIntent(Class classIntent, Bundle bundle, boolean isFinish){
        Intent intent = new Intent(this, classIntent);
        if(bundle != null){
            intent.putExtras(bundle);
        }
        startActivity(intent);
        if(isFinish){
            finish();
        }
    }

    protected AlertDialog.Builder createDialogConfirmation(String title, String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title);
        builder.setMessage(message);
        return builder;
    }
}
