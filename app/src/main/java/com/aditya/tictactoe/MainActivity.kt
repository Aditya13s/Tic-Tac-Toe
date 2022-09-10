package com.aditya.tictactoe

import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.core.view.iterator
import java.util.*
import kotlin.math.log

class MainActivity : AppCompatActivity() {
    /* active player = 0, yellow player | active player = 1, red player */
    private var activePlayer: Int = 0

    var count:Int = 0

    //This will tell us that game is active or not
    var gameActive:Boolean = true

    /*This will give us the state of the game that
    where is yellow player coin and where is red player coin*/
    private var gameState:Array<Int> = arrayOf(2,2,2,2,2,2,2,2,2)

    /*This will tell us that when player wins*/
    var winningPositions = arrayOf(
        arrayOf(0,1,2), arrayOf(3,4,5),
        arrayOf(6,7,8), arrayOf(0,3,6),
        arrayOf(1,4,7), arrayOf(2,5,8),
        arrayOf(0,4,8), arrayOf(2,4,6)
    )

    lateinit var winnerTextView : TextView

    lateinit var playAgainButton : Button

    override
    fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        winnerTextView = findViewById(R.id.winnerTextView)
        playAgainButton = findViewById(R.id.playAgain)
    }

    fun dropIn(view: View) {
        var counter : ImageView = view as ImageView
        val tappedCounter : Int = Integer.parseInt(counter.getTag().toString())

        if(gameState[tappedCounter] == 2 && gameActive && count<9) {
            setImage(counter);
            //This will set the value of the coins where players tapped
            gameState[tappedCounter] = activePlayer

            //This will check that that player won or not
            for(winningPosition : Array<Int> in winningPositions) {
                if(gameState[winningPosition[0]] == gameState[winningPosition[1]]
                    && gameState[winningPosition[1]] == gameState[winningPosition[2]]
                    && gameState[winningPosition[0]] != 2) {

                    if(activePlayer == 1) {
                        winnerTextView.text = "Yellow Wins"
                    } else{
                        winnerTextView.text = "Red Wins"
                    }
                    gameActive = false
                    winnerTextView.visibility = View.VISIBLE
                    playAgainButton.visibility = View.VISIBLE
                    return

                }
            }
            if(count == 9) {
                winnerTextView.setText("Match Draw")
                winnerTextView.visibility = View.VISIBLE
                playAgainButton.visibility = View.VISIBLE
            }
        }
    }

    //This function set image in the ImageView which is also known as counter
    private fun setImage(counter: ImageView) {
        activePlayer = if(activePlayer == 0) {
                counter.setImageResource(R.drawable.yellow)
                1
            } else {
                counter.setImageResource(R.drawable.red)
                0
            }
        animateImage(counter);
        count++
    }

    //This function is used to animate image view
    private fun animateImage(counter: ImageView) {
        counter.animate().rotationBy(1800F).duration = 300
    }

    fun play(view: View) {
        playAgainButton.visibility = View.INVISIBLE
        winnerTextView.visibility = View.INVISIBLE
        resetGame()

    }

    private fun resetGame() {
        for(item in gameState.indices) {
            gameState[item] = 2
        }
        activePlayer = 0
        gameActive = true
        count = 0
        removeAllImagesFromGridLayout()
    }

    private fun removeAllImagesFromGridLayout() {
        var myGrid : androidx.gridlayout.widget.GridLayout = findViewById(R.id.gridLayout)
        for(x in myGrid) {
            var image : ImageView = x as ImageView
            image.setImageDrawable(null)
        }
    }

}