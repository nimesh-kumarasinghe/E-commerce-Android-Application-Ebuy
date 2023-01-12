package com.example.ebuy

import android.content.Context.MODE_PRIVATE
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteDatabase.openOrCreateDatabase
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide


class OrderAdapter(private val orderList: ArrayList<Order>) :
    RecyclerView.Adapter<OrderAdapter.MyViewHolder>() {

    private lateinit var mListener: onItemClickListner

    interface onItemClickListner {
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(listener: onItemClickListner) {
        mListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.order_list, parent, false)
        return OrderAdapter.MyViewHolder(itemView, mListener)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = orderList[position]
        //holder.productImage.setImageResource(currentItem.productImage)
        holder.productName.text = currentItem.product_name
        holder.productPrice.text = currentItem.order_amount
        holder.orderQty.text = currentItem.order_qty
        holder.orderDate.text = currentItem.order_date
        holder.status.text = currentItem.status

        Glide.with(holder.productImage)
            .load(currentItem.product_img)
            .into(holder.productImage);
    }

    override fun getItemCount(): Int {
        return orderList.size
    }

    class MyViewHolder(itemView: View, listener: OrderAdapter.onItemClickListner) :
        RecyclerView.ViewHolder(itemView) {
        val productImage: ImageView = itemView.findViewById(R.id.img_order_item)
        val productName: TextView = itemView.findViewById(R.id.txt_order_pname)
        val productPrice: TextView = itemView.findViewById(R.id.txt_order_pprie)
        val orderQty: TextView = itemView.findViewById(R.id.txt_order_pqty)
        val orderDate: TextView = itemView.findViewById(R.id.txt_order_date)
        val status: TextView = itemView.findViewById(R.id.txt_status)

        init {
            itemView.setOnClickListener {
                listener.onItemClick(adapterPosition)
            }
        }

    }
}