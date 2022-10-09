package com.example.todo.Data

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.Room
import com.example.contactslist.ContactItem
import java.util.concurrent.Executors

class ContactsRepository private constructor(context: Context) {

    private val database: ContactsDatabase = Room.databaseBuilder(
        context.applicationContext,
        ContactsDatabase::class.java,
        "todo_database"
    ).build()

    private val contactsDAO = database.contactsDao()
    private val executor = Executors.newSingleThreadExecutor()

    fun getContacts(): LiveData<List<ContactItem>> = contactsDAO.getContacts()
    fun updateTodo(todo: ContactItem){
        executor.execute{
            contactsDAO.updateContact(todo)
        }
    }
    fun addTodo(todo: ContactItem){
        executor.execute{
            contactsDAO.addContact(todo)
        }
    }
    fun deleteTodo(todo: ContactItem){
        executor.execute{
            contactsDAO.deleteContact(todo)
        }
    }


    companion object{
        private var INSTANCE: ContactsRepository? = null

        fun initialize(context: Context){
            if(INSTANCE == null){
                INSTANCE = ContactsRepository(context)
            }
        }

        fun get(): ContactsRepository {
            return INSTANCE ?: throw IllegalStateException("ContactsRepository must be initialized")
        }
    }
}