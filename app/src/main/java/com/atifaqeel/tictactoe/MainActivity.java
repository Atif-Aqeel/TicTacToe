package com.atifaqeel.tictactoe;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
//alt+enter for import

public class MainActivity extends AppCompatActivity {
    boolean gameActive = true;

    // Player representation
    //( 0 -> x )
    //( 1 -> 0 )
    int activePlayer = 0;
    //An array to represent 9 cells of two states
    int[] gameState = {2, 2, 2, 2, 2, 2, 2, 2, 2};

    // State meanings:
    //    0 -> X
    //    1 -> O
    //    2 -> Null
    // put all win positions in a 2D array {{ }}
    //0,1,2 same position means win, same as 0,3,6 and so on
    int[][] winPositions = {
            {0, 1, 2}, {3, 4, 5}, {6, 7, 8},
            {0, 3, 6}, {1, 4, 7}, {2, 5, 8},
            {0, 4, 8}, {2, 4, 6}
    };

    public static int counter = 0;

    //this function will be called every time a players tap in an empty box of the grid
    @SuppressLint("SetTextI18n")
    public void playerTap(View view) {
        //img type variable and type cast to ImageView
        ImageView img = (ImageView) view;

        //getTag() function get the tags of buttons and convert into string
        int tappedImage = Integer.parseInt(img.getTag().toString());

        // game reset function will be called,,, if someone wins or the boxes are full
        if (!gameActive) {
            gameReset(view);
        }

        // if the tapped image is empty
        if (gameState[tappedImage] == 2) {
            // increase the counter after every tap
            counter++;

            // check if its the last box
            if (counter == 9) {
                // reset the game
                gameActive = false;
            }

            // mark this position
            gameState[tappedImage] = activePlayer;

            // this will give a motion effect to the image
            // this function will translate the view, it takes float
            img.setTranslationY(-1000f);

            // change the active player from 0 to 1 or 1 to 0
            //if active player is 0 then set it to 1... else 0 if it is 1
            if (activePlayer == 0) {
                // set the image of x
                img.setImageResource(R.drawable.x);
                activePlayer = 1;

                TextView status = findViewById(R.id.status);
                // change the status
                status.setText("O's Turn - Tap to play");
            } else {
                // set the image of o
                img.setImageResource(R.drawable.o);
                activePlayer = 0;

                TextView status = findViewById(R.id.status);
                // change the status
                status.setText("X's Turn - Tap to play");
            }
            //animate the image in that way of positive 1000f, and it come back from -1000f to 1000f in 300 milli-seconds
            img.animate().translationYBy(1000f).setDuration(300);
        }

        int flag = 0;
        // Check if any player has won
        //for winPosition in my array winPositions
        for (int[] winPosition : winPositions) {
            //as three states 0,1,2
            if (gameState[winPosition[0]] == gameState[winPosition[1]] &&
                    gameState[winPosition[1]] == gameState[winPosition[2]] &&
                    gameState[winPosition[0]] != 2) {
                flag = 1;

                // Somebody has won! - Find out who!
                String winnerStr;

                // game reset function be called
                gameActive = false;
                //if o then x won
                if (gameState[winPosition[0]] == 0) {
                    winnerStr = "X has won";
                } else {
                    winnerStr = "O has won";
                }
                // Update the status bar for winner announcement
                TextView status = findViewById(R.id.status);
                status.setText(winnerStr);
            }
        }
        // set the status if the match draw
        if (counter == 9 && flag == 0) {
            TextView status = findViewById(R.id.status);
            status.setText("Match Draw");
        }
    }

    // reset the game
    @SuppressLint("SetTextI18n")
    public void gameReset(View view) {
        gameActive = true;
        activePlayer = 0;
        for (int i = 0; i < gameState.length; i++) {
            gameState[i] = 2;
        }

        // remove all the images from the boxes inside the grid
        ((ImageView) findViewById(R.id.imageView0)).setImageResource(0);
        ((ImageView) findViewById(R.id.imageView1)).setImageResource(0);
        ((ImageView) findViewById(R.id.imageView2)).setImageResource(0);
        ((ImageView) findViewById(R.id.imageView3)).setImageResource(0);
        ((ImageView) findViewById(R.id.imageView4)).setImageResource(0);
        ((ImageView) findViewById(R.id.imageView5)).setImageResource(0);
        ((ImageView) findViewById(R.id.imageView6)).setImageResource(0);
        ((ImageView) findViewById(R.id.imageView7)).setImageResource(0);
        ((ImageView) findViewById(R.id.imageView8)).setImageResource(0);

        TextView status = findViewById(R.id.status);
        status.setText("X's Turn - Tap to play");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}