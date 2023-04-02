package com.example.tic_tac_toe

import android.annotation.SuppressLint
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

class MainActivity2 : AppCompatActivity() {
    lateinit var btn1: Button
    lateinit var btn2:Button
    lateinit var btn3:Button
    lateinit var btn4:Button
    lateinit var btn5:Button
    lateinit var btn6:Button
    lateinit var btn7:Button
    lateinit var btn8:Button
    lateinit var btn9:Button
    lateinit var tv: TextView
    lateinit var restart_btn:Button
    lateinit var s1: TextView
    lateinit var s2: TextView
    var score1 = 0
    var score2 = 0
    var flag = false


    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)




        tv = findViewById(R.id.textView)
        btn1 = findViewById(R.id.btn1)
        btn2 = findViewById(R.id.btn2)
        btn3 = findViewById(R.id.btn3)
        btn4 = findViewById(R.id.btn4)
        btn5 = findViewById(R.id.btn5)
        btn6 = findViewById(R.id.btn6)
        btn7 = findViewById(R.id.btn7)
        btn8 = findViewById(R.id.btn8)
        btn9 = findViewById(R.id.btn9)
        s1 = findViewById(R.id.score1)
        s2 = findViewById(R.id.score2)
        restart_btn = findViewById(R.id.restart_btn)


        restart_btn.visibility = View.INVISIBLE
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
    fun selectedButton(view:View){
        val selectedBtn = view as Button
        var  btnNum = 0
        when(selectedBtn.id){
            R.id.btn1 -> btnNum = 1
            R.id.btn2 -> btnNum = 2
            R.id.btn3 -> btnNum = 3
            R.id.btn4 -> btnNum = 4
            R.id.btn5 -> btnNum = 5
            R.id.btn6 -> btnNum = 6
            R.id.btn7 -> btnNum = 7
            R.id.btn8 -> btnNum = 8
            R.id.btn9 -> btnNum = 9
        }

        game(btnNum,selectedBtn)
    }

    var player = 1
    var player1 = ArrayList<Int>()
    var player2 = ArrayList<Int>()

    fun game(cell:Int,selectedBtn:Button){
        restart_btn.visibility = View.INVISIBLE
        if (player == 1){
            selectedBtn.text = "O"
            selectedBtn.setBackgroundColor(Color.parseColor("#FF5757"))
            player1.add(cell)
            player =4
        }else{
            selectedBtn.text = "X"
            selectedBtn.setBackgroundColor(Color.parseColor("#457ab9"))
            player = 1
            player2.add(cell)
        }
        selectedBtn.isEnabled = false
        neutral()
        var won = -1
        //row1
        if (player1.contains(1)&& player1.contains(2) && player1.contains(3)){
            Toast.makeText(this,"Player1 Won",Toast.LENGTH_SHORT).show()
            disableButton()
            won = 0
        }
        if (player2.contains(1) && player2.contains(2) && player2.contains(3)){
            Toast.makeText(this,"Player2 Won",Toast.LENGTH_SHORT).show()
            disableButton()
            won = 1
        }
        //row2
        if (player1.contains(4) && player1.contains(5) && player1.contains(6)){
            Toast.makeText(this,"Player1 Won",Toast.LENGTH_SHORT).show()
            disableButton()
            won = 0
        }
        if (player2.contains(4) && player2.contains(5) && player2.contains(6)){
            Toast.makeText(this,"Player2 Won",Toast.LENGTH_SHORT).show()
            disableButton()
            won = 1
        }
        //row3
        if (player1.contains(7) && player1.contains(8) && player1.contains(9)){
            Toast.makeText(this,"Player1 Won",Toast.LENGTH_SHORT).show()
            disableButton()
            won = 0
        }
        if (player2.contains(7) && player2.contains(8) && player2.contains(9)){
            Toast.makeText(this,"Player2 Won",Toast.LENGTH_SHORT).show()
            disableButton()
            won = 1
        }
        //clmn1
        if (player1.contains(1)&& player1.contains(4) && player1.contains(7)){
            Toast.makeText(this,"Player1 Won",Toast.LENGTH_SHORT).show()
            disableButton()
            won = 0
        }
        if (player2.contains(1) && player2.contains(4) && player2.contains(7)){
            Toast.makeText(this,"Player2 Won",Toast.LENGTH_SHORT).show()
            disableButton()
            won = 1
        }
        //clmn2
        if (player1.contains(2) && player1.contains(5) && player1.contains(8)){
            Toast.makeText(this,"Player1 Won",Toast.LENGTH_SHORT).show()
            disableButton()
            won = 0
        }
        if (player2.contains(2) && player2.contains(5) && player2.contains(8)){
            Toast.makeText(this,"Player2 Won",Toast.LENGTH_SHORT).show()
            disableButton()
            won = 1
        }
        //clmn3
        if (player1.contains(3) && player1.contains(6) && player1.contains(9)){
            Toast.makeText(this,"Player1 Won",Toast.LENGTH_SHORT).show()
            disableButton()
            won = 0
        }
        if (player2.contains(3) && player2.contains(6) && player2.contains(9)){
            Toast.makeText(this,"Player2 Won",Toast.LENGTH_SHORT).show()
            disableButton()
            won = 1
        }
        //
        //
        if (player1.contains(3) && player1.contains(5) && player1.contains(7)){
            Toast.makeText(this,"Player1 Won",Toast.LENGTH_SHORT).show()
            disableButton()
            won = 0
        }
        if (player2.contains(3) && player2.contains(5) && player2.contains(7)){
            Toast.makeText(this,"Player2 Won",Toast.LENGTH_SHORT).show()
            disableButton()
            won = 1
        }
        //
        //
        if (player1.contains(1) && player1.contains(5) && player1.contains(9)){
            Toast.makeText(this,"Player1 Won",Toast.LENGTH_SHORT).show()
            disableButton()
            won = 0
        }
        if (player2.contains(1) && player2.contains(5) && player2.contains(9)){
            Toast.makeText(this,"Player2 Won",Toast.LENGTH_SHORT).show()
            disableButton()
            won = 1
        }
        if(won == 0){
            tv.text = "0 Won!"
            score1++
            s1.text = "O : $score1"
        }
        if(won == 1){
            tv.text = "X Won!"
            score2++
            s2.text = "X : $score2"
        }

    }
    fun restart(){
        player1.clear()
        player2.clear()

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
        tv.text=""



    }

    fun neutral(){

        if (player1.size ==4 && player2.size ==4 ){
            disableButton()
            tv.text="Draw"
        }else if (player1.size >4 || player2.size >4){

        }
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
}