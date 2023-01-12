package com.example.ebuy

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteDatabase.openOrCreateDatabase
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
import com.example.ebuy.Glob.Companion.customer_id

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [OrderFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class OrderFragment() : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var adapter: OrderAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var orderArrayList: ArrayList<Order>

    lateinit var order_id: String
    lateinit var order_amount: String
    lateinit var order_date: String
    lateinit var order_status: String

    lateinit var product_name: String
    lateinit var order_qty: String
    lateinit var img_loc: String

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
        return inflater.inflate(R.layout.fragment_order, container, false)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment OrderFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            OrderFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        orderArrayList = arrayListOf<Order>()
        //var global_variable =
        var customer_id = Glob.Companion.customer_id

        Log.e("xx", customer_id)
        val request = JsonArrayRequest(
            Request.Method.GET,
            "https://duropackaging.com/sv3/user-order-custom?CustomerID=" + customer_id + " ",
            JSONArray(), { response ->
                try {
                    var cont = true
                    var order_data = response
                    var order_count = order_data.length()

                    try {
                        var status = order_data.getJSONObject(0).getString("Status")
                        if (status.toInt() == -1) {
                            Toast.makeText(
                                context, "You Have No Orders",
                                Toast.LENGTH_LONG
                            ).show()
                            cont = false
                        }

                    } catch (e: java.lang.Exception) {

                    }
                    if (cont == true) {

                        var counter = 0
                        while (counter <= order_count) {

                            order_id = order_data.getJSONObject(counter).getString("OrderID")
                            order_amount =
                                "$" + order_data.getJSONObject(counter).getString("Amount")
                            order_date =
                                "Date: " + order_data.getJSONObject(counter).getString("OrderDate")
                            order_status =
                                "Status: " + order_data.getJSONObject(counter)
                                    .getString("OrderStatus")
                            product_name =
                                order_data.getJSONObject(counter).getString("ProductName")
                            order_qty =
                                "Qty: " + order_data.getJSONObject(counter).getString("OrderQTY")
                            img_loc =
                                "https://duropackaging.com/sv2/imgs/" + order_data.getJSONObject(
                                    counter
                                ).getString("imgLocation")

                            val orderx = Order(
                                order_id,
                                order_amount,
                                order_date,
                                order_status,
                                product_name,
                                order_qty,
                                img_loc
                            )
                            orderArrayList.add(orderx)
                            Log.e("Data", orderx.toString())
                            counter++
                        }
                    }

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


        val timer = object : CountDownTimer(10000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                if (orderArrayList.isEmpty()) {
                    Log.e("Order", "Order Array Is Empty")
                } else {
                    try {
                        loadData(view)
                        cancel()
                    } catch (e: Exception) {
                        Log.e("Wrong", "SomeThing Went Wrong")
                    }
                }
            }

            override fun onFinish() {

            }
        }
        timer.start()

    }

    fun loadData(view: View) {

        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        recyclerView = view.findViewById(R.id.order_recycle_view)
        recyclerView.layoutManager = layoutManager
        recyclerView.setHasFixedSize(true)
        adapter = OrderAdapter(orderArrayList)
        recyclerView.adapter = adapter

        adapter.setOnItemClickListener(object : OrderAdapter.onItemClickListner {
            override fun onItemClick(position: Int) {
                //var myintent = Intent(context,ProductActivity::class.java)
                //startActivity(myintent)
            }
        })
    }
}