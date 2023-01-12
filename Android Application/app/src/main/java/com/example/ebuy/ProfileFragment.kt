package com.example.ebuy

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.toolbox.Volley

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ProfileFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ProfileFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

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
        //return inflater.inflate(R.layout.fragment_profile, container, false)

        val view: View = inflater!!.inflate(R.layout.fragment_profile, container, false)

        //GET USER DATA

        view.findViewById<TextView>(R.id.txt_welcome).text =
            "Welcome, " + Glob.Companion.user_first_name
        view.findViewById<TextView>(R.id.txt_name).text = Glob.Companion.user_first_name
        view.findViewById<TextView>(R.id.txt_user_country).text = Glob.Companion.user_country
        //

        view.findViewById<Button>(R.id.btn_edit_profile).setOnClickListener() {
            Log.e("Data", "Edit Profile Button Clicked")
            var edit_profile = Intent(context, EditProfileActivity::class.java)
            startActivity(edit_profile)
        }

        view.findViewById<Button>(R.id.btn_signout).setOnClickListener() {
            Log.e("Data", "Sign Out Clicked")
            Glob.Companion.cus_logout = true
            Toast.makeText(
                context, "Sign Out Successful",
                Toast.LENGTH_LONG
            ).show()
            Glob.Companion.customer_id = "-1"
            Glob.Companion.user_first_name = "null"
            Glob.Companion.user_last_name = "null"
            Glob.Companion.user_address = "null"
            Glob.Companion.user_postal_code = "null"
            Glob.Companion.user_country = "null"
            Glob.Companion.user_city = "null"
            Glob.Companion.user_mobile_number = "null"
            Glob.Companion.user_email = "null"

            Glob.Companion.db_con.execSQL("drop table cart")
            Glob.Companion.cart_pro_max_array_id = 0

            var counter = 0
            while (counter < Glob.Companion.cart_pro_ids.count()) {
                Glob.Companion.cart_pro_ids.set(counter, null)
                Log.e("Remove", "cart products")
                counter++
            }

            var sign_out = Intent(context, Login::class.java)
            startActivity(sign_out)
        }

        // Return the fragment view/layout
        return view
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ProfileFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ProfileFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}