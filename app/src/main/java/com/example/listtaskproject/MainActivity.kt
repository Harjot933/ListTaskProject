package com.example.listtask

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog

import com.example.listtaskproject.R
import com.example.listtaskproject.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {
    var arrayList = arrayListOf<String>()
    lateinit var adapter: ArrayAdapter<String>
    lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, arrayList)
        binding.list.adapter = adapter

        //Floating Action Button
        binding.fab.setOnClickListener {
            val dialog = Dialog(this)
            dialog.setContentView(R.layout.custom_layout)

            val btnUpdate: Button = dialog.findViewById(R.id.btnUpdate)
            val etData: EditText = dialog.findViewById(R.id.etData)
            val btnCancel : Button = dialog.findViewById(R.id.btnCancel)

            btnUpdate.setOnClickListener {
                if (etData.text.toString().isEmpty()) {
                    etData.error = "Please fill the field!"
                } else {
                    arrayList.add(etData.text.toString())
                    Toast.makeText(this,"Data added successfully",Toast.LENGTH_SHORT).show()
                    adapter.notifyDataSetChanged()
                    dialog.dismiss()
                }
            }
            btnCancel.setOnClickListener {
                dialog.hide()
            }
            dialog.show()
        }


        binding.list.setOnItemClickListener { _, _, position, _ ->
            val alertDialog = AlertDialog.Builder(this)
            alertDialog.setTitle("What do you want to do ? ${arrayList[position]}?")
            alertDialog.setCancelable(false)

            //Update Button

            alertDialog.setPositiveButton("Update") { _, _ ->
                val dialog = Dialog(this)
                dialog.setContentView(R.layout.custom_layout)
                dialog.setCancelable(false)
                val btnUpdate: Button = dialog.findViewById(R.id.btnUpdate)
                val btnCancel: Button = dialog.findViewById(R.id.btnCancel)
                val etData: EditText = dialog.findViewById(R.id.etData)
                btnUpdate.setOnClickListener {
                    if (etData.text.toString().isEmpty()) {
                        etData.error = "Please fill the field!"
                    } else {
                        arrayList[position] = etData.text.toString()
                        Toast.makeText(this, "Data updated successfully", Toast.LENGTH_SHORT).show()
                        adapter.notifyDataSetChanged()
                        dialog.dismiss()
                    }
                }
                btnCancel.setOnClickListener {
                    dialog.hide()
                }
                dialog.show()
            }

            //Delete Button

            alertDialog.setNegativeButton("Delete") { _, _ ->
                val deletedItem = arrayList[position]
                arrayList.removeAt(position)
                adapter.notifyDataSetChanged()
            }

            //Cancel Button

            alertDialog.setNeutralButton("CANCEL") { _, _ ->
                alertDialog.setCancelable(true)
            }
            alertDialog.show()

        }
    }
}