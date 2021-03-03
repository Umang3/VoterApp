package com.example.voterapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import java.lang.Exception

class VoteCandidate : AppCompatActivity() {
    lateinit var radioGroup : RadioGroup
    lateinit var candidate1 : RadioButton
    lateinit var candidate2 : RadioButton
    lateinit var candidate3 : RadioButton
    lateinit var candidate4 : RadioButton
    lateinit var btnRegLogin : Button
    lateinit var toolbar3 : Toolbar
    var gender = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vote_candidate)
        radioGroup = findViewById(R.id.radioGroup)
        candidate1 = findViewById(R.id.candidate1)
        candidate2 = findViewById(R.id.candidate2)
        candidate3 = findViewById(R.id.candidate3)
        candidate4 = findViewById(R.id.candidate4)
        btnRegLogin = findViewById(R.id.btnRegLogin)
        toolbar3 = findViewById(R.id.toolbar3)
        setupToolbar()

        radioGroup.setOnCheckedChangeListener { group, checkedId ->
            if (checkedId == R.id.candidate1) {
                gender = candidate1.text.toString()
            }
            if (checkedId == R.id.candidate2) {
                gender = candidate2.text.toString()
            }
            if (checkedId == R.id.candidate3) {
                gender = candidate3.text.toString()
            }
            if (checkedId == R.id.candidate4) {
                gender = candidate4.text.toString()
            }
        }

        btnRegLogin.setOnClickListener {
            try{
                if(gender == candidate1.text.toString() ||
                    gender == candidate2.text.toString() ||
                    gender == candidate3.text.toString() ||
                    gender == candidate4.text.toString())
                {
                    val ref = FirebaseDatabase.getInstance().getReference("datas")
                    val datas = DataClass(gender)
                    val id = ref.push().key
                    if (id != null) {
                        ref.child(id).setValue(datas).addOnCompleteListener {
                            Toast.makeText(this,"done", Toast.LENGTH_SHORT).show()
                        }
                    }
                    Firebase.auth.signOut()
                    startActivity(Intent(this,MainActivity::class.java))
                    finish()
                }
                else{
                    Toast.makeText(this,"Select one Candidate",Toast.LENGTH_SHORT).show()
                }
            }
            catch(e : Exception){
                e.printStackTrace()
            }
        }

    }

    private fun setupToolbar() {
        toolbar3 = findViewById(R.id.toolbar3)
        setSupportActionBar(toolbar3)
        supportActionBar?.title = "Vote Candidate"
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == android.R.id.home) {
            finish()
            Toast.makeText(this@VoteCandidate,"You have to Vote one candidate",Toast.LENGTH_SHORT).show()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        finish()
        Toast.makeText(this@VoteCandidate,"You have to move forward",Toast.LENGTH_SHORT).show()
    }
}