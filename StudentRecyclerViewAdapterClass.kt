package com.zexample.studentregster

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ExpandableListView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.studentregster.R
import com.example.studentregster.databinding.ListItemBinding
import com.example.studentregster.db.Student
// private lateinit var binding: ListItemBinding
class StudentRecyclerViewAdapter(
    private var clickListener: (Student) -> Unit
): RecyclerView.Adapter<StudentViewHolder>() {

    private var studentList = ArrayList<Student>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentViewHolder {
        val binding = ListItemBinding.inflate(LayoutInflater.from(parent.context),parent, false)
        return  StudentViewHolder(binding)
    }

    override fun onBindViewHolder(holder: StudentViewHolder, position: Int) {
        holder.bind(studentList[position], clickListener)
    }

    override fun getItemCount(): Int {
        return studentList.size
    }

    fun setList(students:List<Student>){
        studentList.clear()
        studentList.addAll(students)
    }
}


class StudentViewHolder(private val binding: ListItemBinding):RecyclerView.ViewHolder(binding.root){
    fun bind(student: Student, clickListener: (Student)->Unit) {
        binding.apply {
            binding.apply {
                tvName.text = student.name
               tvEmail.text = student.email
                // implement clickListener
                root.setOnClickListener {
                    clickListener(student)
                }
            }
        }
    }
}