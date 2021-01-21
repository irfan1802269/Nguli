package com.ti2.nguli

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.ti2.nguli.databinding.ActivityMainBinding
import com.ti2.nguli.ui.account.AccountFragment
import com.ti2.nguli.ui.cart.CartFragment
import com.ti2.nguli.ui.history.HistoryFragment
import com.ti2.nguli.ui.home.HomeFragment

class MainActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivityMainBinding
    private lateinit var googleSignInClient: GoogleSignInClient


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = Firebase.auth

        val currentUser = auth.currentUser
        if (currentUser == null) {
            val intent = Intent(this@MainActivity, SignInActivity::class.java)
            startActivity(intent)
            finish()
        }
        binding.btnSignOut.setOnClickListener(this)
        binding.btnEmailVerify.setOnClickListener(this)


        val navView: BottomNavigationView = findViewById(R.id.nav_view)
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

        if (savedInstanceState == null) {
            val fragment = HomeFragment()
            supportFragmentManager.beginTransaction()
                .replace(R.id.nav_host_fragment, fragment, fragment.javaClass.getSimpleName())
                .commit()
        }


    }

    public override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        if (currentUser == null) {
            val intent = Intent(this@MainActivity, SignInActivity::class.java)
            startActivity(intent)
            finish()
        }
        else{
            updateUI(currentUser)
        }
    }


    private val mOnNavigationItemSelectedListener =
        BottomNavigationView.OnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.navigation_home -> {
                    val fragment = HomeFragment()
                    supportFragmentManager.beginTransaction().replace(
                        R.id.nav_host_fragment,
                        fragment,
                        fragment.javaClass.getSimpleName()
                    )
                        .commit()
                    return@OnNavigationItemSelectedListener true
                }
                R.id.navigation_cart -> {
                    val fragment = CartFragment()
                    supportFragmentManager.beginTransaction().replace(
                        R.id.nav_host_fragment,
                        fragment,
                        fragment.javaClass.getSimpleName()
                    )
                        .commit()
                    return@OnNavigationItemSelectedListener true
                }
                R.id.navigation_history -> {
                    val fragment = HistoryFragment()
                    supportFragmentManager.beginTransaction().replace(
                        R.id.nav_host_fragment,
                        fragment,
                        fragment.javaClass.getSimpleName()
                    )
                        .commit()
                    return@OnNavigationItemSelectedListener true
                }
                R.id.navigation_account -> {
                    val fragment = AccountFragment()
                    supportFragmentManager.beginTransaction().replace(
                        R.id.nav_host_fragment,
                        fragment,
                        fragment.javaClass.getSimpleName()
                    )
                        .commit()
                    return@OnNavigationItemSelectedListener true
                }
            }
            false
        }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.btnSignOut -> {
                signOut()
            }
            R.id.btnEmailVerify -> {
                sendEmailVerification()
            }
            R.id.btnHomePage -> {
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
            val intent = Intent(this@MainActivity, SignInActivity::class.java)
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
}



