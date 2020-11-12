package com.allana.mahasiswa_app_hanif.ViewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.allana.mahasiswa_app_hanif.Model.action.ResponseAction
import com.allana.mahasiswa_app_hanif.Model.getData.ResponseGetData
import com.allana.mahasiswa_app_hanif.Repository.Repository

class ViewModelInput: ViewModel() {

    private val repository = Repository()

    var isError = MutableLiveData<Throwable>()
    var responseInsertData = MutableLiveData<ResponseAction>()
    var responseUpdateData = MutableLiveData<ResponseAction>()

    fun insertData(namaMahasiswa: String, noHpMahasiswa: String, alamatMahasiswa: String) {
        repository.getInsertData(namaMahasiswa, noHpMahasiswa, alamatMahasiswa, {
            responseInsertData.value = it
        }, {
            isError.value = it
        })
    }

    fun updateData(idMahasiswa: String, namaMahasiswa: String, noHpMahasiswa: String, alamatMahasiswa: String, ) {
        repository.getUpdateData(idMahasiswa, namaMahasiswa, noHpMahasiswa, alamatMahasiswa, {
            responseUpdateData.value = it
        }, {
            isError.value = it
        })
    }

    fun getDisposableClear() {
        repository.onDetach()
    }

}