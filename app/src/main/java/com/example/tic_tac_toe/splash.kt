package com.example.tic_tac_toe

import android.app.ActivityOptions
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.WindowManager
import android.widget.ImageView
import com.bumptech.glide.Glide

class splash : AppCompatActivity() {

    lateinit var imageView: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        supportActionBar?.hide()


        imageView = findViewById(R.id.imageview);

        // Adding the gif here using glide library
        //Glide.with(this).load(R.drawable.splashh).into(imageView);
        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, MainActivity::class.java)
            val options: ActivityOptions = ActivityOptions.makeSceneTransitionAnimation(this,android.util.Pair(imageView,"main_logo"))
            startActivity(intent,options.toBundle())
            finish()
        }, 3000) // 3000 is the delayed time in milliseconds.

    }
}