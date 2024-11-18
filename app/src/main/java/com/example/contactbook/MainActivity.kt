package com.example.contactbook

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.contactbook.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var db : ContactDatabaseHelper
    private lateinit var contactAdapter : ContactAdapter

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = ContactDatabaseHelper(this)
        contactAdapter = ContactAdapter(db.getAllContacts(),this)

        binding.contactRecyclerview.layoutManager = LinearLayoutManager(this)
        binding.contactRecyclerview.adapter = contactAdapter

        binding.addButton.setOnClickListener{
            val intent = Intent(this,AddContactActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        // Update the dataset of the adapter with the latest contacts from the database
        val updatedContacts = db.getAllContacts()
        contactAdapter.refreshData(updatedContacts)
    }

}