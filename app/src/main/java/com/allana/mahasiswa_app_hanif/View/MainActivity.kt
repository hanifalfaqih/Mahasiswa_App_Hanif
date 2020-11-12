package com.allana.mahasiswa_app_hanif.View

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.allana.mahasiswa_app_hanif.Adapter.DataAdapter
import com.allana.mahasiswa_app_hanif.Model.action.ResponseAction
import com.allana.mahasiswa_app_hanif.Model.getData.DataItem
import com.allana.mahasiswa_app_hanif.R
import com.allana.mahasiswa_app_hanif.ViewModel.ViewModelMain
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var viewModelMain: ViewModelMain

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fab.setOnClickListener {
            val intent = Intent(this, InputActivity::class.java)
            startActivity(intent)
        }

        viewModelMain = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(ViewModelMain::class.java)
        attachObserve()

        viewModelMain.getListUser()
    }

    private fun attachObserve() {
        viewModelMain.isError.observe(this, { showError(it) })
        viewModelMain.responseGetData.observe(this, { it.data?.let { it1 -> showListData(it1) } })
        viewModelMain.responseDeleteData.observe(this, {showSuccess(it)})
    }

    private fun showSuccess(it: ResponseAction) {
        if (it.isSuccess == true) {
            Toast.makeText(this, "Data berhasil dihapus!", Toast.LENGTH_SHORT).show()
            viewModelMain.getListUser()
        }
    }

    private fun showError(it: Throwable) {
        Toast.makeText(applicationContext, it.message, Toast.LENGTH_SHORT).show()
    }

    private fun showListData(listData: List<DataItem>) {
        val adapter = DataAdapter(listData)
        rv_list_item.layoutManager = LinearLayoutManager(this)
        rv_list_item.adapter = adapter

        adapter.setOnItemClickCallback(object: DataAdapter.OnItemClickCallback{
            override fun onItemClicked(data: DataItem?) {
                data?.let { showItemClicked(it) }
            }

            override fun onItemDeleted(data: DataItem?) {
                data?.let { clickItemDeleted(it) }
            }

        })
    }

    private fun showItemClicked(listData: DataItem) {
        val intent = Intent(this@MainActivity, InputActivity::class.java)
        intent.putExtra("data", listData)
        startActivity(intent)
    }

    private fun clickItemDeleted(data: DataItem) {
        AlertDialog.Builder(this@MainActivity).apply {
            setMessage("Apakah Anda yakin ingin menghapus data ${data.mahasiswaNama}?")
            setPositiveButton("HAPUS"){ dialogInterface: DialogInterface, _: Int ->
                deleteDataMahasiswa(data.idMahasiswa)
                dialogInterface.dismiss()
            }
            setNegativeButton("BATAL"){ dialogInterface: DialogInterface, _: Int ->
                dialogInterface.dismiss()
            }
        }.show()
    }

    private fun deleteDataMahasiswa(idMahasiswa: String?) {
        idMahasiswa?.let { viewModelMain.deleteData(it) }
    }

    override fun onResume() {
        super.onResume()
        viewModelMain.getListUser()
    }
}