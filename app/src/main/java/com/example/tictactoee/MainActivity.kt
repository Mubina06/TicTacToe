package com.example.tictactoee

import android.app.Dialog
import android.content.Intent
import android.content.res.Resources
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.location.GnssAntennaInfo.Listener
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.View.OnClickListener
import android.view.Window
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.AppCompatButton
import java.lang.reflect.Modifier
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity(), OnClickListener {

    var matrix = Array(3) { IntArray(3) { -1 } }
    var active = true

    private var Turn = true

    lateinit var animation1: Animation
    lateinit var animation2: Animation

    private var boardList = mutableListOf<ImageView>()

    private lateinit var playerCh: TextView

    private lateinit var winner: TextView

    private lateinit var a1: ImageView
    private lateinit var a2: ImageView
    private lateinit var a3: ImageView

    private lateinit var b1: ImageView
    private lateinit var b2: ImageView
    private lateinit var b3: ImageView

    private lateinit var c1: ImageView
    private lateinit var c2: ImageView
    private lateinit var c3: ImageView

    private lateinit var name1: TextView
    private lateinit var name2: TextView

    private lateinit var restart: TextView

    private var fPlayerName: String = ""
    private var sPlayerName: String = ""

    private var crossesScore = 0
    private var noughtsScore = 0

//    private lateinit var viewKonfetti: KonfettiView

    private lateinit var score1: TextView
    private lateinit var score2: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        animation1 = AnimationUtils.loadAnimation(this, R.anim.anim1)
        animation2 = AnimationUtils.loadAnimation(this, R.anim.winneranimation)


        loadElements()
        loadBoard()

        fPlayerName = intent.getStringExtra("name1")!! //x qaysi biri bosa usha fPlayer boladi
        sPlayerName = intent.getStringExtra("name2")!!

        playerCh.text = fPlayerName

        name1.text = fPlayerName
        name2.text = sPlayerName

    }

    private fun loadBoard() {
        a1.setOnClickListener(this)
        a2.setOnClickListener(this)
        a3.setOnClickListener(this)

        b1.setOnClickListener(this)
        b2.setOnClickListener(this)
        b3.setOnClickListener(this)

        c1.setOnClickListener(this)
        c2.setOnClickListener(this)
        c3.setOnClickListener(this)


    }

    private fun loadElements() {
        playerCh = findViewById(R.id.Playerchanger)

        a1 = findViewById(R.id.a1)
        a2 = findViewById(R.id.a2)
        a3 = findViewById(R.id.a3)

        b1 = findViewById(R.id.b1)
        b2 = findViewById(R.id.b2)
        b3 = findViewById(R.id.b3)

        c1 = findViewById(R.id.c1)
        c2 = findViewById(R.id.c2)
        c3 = findViewById(R.id.c3)

        name1 = findViewById(R.id.name1)
        name2 = findViewById(R.id.name2)
        winner = findViewById(R.id.winner)

        score1 = findViewById(R.id.score1)
        score2 = findViewById(R.id.score2)

//        viewKonfetti = findViewById(R.id.konfettiView)
    }

    private var k = 0
    override fun onClick(v: View?) {
        val img = findViewById<ImageView>(v!!.id)
        val t = img.tag.toString().toInt()
        val col: Int = t / 3
        val row: Int = t % 3
        if (matrix[col][row] == -1) {
            if (active) {
                img.setImageResource(R.drawable.x_sign)
                img.startAnimation(animation1)
                active = false
                matrix[col][row] = 1
                isWinner(1)
                playerCh.text = sPlayerName
                k++
            } else {
                img.setImageResource(R.drawable.o_sign)
                img.startAnimation(animation1)
                active = true
                matrix[col][row] = 0
                isWinner(0)
                playerCh.text = fPlayerName
                k++
            }
        }

        if (k == 9) {
            winner.text = "Draw"
            finishGame()
        }
    }

    private fun finishGame() {
        a1.isEnabled = false
        a2.isEnabled = false
        a3.isEnabled = false

        b1.isEnabled = false
        b2.isEnabled = false
        b3.isEnabled = false

        c1.isEnabled = false
        c2.isEnabled = false
        c3.isEnabled = false

        k = 0
    }

    var count = 0
    private fun isWinner(a: Int) {
        //check by horizontal. left to right
        horizontalCheck(a)
        count = 0

        //check by vertical. top to bottom
        verticalCheck(a)
        count = 0

        leftTopToRightBottom(a)
        count = 0

        rightTopToLeftBottom(a)
        count = 0
    }

    private fun rightTopToLeftBottom(a: Int) {
        for (i in 0..2) {
            for (j in 0..2) {
                if (i + j == 2) {
                    if (matrix[j][i] == a) {
                        count++
                    }
                }
            }
        }

        showWinnerName(a)
    }

    private fun leftTopToRightBottom(a: Int) {
        for (i in 0..2) {
            for (j in 0..2) {
                if (i == j) {
                    if (matrix[j][i] == a) {
                        count++
                    }
                }
            }
        }
        showWinnerName(a)
    }

    private fun verticalCheck(a: Int) {
        for (i in 0..2) {
            for (j in 0..2) {
                if (matrix[i][j] == a) {
                    count++
                }
            }
            showWinnerName(a)
            count = 0
        }
    }

    private fun horizontalCheck(a: Int) {
        for (i in 0..2) {
            for (j in 0..2) {
                if (matrix[j][i] == a) {
                    count++
                }
            }
            showWinnerName(a)
            count = 0
        }
    }

    private fun restart() {
        matrix = Array(3) { IntArray(3) { -1 } }
        active = true
        playerCh.text = fPlayerName

        winner.text = ""

        a1.isEnabled = true
        a2.isEnabled = true
        a3.isEnabled = true

        b1.isEnabled = true
        b2.isEnabled = true
        b3.isEnabled = true

        c1.isEnabled = true
        c2.isEnabled = true
        c3.isEnabled = true

        a1.setImageDrawable(null)
        a2.setImageDrawable(null)
        a3.setImageDrawable(null)

        b1.setImageDrawable(null)
        b2.setImageDrawable(null)
        b3.setImageDrawable(null)

        c1.setImageDrawable(null)
        c2.setImageDrawable(null)
        c3.setImageDrawable(null)

        k = 0
    }

    private fun showWinnerName(a: Int) {
        var winnerName = ""
        winnerName = if (a == 0) sPlayerName else fPlayerName

//        if (count == 3) {
//            winner.text
//            var party = Party(
//                speed = 0f,
//                maxSpeed = 30f,
//                damping = 0.9f,
//                spread = 360,
//                colors = listOf(0xfce18a, 0xff726d, 0xf4306d, 0xb48def),
////                emitter = Emitter(duration = 100, TimeUnit.MILLISECONDS).max(100),
////                position = Position.Relative(0.5, 0.3)
//            )
//            viewKonfetti.start(party)

            val dialog = Dialog(this)
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog.setCancelable(false)
            dialog.setContentView(R.layout.dialog)

            val body = dialog.findViewById(R.id.winner) as TextView
            body.text = "Winner is " + winnerName
            val yesBtn = dialog.findViewById(R.id.menu) as AppCompatButton
            val noBtn = dialog.findViewById(R.id.restart) as AppCompatButton
            val goHome = dialog.findViewById(R.id.home) as AppCompatButton
            yesBtn.setOnClickListener {
                dialog.dismiss()
            }
            noBtn.setOnClickListener {
                restart()
                dialog.dismiss()
            }
            goHome.setOnClickListener {
                finish()
                dialog.dismiss()
            }
            dialog.show()
            if (winnerName == sPlayerName) {

                noughtsScore++
            } else {
                crossesScore++
            }
            finishGame()
            score2.text = noughtsScore.toString()
            score1.text = crossesScore.toString()
            return
        }


    }