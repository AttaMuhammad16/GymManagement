package com.example.gymmanagement.adapters

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.gymmanagement.R
import com.example.gymmanagement.models.MemberModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MembersAdapter(var context: Context, var memberslist: List<MemberModel>) : RecyclerView.Adapter<MembersAdapter.MyViewHolder>() {



    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var member_name: TextView = itemView.findViewById(R.id.a_name)
        var member_phone: TextView = itemView.findViewById(R.id.a_phone)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.member_view, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return memberslist.size
    }

    @SuppressLint("RestrictedApi")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
       var list= memberslist[position]


        holder.member_name.text = list.name
        holder.member_phone.append(list.phone)
        holder.itemView.setOnClickListener {

//            val intent = Intent(context, StudentsActivity::class.java)
//            intent.putExtra("classModel" , list[position])
//            ContextUtils.getActivity(context)?.startActivity(intent)
        }
        holder.itemView.setOnLongClickListener {
            val items = arrayOf<CharSequence>("Update Member", "Delete Member")
            val alert = AlertDialog.Builder(it.context)
            alert.setItems(items) { _, which ->

                if (which == 1) {
                    val builder = AlertDialog.Builder(context)
                    builder.setMessage("Do you Want to delete this member")
                    builder.setPositiveButton("Yes") { dialog, which ->
                        dialog.cancel()
                    }
                    builder.setNegativeButton("No") { dialog, which ->
                        dialog.cancel()
                    }
                    builder.show()
                }

            else if (which == 0) {
//
//            val alertdialog = AlertDialog.Builder(context).setView(R.layout.manage_class_dialog).show()
//            val cancelBtn = alertdialog.findViewById<Button>(R.id.cancelBtn)
//            val saveBtn = alertdialog.findViewById<Button>(R.id.saveBtn)
//            val updateClassName = alertdialog.findViewById<EditText>(R.id.updateClassNameEt)
//            updateClassName.setText(list[position].className)
//            val updateFeeAmount = alertdialog.findViewById<EditText>(R.id.updateClassFeeEt)
//            updateFeeAmount.setText(list[position].fee)
//
//            saveBtn.setOnClickListener {
//                val newName = updateClassName.text.toString()
//                val newFee = updateFeeAmount.text.toString()
//                CoroutineScope(Dispatchers.IO).launch {
//                    if (classesViewModel.updateClassToServer(ClassModel(newFee, newName))) {
//                        context.showToast("class updated successfully")
//                    } else {
//                        context.showToast("class can't updated")
//                    }
//                }
//                alertdialog.dismiss()
//            }
//            cancelBtn.setOnClickListener {
//                alertdialog.dismiss()
//            }

        }

        }

                alert.setNegativeButton("Cancel") { dialogInterface, i -> dialogInterface.cancel() }


            alert.create().show()
            true
        }
    }
}