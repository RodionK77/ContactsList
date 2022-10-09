package com.example.contactslist.Data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.contactslist.ContactItem

@Dao
interface ContactsDAO {
    @Query("SELECT * FROM contactItem")
    fun getContacts(): LiveData<List<ContactItem>>

    @Update
    fun updateContact(cont: ContactItem)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addContact(todoItem: ContactItem)

    @Delete
    fun deleteContact(todoItem: ContactItem)
}