package com.example.tic_tac_toe

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity() {
    lateinit var offline: Button
    lateinit var onlineBtn: Button
    lateinit var single: Button
    lateinit var dual: Button
    lateinit var create: Button
    lateinit var join: Button

    lateinit var onn_off: LinearLayout
    lateinit var single_dual: LinearLayout
    lateinit var room: LinearLayout

    var count: Int = 0
    var on: Boolean = false


    val firebaseDatabase: FirebaseDatabase = FirebaseDatabase.getInstance()
    val firebaseReference = firebaseDatabase.reference.child("Rooms")

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        offline = findViewById(R.id.offline)
        onlineBtn = findViewById(R.id.online)
        onn_off = findViewById(R.id.off_on)
        single_dual = findViewById(R.id.single_dual)
        room = findViewById(R.id.room)


        single = findViewById(R.id.single)
        dual = findViewById(R.id.duo)
        create = findViewById(R.id.create)
        join = findViewById(R.id.join)


        create.setOnClickListener{
            if(!isOnline(applicationContext)){
                Toast.makeText(this, "Please connect to internet", Toast.LENGTH_SHORT).show()

            }else
            createDialogue()

        }

        join.setOnClickListener{
            if(!isOnline(applicationContext)){
                Toast.makeText(this, "Please connect to internet", Toast.LENGTH_SHORT).show()

            }else
           joinDialogue()
        }


        offline.setOnClickListener {

            onn_off.visibility = View.INVISIBLE
            single_dual.visibility = View.VISIBLE


            count = 0
        }
        onlineBtn.setOnClickListener {
            onn_off.visibility = View.INVISIBLE
            room.visibility = View.VISIBLE
            on = true
            count = 0
//            val intent = Intent(this,online::class.java)
//            startActivity(intent)
        }
        single.setOnClickListener {
            val intent = Intent(this, singlePlayer::class.java)
            startActivity(intent)
        }
        dual.setOnClickListener {
            val intent = Intent(this, MainActivity2::class.java)
            startActivity(intent)
        }
    }

    override fun onBackPressed() {
        count += 1
        if (count == 2) {
            super.onBackPressed()
        }
        if (on) {
            room.visibility = View.INVISIBLE
        } else single_dual.visibility = View.INVISIBLE

        onn_off.visibility = View.VISIBLE


    }

     fun create_room(username: String) {

        if (username == "") {
            Toast.makeText(this, "Enter Username", Toast.LENGTH_LONG).show()
        } else {

            val room_id: Int = (1000..9999).random()
            //var player = Player1(username)
            firebaseReference.child(room_id.toString()).child("Players").child("Player1")
                .setValue(username)
            firebaseReference.child(room_id.toString()).child("Current_Player").setValue(username)
            firebaseReference.child(room_id.toString()).child("Restart").setValue(0)
            firebaseReference.child(room_id.toString()).child("Player1Wins").setValue(0)
            firebaseReference.child(room_id.toString()).child("Player2Wins").setValue(0)

            val intent = Intent(applicationContext,online::class.java )
            intent.putExtra("my_username", username)
            intent.putExtra("room_id", room_id.toString())
            Toast.makeText(this, "Room Created", Toast.LENGTH_SHORT).show()
            startActivity(intent)
        }
    }

     fun join_room(username: String,room_id: String) {


        if (username == "") {
            Toast.makeText(this, "Enter Username", Toast.LENGTH_LONG).show()
        } else if (room_id == "") {
            Toast.makeText(this, "Enter room id", Toast.LENGTH_LONG).show()
        } else {
            firebaseReference.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    var foundRoom = false
                    for (eachRoom in snapshot.children) {
                        if (eachRoom.key == room_id) {
                            foundRoom = true
                            if (eachRoom.child("Players").childrenCount.toInt() == 2) {
                                Toast.makeText(this@MainActivity, "Room is full", Toast.LENGTH_LONG)
                                    .show()
                            } else {
                                //var player = Player2(username)
                                firebaseReference.child(room_id.toString()).child("Players")
                                    .child("Player2").setValue(username)

                                val intent =
                                    Intent(this@MainActivity, online::class.java)
                                intent.putExtra("my_username", username)
                                intent.putExtra("room_id", room_id.toString())
                                startActivity(intent)
                            }
                        }
                    }
                    if (foundRoom == false) {
                        Toast.makeText(this@MainActivity, "Room not found", Toast.LENGTH_LONG)
                            .show()
                    }

                }

                override fun onCancelled(error: DatabaseError) {

                }

            })


        }
    }

    fun joinDialogue() {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.join_dialogue, null)

        val editTextName = dialogView.findViewById<EditText>(R.id.editTextNameJoin)
        val editTextId = dialogView.findViewById<EditText>(R.id.editTextIdJoin)
        val startBtn = dialogView.findViewById<Button>(R.id.joinStartBtn)
        val cancelBtn = dialogView.findViewById<Button>(R.id.joinCancelBtn)


        val dialogBuilder = AlertDialog.Builder(this)
            .setView(dialogView)

        val dialog = dialogBuilder.create()
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.show()


        startBtn.setOnClickListener{
            val name = editTextName.text.toString()
            val id = editTextId.text.toString()
            if(!isOnline(applicationContext)){
                Toast.makeText(this, "Please connect to internet", Toast.LENGTH_SHORT).show()

            }else
            join_room(name,id)
            dialog.cancel()


        }
        cancelBtn.setOnClickListener{
            dialog.cancel()
        }





    }

    fun createDialogue() {

        val dialogView = LayoutInflater.from(this).inflate(R.layout.create_dialogue, null)


        val editTextName = dialogView.findViewById<EditText>(R.id.editTextName2)
        val startBtn = dialogView.findViewById<Button>(R.id.createStartBtn)
        val cancelBtn = dialogView.findViewById<Button>(R.id.createCancelBtn)


        val dialogBuilder = AlertDialog.Builder(this)
            .setView(dialogView)

        val dialog = dialogBuilder.create()
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.show()


        startBtn.setOnClickListener{
            val name = editTextName.text.toString()
            if(!isOnline(applicationContext)){
                Toast.makeText(this, "Please connect to internet", Toast.LENGTH_SHORT).show()

            }
            else
            create_room(name)
            dialog.cancel()
        }
        cancelBtn.setOnClickListener{
            dialog.cancel()
        }





    }
    fun isOnline(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (connectivityManager != null) {
            val capabilities =
                connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
            if (capabilities != null) {
                if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_CELLULAR")
                    return true
                } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_WIFI")
                    return true
                } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)) {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_ETHERNET")
                    return true
                }
            }
        }
        return false
    }


}
