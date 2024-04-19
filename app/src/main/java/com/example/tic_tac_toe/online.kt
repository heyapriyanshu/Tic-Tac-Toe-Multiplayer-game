package com.example.tic_tac_toe

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isVisible
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class online : AppCompatActivity() {
    lateinit var btn1: Button
    lateinit var btn2: Button
    lateinit var btn3: Button
    lateinit var btn4: Button
    lateinit var btn5: Button
    lateinit var btn6: Button
    lateinit var btn7: Button
    lateinit var btn8: Button
    lateinit var btn9: Button

    val firebaseDatabase: FirebaseDatabase = FirebaseDatabase.getInstance()
    var firebaseReference = firebaseDatabase.reference.child("Rooms")

    lateinit var tv: TextView
    lateinit var s1: TextView
    lateinit var s2: TextView
    lateinit var p1: TextView
    lateinit var p2: TextView
    lateinit var restart_btn: Button

    var player1: String = ""
    var player2: String = ""
    lateinit var current_player: String

    var player1Steps = ArrayList<Int>()
    var player2Steps = ArrayList<Int>()

    var my_username: String = ""
    var room_id: String = ""


    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_online)

        tv = findViewById(R.id.textView)
        s1 = findViewById(R.id.score1)
        s2 = findViewById(R.id.score2)
        p1 = findViewById(R.id.player1)
        p2 = findViewById(R.id.player2)
        restart_btn = findViewById(R.id.restart_btn)
        btn1 = findViewById(R.id.online1)
        btn2 = findViewById(R.id.online2)
        btn3 = findViewById(R.id.online3)
        btn4 = findViewById(R.id.online4)
        btn5 = findViewById(R.id.online5)
        btn6 = findViewById(R.id.online6)
        btn7 = findViewById(R.id.online7)
        btn8 = findViewById(R.id.online8)
        btn9 = findViewById(R.id.online9)



        restart_btn.visibility = View.INVISIBLE

        restart_btn.setOnClickListener {
            firebaseReference.child("Restart").setValue(1)


        }
        enableActivity(false)

        my_username = intent.getStringExtra("my_username").toString()
        room_id = intent.getStringExtra("room_id").toString()



        firebaseReference = firebaseReference.child(room_id)

        waitingForPlayer()
        updateCurrentPlayer()
        updatePlayerMove()
        startGame()
        checkForRestart()
        updateScoreBoard()
    }


    private fun updateScoreBoard() {
        var player1WinsReference = firebaseReference.child("Player1Wins")
        var player2WinsReference = firebaseReference.child("Player2Wins")

        player1WinsReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                s1.text = snapshot.value.toString()
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })

        player2WinsReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                s2.text = snapshot.value.toString()
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })


    }

    private fun checkForRestart() {
        firebaseReference.child("Restart").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val restart: Int = snapshot.value.toString().toInt()
                if (restart == 1) {
                    firebaseReference.child("Restart").setValue(0)
                    tv.text = "Online"
                    clearAll()
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })
    }

    private fun clearAll() {
        firebaseReference.child("Steps").removeValue()
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

        restart_btn.visibility = View.INVISIBLE
        player1Steps.clear()
        player2Steps.clear()
        

    }

    private fun updatePlayerMove() {

        //Player 1 moves
        firebaseReference.child("Steps").child("Player1Steps").addValueEventListener(object :
            ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                player1Steps.clear()
                for (eachSteps in snapshot.children) {
                    player1Steps.add(eachSteps.value.toString().toInt())
                }
                for (num in player1Steps) {
                    when (num) {
                        1 -> updateBtn(btn1, 0)
                        2 -> updateBtn(btn2, 0)
                        3 -> updateBtn(btn3, 0)
                        4 -> updateBtn(btn4, 0)
                        5 -> updateBtn(btn5, 0)
                        6 -> updateBtn(btn6, 0)
                        7 -> updateBtn(btn7, 0)
                        8 -> updateBtn(btn8, 0)
                        9 -> updateBtn(btn9, 0)

                    }
                }
                checkWinningPossibility(player1Steps, player1, player2Steps)
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })

        //Player 2 moves
        firebaseReference.child("Steps").child("Player2Steps").addValueEventListener(object :
            ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                player2Steps.clear()
                for (eachSteps in snapshot.children) {
                    player2Steps.add(eachSteps.value.toString().toInt())
                }

                for (num in player2Steps) {
                    when (num) {
                        1 -> updateBtn(btn1, 1)
                        2 -> updateBtn(btn2, 1)
                        3 -> updateBtn(btn3, 1)
                        4 -> updateBtn(btn4, 1)
                        5 -> updateBtn(btn5, 1)
                        6 -> updateBtn(btn6, 1)
                        7 -> updateBtn(btn7, 1)
                        8 -> updateBtn(btn8, 1)
                        9 -> updateBtn(btn9, 1)

                    }
                }
                checkWinningPossibility(player2Steps, player2, player1Steps)
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })


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

    private fun checkWinningPossibility(
        player1Steps: ArrayList<Int>,
        player: String,
        player2Steps: ArrayList<Int>
    ) {

        if ((player1Steps.contains(1) && player1Steps.contains(2) && player1Steps.contains(3)) || (player1Steps.contains(
                1
            ) && player1Steps.contains(4) && player1Steps.contains(7)) ||
            (player1Steps.contains(3) && player1Steps.contains(6) && player1Steps.contains(9)) || (player1Steps.contains(
                7
            ) && player1Steps.contains(8) && player1Steps.contains(9)) ||
            (player1Steps.contains(4) && player1Steps.contains(5) && player1Steps.contains(6)) || (player1Steps.contains(
                1
            ) && player1Steps.contains(5) && player1Steps.contains(9)) ||
            player1Steps.contains(3) && player1Steps.contains(5) && player1Steps.contains(7) || (player1Steps.contains(
                2
            ) && player1Steps.contains(
                5
            ) && player1Steps.contains(8))
        ) {
            //pLAYER 1 WINS
            Toast.makeText(this@online, "$player Won", Toast.LENGTH_SHORT).show()
            disableButton()
            tv.text = "$player Won!"

            updateScoreInDatabase(player)
        } else if ((player1Steps.size + player2Steps.size) == 9) {
            Toast.makeText(this@online, "Tied", Toast.LENGTH_SHORT).show()
            restart_btn.isVisible = true
            tv.text = "Tied!"
        }


    }

    private fun updateScoreInDatabase(player: String) {
        var scoreReference = firebaseReference
        if (player == player1) {
            scoreReference = scoreReference.child("Player1Wins")
        } else if (player == player2) {
            scoreReference = scoreReference.child("Player2Wins")
        }

        scoreReference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                scoreReference.setValue(snapshot.value.toString().toInt() + 1)
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })

    }

    private fun updateBtn(buttonselected: Button, i: Int) {
        if (i == 0) {
            buttonselected.text = "X"
            buttonselected.setBackgroundColor(Color.parseColor("#457ab9"))
            buttonselected.isEnabled = false
        } else {
            buttonselected.text = "O"
            buttonselected.setBackgroundColor(Color.parseColor("#FF5757"))
            buttonselected.isEnabled = false
        }
    }
    private fun enableActivity(isEnabled: Boolean) {
        if (!isEnabled) {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
            )
        } else {
            window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
        }
    }
    private fun updateCurrentPlayer() {
        firebaseReference.child("Current_Player").addValueEventListener(object :
            ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                current_player = snapshot.value.toString()
                if(current_player==my_username){
                    enableActivity(true)
                }
                if (current_player == player1) {
                    p1.setTextColor(Color.parseColor("#FF5757"))
                    p2.setTextColor(Color.parseColor("#77000000"))


                }
                if (current_player == player2) {
                    p2.setTextColor(Color.parseColor("#FF5757"))

                    p1.setTextColor(Color.parseColor("#77000000"))

                }
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })
    }

    private fun startGame() {

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


    }

    private fun selectedButton(view: Button) {
        val selectedBtn = view as Button
        var btnNum = 0
        when (selectedBtn.id) {
            R.id.online1 -> btnNum = 1
            R.id.online2 -> btnNum = 2
            R.id.online3 -> btnNum = 3
            R.id.online4 -> btnNum = 4
            R.id.online5 -> btnNum = 5
            R.id.online6 -> btnNum = 6
            R.id.online7 -> btnNum = 7
            R.id.online8 -> btnNum = 8
            R.id.online9 -> btnNum = 9
        }
        enableActivity(false)
        game(btnNum, selectedBtn)

    }

    private fun game(btnNum: Int, selectedBtn: Button) {
        if (current_player == player1) {

            //player1Steps.add(btnNum)
            updateBtn(selectedBtn,1)
//            selectedBtn.text = "O"
//            selectedBtn.setBackgroundColor(Color.parseColor("#FF5757"))
//            selectedBtn.isEnabled = false
            addStepsToDatabase(current_player, btnNum)

        } else if (current_player == player2) {
            updateBtn(selectedBtn,0)
            addStepsToDatabase(current_player, btnNum)
        }

    }

    private fun addStepsToDatabase(currentPlayer: String, btnNum: Int) {

        var stepsReference = firebaseReference.child("Steps")
        if (currentPlayer == player1) {
            stepsReference.child("Player1Steps").push().setValue(btnNum)
            firebaseReference.child("Current_Player").setValue(player2)
        } else if (currentPlayer == player2) {
            stepsReference.child("Player2Steps").push().setValue(btnNum)
            firebaseReference.child("Current_Player").setValue(player1)
        }

    }

    //--------------------------------------------------------------------------------//
    private fun waitingForPlayer() {
        val mProgressDialog = ProgressDialog(this)
        mProgressDialog.setTitle("Waiting For Player 2 : Room id - $room_id")
        mProgressDialog.show()
        firebaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.child("Players").childrenCount.toInt() == 2) {
                    player1 = snapshot.child("Players").child("Player1").value.toString()
                    player2 = snapshot.child("Players").child("Player2").value.toString()

                    p1.text = player1
                    p2.text = player2



                    mProgressDialog.dismiss()
                    //Toast.makeText(this@GameActivity,"Start the game",Toast.LENGTH_LONG).show()
                }
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })
    }
}