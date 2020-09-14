package com.allana.mahasiswa_app_hanif

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.allana.mahasiswa_app_hanif.Adapter.DataAdapter
import com.allana.mahasiswa_app_hanif.Config.NetworkModule
import com.allana.mahasiswa_app_hanif.Model.action.ResponseAction
import com.allana.mahasiswa_app_hanif.Model.getData.DataItem
import com.allana.mahasiswa_app_hanif.Model.getData.ResponseGetData
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fab.setOnClickListener {
            val intent = Intent(this, InputActivity::class.java)
            startActivity(intent)
        }

        getDataMahasiswa()

    }

    override fun onResume() {
        super.onResume()
        getDataMahasiswa()
    }

    private fun getDataMahasiswa() {
        val listMahasiswa = NetworkModule.getService().getData()
        listMahasiswa.enqueue(object: Callback<ResponseGetData>{
            override fun onResponse(
                call: Call<ResponseGetData>,
                response: Response<ResponseGetData>
            ) {
                if (response.isSuccessful){
                    val dataMahasiswa = response.body()?.data
                    showDataMahasiswa(dataMahasiswa)
                }
            }

            override fun onFailure(call: Call<ResponseGetData>, t: Throwable) {
                Toast.makeText(applicationContext, t.message, Toast.LENGTH_LONG).show()
            }

        })
    }

    private fun showDataMahasiswa(data: List<DataItem>?) {
        val dataAdapter = DataAdapter(data)
        rv_list_item.layoutManager = LinearLayoutManager(this)
        rv_list_item.adapter = dataAdapter

        dataAdapter.setOnItemClickCallback(object: DataAdapter.OnItemClickCallback{
            override fun onItemClicked(data: DataItem?) {
                val intent = Intent(applicationContext, InputActivity::class.java)
                intent.putExtra("data", data)
                startActivity(intent)

            }

            override fun onItemDeleted(data: DataItem?) {
                AlertDialog.Builder(this@MainActivity).apply {
                    setMessage("Apakah yakin ingin menghapus data ${data?.mahasiswaNama}?")
                    setPositiveButton("HAPUS"){ dialogInterface: DialogInterface, i: Int ->
                        deleteData(data?.idMahasiswa)
                        dialogInterface.dismiss()
                    }
                    setNegativeButton("BATAL"){ dialogInterface: DialogInterface, i: Int ->
                        dialogInterface.dismiss()
                    }
                }.show()

            }

        })

    }

    private fun deleteData(id_mahasiswa: String?) {
        val deleteDataMahasiswa = NetworkModule.getService().deleteData(id_mahasiswa ?: "")
        deleteDataMahasiswa.enqueue(object: Callback<ResponseAction>{
            override fun onResponse(
                call: Call<ResponseAction>,
                response: Response<ResponseAction>
            ) {
                Toast.makeText(applicationContext, "Data berhasil dihapus", Toast.LENGTH_SHORT).show()
                getDataMahasiswa()
            }

            override fun onFailure(call: Call<ResponseAction>, t: Throwable) {
                Toast.makeText(applicationContext, t.message, Toast.LENGTH_LONG).show()
            }

        })
    }

}