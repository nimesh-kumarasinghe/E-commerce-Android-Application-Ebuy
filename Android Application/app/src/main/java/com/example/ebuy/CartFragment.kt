package com.example.ebuy

import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Request
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import org.json.JSONArray
import org.json.JSONException

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [CartFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CartFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var adapter: CartAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var cartArrayList: ArrayList<Cart>


    //lateinit var product_id : Array<String>
    lateinit var cart_pro_img: Array<Int>
    lateinit var cart_pro_name: Array<String>
    lateinit var cart_pro_price: Array<String>
    lateinit var cart_pro_desc: Array<String>
    lateinit var cart_pro_qty: Array<String>

    lateinit var product_id: String
    lateinit var product_name: String
    lateinit var product_des: String
    lateinit var product_price: String
    lateinit var product_qty: String
    lateinit var product_img: String
    lateinit var product_cateid: String
    lateinit var product_user: String

    //lateinit var product_cateid : Array<String>
    //lateinit var product_user : Array<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_cart, container, false)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment CartFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            CartFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //cartInitialize()

        cartArrayList = arrayListOf<Cart>()
        for (prod in Glob.Companion.cart_pro_ids) {
            if (prod == null) {
                break
            } else {
                val request = JsonArrayRequest(
                    Request.Method.GET,
                    "https://duropackaging.com/sv2/products?id=" + prod.toString(),
                    JSONArray(), { response ->
                        try {
                            var product_data = response
                            product_id = product_data.getJSONObject(0).getString("ProductID")
                            product_name = product_data.getJSONObject(0).getString("ProductName")
                            product_des = product_data.getJSONObject(0).getString("PDescription")
                            product_price = "$" + product_data.getJSONObject(0).getString("Price")
                            product_qty =
                                "Qty: " + product_data.getJSONObject(0).getString("Quantity")
                            product_img =
                                "https://duropackaging.com/sv2/imgs/" + product_data.getJSONObject(0)
                                    .getString("imgLocation")
                            product_cateid = product_data.getJSONObject(0).getString("category_id")
                            product_user = product_data.getJSONObject(0).getString("user_id")

                            val cart = Cart(
                                product_img,
                                product_name,
                                product_price,
                                product_qty,
                                product_des,
                                product_id
                            )
                            cartArrayList.add(cart)

                        } catch (e: JSONException) {

                        }
                        Log.e("response", response.toString())
                    }, { error ->
                        Log.e("response", error.toString())
                    }
                )
                request.setRetryPolicy(
                    DefaultRetryPolicy(
                        10000,
                        DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
                    )
                )

                Volley.newRequestQueue(this.context).add(request)
            }
        }

        val timer = object : CountDownTimer(10000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                if (cartArrayList.isEmpty()) {
                    Log.e("Product", "Product Array Is Empty")
                } else {
                    try {
                        loadProducts(view)
                        cancel()
                    } catch (e: Exception) {
                        Log.e("Wrong", "SomeThing Went Wrong")
                    }
                }
            }

            override fun onFinish() {
                if (cartArrayList.isEmpty()) {
                    Toast.makeText(context, "You have no products in cart", Toast.LENGTH_LONG)
                        .show()
                }
            }
        }
        timer.start()
    }

    fun loadProducts(view: View) {
        val layoutManager = LinearLayoutManager(
            context, LinearLayoutManager.VERTICAL,
            false
        )
        recyclerView = view.findViewById(R.id.cart_recycler_view)
        recyclerView.layoutManager = layoutManager
        recyclerView.setHasFixedSize(true)
        adapter = CartAdapter(cartArrayList)
        recyclerView.adapter = adapter


        //var adapter = MyAdapter(newsArrayList)
        //recyclerView.adapter = adapter
        adapter.setOnItemClickListener(object : CartAdapter.onItemClickListner {
            override fun onItemClick(position: Int) {
                var myintent = Intent(context, ProductActivity::class.java)

                val currentItem = cartArrayList[position.toInt()]
                myintent.putExtra("proid", currentItem.cart_pro_id.toString())
                startActivity(myintent)
            }

        })
    }
}