package com.example.android.android_import_it_application.controllers.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import com.example.android.android_import_it_application.R
import com.example.android.android_import_it_application.controllers.fragments.CustomerOrderFragment

class CustomerOrderActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_customer_order)

        val dni = intent.getStringExtra("DNI")
        val user = intent.getSerializableExtra("User")
        val role = intent.getStringExtra("role")

        val transaction = supportFragmentManager.beginTransaction()
        val ibBackCusOrder = findViewById<ImageButton>(R.id.ibBackDashboard)

        transaction.add(R.id.flFragmentCustomerOrder, CustomerOrderFragment()).commitAllowingStateLoss()

        ibBackCusOrder.setOnClickListener {
            val intent: Intent = Intent(this, TravelerActivity::class.java)
            intent.putExtra("DNI", dni)
            intent.putExtra("User", user)
            intent.putExtra("role", role)
            startActivity(intent)
        }
    }
}