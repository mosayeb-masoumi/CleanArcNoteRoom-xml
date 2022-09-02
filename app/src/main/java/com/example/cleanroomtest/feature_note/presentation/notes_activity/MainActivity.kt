package com.example.cleanroomtest.feature_note.presentation.notes_activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cleanroomtest.R
import com.example.cleanroomtest.databinding.ActivityMainBinding
import com.example.cleanroomtest.feature_note.controller.NoteAdapter
import com.example.cleanroomtest.feature_note.controller.NoteItemInteraction
import com.example.cleanroomtest.feature_note.domain.model.Note
import com.example.cleanroomtest.feature_note.presentation.edit_note_activity.AddEditActivity
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), NoteItemInteraction {

    private lateinit var binding: ActivityMainBinding

    @Inject
    lateinit var adapter: NoteAdapter

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.fabAdd.setOnClickListener {
            val intent = Intent(this@MainActivity, AddEditActivity::class.java)
            intent.putExtra("id", -1)
            startActivity(intent)
        }


    }

    override fun onResume() {
        super.onResume()
        geNotesFromDb()
    }

    private fun geNotesFromDb() {

        viewModel.getNoteList()

        val getNoteListLiveData = viewModel.getNotesLivedata
        getNoteListLiveData.observe(this) {

            if (it != null) {
                setRecycleView(it)
            }
        }

    }


    private fun setRecycleView(list: List<Note>) {

        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.recycleview.layoutManager = layoutManager
        adapter.setListener(this)
        adapter.submitList(list)
        binding.recycleview.adapter = adapter
    }


    override fun noteItemOnclick(note: Note) {

        val gson = Gson()
        val json = gson.toJson(note)

        val intent = Intent(this@MainActivity, AddEditActivity::class.java)
        intent.putExtra("model", json)
        intent.putExtra("id", note.id)
        startActivity(intent)
    }

    override fun deleteItemOnclick(note: Note) {
        deleteItem(note)
    }

    private fun deleteItem(note: Note) {

        viewModel.deleteNoteItem(note)
        val deleteNoteListLiveData = viewModel.deleteNotesLivedata
        deleteNoteListLiveData.observe(this) {

            if (it != null) {

                when {
                    it.isLoading -> {
                        Log.i("loading", "loading")
                    }

                    it.error != "" -> {
                        Log.e("loading", "an error occured")
                    }

                    it.data != null -> {
                        Toast.makeText(this@MainActivity, "delete susccessfully", Toast.LENGTH_LONG)
                            .show()
                        geNotesFromDb()
                    }
                }
            }
        }
    }
}