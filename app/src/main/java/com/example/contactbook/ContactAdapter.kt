package com.example.contactbook

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import android.app.AlertDialog

class ContactAdapter(private var contacts: List<Contact>, private val context: Context) :
    RecyclerView.Adapter<ContactAdapter.ContactViewHolder>() {

        private val db:ContactDatabaseHelper = ContactDatabaseHelper(context)

    // ViewHolder for each item in the RecyclerView
    class ContactViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val firstNameTextView: TextView = itemView.findViewById(R.id.FirstNameEditText)
        val lastNameTextView: TextView = itemView.findViewById(R.id.LastNameEditText)
        val phoneNumberTextView: TextView = itemView.findViewById(R.id.NumberText)
        val emailTextView: TextView = itemView.findViewById(R.id.emailTxt)
        val contentTextView: TextView = itemView.findViewById(R.id.ContentEditText)
        val updateButton: ImageView = itemView.findViewById(R.id.updateButton)
        val deleteButton: ImageView = itemView.findViewById(R.id.deleteButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        // Inflate the item layout
        val itemView = LayoutInflater.from(context).inflate(R.layout.contact_item, parent, false)
        return ContactViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        // Return the size of the contacts list
        return contacts.size
    }

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        // Bind data to the views inside ViewHolder
        val contact = contacts[position]
        holder.firstNameTextView.text = contact.firstName
        holder.lastNameTextView.text = contact.lastName
        holder.phoneNumberTextView.text = contact.phoneNumber
        holder.emailTextView.text = contact.email
        holder.contentTextView.text = contact.content

        holder.updateButton.setOnClickListener {
            val intent = Intent(holder.itemView.context,UpdateContactActivity::class.java).apply{
                putExtra("contact_Id", contact.id)
            }
            holder.itemView.context.startActivity(intent)
        }

        holder.deleteButton.setOnClickListener {
            // Display a confirmation dialog before deleting the contact
            AlertDialog.Builder(holder.itemView.context)
                .setTitle("Confirm Delete")
                .setMessage("Are you sure you want to delete this contact?")
                .setPositiveButton("Yes") { dialog, _ ->
                    // User confirmed, delete the contact
                    db.deleteContact(contact.id)
                    refreshData(db.getAllContacts())
                    Toast.makeText(holder.itemView.context, "Contact Deleted", Toast.LENGTH_SHORT).show()
                    dialog.dismiss()
                }
                .setNegativeButton("No") { dialog, _ ->
                    // User cancelled the deletion action
                    dialog.dismiss()
                }
                .show()
        }
    }


    fun refreshData(newContacts:List<Contact>){
        contacts = newContacts
        notifyDataSetChanged()
    }


}
