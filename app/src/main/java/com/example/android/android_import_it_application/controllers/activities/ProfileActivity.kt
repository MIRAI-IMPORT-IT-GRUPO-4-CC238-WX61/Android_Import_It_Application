package com.example.android.android_import_it_application.controllers.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import com.example.android.android_import_it_application.R
import com.example.android.android_import_it_application.controllers.activities.SignIn.SelectRole
import com.example.android.android_import_it_application.models.ProductList
import com.example.android.android_import_it_application.models.User
import com.squareup.picasso.OkHttp3Downloader
import com.squareup.picasso.Picasso

class ProfileActivity : AppCompatActivity() {

    lateinit var tvNameProfile: TextView
    lateinit var tvAgeProfile: TextView
    lateinit var tvDNIProfile: TextView
    lateinit var tvDayProfile: TextView
    lateinit var tvMonthProfile: TextView
    lateinit var tvYearProfile: TextView
    lateinit var tvCellProfile: TextView
    lateinit var tvDirectionProfile: TextView
    lateinit var btnCerrarSesion:Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        val dni = intent.getStringExtra("DNI")
        val user = intent.getSerializableExtra("User")
        val role = intent.getStringExtra("role")

        tvNameProfile = findViewById(R.id.tvNameProfileEdit)
        tvAgeProfile = findViewById(R.id.tvAgeProfileEdit)
        tvDNIProfile = findViewById(R.id.tvDNIProfileEdit)
        tvDayProfile = findViewById(R.id.tvDayProfile)
        tvMonthProfile = findViewById(R.id.tvMonthProfile)
        tvYearProfile = findViewById(R.id.tvYearProfile)
        tvCellProfile = findViewById(R.id.tvCellProfileEdit)
        tvDirectionProfile = findViewById(R.id.tvDirectionProfileEdit)
        btnCerrarSesion = findViewById(R.id.btnCerrarSesion)

        val ibArrow = findViewById<ImageButton>(R.id.ibArrowProfile)
        val ibEditProfile = findViewById<Button>(R.id.btEditProfile)

        ibArrow.setOnClickListener {
            if(role == "buyer"){
                val intent: Intent = Intent(this, BuyerActivity::class.java)
                intent.putExtra("DNI", dni)
                intent.putExtra("User", user)
                intent.putExtra("role", role)
                startActivity(intent)
            } else{
                val intent: Intent = Intent(this, TravelerActivity::class.java)
                intent.putExtra("DNI", dni)
                intent.putExtra("User", user)
                intent.putExtra("role", role)
                startActivity(intent)
            }
        }

        ibEditProfile.setOnClickListener {
            val intent: Intent = Intent(this, EditProfileActivity::class.java)
            intent.putExtra("DNI", dni)
            intent.putExtra("User", user)
            intent.putExtra("role", role)
            startActivity(intent)
        }

        btnCerrarSesion.setOnClickListener {
            val intent: Intent = Intent(this, SelectRole::class.java)
            startActivity(intent)
        }

        initFields()
    }

    private fun initFields() {
        val userObject: User? = intent.getSerializableExtra("User") as? User

        tvNameProfile.text = userObject?.name_user
        tvAgeProfile.text = userObject?.age
        tvDNIProfile.text = userObject?.dni
        tvDayProfile.text = userObject?.day
        tvMonthProfile.text = userObject?.month
        tvYearProfile.text = userObject?.year
        tvCellProfile.text = userObject?.phone
        tvDirectionProfile.text = userObject?.address


    }
}