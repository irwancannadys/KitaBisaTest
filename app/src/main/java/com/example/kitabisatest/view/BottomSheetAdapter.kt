package com.example.kitabisatest.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.kitabisatest.R
import com.example.kitabisatest.model.BottomSheetValue
import kotlinx.android.synthetic.main.item_bottom_sheet.view.*

class BottomSheetAdapter(private val listValue: MutableList<BottomSheetValue>, val listener: CategoryListener) : RecyclerView.Adapter<BottomSheetAdapter.ViewHolderItem>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderItem {
        return ViewHolderItem(LayoutInflater.from(parent.context).inflate(R.layout.item_bottom_sheet, parent, false))
    }

    override fun getItemCount(): Int = listValue.size

    override fun onBindViewHolder(holder: ViewHolderItem, position: Int) {
        holder.bind(listValue[position])
        holder.itemView.setOnClickListener {
            listener.clickCategory(listValue[position].movieType)
        }
    }

    class ViewHolderItem(view: View) : RecyclerView.ViewHolder(view) {
        private val name = view.tv_bottom_sheet
        fun bind(value: BottomSheetValue) {
            name.text = value.name
        }
    }

    interface CategoryListener {
        fun clickCategory(url: String)
    }
}