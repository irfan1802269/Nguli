package com.ti2.nguli.ui.account

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.ti2.nguli.*
import com.ti2.nguli.buttonHome.CategoryActivity
import com.ti2.nguli.databinding.FragmentAccountBinding
import com.ti2.nguli.databinding.FragmentHomeBinding

class AccountFragment : Fragment(), View.OnClickListener {
    private lateinit var auth: FirebaseAuth
    private lateinit var binding: FragmentAccountBinding
    private lateinit var googleSignInClient: GoogleSignInClient

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //(requireActivity() as MainActivity).supportActionBar?.title = "Akun"

        auth = Firebase.auth
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(requireActivity(), gso)
        binding.stgPesanan.setOnClickListener(this)
        binding.stgVoucher.setOnClickListener(this)
        binding.stgPengaturanProfil.setOnClickListener(this)
        binding.stgKebijakanPrivacy.setOnClickListener(this)
        binding.stgLogout.setOnClickListener(this)
        val currentUser = auth.currentUser
        if (currentUser == null) {
            val intent = Intent(activity, SignInActivity::class.java)
            startActivity(intent)
            activity?.finish()
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAccountBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.stgPesanan -> {
                findNavController().navigate(R.id.navigation_cart)
            }
            R.id.stgVoucher -> {
                val intent = Intent(activity, CategoryActivity::class.java)
                startActivity(intent)
            }
            R.id.stgPengaturanProfil -> {
                val intent = Intent(activity, SettingPreferenceActivity::class.java)
                startActivity(intent)
            }
            R.id.stgKebijakanPrivacy -> {
                val intent = Intent(activity, KebijakanPrivacyActivity::class.java)
                startActivity(intent)
            }
            R.id.stgLogout -> {
                signOut()


            }
        }
    }

    private fun signOut() {
        auth.signOut()
        val currentUser = auth.currentUser
        if (currentUser == null) {
            val intent = Intent(activity, SignInActivity::class.java)
            startActivity(intent)
            activity?.finish()
        }
        googleSignInClient.signOut().addOnCompleteListener(requireActivity()) {
        }
    }


}