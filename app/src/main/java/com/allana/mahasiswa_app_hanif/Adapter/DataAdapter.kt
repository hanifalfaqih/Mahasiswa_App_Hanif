package com.allana.mahasiswa_app_hanif.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.allana.mahasiswa_app_hanif.Model.getData.DataItem
import com.allana.mahasiswa_app_hanif.R
import kotlinx.android.synthetic.main.list_item.view.*

class DataAdapter(val data: List<DataItem>?): RecyclerView.Adapter<DataAdapter.DataAdapterViewHolder>() {

    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback){
        this.onItemClickCallback = onItemClickCallback
    }

    inner class DataAdapterViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val name = itemView.tv_data_name
        val numberPhone = itemView.tv_data_phone_number
        val address = itemView.tv_data_address
        val btnDelete = itemView.btn_delete
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataAdapterViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        return DataAdapterViewHolder(view)
    }

    override fun onBindViewHolder(holder: DataAdapterViewHolder, position: Int) {
        val itemData = data?.get(position)
        holder.name.text = itemData?.mahasiswaNama
        holder.numberPhone.text = itemData?.mahasiswaNohp
        holder.address.text = itemData?.mahasiswaAlamat

        holder.itemView.setOnClickListener {
            onItemClickCallback.onItemClicked(data?.get(holder.adapterPosition)) }

        holder.btnDelete.setOnClickListener {
            onItemClickCallback.onItemDeleted(data?.get(holder.adapterPosition))
        }
    }

    override fun getItemCount(): Int = data?.size ?: 0

    interface OnItemClickCallback{
        fun onItemClicked(data: DataItem?)

        fun onItemDeleted(data: DataItem?)
    }

}