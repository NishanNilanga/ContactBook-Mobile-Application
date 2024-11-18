package com.example.contactbook

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.database.Cursor

class ContactDatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "contactApp.db"
        private const val DATABASE_VERSION = 1
        private const val TABLE_NAME = "allContact"
        private const val COLUMN_ID = "id"
        private const val COLUMN_FIRSTNAME = "fName"
        private const val COLUMN_LASTNAME = "lName"
        private const val COLUMN_NUMBER = "phoneNumber"
        private const val COLUMN_EMAIL = "email"
        private const val COLUMN_CONTENT = "content"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createTableQuery = "CREATE TABLE $TABLE_NAME (" +
                "$COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "$COLUMN_FIRSTNAME TEXT, " +
                "$COLUMN_LASTNAME TEXT, " +
                "$COLUMN_NUMBER TEXT, " +
                "$COLUMN_EMAIL TEXT, " +
                "$COLUMN_CONTENT TEXT)"

        db.execSQL(createTableQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        val dropTableQuery = "DROP TABLE IF EXISTS $TABLE_NAME"
        db.execSQL(dropTableQuery)
        onCreate(db)
    }

    fun insertContact(contact: Contact) {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_FIRSTNAME, contact.firstName)
            put(COLUMN_LASTNAME, contact.lastName)
            put(COLUMN_NUMBER, contact.phoneNumber)
            put(COLUMN_EMAIL, contact.email)
            put(COLUMN_CONTENT, contact.content)
        }

        db.insert(TABLE_NAME, null, values)
        db.close()
    }

    fun getAllContacts(): List<Contact> {
        val contactList = mutableListOf<Contact>()
        val db = readableDatabase
        val query = "SELECT * FROM $TABLE_NAME"
        val cursor = db.rawQuery(query, null)

            while (cursor.moveToNext()) {
                val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID))
                val firstName = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_FIRSTNAME))
                val lastName = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_LASTNAME))
                val phoneNumber = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NUMBER))
                val email = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_EMAIL))
                val content = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CONTENT))

                val contact = Contact(id, firstName, lastName, phoneNumber, email, content)
                contactList.add(contact)
            }

        cursor.close()
        db.close()

        return contactList
    }

    fun updateContact(contact: Contact) {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_FIRSTNAME, contact.firstName)
            put(COLUMN_LASTNAME, contact.lastName)
            put(COLUMN_NUMBER, contact.phoneNumber)
            put(COLUMN_EMAIL, contact.email)
            put(COLUMN_CONTENT, contact.content)
        }

        // Define the WHERE clause to update the specific contact by ID
        val whereClause = "$COLUMN_ID = ?"
        val whereArgs = arrayOf(contact.id.toString())

        // Perform the update operation
        db.update(TABLE_NAME, values, whereClause, whereArgs)
        db.close()
    }


    fun getContactById(contactId: Int): Contact {
        val db = readableDatabase
        val query = "SELECT * FROM $TABLE_NAME WHERE $COLUMN_ID = $contactId"
        val cursor = db.rawQuery(query, null)
        cursor.moveToFirst()

            // Retrieve data from cursor
            val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID))
            val firstName = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_FIRSTNAME))
            val lastName = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_LASTNAME))
            val phoneNumber = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NUMBER))
            val email = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_EMAIL))
            val content = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CONTENT))

        cursor.close()
        db.close()

        return Contact(id, firstName, lastName, phoneNumber, email, content)
    }


    fun deleteContact(contactId: Int){
        val db = writableDatabase

        // Define the WHERE clause to delete the specific contact by ID
        val whereClause = "$COLUMN_ID = ?"
        val whereArgs = arrayOf(contactId.toString())

        // Perform the delete operation
        db.delete(TABLE_NAME, whereClause, whereArgs)
        db.close()

    }



}
