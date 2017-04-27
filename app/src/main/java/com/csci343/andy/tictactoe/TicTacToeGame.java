package com.csci343.andy.tictactoe;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;

import java.net.URISyntaxException;

/**
 * Created by andy on 3/21/17.
 */


public class TicTacToeGame extends View {
    //constants
    public final int NUM_ROWS = 3;
    public final int NUM_COLUMNS = 3;

    private final int LINE_WIDTH = 16;
    private final int SPACE_PADDING = 32;

    //public variables
    public int[][] data = new int[NUM_COLUMNS][NUM_ROWS];
    public RectF[][] spaces = new RectF[NUM_COLUMNS][NUM_ROWS];

    //private variables
    private Paint drawPaint;

    private float w = -1;
    private float h = -1;

    private float gridSize;

    private float xOffset = 0;
    private float yOffset = 0;

    private float spaceW;
    private float spaceH;

    //constructor
    public TicTacToeGame(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        drawPaint = new Paint();
    }

    //methods
    /**
     * the big draw
     * @param canvas - our canvas
     */
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        /*
        TODO
        */
        //draw grid
        drawPaint.setARGB(255, 120, 120, 120);
        this.drawGrid(canvas);

        //draw spaces
        drawPaint.setARGB(10, 0, 0, 0);
        this.drawSpaces(canvas);

        //draw moves
        this.drawMoves(canvas);
    }

    /**
     * save size of canvas
     */
    @Override
    protected void onSizeChanged(int nx, int ny, int ox, int oy) {
        super.onSizeChanged(nx, ny, ox, oy);

        //save our new width and height
        this.w = nx;
        this.h = ny;

        //get the size of the grid we're about to draw
        //one size, it's square
        gridSize = Math.min(w, h);

        //draw at the center
        xOffset = w / 2 - gridSize / 2;
        yOffset = h / 2 - gridSize / 2;

        //get the size of each space
        spaceW = gridSize / NUM_ROWS;
        spaceH = gridSize / NUM_COLUMNS;

        //save spaces as rects - saves us headaches later
        float halfWidth = LINE_WIDTH / 2;

        for(int i = 0; i < NUM_COLUMNS; i++) {
            for(int j = 0; j < NUM_ROWS; j++) {
                float x = i * spaceW + xOffset;
                float y = j * spaceH + yOffset;

                this.spaces[i][j] = new RectF(
                        x + halfWidth + SPACE_PADDING,
                        y + halfWidth + SPACE_PADDING,
                        x + spaceW - halfWidth - SPACE_PADDING,
                        y + spaceH - halfWidth - SPACE_PADDING
                );
            }
        }
    }

    //drawing functions
    /**
     * draw grid lines
     */
    private void drawGrid(Canvas canvas) {
        float halfWidth = LINE_WIDTH / 2;

        //draw
        for(int i = 1; i < NUM_ROWS; i++) {
            float lineX = i * spaceW;

            canvas.drawRoundRect(
                    lineX + xOffset - halfWidth,
                    0f + yOffset,
                    lineX + xOffset + halfWidth,
                    gridSize + yOffset,
                    LINE_WIDTH, LINE_WIDTH, drawPaint
            );
        }

        for(int j = 1; j < NUM_COLUMNS; j++) {
            float lineY = j * spaceH;

            canvas.drawRoundRect(
                    0f + xOffset,
                    lineY + yOffset - halfWidth,
                    gridSize + xOffset,
                    lineY + yOffset + halfWidth,
                    LINE_WIDTH, LINE_WIDTH, drawPaint
            );
        }
    }

    /**
     * draw current available spaces
     */
    private void drawSpaces(Canvas canvas) {
        float halfWidth = LINE_WIDTH / 2;

        for(int i = 0; i < NUM_COLUMNS; i++) {
            for(int j = 0; j < NUM_ROWS; j++) {
                if(this.data[i][j] == 0) {
                    canvas.drawRoundRect(
                        this.spaces[i][j],
                        LINE_WIDTH,
                        LINE_WIDTH,
                        drawPaint
                    );
                }
            }
        }
    }

    /**
     * draw all played moves
     */
    private void drawMoves(Canvas canvas) {
        for(int i = 0; i < NUM_COLUMNS; i++) {
            for(int j = 0; j < NUM_ROWS; j++) {
                int data = this.data[i][j];
                RectF space = this.spaces[i][j];

                if(data > 0) {
                    if(data == 1)
                        drawPaint.setARGB(255, 255, 0, 0);
                    else
                        drawPaint.setARGB(255, 0, 0, 255);

                    canvas.drawCircle(
                        space.centerX(),
                        space.centerY(),
                        Math.min(space.width(), space.height()) / 2,
                        drawPaint
                    );
                }
            }
        }
    }

    //util funcs
    /**
     * Get a view's current activity
     * //http://stackoverflow.com/a/32973351
     *
     * @return the activity
     */
    private Activity getActivity() {
        Context context = getContext();

        while(context instanceof ContextWrapper) {
            if(context instanceof Activity)
                return (Activity) context;

            context = ((ContextWrapper) context).getBaseContext();
        }

        return null;
    }
}
