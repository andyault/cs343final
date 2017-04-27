package com.csci343.andy.tictactoe;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView display = (ImageView)findViewById(R.id.imageView);
        display.setImageResource(R.drawable.logo);

        TextView displayWinner = (TextView)findViewById(R.id.winnerText);

        Intent i = getIntent();
        String winner = i.getExtras().getString("winner");

        if(winner.equals("draw"))
        {
            displayWinner.setText(R.string.display_winner_d);
        }
        if(winner.equals("player 1 wins"))
        {
            displayWinner.setText(R.string.txt_game_turn_p1);
        }
        if(winner.equals("player 2 wins"))
        {
            displayWinner.setText(R.string.txt_game_turn_p1);
        }
    }
}
