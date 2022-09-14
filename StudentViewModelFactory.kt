package com.example.studentregster

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.studentregster.db.StudentDao
import java.lang.IllegalArgumentException

class StudentViewModelFactory(
    private var doa: StudentDao
):ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(StudentViewModel::class.java)){
            return StudentViewModel(doa) as T
    }
        throw IllegalArgumentException ("Unknown View Model Class")
    }
}