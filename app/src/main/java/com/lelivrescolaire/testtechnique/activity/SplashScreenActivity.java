package com.lelivrescolaire.testtechnique.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.lelivrescolaire.testtechnique.R;
import com.lelivrescolaire.testtechnique.interfaces.InitTaskListener;
import com.lelivrescolaire.testtechnique.tasks.InitTask;

public class SplashScreenActivity extends AppCompatActivity implements InitTaskListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
    }

    @Override
    protected void onResume() {
        super.onResume();
        InitTask initTask = new InitTask(this);
        initTask.execute();
    }

    @Override
    public void onInitFinished() {
        Intent intent = new Intent(this, LibraryActivity.class);
        startActivity(intent);
        finish();
    }
}
