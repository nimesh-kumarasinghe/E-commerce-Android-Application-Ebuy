package com.example.ebuy

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.imageview.ShapeableImageView

class ProductAdapter(private val productList: ArrayList<Product>) :
    RecyclerView.Adapter<ProductAdapter.MyViewHolder>() {

    private lateinit var mListener: onItemClickListner

    interface onItemClickListner {
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(listener: onItemClickListner) {
        mListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.product_list, parent, false)
        return ProductAdapter.MyViewHolder(itemView, mListener)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        //Log.e("Got","working")
        val currentItem = productList[position]
        //holder.productImage.setImageResource(currentItem.productImage)
        holder.productName.text = currentItem.pro_name
        holder.productPrice.text = currentItem.pro_price
        holder.productQty.text = currentItem.pro_qty
        //holder.productDesc.text = currentItem.pro_desc

        Glide.with(holder.productImage)
            .load(currentItem.pro_img)
            .into(holder.productImage);
    }

    override fun getItemCount(): Int {
        return productList.size
    }


    class MyViewHolder(itemView: View, listener: ProductAdapter.onItemClickListner) :
        RecyclerView.ViewHolder(itemView) {
        val productImage: ImageView = itemView.findViewById(R.id.img_item)
        val productName: TextView = itemView.findViewById(R.id.txt_pname)
        val productPrice: TextView = itemView.findViewById(R.id.txt_pprie)
        val productQty: TextView = itemView.findViewById(R.id.txt_pqty)
        //val productDesc : TextView = itemView.findViewById(R.id.txt_pdesc)

        init {
            itemView.setOnClickListener {
                listener.onItemClick(adapterPosition)
            }
        }

    }
}