package com.example.ebuy

import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.json.JSONArray
import org.json.JSONException
import com.example.ebuy.Glob.Companion.cate_load
import com.example.ebuy.Glob.Companion.cate_id
import com.example.ebuy.Glob.Companion.pro_search
import com.example.ebuy.Glob.Companion.pro_search_q

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeFragment : Fragment() {
    lateinit var categoryRecycle: RecyclerView

    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var adapter: ProductAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var productyArrayList: ArrayList<Product>


    lateinit var product_id: String
    lateinit var product_name: String
    lateinit var product_des: String
    lateinit var product_price: String
    lateinit var product_qty: String
    lateinit var product_img: String
    lateinit var product_cateid: String
    lateinit var product_user: String


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
        //return inflater.inflate(R.layout.fragment_home, container, false)

        val view: View = inflater!!.inflate(R.layout.fragment_home, container, false)

        var btn_s = view.findViewById<ImageButton>(R.id.btn_search)

        btn_s.setOnClickListener() {
            Log.e("Data", "Search Button Cliked")

            val sch_text = view.findViewById<EditText>(R.id.txt_search).text.toString()

            Glob.Companion.pro_search = true
            Glob.Companion.pro_search_q = sch_text

            var home_s = Intent(context, Home::class.java)
            startActivity(home_s)

        }

        return view
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment HomeFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HomeFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var product_url = "https://duropackaging.com/sv2/products"
        //
        if (cate_load == true) {
            product_url = "https://duropackaging.com/sv2/products?category_id=" + cate_id + ""
            cate_load = false
        }
        if (pro_search == true) {
            product_url = "https://duropackaging.com/sv2/products?search=" + pro_search_q + ""
            view.findViewById<EditText>(R.id.txt_search).setText(Glob.pro_search_q)
            pro_search = false
        }

        productyArrayList = arrayListOf<Product>()
        val request = JsonArrayRequest(
            Request.Method.GET,
            product_url,
            JSONArray(), { response ->
                try {
                    var product_data = response
                    var products_count = product_data.length()
                    //var logged = product_data.getJSONObject(0).getString("Status")
                    var counter = 0

                    while (counter <= products_count) {
                        //Log.e("Product Names ",product_data.getJSONObject(counter).getString("ProductName"))

                        product_id = product_data.getJSONObject(counter).getString("ProductID")
                        product_name = product_data.getJSONObject(counter).getString("ProductName")
                        product_des = product_data.getJSONObject(counter).getString("PDescription")
                        product_price = "$" + product_data.getJSONObject(counter).getString("Price")
                        product_qty =
                            "Qty: " + product_data.getJSONObject(counter).getString("Quantity")
                        product_img =
                            "https://duropackaging.com/sv2/imgs/" + product_data.getJSONObject(
                                counter
                            ).getString("imgLocation")
                        product_cateid =
                            product_data.getJSONObject(counter).getString("category_id")
                        product_user = product_data.getJSONObject(counter).getString("user_id")


                        val product = Product(
                            product_id,
                            product_name,
                            product_des,
                            product_price,
                            product_qty,
                            product_img,
                            product_cateid,
                            product_user
                        )
                        //val product = Product("product_id","product_name","product_des","product_price","product_qty","product_img","product_cateid","product_user")
                        Log.e("Data", productyArrayList.toString())
                        productyArrayList.add(product)

                        counter++

                    }
                    Log.e("Data", productyArrayList.toString())


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
                if (productyArrayList.isEmpty()) {
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

            }
        }
        timer.start()


    }

    fun loadProducts(view: View) {
        val layoutManager = LinearLayoutManager(
            context, LinearLayoutManager.VERTICAL,
            false
        )
        recyclerView = view.findViewById(R.id.product_recycle_view)
        recyclerView.layoutManager = GridLayoutManager(context, 2)
        recyclerView.setHasFixedSize(true)
        adapter = ProductAdapter(productyArrayList)
        recyclerView.adapter = adapter

        adapter.setOnItemClickListener(object : ProductAdapter.onItemClickListner {
            override fun onItemClick(position: Int) {
                var myintent = Intent(context, ProductActivity::class.java)
                Log.e("Data", position.toString())
                val currentItem = productyArrayList[position.toInt()]
                Log.e("ddd", currentItem.pro_id.toString())

                myintent.putExtra("proid", currentItem.pro_id.toString())
                startActivity(myintent)
            }
        })
    }

}