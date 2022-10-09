package com.example.contactslist

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.PersistableBundle
import android.provider.ContactsContract
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ContactsActivity : AppCompatActivity() {

    private val adapter: ContactsAdapter = ContactsAdapter()
    private var contacts: List<ContactItem>? = null
    private lateinit var recyclerView: RecyclerView

    private val contactsViewModel by lazy {
        ViewModelProvider(this)[ContactsViewModel::class.java]
    }

    @SuppressLint("Range")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contacts)

        Toast.makeText(applicationContext, "Start", Toast.LENGTH_SHORT).show()
        recyclerView = findViewById(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(applicationContext)
        recyclerView.adapter = adapter
        adapter.notifyDataSetChanged()

        viewModelObserver()

        val phones = contentResolver.query(
            ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null,
            null, null )
        while (phones!!.moveToNext()) {
            val name = phones!!.getString(phones!!.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME))
            val phoneNumber = phones!!.getString(phones!!.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
            val contact = ContactItem()
            contact.name = name
            contact.phone = phoneNumber
            contactsViewModel.addContact(contact)
        }
        phones!!.close()

    }

    fun viewModelObserver(){
        Toast.makeText(applicationContext, "Start", Toast.LENGTH_SHORT).show()
        contactsViewModel.contactsListLiveData.observe(
            this,
            Observer { contacts ->
                contacts.let {
                    if (contacts.isEmpty()) {
                        Toast.makeText(applicationContext, "Empty", Toast.LENGTH_SHORT).show()
                    } else {
                        recyclerView.visibility = View.VISIBLE
                        this.contacts = contacts
                    }
                    adapter.items = contacts
                    adapter.notifyDataSetChanged()
                }
            }
        )
    }

    private inner class ContactsHolder(view: View) : RecyclerView.ViewHolder(view) {

        private lateinit var item: ContactItem
        private val contactName: TextView = itemView.findViewById(R.id.name)
        private val contactPhone: TextView = itemView.findViewById(R.id.number)


        fun bind(item: ContactItem) {
            this.item = item
            contactName.text = item.name
            contactPhone.text = item.phone
        }

    }

    private inner class ContactsAdapter : RecyclerView.Adapter<ContactsHolder>() {
        var items = listOf<ContactItem>()

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactsHolder {
            val view = layoutInflater.inflate(R.layout.contact_item, parent, false)
            return ContactsHolder(view)
        }

        override fun onBindViewHolder(holder: ContactsHolder, position: Int) {
            val item = items[position]
            holder.bind(item)
        }

        override fun getItemCount() = items.size

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        override fun getItemViewType(position: Int): Int {
            return position
        }

    }
}