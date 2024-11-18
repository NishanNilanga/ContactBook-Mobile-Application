package com.example.contactbook

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.contactbook.databinding.ActivityAddContactBinding
import com.example.contactbook.databinding.ActivityMainBinding

class AddContactActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddContactBinding
    private lateinit var db : ContactDatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddContactBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = ContactDatabaseHelper(this)

        binding.saveButton.setOnClickListener{
            val firstName = binding.FirstNameEditText.text.toString()
            val lastName = binding.LastNameEditText.text.toString()
            val phoneNumber = binding.NumberText.text.toString()
            val email = binding.emailTxt.text.toString()
            val content = binding.ContentEditText.text.toString()
            val contact = Contact(0, firstName, lastName, phoneNumber, email, content)

            db.insertContact(contact)
            finish()
            Toast.makeText(this,"Cantact Saved",Toast.LENGTH_SHORT).show()
        }
    }
}