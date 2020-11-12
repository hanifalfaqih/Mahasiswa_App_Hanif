package com.allana.mahasiswa_app_hanif.ViewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.allana.mahasiswa_app_hanif.Model.action.ResponseAction
import com.allana.mahasiswa_app_hanif.Model.getData.ResponseGetData
import com.allana.mahasiswa_app_hanif.Repository.Repository

class ViewModelMain: ViewModel() {

    private val repository = Repository()

    var isError = MutableLiveData<Throwable>()
    var responseGetData = MutableLiveData<ResponseGetData>()
    var responseDeleteData = MutableLiveData<ResponseAction>()

    fun getListUser() {
        repository.getListData({
            responseGetData.value = it
        }, {
            isError.value = it
        })
    }

    fun deleteData(idMahasiswa: String) {
        repository.getDeleteData(idMahasiswa, {
            responseDeleteData.value = it
        }, {
            isError.value = it
        })
    }

    fun getDisposableClear() {
        repository.onDetach()
    }
}