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

public class MainActivity extends AppCompatActivity  {

    private boolean isPvp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView display = (ImageView)findViewById(R.id.tictactoe);
        display.setImageResource(R.drawable.logo);

        TextView displayWinner = (TextView)findViewById(R.id.winnerText);

        Intent i = getIntent();
        String winner = i.getExtras().getString("winner");
        String[] winnerChoice = new String[] {"draw", "player 1 wins",
                "player 2 wins", "computer wins"};

        int z = 0;
        while(!(winnerChoice[z].equals(winner)))
        {
            z++;
        }

        String realWinner = winnerChoice[z];
        displayWinner.setText(realWinner);

        GameActivity gA = new GameActivity();
        this.isPvp = gA.isPVP;

        Button replay = (Button)findViewById(R.id.replayButton);
        Button home = (Button)findViewById(R.id.homeButton);
    }
        public void onClick(View v) {
            switch (v.getId())
            {
                case R.id.replayButton:
                    if(isPvp) {
                        Intent i = new Intent(v.getContext(), GameActivity.class);
                        i.putExtra("isPvP", this.isPvp);
                        startActivity(i);
                        break;
                    }else {
                        this.isPvp = false;
                        Intent i = new Intent(v.getContext(), GameActivity.class);
                        i.putExtra("isPvP", this.isPvp);
                        startActivity(i);
                        break;
                    }
                case R.id.homeButton:
                    Intent j = new Intent(v.getContext(), MenuActivity.class);
                    startActivity(j);
                    break;
                }
            }
    }
