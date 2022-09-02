package com.example.cleanroomtest.feature_note.presentation.edit_note_activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.cleanroomtest.R
import com.example.cleanroomtest.databinding.ActivityAddEditBinding
import dagger.hilt.android.AndroidEntryPoint
import androidx.activity.viewModels
import com.example.cleanroomtest.feature_note.domain.model.Note
import com.example.cleanroomtest.feature_note.presentation.notes_activity.MainActivity


import com.google.gson.Gson
import com.google.gson.reflect.TypeToken




@AndroidEntryPoint
class AddEditActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddEditBinding
    private val viewModel: AddEditViewModel by viewModels()
    lateinit var  updateNote: Note

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAddEditBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val id = intent.getIntExtra("id", -1)
//        id == -1  means new Item

        if(id != -1){
            //update
            val json = intent.getStringExtra("model")
            val type = object : TypeToken<Note?>() {}.type
            updateNote = Gson().fromJson<Note?>(json, type)
            binding.edtTitle.setText(updateNote.title)
            binding.edtDesc.setText(updateNote.content)
            binding.edtAge.setText(updateNote.age.toString())

        }




        binding.fabAdd.setOnClickListener {

            val title = binding.edtTitle.text?.trim().toString()
            val content = binding.edtDesc.text?.trim().toString()
            val age = binding.edtAge.text?.trim().toString()

            if (title.isNotBlank() && content.isNotBlank() && age.isNotBlank()) {


                if(id == -1){
                    // new item
                    val note = Note(title,content,"time",age.toInt())
                    addEditItemToDB(note)
                }else{

                    updateNote.title = title
                    updateNote.content = content
                    updateNote.age = age.toInt()

                    addEditItemToDB(updateNote)
                }

            }else{
                Toast.makeText(this@AddEditActivity , "please fill required fields" , Toast.LENGTH_SHORT).show()
            }

            //  add to database
        }
    }

    private fun addEditItemToDB(note: Note) {

        viewModel.addEditNoteItem(note)

        // wait for observing
        val saveItemLivedata = viewModel.saveItemLivedata
        saveItemLivedata.observe(this) {

            if (it != null) {
                when {

                    it.isLoading -> {
                        Log.i("loading", "loading")
                    }

                    it.error != "" -> {
                        Toast.makeText(this@AddEditActivity, "Error Occurred!!!", Toast.LENGTH_LONG)
                            .show()
                    }

                    it.data != null -> {
                        Toast.makeText(this@AddEditActivity, "saved susccessfully", Toast.LENGTH_LONG
                        ).show()

                        startActivity(Intent(this@AddEditActivity , MainActivity::class.java))
                    }


                }
            }

        }

    }


    private fun getItemFromDatabase(id: Int) {


    }
}