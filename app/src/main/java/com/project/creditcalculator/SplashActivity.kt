package com.project.creditcalculator

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.animation.AnimationUtils
import com.project.creditcalculator.databinding.ActivitySplashBinding

class SplashActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val leftbaloonanim = AnimationUtils.loadAnimation(this, R.anim.leftbaloonanim)
        binding.imageView.animation = leftbaloonanim

        val downbuttonanim = AnimationUtils.loadAnimation(this, R.anim.downbuttonanim)
        binding.imageButton.animation = downbuttonanim

        binding.imageButton.setOnClickListener {
            val rightbaloonanim = AnimationUtils.loadAnimation(this, R.anim.rightbaloonanim)
            val upbuttonanim = AnimationUtils.loadAnimation(this, R.anim.upbuttonanim)
            binding.imageButton.startAnimation(upbuttonanim)
            binding.imageView.startAnimation(rightbaloonanim)

            object :CountDownTimer(1000,1000){
                override fun onTick(p0: Long) {

                }

                override fun onFinish() {
                    var intent= Intent(this@SplashActivity,MainActivity::class.java)
                    startActivity(intent)
                    finish()
                }

            }.start()


        }
    }
}