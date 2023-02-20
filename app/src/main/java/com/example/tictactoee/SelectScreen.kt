package com.example.tictactoee


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.ImageView

class SelectScreen : AppCompatActivity() {
    private lateinit var tv1: EditText
    private lateinit var tv2: EditText

    private lateinit var nextbutton: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_screen)

        loadElements()

        nextbutton.setOnClickListener {
            var name1 = tv1.text.toString()
            var name2 = tv2.text.toString()

            sendResource(name1, name2)
        }
    }

    private fun loadElements() {
        tv1 = findViewById(R.id.oSignName)
        tv2 = findViewById(R.id.xSignName)
        nextbutton = findViewById(R.id.nextButton)
    }

    private fun sendResource(name1: String, name2: String) {
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra("name2", name1)
        intent.putExtra("name1", name2)
        startActivity(intent)
    }
}