package com.example.tic_tac_toe

import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.airbnb.lottie.LottieAnimationView


@Suppress("DEPRECATION")
class singlePlayer : AppCompatActivity() {
    var flag:Boolean = false
    lateinit var btn1: Button
    lateinit var btn2: Button
    lateinit var btn3: Button
    lateinit var btn4: Button
    lateinit var btn5: Button
    lateinit var btn6: Button
    lateinit var btn7: Button
    lateinit var btn8: Button
    lateinit var btn9: Button
    lateinit var tv: TextView
    lateinit var s1: TextView
    lateinit var s2: TextView
    lateinit var restart_btn: Button
    var score1 = 0
    var score2 = 0

    var player = 1
    var player1 = ArrayList<Int>()
    var player2 = ArrayList<Int>()
    var emptyCells = ArrayList<Int>()


    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_single_player)
        var nums = arrayOf(1,2,3,4,5,6,7,8,9)
        for(i in nums){
            emptyCells.add(i)
        }



        tv = findViewById(R.id.textView_won)
        s1 = findViewById(R.id.score1)
        s2 = findViewById(R.id.score2)
        btn1 = findViewById(R.id.button1)
        btn2 = findViewById(R.id.button2)
        btn3 = findViewById(R.id.button3)
        btn4 = findViewById(R.id.button4)
        btn5 = findViewById(R.id.button5)
        btn6 = findViewById(R.id.button6)
        btn7 = findViewById(R.id.button7)
        btn8 = findViewById(R.id.button8)
        btn9 = findViewById(R.id.button9)
        restart_btn = findViewById(R.id.restart_button)


        restart_btn.visibility = View.INVISIBLE
        btn1.setOnClickListener {
            selectedButton(btn1)
        }
        btn2.setOnClickListener {
            selectedButton(btn2)
        }
        btn3.setOnClickListener {
            selectedButton(btn3)
        }
        btn4.setOnClickListener {
            selectedButton(btn4)
        }
        btn5.setOnClickListener {
            selectedButton(btn5)
        }
        btn6.setOnClickListener {
            selectedButton(btn6)
        }
        btn7.setOnClickListener {
            selectedButton(btn7)
        }
        btn8.setOnClickListener {
            selectedButton(btn8)
        }
        btn9.setOnClickListener {
            selectedButton(btn9)
        }
        restart_btn.setOnClickListener {
            restart()

        }

    }
    fun selectedButton(view: View){
        val selectedBtn = view as Button
        var  btnNum = 0
        when(selectedBtn.id){
            R.id.button1 -> btnNum = 1
            R.id.button2 -> btnNum = 2
            R.id.button3 -> btnNum = 3
            R.id.button4 -> btnNum = 4
            R.id.button5 -> btnNum = 5
            R.id.button6 -> btnNum = 6
            R.id.button7 -> btnNum = 7
            R.id.button8 -> btnNum = 8
            R.id.button9 -> btnNum = 9
        }

        game(btnNum,selectedBtn)
    }



fun checkWin() {

    if (player1.contains(1)&& player1.contains(2) && player1.contains(3) || player1.contains(4) && player1.contains(5) && player1.contains(6)
        ||player1.contains(7) && player1.contains(8) && player1.contains(9) || player1.contains(1)&& player1.contains(4) && player1.contains(7)
        || player1.contains(2) && player1.contains(5) && player1.contains(8) || player1.contains(3) && player1.contains(6) && player1.contains(9)
        ||player1.contains(3) && player1.contains(5) && player1.contains(7) || player1.contains(1) && player1.contains(5) && player1.contains(9)){
        Toast.makeText(this, "You Won!", Toast.LENGTH_SHORT).show()
        tv.text = "You Won!"
        score1++
        s1.text = "You: $score1"
        flag = true

        disableButton()
    }else if(player2.contains(1)&& player2.contains(2) && player2.contains(3) || player2.contains(4) && player2.contains(5) && player2.contains(6)
        ||player2.contains(7) && player2.contains(8) && player2.contains(9) || player2.contains(1)&& player2.contains(4) && player2.contains(7)
        || player2.contains(2) && player2.contains(5) && player2.contains(8) || player2.contains(3) && player2.contains(6) && player2.contains(9)
        ||player2.contains(3) && player2.contains(5) && player2.contains(7) || player2.contains(1) && player2.contains(5) && player2.contains(9)){
        Toast.makeText(this,"Computer Won!", Toast.LENGTH_SHORT).show()
        tv.text = "Computer Won!"
        score2++
        s2.text = "Computer: $score2"
        flag = true

        disableButton()
    }


}

    @SuppressLint("ResourceAsColor", "SuspiciousIndentation")
    fun game(cell:Int, selectedBtn: Button){
        restart_btn.visibility = View.INVISIBLE
        if (player == 1){
            selectedBtn.text = "O"
            selectedBtn.setBackgroundColor(Color.parseColor("#FF5757"))
            player1.add(cell)
            emptyCells.remove(cell)
            player =4
        }
        selectedBtn.isEnabled = false
        checkWin()
        if(flag==false){
            if(!neutral())
            Handler().postDelayed(Runnable {robot()}, 200)   //TODO singleplayer all done✅,dual player pending ,icon,online,sound,crossline
        }




    }
    fun restart(){
        restart_btn.visibility = View.INVISIBLE
        player1.clear()
        player2.clear()
        tv.text = ""
        flag = false
        btn1.setBackgroundColor(Color.parseColor("#DFDEDE"))
        btn2.setBackgroundColor(Color.parseColor("#DFDEDE"))
        btn3.setBackgroundColor(Color.parseColor("#DFDEDE"))
        btn4.setBackgroundColor(Color.parseColor("#DFDEDE"))
        btn5.setBackgroundColor(Color.parseColor("#DFDEDE"))
        btn6.setBackgroundColor(Color.parseColor("#DFDEDE"))
        btn7.setBackgroundColor(Color.parseColor("#DFDEDE"))
        btn8.setBackgroundColor(Color.parseColor("#DFDEDE"))
        btn9.setBackgroundColor(Color.parseColor("#DFDEDE"))
        btn1.text = ""
        btn2.text = ""
        btn3.text = ""
        btn4.text = ""
        btn5.text = ""
        btn6.text = ""
        btn7.text = ""
        btn8.text = ""
        btn9.text = ""
        btn1.isEnabled = true
        btn2.isEnabled = true
        btn3.isEnabled = true
        btn4.isEnabled = true
        btn5.isEnabled = true
        btn6.isEnabled = true
        btn7.isEnabled = true
        btn8.isEnabled = true
        btn9.isEnabled = true
        player = 1
        emptyCells.clear()
        var nums = arrayOf(1,2,3,4,5,6,7,8,9)
        for(i in nums){
            emptyCells.add(i)
        }

    }

    fun neutral(): Boolean{
        if (player1.size + player2.size ==9 && !flag){
            disableButton()
            Toast.makeText(this, "Tied", Toast.LENGTH_SHORT).show()
            tv.text = "Tied!"
            return true
        }
        return false
    }

    fun disableButton(){
        btn1.isEnabled = false
        btn2.isEnabled = false
        btn3.isEnabled = false
        btn4.isEnabled = false
        btn5.isEnabled = false
        btn6.isEnabled = false
        btn7.isEnabled = false
        btn8.isEnabled = false
        btn9.isEnabled = false
        restart_btn.visibility = View.VISIBLE
    }
    // This function automatically make a move at a random position.
    fun robot()
    {

        val rnd:Int = emptyCells.random()
        if(emptyCells.contains(rnd)){
            val buttonselected : Button?
            buttonselected = when(rnd) {
                1 -> btn1
                2 -> btn2
                3 -> btn3
                4 -> btn4
                5 -> btn5
                6 -> btn6
                7 -> btn7
                8 -> btn8
                9 -> btn9
                else -> {restart_btn}
            }
//            game(rnd,buttonselected)

            if (player == 4){
                buttonselected.text = "X"
                buttonselected.setBackgroundColor(Color.parseColor("#457ab9"))
                player = 1
                emptyCells.remove(rnd)
                player2.add(rnd)
            }
            buttonselected.isEnabled = false
            checkWin()

        }
    }

}