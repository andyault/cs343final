package com.csci343.andy.tictactoe;

import android.content.Intent;
import android.graphics.RectF;
import android.support.v7.app.AlertDialog;
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
    public boolean isPVP;
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

    //logic
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

                if(space.contains(x, y) && this.game.data[i][j] == 0) {
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
     * prepare to switch between players/computer
     */
    public void prepNextMove() {
        /*
        first, toggle between 1 and 2 for currentPiece

        if it's pvp, call a function to handle that:
            change textview text
            ???

        if it's ai, call another function:
            find a random, available space
                make arraylist
                for each value in this.game.data, if it's blank, add {i, j} to array
                select random index from 0-size of array
            call fillSpace
         */

        //see if game is over
        int result = checkGameResult();

        if(result != 0) {
            endGame(result);

            return;
        }

        //switch piece color
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
        for each value in this.game.data, if it's blank, add index to array
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
        ArrayList<Integer> num = new ArrayList<>();

        //if any spaces are empty, add them to our list
        for(int i = 0; i < this.game.NUM_COLUMNS; i++) {
            for(int j = 0; j < this.game.NUM_ROWS; j++) {
                if (this.game.data[i][j] == 0) {
                    num.add(coordsToIndex(i, j));
                }
            }
        }

        //pick random space from list
        Random randomizer = new Random();
        int index = randomizer.nextInt(num.size());
        int[] nextSpace = indexToCoords(num.get(index));

        //get i and j
        int i = nextSpace[0];
        int j = nextSpace[1];


        //fill it
        fillSpace(i, j);

        //done :)
        prepNextMove();
    }

    /**
     * see if game can still be played
     * @return -1 if draw, 0 if not ended, 1-2 if activity_winner
     */
    public int checkGameResult() {
        //see if there are any spaces left
        boolean spacesLeft = false;

        for(int i = 0; i < this.game.NUM_COLUMNS; i++) {
            for(int j = 0; j < this.game.NUM_ROWS; j++) {
                if(this.game.data[i][j] == 0) {
                    spacesLeft = true;
                    break;
                }
            }

            if(spacesLeft)
                break;
        }

        if(!spacesLeft)
            return -1; //game over

        //see if anyone got 3 in a row
        //maybe don't hard code this?
        int[][] possibilities = {
            {0, 1, 2},
            {3, 4, 5},
            {6, 7, 8},
            {0, 3, 6},
            {1, 4, 7},
            {2, 5, 8},
            {0, 4, 8},
            {2, 4, 6}
        };

        for(int i = 0; i < possibilities.length; i++) {
            int[] spaces = possibilities[i];

            int data = -1;
            boolean winner = true;

            for(int j = 0; j < 3; j++) {
                int[] space = indexToCoords(spaces[j]);

                if(data < 0)
                    data = this.game.data[space[0]][space[1]];
                else if(this.game.data[space[0]][space[1]] != data) {
                    winner = false;
                    break;
                }
            }

            if(winner)
                return data;
        }

        return 0;
    }

    public void endGame(int result) {
        //TODO - make this not suck
        String[] winners = {
            "draw",
            null,
            "player 1 wins",
            (this.isPVP ? "player 2 wins" : "computer wins")
        };

        String winner = winners[result + 1];
        Intent displayWinner = new Intent(this, MainActivity.class);
        displayWinner.putExtra("winner", winner);
        startActivity(displayWinner);
        //System.out.println(winners[result + 1]);
    }

    //util
    /**
     * actually set game data for space
     */
    public void fillSpace(int i, int j) {
        //set data
        this.game.data[i][j] = this.currentPiece;

        //redraw
        this.game.invalidate();
    }

    public int coordsToIndex(int i, int j) {
        return j * this.game.NUM_COLUMNS + i;
    }

    public int[] indexToCoords(int index) {
        return new int[]{
            index % this.game.NUM_COLUMNS,
            (int) Math.floor(index / this.game.NUM_COLUMNS)
        };
    }
}
