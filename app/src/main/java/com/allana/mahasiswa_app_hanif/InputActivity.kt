package com.allana.mahasiswa_app_hanif

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.allana.mahasiswa_app_hanif.Config.NetworkModule
import com.allana.mahasiswa_app_hanif.Model.action.ResponseAction
import com.allana.mahasiswa_app_hanif.Model.getData.DataItem
import kotlinx.android.synthetic.main.activity_input.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class InputActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_input)

        val getData = intent.getParcelableExtra<DataItem>("data")
        if (getData != null){
            edt_input_name.setText(getData.mahasiswaNama)
            edt_input_address.setText(getData.mahasiswaAlamat)
            edt_input_phone_number.setText(getData.mahasiswaNohp)

            btn_save.text = "Update"
        }

        if (btn_save.text == "Update"){
            btn_save.setOnClickListener {
                updateData(getData?.idMahasiswa, edt_input_name.text.toString(), edt_input_phone_number.text.toString(), edt_input_address.text.toString())
            }
        } else {
            btn_save.setOnClickListener {
                inputData(edt_input_name.text.toString(), edt_input_phone_number.text.toString(), edt_input_address.text.toString())
            }
        }

        btn_cancel.setOnClickListener {
            finish()
        }
    }

    private fun inputData(mahasiswa_nama: String?, mahasiswa_nohp: String?, mahasiswa_alamat: String?){
        val inputMahasiswa = NetworkModule.getService().insertData(mahasiswa_nama ?: "", mahasiswa_nohp ?: "", mahasiswa_alamat ?: "")
        inputMahasiswa.enqueue(object: Callback<ResponseAction>{
            override fun onResponse(
                call: Call<ResponseAction>,
                response: Response<ResponseAction>
            ) {
                Toast.makeText(applicationContext, "Data berhasil ditambahkan", Toast.LENGTH_SHORT).show()
                finish()
            }

            override fun onFailure(call: Call<ResponseAction>, t: Throwable) {
                Toast.makeText(applicationContext, t.message, Toast.LENGTH_LONG).show()
            }

        })
    }

    private fun updateData(id_mahasiswa: String?, mahasiswa_nama: String?, mahasiswa_nohp: String?, mahasiswa_alamat: String?){
        val inputMahasiswa = NetworkModule.getService().updateData(id_mahasiswa ?: "", mahasiswa_nama ?: "", mahasiswa_nohp ?: "", mahasiswa_alamat ?: "")
        inputMahasiswa.enqueue(object: Callback<ResponseAction>{
            override fun onResponse(
                call: Call<ResponseAction>,
                response: Response<ResponseAction>
            ) {
                Toast.makeText(applicationContext, "Data berhasil diupdate", Toast.LENGTH_SHORT).show()
                finish()
            }

            override fun onFailure(call: Call<ResponseAction>, t: Throwable) {
                Toast.makeText(applicationContext, t.message, Toast.LENGTH_LONG).show()
            }

        })
    }
}