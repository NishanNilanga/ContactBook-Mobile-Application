package com.example.contactbook

data class Contact(
    val id: Int,
    val firstName: String,
    val lastName: String,
    val phoneNumber: String,
    val email: String,
    val content: String,
)
