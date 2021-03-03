package com.example.voterapp

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase
import java.lang.Exception
import java.util.*
import kotlin.collections.ArrayList

class FinalActivity : AppCompatActivity() {

    var count1 = 0
    var count2 = 0
    var count3 = 0
    var count4 = 0
    lateinit var txtCandi1 : TextView
    lateinit var txtCandi2 : TextView
    lateinit var txtCandi3 : TextView
    lateinit var txtCandi4 : TextView
    lateinit var btnBackToLogin : Button
    lateinit var toolbar4 : Toolbar
    var arrayList = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_final)
        txtCandi1 = findViewById(R.id.txtCandi1)
        txtCandi2 = findViewById(R.id.txtCandi2)
        txtCandi3 = findViewById(R.id.txtCandi3)
        txtCandi4 = findViewById(R.id.txtCandi4)
        btnBackToLogin = findViewById(R.id.btnBackToLogin)
        setupToolbar()

        try{
            val ref = FirebaseDatabase.getInstance().getReference().child("datas")
            ref.addValueEventListener(object : ValueEventListener {
                @SuppressLint("SetTextI18n")
                override fun onDataChange(snapshot: DataSnapshot) {
                    for (data in snapshot.children) {
                        val data1 = data.value
                        arrayList.add(data1.toString())
                    }

                    for (data in 0 until arrayList.size) {
                        if (arrayList[data].equals("{gen=Sunil Chavda}")) {
                            count1 += 1
                        }
                        if (arrayList[data].equals("{gen=Aakash Shah}")) {
                            count2 += 1
                        }
                        if (arrayList[data].equals("{gen=Anjali Kumari}")) {
                            count3 += 1
                        }
                        if (arrayList[data].equals("{gen=Rahul Verma}")) {
                            count4 += 1
                        }
                    }
                    if(count1 <=1){
                        txtCandi1.setText("Sunil Chavda has $count1 Vote")
                    }else{
                        txtCandi1.setText("Sunil Chavda has $count1 Votes")
                    }
                    if(count2 <=1){
                        txtCandi2.setText("Aakash Shah has $count2 Vote")
                    }else{
                        txtCandi2.setText("Aakash Shah has $count2 Votes")
                    }
                    if(count3 <=1){
                        txtCandi3.setText("Anjali Kumari has $count3 Vote")
                    }else{
                        txtCandi3.setText("Anjali Kumari has $count3 Votes")
                    }
                    if(count4 <=1){
                        txtCandi4.setText("Rahul Verma has $count4 Vote")
                    }else{
                        txtCandi4.setText("Rahul Verma has $count4 Votes")
                    }

                }

                override fun onCancelled(error: DatabaseError) {
                    Log.e("cancel", error.toString())
                }
            })
        }
        catch(e : Exception){
        e.printStackTrace()
        }

        btnBackToLogin.setOnClickListener {
            count1 = 0
            count2 = 0
            count3 = 0
            count4 = 0
            arrayList.clear()
            Firebase.auth.signOut()
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }
    private fun setupToolbar() {
        toolbar4 = findViewById(R.id.toolbar4)
        setSupportActionBar(toolbar4)
        supportActionBar?.title = "Hello Admin !!"
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == android.R.id.home) {
            finish()
            startActivity(Intent(this@FinalActivity,MainActivity::class.java))
            Firebase.auth.signOut()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        finish()
        startActivity(Intent(this@FinalActivity,MainActivity::class.java))
        Firebase.auth.signOut()
    }

}