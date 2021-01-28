package com.ti2.nguli

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.ti2.nguli.databinding.ActivityMainBinding
import com.ti2.nguli.databinding.ActivityVerifyAccountBinding
import kotlinx.android.synthetic.main.activity_verify_account.*

class VerifyAccountActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivityVerifyAccountBinding
    private lateinit var googleSignInClient: GoogleSignInClient
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVerifyAccountBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = Firebase.auth
        binding.btnSignOut.setOnClickListener(this)
        binding.btnEmailVerify.setOnClickListener(this)
        //binding..setOnClickListener(this)


        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)
        val currentUser = auth.currentUser
        if (currentUser == null) {
            val intent = Intent(this@VerifyAccountActivity, SignInActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
    public override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        if (currentUser == null) {
            val intent = Intent(this@VerifyAccountActivity, SignInActivity::class.java)
            startActivity(intent)
            finish()
        }
        else{
            updateUI(currentUser)
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btnSignOut -> {
                signOut()
            }
            R.id.btnEmailVerify -> {
                sendEmailVerification()
            }
        }
    }

    private fun sendEmailVerification() {
        binding.btnEmailVerify.isEnabled = false
        val user = auth.currentUser!!
        user.sendEmailVerification()
            .addOnCompleteListener(this) { task ->
                binding.btnEmailVerify.isEnabled = true
                if (task.isSuccessful) {
                    Toast.makeText(baseContext,
                        "Verification email sent to ${user.email} ",
                        Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(baseContext,
                        "Failed to send verification email.",
                        Toast.LENGTH_SHORT).show()
                }
            }
    }
    private fun signOut() {
        auth.signOut()
        val currentUser = auth.currentUser
        if (currentUser == null) {
            val intent = Intent(this@VerifyAccountActivity, SignInActivity::class.java)
            startActivity(intent)
            finish()
        }
        googleSignInClient.signOut().addOnCompleteListener(this) {
        }
    }

    private fun updateUI(currentUser: FirebaseUser) {
        currentUser?.let {
            val name = currentUser.displayName
            val phoneNumber = currentUser.phoneNumber
            val email = currentUser.email
            val photoUrl = currentUser.photoUrl
            val emailVerified = currentUser.isEmailVerified
            val uid = currentUser.uid
            Glide.with(this)
                .load(photoUrl.toString())
                .into(imageView);
            binding.tvName.text = name
            if(TextUtils.isEmpty(name)){
                binding.tvName.text = "No Name"
            }
            binding.tvUserId.text = email
            for (profile in it.providerData) {
                val providerId = profile.providerId
                if(providerId=="password" && emailVerified==true){
                    binding.btnEmailVerify.isVisible = false
                }
                if(providerId=="phone"){
                    binding.tvName.text = phoneNumber
                    binding.tvUserId.text = providerId
                }
            }
        }
    }
    companion object {
        private const val REQUEST_CODE = 100
    }

    fun openQuotes(item: MenuItem){
        val intent = Intent(this@VerifyAccountActivity, MainActivity::class.java)
        startActivityForResult(intent, REQUEST_CODE)
    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_action_bar, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onBackPressed() {
        val alertDialogBuilder = AlertDialog.Builder(this)
        alertDialogBuilder.setIcon(R.mipmap.ic_launcher)
        alertDialogBuilder.setTitle(R.string.app_name)
            .setMessage("Kamu yakin ingin keluar?")
            .setPositiveButton("OK") { _, _ -> finish() }
            .setNegativeButton("Cancel") { dialog, _ -> dialog.cancel() }
        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }

}