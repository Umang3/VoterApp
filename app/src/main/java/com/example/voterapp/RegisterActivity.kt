package com.example.voterapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import java.lang.Exception

class RegisterActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    lateinit var txtRegUSerName : EditText
    lateinit var txtRegUSerPass : EditText
    lateinit var txtRegEmailId : EditText
    lateinit var txtRegPhoneNo : EditText
    lateinit var btnRegRegister : Button
    lateinit var toolbar2 : Toolbar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        auth = Firebase.auth
        txtRegUSerName = findViewById(R.id.txtRegUSerName)
        txtRegUSerPass = findViewById(R.id.txtRegUSerPass)
        txtRegEmailId = findViewById(R.id.txtRegEmailId)
        txtRegPhoneNo = findViewById(R.id.txtRegPhoneNo)
        btnRegRegister = findViewById(R.id.btnRegRegister)
        toolbar2 = findViewById(R.id.toolbar2)
        setupToolbar()
        btnRegRegister.setOnClickListener {
            val email1 = txtRegEmailId.text.toString()
            val pass1 = txtRegUSerPass.text.toString()
            validateData(email1,pass1)
        }
    }

    private fun validateData(email : String , pass : String) {
        try{
            if(txtRegUSerName.text.toString() != ""){
                if(Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    if(pass.isNotEmpty()){
                        if(txtRegPhoneNo.text.toString() != "" && txtRegPhoneNo.text.toString().length == 10){

                            auth!!.createUserWithEmailAndPassword(email, pass)
                                .addOnCompleteListener(this) { task ->
                                    if (task.isSuccessful) {
                                        Toast.makeText(this, "stored in firebase", Toast.LENGTH_SHORT).show()

                                        startActivity(Intent(this,MainActivity::class.java))
                                        finish()
                                    } else {
                                        Toast.makeText(baseContext, "SignUp failed try again after some time.",
                                            Toast.LENGTH_SHORT).show()
                                    }
                                }

                        }else{
                            txtRegPhoneNo.error = "Please enter valid Number"
                            txtRegPhoneNo.requestFocus()
                        }
                    }else{
                        txtRegUSerPass.error = "Please enter valid Password"
                        txtRegUSerPass.requestFocus()
                    }
                }else{
                    txtRegEmailId.error = "Please enter Email"
                    txtRegEmailId.requestFocus()
                }
            }else{
                txtRegUSerName.error = "Please enter User Name"
                txtRegUSerName.requestFocus()
            }
        }
        catch(e: Exception){
            e.printStackTrace()
        }
    }

    private fun setupToolbar() {
        toolbar2 = findViewById(R.id.toolbar2)
        setSupportActionBar(toolbar2)
        supportActionBar?.title = "Register page"
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == android.R.id.home) {
            finish()
            startActivity(Intent(this@RegisterActivity,MainActivity::class.java))
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        finish()
        startActivity(Intent(this@RegisterActivity,MainActivity::class.java))
    }

}