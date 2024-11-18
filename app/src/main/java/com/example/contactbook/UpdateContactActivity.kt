package com.example.contactbook

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.contactbook.databinding.ActivityUpdateContactBinding

class UpdateContactActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUpdateContactBinding
    private lateinit var db: ContactDatabaseHelper
    private var contactId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateContactBinding.inflate(layoutInflater)
        setContentView(binding.root) // Use binding.root to set the content view

        db = ContactDatabaseHelper(this)

        contactId = intent.getIntExtra("contact_Id", -1)
        if (contactId == -1) {
            finish()
            Toast.makeText(this, "Contact not found", Toast.LENGTH_SHORT).show()
            return
        }

        val contact = db.getContactById(contactId)
        if (contact == null) {
            finish()
            Toast.makeText(this, "Contact did not found", Toast.LENGTH_SHORT).show()
            return
        }

        binding.updateFirstName.setText(contact.firstName)
        binding.updateLastName.setText(contact.lastName)
        binding.updateNumber.setText(contact.phoneNumber)
        binding.updateEmail.setText(contact.email)
        binding.updateContent.setText(contact.content)

        binding.updateSaveButton.setOnClickListener {
            val newFirstName = binding.updateFirstName.text.toString()
            val newLastName = binding.updateLastName.text.toString()
            val newPhoneNumber = binding.updateNumber.text.toString()
            val newEmail = binding.updateEmail.text.toString()
            val newContent = binding.updateContent.text.toString()

            val updatedContact = Contact(contactId, newFirstName, newLastName, newPhoneNumber, newEmail, newContent)

            db.updateContact(updatedContact)
            Toast.makeText(this, "Contact updated successfully", Toast.LENGTH_SHORT).show()
            finish()
        }
    }
}
