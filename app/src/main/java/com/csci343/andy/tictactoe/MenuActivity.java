package com.csci343.andy.tictactoe;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MenuActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
    }

    public void onClick(View v) {
        //?
        //you checked if a button was equal to true

        boolean isPVP = true;

        if(v.getId() == R.id.btnMenu_Computer)
            isPVP = false;

        Intent gameActivity = new Intent(this, GameActivity.class);
        gameActivity.putExtra("tictactoe.isPVP", isPVP);

        startActivity(gameActivity);
    }
}
