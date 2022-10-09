package com.example.contactslist

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.todo.Data.ContactsRepository

class ContactsViewModel : ViewModel() {

    private val contactsRepository = ContactsRepository.get()
    val contactsListLiveData = contactsRepository.getContacts()

    fun getContacts(): LiveData<List<ContactItem>> {
        return contactsRepository.getContacts()
    }

    fun addContact(contact: ContactItem) {
        contactsRepository.addTodo(contact)
    }

}