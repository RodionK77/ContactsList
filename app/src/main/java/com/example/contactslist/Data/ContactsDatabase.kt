package com.example.todo.Data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.contactslist.ContactItem
import com.example.contactslist.Data.ContactsDAO

@Database(entities = [ContactItem::class], version = 1, exportSchema = false)
abstract class ContactsDatabase : RoomDatabase() {

    abstract fun contactsDao(): ContactsDAO
}