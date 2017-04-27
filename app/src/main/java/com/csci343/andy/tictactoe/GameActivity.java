package com.csci343.andy.tictactoe;

import android.content.Intent;
import android.graphics.RectF;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

public class GameActivity extends AppCompatActivity {
    //private variables
    private TicTacToeGame game;
    private boolean isPVP;
    private int totIndex = 1;
    private boolean isPlayerTurn = true;
    private int currentPiece = 1;

    //methods
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        //handle intent, whether game is pvp or not
        Intent gameActivity = getIntent();
        this.isPVP = gameActivity.getBooleanExtra("tictactoe.isPVP", true);

        //save reference to game and add touch support
        this.game = (TicTacToeGame) findViewById(R.id.gameMain);

        this.game.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch(event.getAction()) {
                    case MotionEvent.ACTION_UP:
                        float x = event.getX();
                        float y = event.getY();

                        handleTouch(x, y);

                    case MotionEvent.ACTION_DOWN:
                        return true;

                    default:
                        return false;
                }
            }
        });
    }

    /**
     * handle touching
     */
    public void handleTouch(float x, float y) {
        //nice and easy - get our i and j for our our space being pressed
        int i = -1;
        int j = -1;

        boolean found = false;

        for(i = 0; i < this.game.NUM_COLUMNS; i++) {
            for(j = 0; j < this.game.NUM_ROWS; j++) {
                RectF space = this.game.spaces[i][j];

                if(space.contains(x, y)) {
                    found = true;
                    break;
                }
            }

            //break only exits on loop
            if(found)
                break;
        }

        //make sure we're in a space
        if(!found)
            return;

        //changed the data for the move we just made
        fillSpace(i, j);

        //done
        prepNextMove();
    }

    /**
     * actually set game data for space
     */
    public void fillSpace(int i, int j) {
        //set data
        this.game.data[i][j] = this.currentPiece;

        //redraw
        this.game.invalidate();
    }

    public void prepNextMove()
    {
        /*
        TODO
        first, toggle between 1 and 2 for currentPiece

        if it's pvp, call a function to handle that:
            change textview text
            ???

        if it's ai, call another function:
            find a random, available space
                make 1D int[] array [NUM_COLUMNS * NUM_ROWS]
                for each value in this.game.data, if it's blank, add {i, j} to array
                select random index from 0-size of array
            call fillSpace
         */

        if(this.isPVP)
        {
            if(currentPiece == 1)
            {
                currentPiece = 2;
            } else
            {
                currentPiece = 1;
            }
            multiPlayer();
        }
        else
        {
            int i = 0;
            int j = 0;
            aI(i, j);
        }

    }

    public void multiPlayer()
    {
        TextView player = (TextView)findViewById(R.id.txtTurn);

        if(currentPiece == 1)
        {
            player.setText(R.string.txt_game_turn_p1);
        }
        else
        {
            player.setText(R.string.txt_game_turn_p2);
        }
    }

    /*
    TODO
    detect when someone wins
     */
    public void aI(int i, int j) {
        /*
        if it's ai, call another function:
        find a random, available space
        make 1D int[] array [NUM_COLUMNS * NUM_ROWS]
        for each value in this.game.data, if it's blank, add {i, j} to array
        select random index from 0-size of array
        call fillSpace
         */

        if (totIndex % 2 == 1 && currentPiece == 1) {
            TextView player = (TextView) findViewById(R.id.txtTurn);
            player.setText(R.string.txt_game_turn_p);
            currentPiece = 2;
        }
        else if (totIndex % 2 == 0 && currentPiece == 2) {
            currentPiece = 1;
            TextView player = (TextView) findViewById(R.id.txtTurn);
            player.setText(R.string.txt_game_turn_c);
            ArrayList<Integer> num = new ArrayList<Integer>();

            i = 0;
            j = 0;
            int x = 0;


            for (i = 0; i < game.NUM_COLUMNS; i++) {
                for (j = 0; j < game.NUM_ROWS; j++) {
                    if (this.game.data[i][j] == 0) {
                        num.set(x, 0);
                    }
                }
                x++;
            }

            Random randomizer = new Random();
            int random = num.get(randomizer.nextInt(num.size()));

            switch (random) {
                case 1:
                    game.data[i][j] = this.game.data[0][0];
                    fillSpace(i, j);
                case 2:
                    game.data[i][j] = this.game.data[0][1];
                    fillSpace(i, j);
                case 3:
                    game.data[i][j] = this.game.data[0][2];
                    fillSpace(i, j);
                case 4:
                    game.data[i][j] = this.game.data[1][0];
                    fillSpace(i, j);
                case 5:
                    game.data[i][j] = this.game.data[1][1];
                    fillSpace(i, j);
                case 6:
                    game.data[i][j] = this.game.data[1][2];
                    fillSpace(i, j);
                case 7:
                    game.data[i][j] = this.game.data[0][2];
                    fillSpace(i, j);
                case 8:
                    game.data[i][j] = this.game.data[1][2];
                    fillSpace(i, j);
                case 9:
                    game.data[i][j] = this.game.data[2][2];
                    fillSpace(i, j);
            }

            //if 9, game is over (maybe use this later?)
            /**
             * nope -- using arraylist not
             for(i = 0; i < 9; i++)
             {
             sum = num[i] + sum;
             if(sum == 9)
             {
             //game is over, method call
             }
             }
             */
        }
        totIndex++;
    }

}
