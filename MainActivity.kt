package com.example.studentregster

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.studentregster.databinding.ActivityMainBinding
import com.example.studentregster.db.Student
import com.example.studentregster.db.StudentDatabase
import com.zexample.studentregster.StudentRecyclerViewAdapter
import java.util.zip.Inflater

class MainActivity : AppCompatActivity() {
    // using binding
    private lateinit var binding: ActivityMainBinding
    //private lateinit var binding
 // a boolean value to decide task of the buttons
    private  var isListItemClicked = false
// Hold selected student object
    private lateinit var selectedStudent: Student
    private lateinit var studentRecyclerView: RecyclerView
    private lateinit var adapter: StudentRecyclerViewAdapter
    private lateinit var viewModel: StudentViewModel
   /* private lateinit var nameEditText: EditText
    private lateinit var emailEditText: EditText
    private lateinit var saveButton: Button
    private lateinit var clearButton: Button*/

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       // setContentView(R.layout.activity_main)

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        /*nameEditText = findViewById(R.id.etName)
        emailEditText = findViewById(R.id.etEmail)
        saveButton = findViewById(R.id.btnSave)
        clearButton = findViewById(R.id.btnClear)
        studentRecyclerView = findViewById(R.id.rcStudent)
        */

        val dao = StudentDatabase.getInstance(application).studentDoa()
        val factory = StudentViewModelFactory(dao)
        viewModel = ViewModelProvider(this, factory).get(StudentViewModel::class.java)

        binding.apply {
            btnSave.setOnClickListener {
                if (isListItemClicked) {
                    updateStudentData()
                    clearInput()
                } else {
                    saveStudentData()
                    clearInput()
                }
            }
        }

        binding.apply {
            btnClear.setOnClickListener {
                if (isListItemClicked) {
                    deleteStudentData()
                    clearInput()
                } else {
                    clearInput()
                }
            }
        }

        initRecyclerView()

    }

    private fun saveStudentData() {
        binding.apply {
            viewModel.insertStudent(
                Student(
                    0,
                    etName.text.toString(),
                    etEmail.text.toString()
                )
            )
        }
    }
    private fun updateStudentData(){
        binding.apply {
            viewModel.updateStudent(
                Student(
                    selectedStudent.id,
                    etName.text.toString(),
                    etEmail.text.toString()
                )
            )
            binding.btnSave.text = "Save"
            binding.btnClear.text = "Clear"
            isListItemClicked = false
        }
    }

    private fun deleteStudentData(){
        binding.apply {
        viewModel.deleteStudent(
            Student(
                selectedStudent.id,
                etName.text.toString(),
                etEmail.text.toString()
            )
        )
        binding.btnSave.text = "Save"
        binding.btnClear.text = "Clear"
        isListItemClicked = false

        }
    }


    private fun clearInput(){
        binding.apply {
            etName.setText("")
            etEmail.setText("")
        }
    }

    private fun initRecyclerView(){
        binding.rcStudent.layoutManager = LinearLayoutManager(this)
        // initialize the adapter as the adapter of the recycler view
        // receive student item
        adapter = StudentRecyclerViewAdapter{
            selectedItem:Student -> listItemClicked(selectedItem)
        }
        binding.rcStudent.adapter = adapter

        displayStudentList()
    }

    private fun displayStudentList(){
        viewModel.students.observe(this) {
            adapter.setList(it)
            adapter.notifyDataSetChanged()
        }
    }

    private fun listItemClicked(student:Student) {
        /* Toast.makeText(this,
        "Student name is ${student.name}",
        Toast.LENGTH_LONG
        ).show()*/
        binding.apply {
        selectedStudent = student

            btnSave.text = "Update"
            btnClear.text = "Delete"
            isListItemClicked = true
            etName.setText(selectedStudent.name)
            etEmail.setText(selectedStudent.email)
        }
    }
}