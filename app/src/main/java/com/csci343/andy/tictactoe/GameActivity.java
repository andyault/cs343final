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

    /**
     * prepare to switch between players/computer
     */
    public void prepNextMove() {
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

        this.currentPiece = (this.currentPiece == 1 ? 2 : 1);

        if(this.isPVP)
            movePlayers();
        else
            moveComputer();
    }

    /**
     * switch moves in pvp game
     */
    public void movePlayers() {
        TextView player = (TextView)findViewById(R.id.txtTurn);

        if(currentPiece == 1)
            player.setText(R.string.txt_game_turn_p1);
        else
            player.setText(R.string.txt_game_turn_p2);
    }

    /**
     * switch moves in pvc game
     */
    public void moveComputer() {
        /*
        if it's ai, call another function:
        find a random, available space
        make int arraylist
        for each value in this.game.data, if it's blank, add {i, j} to array
        select random index from 0-size of array
        call fillSpace
         */

        TextView player = (TextView)findViewById(R.id.txtTurn);

        if(currentPiece == 1) {
            //if it's player 1's turn, we don't have to worry about picking spaces etc
            player.setText(R.string.txt_game_turn_p1);

            return;
        } else
            player.setText(R.string.txt_game_turn_c);

        //prepare a list of available spaces
        ArrayList<Integer[]> num = new ArrayList<>();

        //if any spaces are empty, add them to our list
        for(int i = 0; i < this.game.NUM_COLUMNS; i++) {
            for(int j = 0; j < this.game.NUM_ROWS; j++) {
                if (this.game.data[i][j] == 0) {
                    num.add(new Integer[]{i, j});
                }
            }
        }

        //pick random space from list
        Random randomizer = new Random();
        int index = randomizer.nextInt(num.size());
        Integer[] nextSpace = num.get(index);

        //get i and j
        int i = nextSpace[0];
        int j = nextSpace[1];


        //fill it
        fillSpace(i, j);

        //done :)
        /*
         nope -- using arraylist not
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
}
