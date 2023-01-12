package com.example.ebuy

import android.app.Application
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.util.Log
import com.onesignal.OneSignal

const val ONESIGNAL_APP_ID = "74e234e2-fcf4-4f3a-b006-a349a3e1bff0"


class Glob : Application() {
    companion object {
        var customer_id = ""

        var cate_load = false
        var cate_id = ""


        var user_first_name = ""
        var user_last_name = ""
        var user_address = ""
        var user_postal_code = ""
        var user_country = ""
        var user_city = ""
        var user_mobile_number = ""

        var user_email = ""


        // Search
        var pro_search = false
        var pro_search_q = ""
        //

        var cus_logout = false

        lateinit var db_con: SQLiteDatabase

        var cart_pro_ids = arrayOfNulls<String>(10)
        var cart_pro_max_array_id = 0
    }


    override fun onCreate() {
        super.onCreate()

        // Logging set to help debug issues, remove before releasing your app.
        OneSignal.setLogLevel(OneSignal.LOG_LEVEL.VERBOSE, OneSignal.LOG_LEVEL.NONE)

        // OneSignal Initialization
        OneSignal.initWithContext(this)
        OneSignal.setAppId(ONESIGNAL_APP_ID)

        try {
            Glob.Companion.db_con = openOrCreateDatabase("db_cart", MODE_PRIVATE, null)
            Glob.Companion.db_con.execSQL("create table IF NOT EXISTS cart(product_id varchar(25))")

            var s_data: Cursor = Glob.Companion.db_con.rawQuery("select * from cart", null)
            s_data.moveToFirst()
            var no_of_cart_products = s_data.count
            var counter = 0
            cart_pro_max_array_id = no_of_cart_products

            while (counter < no_of_cart_products) {
                Log.e("Product ID", s_data.getString(0).toString())
                cart_pro_ids.set(counter, s_data.getString(0).toString())
                s_data.moveToNext()
                counter++
            }

            for (x in cart_pro_ids) {
                Log.e("cc", x.toString())
            }
            //Log.e("xxx = ",Glob.Companion.cart_pro_ids.toString())
            //Log.e("xx", s_data.getString(0).toString())
        } catch (e: Exception) {
            Log.e("Error", e.toString())
        }
    }
}