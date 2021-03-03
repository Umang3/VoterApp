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
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import java.lang.Exception

class MainActivity : AppCompatActivity() {
    lateinit var btnLogLogin : Button
    lateinit var btnLogRegister : Button
    lateinit var txtLogUSerPass : EditText
    lateinit var txtLogUSerName : EditText
    private lateinit var auth: FirebaseAuth
    lateinit var toolbar1 : Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        auth = FirebaseAuth.getInstance()
        btnLogLogin = findViewById(R.id.btnLogLogin)
        btnLogRegister = findViewById(R.id.btnLogRegister)
        txtLogUSerName =findViewById(R.id.txtLogUSerName)
        txtLogUSerPass = findViewById(R.id.txtLogUSerPass)
        toolbar1 = findViewById(R.id.toolbar1)
        setupToolbar()
        btnLogLogin.setOnClickListener {
            val email = txtLogUSerName.text.toString()
            val pass = txtLogUSerPass.text.toString()
            if(email == "admin" && pass == "admin"){
                finish()
                moveToFinal()
            }else{
                checkSuccess(email,pass)
            }
        }
        btnLogRegister.setOnClickListener {
            startActivity(Intent(this,RegisterActivity::class.java))
            finish()
        }
    }

    private fun moveToFinal() {
        finish()
        startActivity(Intent(this,FinalActivity::class.java))
    }

    private fun checkSuccess(email : String , pass : String) {
        try{
            if(email.isNotEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                if(pass != ""){
                    auth.signInWithEmailAndPassword(email, pass)
                        .addOnCompleteListener(this) { task ->
                            if (task.isSuccessful) {
                                val user = auth.currentUser
                                updateUI(user)
                            } else {
                                Toast.makeText(baseContext, "Login failed.",
                                    Toast.LENGTH_SHORT).show()
                                updateUI(null)
                            }
                        }
                }else{
                    txtLogUSerPass.error = "Please enter Password"
                    txtLogUSerPass.requestFocus()
                }
            }else{
                txtLogUSerName.error = "Please enter valid Email"
                txtLogUSerName.requestFocus()
            }
        }
        catch(e : Exception){
            e.printStackTrace()
        }
    }

    public override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        updateUI(currentUser)
    }

    private fun updateUI(currentUser: FirebaseUser?) {
        if(currentUser != null){
            startActivity(Intent(this,VoteCandidate::class.java))
        }
    }


    private fun setupToolbar() {
        toolbar1 = findViewById(R.id.toolbar1)
        setSupportActionBar(toolbar1)
        supportActionBar?.title = "Home Page"
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == android.R.id.home) {
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        finish()
        super.onBackPressed()
    }

}
