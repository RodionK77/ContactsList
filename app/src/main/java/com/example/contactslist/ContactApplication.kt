package com.example.contactslist

import android.app.Application
import com.example.todo.Data.ContactsRepository

class ContactApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        ContactsRepository.initialize(this)
    }
}