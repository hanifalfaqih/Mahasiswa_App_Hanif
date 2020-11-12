package com.allana.mahasiswa_app_hanif.View

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.allana.mahasiswa_app_hanif.Model.action.ResponseAction
import com.allana.mahasiswa_app_hanif.Model.getData.DataItem
import com.allana.mahasiswa_app_hanif.R
import com.allana.mahasiswa_app_hanif.ViewModel.ViewModelInput
import kotlinx.android.synthetic.main.activity_input.*

class InputActivity : AppCompatActivity() {

    private lateinit var viewModelInput: ViewModelInput

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_input)

        viewModelInput = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(ViewModelInput::class.java)
        attachObserve()

        val getData = intent.getParcelableExtra<DataItem>("data")
        if (getData != null){
            edt_input_name.setText(getData.mahasiswaNama)
            edt_input_address.setText(getData.mahasiswaAlamat)
            edt_input_phone_number.setText(getData.mahasiswaNohp)

            btn_save.text = getString(R.string.update)
        }

        if (btn_save.text == "Update"){
            btn_save.setOnClickListener {
                getData?.idMahasiswa?.let { it1 ->
                    updateData(
                        it1, edt_input_name.text.toString(),
                        edt_input_phone_number.text.toString(),
                        edt_input_address.text.toString()) }
            }
        } else {
            btn_save.setOnClickListener {
                val edtName = edt_input_name.text.toString()
                val edtPhoneNumber = edt_input_phone_number.text.toString().trim()
                val edtAddress = edt_input_address.text.toString()

                var isEmptyField = false
                when{
                    edtName.isEmpty() -> {
                        isEmptyField = true
                        edt_input_name.error = "Nama harus diisi!"
                    }
                    edtAddress.isEmpty() -> {
                        isEmptyField = true
                        edt_input_address.error = "Alamat harus diisi!"
                    }
                    edtPhoneNumber.isEmpty() -> {
                        isEmptyField = true
                        edt_input_phone_number.error = "Nomor handphone harus diisi!"
                    }
                }

                if (!isEmptyField){
                    inputData(edtName, edtPhoneNumber, edtAddress)
                }
            }
        }

        btn_cancel.setOnClickListener {
            finish()
        }

    }

    private fun attachObserve() {
        viewModelInput.isError.observe(this, { showError(it) })
        viewModelInput.responseInsertData.observe(this, { showSuccessInsert(it) })
        viewModelInput.responseUpdateData.observe(this, { showSuccessUpdate(it) })
    }

    private fun showError(it: Throwable) {
        Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
    }

    private fun showSuccessInsert(it: ResponseAction) {
        if (it.isSuccess == true) {
            Toast.makeText(this, "Data berhasil ditambahkan!", Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    private fun showSuccessUpdate(it: ResponseAction) {
        if (it.isSuccess == true) {
            Toast.makeText(this, "Data berhasil diperbarui!", Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    private fun inputData(mahasiswa_nama: String, mahasiswa_nohp: String, mahasiswa_alamat: String){
        viewModelInput.insertData(mahasiswa_nama, mahasiswa_nohp, mahasiswa_alamat)
    }

    private fun updateData(id_mahasiswa: String, mahasiswa_nama: String, mahasiswa_nohp: String, mahasiswa_alamat: String){
        viewModelInput.updateData(id_mahasiswa, mahasiswa_nama, mahasiswa_nohp, mahasiswa_alamat)
    }
}