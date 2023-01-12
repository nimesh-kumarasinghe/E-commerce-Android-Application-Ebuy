package com.example.ebuy

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.imageview.ShapeableImageView

class CartAdapter(private val cartList: ArrayList<Cart>) :
    RecyclerView.Adapter<CartAdapter.MyViewHolder>() {

    private lateinit var mListener: onItemClickListner

    interface onItemClickListner {
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(listener: onItemClickListner) {
        mListener = listener
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.cart_list, parent, false)
        return CartAdapter.MyViewHolder(itemView, mListener)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = cartList[position]
        //holder.cartProductImage.setImageResource(currentItem.cart_pro_img)
        holder.cartProductName.text = currentItem.cart_pro_name
        holder.cartProductPrice.text = currentItem.cart_pro_price
        holder.cartProductQty.text = currentItem.cart_pro_qty
        holder.cartProductDesc.text = currentItem.cart_pro_desc

        Glide.with(holder.cartProductImage)
            .load(currentItem.cart_pro_img)
            .into(holder.cartProductImage);
    }

    override fun getItemCount(): Int {
        return cartList.size
    }

    class MyViewHolder(itemView: View, listener: CartAdapter.onItemClickListner) :
        RecyclerView.ViewHolder(itemView) {
        val cartProductImage: ImageView = itemView.findViewById(R.id.img_cart_item)
        val cartProductName: TextView = itemView.findViewById(R.id.txt_cart_pname)
        val cartProductPrice: TextView = itemView.findViewById(R.id.txt_cart_pprie)
        val cartProductQty: TextView = itemView.findViewById(R.id.txt_cart_pqty)
        val cartProductDesc: TextView = itemView.findViewById(R.id.txt_cart_pdesc)

        init {
            itemView.setOnClickListener {
                listener.onItemClick(adapterPosition)
            }
        }

    }
}