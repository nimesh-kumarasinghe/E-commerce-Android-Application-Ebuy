package com.example.ebuy

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class categryProductItemAdapter(private val categoryProductItemList: ArrayList<CategryProductItemData>) :
    RecyclerView.Adapter<categryProductItemAdapter.MyViewHolder>() {

    private lateinit var mListener: onItemClickListner

    interface onItemClickListner {
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(listener: onItemClickListner) {
        mListener = listener
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.category_product_list, parent, false)
        return categryProductItemAdapter.MyViewHolder(itemView, mListener)

    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = categoryProductItemList[position]
        holder.productImage.setImageResource(currentItem.productImage)
        holder.productName.text = currentItem.productName
        holder.productPrice.text = currentItem.productPrice
        holder.productQty.text = currentItem.productQty
        holder.productDesc.text = currentItem.productDesc

    }

    override fun getItemCount(): Int {
        return categoryProductItemList.size
    }

    class MyViewHolder(itemView: View, listener: categryProductItemAdapter.onItemClickListner) :
        RecyclerView.ViewHolder(itemView) {
        val productImage: ImageView = itemView.findViewById(R.id.img_c_p)
        val productName: TextView = itemView.findViewById(R.id.txt_c_p_name)
        val productPrice: TextView = itemView.findViewById(R.id.txt_c_p_price)
        val productQty: TextView = itemView.findViewById(R.id.txt_pqty)
        val productDesc: TextView = itemView.findViewById(R.id.txt_c_p_desc)

        init {
            itemView.setOnClickListener {
                listener.onItemClick(adapterPosition)
            }
        }

    }
}