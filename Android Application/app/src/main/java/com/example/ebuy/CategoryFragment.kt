package com.example.ebuy

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ebuy.Glob.Companion.cate_load
import com.example.ebuy.Glob.Companion.cate_id

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [CategoryFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CategoryFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var adapter: CategoryAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var categoryArrayList: ArrayList<Catgeory>

    lateinit var imageId: Array<Int>
    lateinit var heading: Array<String>

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
        return inflater.inflate(R.layout.fragment_category, container, false)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment CategoryFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            CategoryFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dataInitialize()
        val layoutManager = LinearLayoutManager(
            context, LinearLayoutManager.VERTICAL,
            false
        )
        recyclerView = view.findViewById(R.id.category_recycle_view)
        recyclerView.layoutManager = layoutManager
        recyclerView.setHasFixedSize(true)
        adapter = CategoryAdapter(categoryArrayList)
        recyclerView.adapter = adapter


        //var adapter = MyAdapter(newsArrayList)
        //recyclerView.adapter = adapter
        adapter.setOnItemClickListener(object : CategoryAdapter.onItemClickListner {
            override fun onItemClick(position: Int) {
                var myintent = Intent(context, Home::class.java)

                cate_load = true
                cate_id = position.toString()

                startActivity(myintent)
            }

        })
    }

    private fun dataInitialize() {
        categoryArrayList = arrayListOf<Catgeory>()

        imageId = arrayOf(
            R.drawable.home,
            R.drawable.bags,
            R.drawable.makeups,
            R.drawable.books,
            R.drawable.sport,
            R.drawable.jewelrys,
            R.drawable.phones,
            R.drawable.toy,
            R.drawable.electronic,
            R.drawable.gardens

        )

        heading = arrayOf(
            getString(R.string.head_1),
            getString(R.string.head_2),
            getString(R.string.head_3),
            getString(R.string.head_4),
            getString(R.string.head_5),
            getString(R.string.head_6),
            getString(R.string.head_7),
            getString(R.string.head_8),
            getString(R.string.head_9),
            getString(R.string.head_10)

        )


        for (i in imageId.indices) {
            val category = Catgeory(imageId[i], heading[i])
            categoryArrayList.add(category)
        }


    }
}