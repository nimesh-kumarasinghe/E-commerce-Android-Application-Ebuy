package com.example.ebuy

import android.annotation.SuppressLint
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Request
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import org.json.JSONArray
import org.json.JSONException

class SignUp : AppCompatActivity() {
    @SuppressLint("MissingInflatedId", "WrongViewCast")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        supportActionBar?.hide()

        var login = findViewById<Button>(R.id.btn_login)
        var btn_register = findViewById<Button>(R.id.btn_register_user)

        var first_name = findViewById<EditText>(R.id.txt_fname)
        var lst_name = findViewById<EditText>(R.id.txt_lname)
        var address = findViewById<EditText>(R.id.txt_address)
        var postal_code = findViewById<EditText>(R.id.txt_postal_code)
        //var country = findViewById<EditText>(R.id.txt_country)
        var city = findViewById<EditText>(R.id.txt_city)
        var mobile_number = findViewById<EditText>(R.id.txt_telephone)
        var email = findViewById<EditText>(R.id.txt_register_email)
        var password = findViewById<EditText>(R.id.txt_register_password)
        var spn_country = findViewById<Spinner>(R.id.spn_country)

        var country_list = ArrayList<String>()
        country_list.add("Afghanistan")
        country_list.add("Albania")
        country_list.add("Algeria")
        country_list.add("Andorra")
        country_list.add("Angola")
        country_list.add("Anguilla")
        country_list.add("Antigua &amp; Barbuda")
        country_list.add("Argentina")
        country_list.add("Armenia")
        country_list.add("Aruba")
        country_list.add("Australia")
        country_list.add("Austria")
        country_list.add("Azerbaijan")
        country_list.add("Bahamas")
        country_list.add("Bahrain")
        country_list.add("Bangladesh")
        country_list.add("Barbados")
        country_list.add("Belarus")
        country_list.add("Belgium")
        country_list.add("Belize")
        country_list.add("Benin")
        country_list.add("Bermuda")
        country_list.add("Bhutan")
        country_list.add("Bolivia")
        country_list.add("Bosnia &amp; Herzegovina")
        country_list.add("Botswana")
        country_list.add("Brazil")
        country_list.add("British Virgin Islands")
        country_list.add("Brunei")
        country_list.add("Bulgaria")
        country_list.add("Burkina Faso")
        country_list.add("Burundi")
        country_list.add("Cambodia")
        country_list.add("Cameroon")
        country_list.add("Cape Verde")
        country_list.add("Cayman Islands")
        country_list.add("Chad")
        country_list.add("Chile")
        country_list.add("China")
        country_list.add("Colombia")
        country_list.add("Congo")
        country_list.add("Cook Islands")
        country_list.add("Costa Rica")
        country_list.add("Cote D Ivoire")
        country_list.add("Croatia")
        country_list.add("Cruise Ship")
        country_list.add("Cuba")
        country_list.add("Cyprus")
        country_list.add("Czech Republic")
        country_list.add("Denmark")
        country_list.add("Djibouti")
        country_list.add("Dominica")
        country_list.add("Dominican Republic")
        country_list.add("Ecuador")
        country_list.add("Egypt")
        country_list.add("El Salvador")
        country_list.add("Equatorial Guinea")
        country_list.add("Estonia")
        country_list.add("Ethiopia")
        country_list.add("Falkland Islands")
        country_list.add("Faroe Islands")
        country_list.add("Fiji")
        country_list.add("Finland")
        country_list.add("France")
        country_list.add("French Polynesia")
        country_list.add("French West Indies")
        country_list.add("Gabon")
        country_list.add("Gambia")
        country_list.add("Georgia")
        country_list.add("Germany")
        country_list.add("Ghana")
        country_list.add("Gibraltar")
        country_list.add("Greece")
        country_list.add("Greenland")
        country_list.add("Grenada")
        country_list.add("Guam")
        country_list.add("Guatemala")
        country_list.add("Guernsey")
        country_list.add("Guinea")
        country_list.add("Guinea Bissau")
        country_list.add("Guyana")
        country_list.add("Haiti")
        country_list.add("Honduras")
        country_list.add("Hong Kong")
        country_list.add("Hungary")
        country_list.add("Iceland")
        country_list.add("India")
        country_list.add("Indonesia")
        country_list.add("Iran")
        country_list.add("Iraq")
        country_list.add("Ireland")
        country_list.add("Isle of Man")
        country_list.add("Israel")
        country_list.add("Italy")
        country_list.add("Jamaica")
        country_list.add("Japan")
        country_list.add("Jersey")
        country_list.add("Jordan")
        country_list.add("Kazakhstan")
        country_list.add("Kenya")
        country_list.add("Kuwait")
        country_list.add("Kyrgyz Republic")
        country_list.add("Laos")
        country_list.add("Latvia")
        country_list.add("Lebanon")
        country_list.add("Lesotho")
        country_list.add("Liberia")
        country_list.add("Libya")
        country_list.add("Liechtenstein")
        country_list.add("Lithuania")
        country_list.add("Luxembourg")
        country_list.add("Macau")
        country_list.add("Macedonia")
        country_list.add("Madagascar")
        country_list.add("Malawi")
        country_list.add("Malaysia")
        country_list.add("Maldives")
        country_list.add("Mali")
        country_list.add("Malta")
        country_list.add("Mauritania")
        country_list.add("Mauritius")
        country_list.add("Mexico")
        country_list.add("Moldova")
        country_list.add("Monaco")
        country_list.add("Mongolia")
        country_list.add("Montenegro")
        country_list.add("Montserrat")
        country_list.add("Morocco")
        country_list.add("Mozambique")
        country_list.add("Namibia")
        country_list.add("Nepal")
        country_list.add("Netherlands")
        country_list.add("Netherlands Antilles")
        country_list.add("New Caledonia")
        country_list.add("New Zealand")
        country_list.add("Nicaragua")
        country_list.add("Niger")
        country_list.add("Nigeria")
        country_list.add("Norway")
        country_list.add("Oman")
        country_list.add("Pakistan")
        country_list.add("Palestine")
        country_list.add("Panama")
        country_list.add("Papua New Guinea")
        country_list.add("Paraguay")
        country_list.add("Peru")
        country_list.add("Philippines")
        country_list.add("Poland")
        country_list.add("Portugal")
        country_list.add("Puerto Rico")
        country_list.add("Qatar")
        country_list.add("Reunion")
        country_list.add("Romania")
        country_list.add("Russia")
        country_list.add("Rwanda")
        country_list.add("Saint Pierre &amp; Miquelon")
        country_list.add("Samoa")
        country_list.add("San Marino")
        country_list.add("Satellite")
        country_list.add("Saudi Arabia")
        country_list.add("Senegal")
        country_list.add("Serbia")
        country_list.add("Seychelles")
        country_list.add("Sierra Leone")
        country_list.add("Singapore")
        country_list.add("Slovakia")
        country_list.add("Slovenia")
        country_list.add("South Africa")
        country_list.add("South Korea")
        country_list.add("Spain")
        country_list.add("Sri Lanka")
        country_list.add("St Kitts &amp; Nevis")
        country_list.add("St Lucia")
        country_list.add("St Vincent")
        country_list.add("St. Lucia")
        country_list.add("Sudan")
        country_list.add("Suriname")
        country_list.add("Swaziland")
        country_list.add("Sweden")
        country_list.add("Switzerland")
        country_list.add("Syria")
        country_list.add("Taiwan")
        country_list.add("Tajikistan")
        country_list.add("Tanzania")
        country_list.add("Thailand")
        country_list.add("Timor L'Este")
        country_list.add("Togo")
        country_list.add("Tonga")
        country_list.add("Trinidad &amp; Tobago")
        country_list.add("Tunisia")
        country_list.add("Turkey")
        country_list.add("Turkmenistan")
        country_list.add("Turks &amp; Caicos")
        country_list.add("Uganda")
        country_list.add("Ukraine")
        country_list.add("United Arab Emirates")
        country_list.add("United Kingdom")
        country_list.add("Uruguay")
        country_list.add("Uzbekistan")
        country_list.add("Venezuela")
        country_list.add("Vietnam")
        country_list.add("Virgin Islands (US)")
        country_list.add("Yemen")
        country_list.add("Zambia")
        country_list.add("Zimbabwe")

        var adapter_country = ArrayAdapter(
            applicationContext,
            android.R.layout.simple_spinner_dropdown_item,
            country_list
        )
        adapter_country.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spn_country!!.setAdapter(adapter_country)

        login.setOnClickListener()
        {
            var login_intent = Intent(this@SignUp, SignIn::class.java)
            startActivity(login_intent)
            finish()
        }

        btn_register.setOnClickListener()
        {
            var validatex: Boolean = true

            if (Validatetxt(first_name) == false) {
                if (validatex == true) {
                    validatex = false
                    Toast.makeText(
                        applicationContext, "First Name Cannot Be Empty",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
            if (Validatetxt(lst_name) == false) {
                if (validatex == true) {
                    validatex = false
                    Toast.makeText(
                        applicationContext, "Last Name Cannot Be Empty",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
            if (Validatetxt(address) == false) {
                if (validatex == true) {
                    validatex = false
                    Toast.makeText(
                        applicationContext, "Address Cannot Be Empty",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
            if (Validatetxt(postal_code) == false) {
                if (validatex == true) {
                    validatex = false
                    Toast.makeText(
                        applicationContext, "Postal code Cannot Be Empty",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
            /*if(Validatetxt(country) == false){
                if(validatex == true) {
                    validatex = false
                    Toast.makeText(
                        applicationContext, "Country Cannot Be Empty",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }*/
            if (Validatetxt(city) == false) {
                if (validatex == true) {
                    validatex = false
                    Toast.makeText(
                        applicationContext, "City Cannot Be Empty",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
            if (Validatetxt(mobile_number) == false) {
                if (validatex == true) {
                    validatex = false
                    Toast.makeText(
                        applicationContext, "Mobile number Cannot Be Empty",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
            if (mobileValidate(mobile_number) == false) {
                if (validatex == true) {
                    validatex = false
                    Toast.makeText(
                        applicationContext, "Please recheck your mobile number",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
            if (emailValidate(email) == false) {
                if (validatex == true) {
                    validatex = false
                    Toast.makeText(
                        applicationContext, "Invalid email address",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
            if (Validatetxt(email) == false) {
                if (validatex == true) {
                    validatex = false
                    Toast.makeText(
                        applicationContext, "Email Cannot Be Empty",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
            if (Validatetxt(password) == false) {
                if (validatex == true) {
                    validatex = false
                    Toast.makeText(
                        applicationContext, "Password Cannot Be Empty",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
            if (passwordValidate(password) == false) {
                if (validatex == true) {
                    validatex = false
                    Toast.makeText(
                        applicationContext, "Your password should be more than 8 characters",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            if (validatex == true) {
                Toast.makeText(
                    applicationContext, "Success",
                    Toast.LENGTH_LONG
                ).show()

                val request = JsonArrayRequest(
                    Request.Method.GET,
                    "https://duropackaging.com/sv4/register?FirstName=" + first_name.text.toString() + "&LastName=" + lst_name.text.toString() + "&Address=" + address.text.toString() + "&City=" + city.text.toString() + "&Country=" + spn_country.selectedItem.toString() + "&PostalCode=" + postal_code.text.toString() + "&PhoneNo=" + mobile_number.text.toString() + "&Email=" + email.text.toString() + "&CPassword=" + password.text.toString() + " ",
                    JSONArray(), { response ->
                        try {
                            var status = response
                            var logged = status.getJSONObject(0).getString("Status")

                            if (logged.toInt() == 1) {
                                Log.e("Registered", "TRUE")
                                Toast.makeText(
                                    applicationContext, "Successfully Registered",
                                    Toast.LENGTH_LONG
                                ).show()

                                var login_intent = Intent(this@SignUp, SignIn::class.java)
                                startActivity(login_intent)
                                finish()

                            } else {
                                Log.e("Registered", "FALSE")
                                Toast.makeText(
                                    applicationContext, "Please try again",
                                    Toast.LENGTH_LONG
                                ).show()
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

                Volley.newRequestQueue(applicationContext).add(request)

            }
        }
    }

    fun Validatetxt(textx: EditText): Boolean {

        var res: Boolean = true
        if (textx.text.length == 0) {
            res = false
        }
        return res
    }

    fun mobileValidate(numtxt: EditText): Boolean {
        var res: Boolean = true

        var xx = numtxt.text

        val regex = "-?[0-9]+(\\.[0-9]+)?".toRegex()
        if (xx.matches(regex) != true) {
            res = false
        }
        if (xx.length != 10) {
            res = false
        }

        return res
    }

    fun emailValidate(emailtxt: EditText): Boolean {
        var res: Boolean = true

        var emailx = emailtxt.text

        if (android.util.Patterns.EMAIL_ADDRESS.matcher(emailx).matches() == false) {
            res = false
        }

        return res
    }

    fun passwordValidate(pwdtxt: EditText): Boolean {
        var res: Boolean = true
        var pwdx = pwdtxt.text

        if (pwdx.length < 8) {
            res = false
        }

        return res
    }
}