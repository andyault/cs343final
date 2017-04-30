package com.csci343.andy.tictactoe;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    //private variables
    private boolean isPVP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //getting intent
        Intent intent = getIntent();

        this.isPVP = intent.getBooleanExtra("tictactoe.isPVP", true);

        //winner messages
        int[] winners = {
                R.string.display_winner_d,
                -1,
                R.string.display_winner_pl1,
                (this.isPVP ? R.string.display_winner_pl2 : R.string.display_winner_c)
        };

        //set winner text
        TextView displayWinner = (TextView) findViewById(R.id.txtResult);

        int result = intent.getExtras().getInt("tictactoe.winner") + 1;

        displayWinner.setText(winners[result]);
    }

    public void onClick(View v) {
        Intent intent;

        switch (v.getId()) {
            case R.id.btnEnd_Replay:
                intent = new Intent(v.getContext(), GameActivity.class);
                intent.putExtra("tictactoe.isPVP", this.isPVP);
                break;

            case R.id.btnEnd_Menu:
            default:
                intent = new Intent(v.getContext(), MenuActivity.class);
                break;
        }

        startActivity(intent);
    }
}
