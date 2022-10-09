package com.example.contactslist

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(primaryKeys = ["name", "phone"])
data class ContactItem(
    var name: String = "None",
    var phone: String = "None"
)
