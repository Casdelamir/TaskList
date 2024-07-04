package com.example.tasklist.utils

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.tasklist.data.Task
import com.example.tasklist.databinding.TaskRecicleViewLayoutBinding

class TaskRecyclerViewAdapter(private var taskList: List<Task>, private val onClickListener: (Task) -> Unit, private val onClickCheckBoxListener: (Task) -> Unit, private val onDeleteClickListener: (Task) -> Unit): RecyclerView.Adapter<TaskRecyclerViewAdapter.TaskViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val binding = TaskRecicleViewLayoutBinding.inflate(LayoutInflater.from(parent.context))
        return TaskViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return taskList.size
    }

    fun updateData(dataSet: List<Task>) {
        this.taskList = dataSet
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        // Here we pass the lambda function onClickListener to the render function and execute it there
        holder.render(taskList[position], onClickListener, onClickCheckBoxListener, onDeleteClickListener)
    }

    class TaskViewHolder(val binder: TaskRecicleViewLayoutBinding): RecyclerView.ViewHolder(binder.root) {
        fun render(
            task: Task,
            onClickListener: (Task) -> Unit,
            onClickCheckBoxListener: (Task) -> Unit,
            onDeleteClickListener: (Task) -> Unit
        ) {
            binder.taskName.text = task.name
            binder.checkbox.isChecked = task.done
            binder.checkbox.setOnCheckedChangeListener { checkbox, _ ->
                if (checkbox.isPressed) {
                    onClickCheckBoxListener(task)
                }
            }
            binder.taskName.setOnClickListener {
                onClickListener(task)
            }
            binder.deleteButton.setOnClickListener {
                onDeleteClickListener(task)
            }
        }
    }
}
