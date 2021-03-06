package com.ti2.nguli

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import com.ti2.nguli.data.SettingModel
import com.ti2.nguli.databinding.ActivitySettingPreferenceBinding
import com.ti2.nguli.preferences.SettingPreference

class SettingPreferenceActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var mSettingPreference: SettingPreference
    private lateinit var settingModel: SettingModel
    private lateinit var binding: ActivitySettingPreferenceBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingPreferenceBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnSave.setOnClickListener(this)
        mSettingPreference = SettingPreference(this)
        settingModel = mSettingPreference.getSetting()
        showPreferenceInForm()
        supportActionBar?.title = "Pengaturan Profil"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

    }

    override fun onClick(p0: View?) {
        if (p0?.id == R.id.btn_save) {
            val name = binding.edtName.text.toString().trim()
            val email = binding.edtEmail.text.toString().trim()
            val age = binding.edtAge.text.toString().trim()
            val phoneNo = binding.edtPhone.text.toString().trim()

            val isGender = binding.rgGender.checkedRadioButtonId == R.id.rb_genderyes
            if (name.isEmpty()) {
                binding.edtName.error = getString(R.string.field_required)
                return
            }
            if (email.isEmpty()) {
                binding.edtEmail.error = getString(R.string.field_required)
                return
            }
            if (!isValidEmail(email)) {
                binding.edtEmail.error = getString(R.string.email_is_not_valid)
                return
            }
            if (age.isEmpty()) {
                binding.edtAge.error = getString(R.string.field_required)
                return
            }
            if (phoneNo.isEmpty()) {
                binding.edtPhone.error = getString(R.string.field_required)
                return
            }

            if (!TextUtils.isDigitsOnly(phoneNo)) {
                binding.edtPhone.error = getString(R.string.field_digit_only)
                return
            }
            saveSetting(name, email, age, phoneNo, isGender)
            val resultIntent = Intent()
            setResult(RESULT_CODE, resultIntent)
            finish()
        }

    }



    companion object {
        const val RESULT_CODE = 101
    }

    private fun isValidEmail(email: CharSequence): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    private fun showPreferenceInForm() {
        binding.edtName.setText(settingModel.name)
        binding.edtEmail.setText(settingModel.email)
        binding.edtAge.setText(settingModel.age)
        binding.edtPhone.setText(settingModel.phoneNumber)
        if (settingModel.isLaki) {
            binding.rbGenderyes.isChecked = true
        } else {
            binding.rbGenderno.isChecked = true
        }

    }

    private fun saveSetting(
        name: String,
        email: String,
        age: String,
        phoneNo: String,
        isGender: Boolean,

    ) {
        val settingPreference = SettingPreference(this)
        settingModel.name = name
        settingModel.email = email
        settingModel.age = age
        settingModel.phoneNumber = phoneNo
        settingModel.isLaki = isGender

        settingPreference.setSetting(settingModel)
        Toast.makeText(this, "Data tersimpan", Toast.LENGTH_SHORT).show()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }





}