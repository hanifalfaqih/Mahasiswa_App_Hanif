package com.allana.mahasiswa_app_hanif.Repository

import com.allana.mahasiswa_app_hanif.Config.NetworkModule
import com.allana.mahasiswa_app_hanif.Model.action.ResponseAction
import com.allana.mahasiswa_app_hanif.Model.getData.ResponseGetData
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.schedulers.Schedulers

class Repository {

    private val disposable: CompositeDisposable = CompositeDisposable()

    fun getListData(responseListData: (ResponseGetData) -> Unit, error: (Throwable) -> Unit) {
        NetworkModule.getService().getData()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                responseListData(it)
            }, {
                error(it)
            }).addTo(disposable)
    }

    fun getDeleteData(id: String, responseAction: (ResponseAction) -> Unit, error: (Throwable) -> Unit) {
        NetworkModule.getService().deleteData(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                responseAction(it)
            }, {
                error(it)
            })
    }

    fun getInsertData(nama: String, nohp: String, alamat: String, responseAction: (ResponseAction) -> Unit, error: (Throwable) -> Unit) {
        NetworkModule.getService().insertData(nama, nohp, alamat)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                responseAction(it)
            }, {
                error(it)
            }).addTo(disposable)
    }

    fun getUpdateData(id: String, nama: String, noHp: String, alamat: String, responseAction: (ResponseAction) -> Unit, error: (Throwable) -> Unit) {
        NetworkModule.getService().updateData(id, nama, noHp, alamat)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                responseAction(it)
            }, {
                error(it)
            }).addTo(disposable)
    }

    fun onDetach() {
        disposable.clear()
    }
}