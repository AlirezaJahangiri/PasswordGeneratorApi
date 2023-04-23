package com.jahangiri.passwordgenerator.activity

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.jahangiri.passwordgenerator.customToast.showCustomToastSuccess
import com.jahangiri.passwordgenerator.customToast.showCustomToastWarning
import com.jahangiri.passwordgenerator.databinding.ActivityMainBinding
import com.jahangiri.passwordgenerator.model.ResponseRandomFacts
import com.jahangiri.passwordgenerator.model.ResponseRandomPassword
import com.jahangiri.passwordgenerator.model.ResponseWorldTime
import com.jahangiri.passwordgenerator.server.ApiClient
import com.jahangiri.passwordgenerator.server.ApiServices
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    //binding =>
    private lateinit var binding: ActivityMainBinding

    //others =>
    private lateinit var resultRadNumbers: String
    private lateinit var resultRadChars: String
    private lateinit var resultEdtLengthPassword: String

    //ApiServices =>
    private val api: ApiServices by lazy {
        ApiClient().getClient().create(ApiServices::class.java)
    }

    //Call RandomFacts Api =>
    private val callRandomFactsApi by lazy {
        api.getRandomFacts()
    }

    //Call WorldTime Api =>
    private val callWorldTimeApi by lazy {
        api.getWorldTime("tehran")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Init views =>
        initViews()

        //Api =>
        randomFactsApi()
        worldTimeApi()
        randomPasswordApi()


    }

    private fun initViews() {

        binding.apply {
            cardViewShowPassword.visibility = View.GONE
            radbtnTrueNumbers.isChecked = true
            radbtnCharsFalse.isChecked = true
            txtFact.text = "loading Fact..."
            txtFact.setTextColor(Color.RED)
            txtDayOfWeek.text = "Loading the day of week"
            txtDayOfWeek.setTextColor(Color.RED)
        }
    }

    private fun randomFactsApi() {

        binding.apply {

            //Random Facts Api =>
            callRandomFactsApi.enqueue(object :
                Callback<MutableList<ResponseRandomFacts.ResponseRandomFactsItem>> {
                override fun onResponse(
                    call: Call<MutableList<ResponseRandomFacts.ResponseRandomFactsItem>>,
                    response: Response<MutableList<ResponseRandomFacts.ResponseRandomFactsItem>>
                ) {
                    if (response.isSuccessful) {
                        response.body()?.let { itBody ->
                            if (itBody.isNotEmpty()) {
                                txtFact.text = itBody[0].fact
                                txtFact.setTextColor(Color.WHITE)
                            }
                        }
                    }
                }

                override fun onFailure(
                    call: Call<MutableList<ResponseRandomFacts.ResponseRandomFactsItem>>,
                    t: Throwable
                ) {
                    Log.e("onFailureRandomFacts", "Err: ${t.message}")
                    txtFact.text =
                        "Failed to load Random Facts because of internet connection problems"
                    txtFact.setTextColor(Color.RED)
                }

            })
        }
    }

    private fun worldTimeApi() {

        binding.apply {

            //World Time Api =>
            callWorldTimeApi.enqueue(object : Callback<ResponseWorldTime> {
                override fun onResponse(
                    call: Call<ResponseWorldTime>,
                    response: Response<ResponseWorldTime>
                ) {
                    if (response.isSuccessful) {
                        response.body()?.let { itBody ->
                            txtDayOfWeek.text = "${itBody.dayOfWeek}, ${itBody.date}"
                            txtDayOfWeek.setTextColor(Color.WHITE)
                        }
                    }
                }

                override fun onFailure(call: Call<ResponseWorldTime>, t: Throwable) {
                    Log.e("onFailureWorldTime", "Err: ${t.message}")
                    txtDayOfWeek.text =
                        "Failed to load day of week because of internet connection problems"
                    txtDayOfWeek.setTextColor(Color.RED)
                }

            })
        }
    }

    private fun randomPasswordApi() {

        binding.apply {

            //button generate password onClick listener =>
            btnGeneratePassword.setOnClickListener() {

                //Check EditText length password =>
                if (edtLengthPassword.text.isNullOrBlank()) {
                    Toast(this@MainActivity).showCustomToastWarning(
                        "Length of password cannot be empty!",
                        this@MainActivity
                    )
                } else if (edtLengthPassword.text.toString().toInt() < 4) {
                    Toast(this@MainActivity).showCustomToastWarning(
                        "Length of password should be more than 3 characters!",
                        this@MainActivity
                    )
                } else {
                    //EveryThing is well =>

                    //visibilities =>
                    progressbarMain.visibility = View.VISIBLE

                    //Check radioButtons (include numbers) , (include chars) =>
                    if (radbtnTrueNumbers.isChecked) {
                        resultRadNumbers = "false"
                    } else if (radbtnFalseNumbers.isChecked) {
                        resultRadNumbers = "true"
                    }

                    if (radbtnCharsTrue.isChecked) {
                        resultRadChars = "false"
                    } else if (radbtnCharsFalse.isChecked) {
                        resultRadChars = "true"
                    }

                    //initialized the value of resultEdtLengthPassword =>
                    resultEdtLengthPassword = edtLengthPassword.text.toString()


                    //Call Random Password Api =>
                    val callRandomPasswordApi = api.getRandomPassword(
                        resultEdtLengthPassword.toInt(),
                        resultRadNumbers,
                        resultRadChars
                    )

                    //Random password Api
                    callRandomPasswordApi.enqueue(object : Callback<ResponseRandomPassword> {
                        override fun onResponse(
                            call: Call<ResponseRandomPassword>,
                            response: Response<ResponseRandomPassword>
                        ) {
                            if (response.isSuccessful) {
                                response.body()?.let { itBody ->
                                    txtShowPassword.text = itBody.randomPassword
                                    txtShowPassword.setTextColor(Color.WHITE)

                                    //Visibilities =>
                                    btnCopy.visibility = View.VISIBLE
                                    progressbarMain.visibility = View.GONE
                                    cardViewShowPassword.visibility = View.VISIBLE
                                }
                            }
                        }

                        override fun onFailure(call: Call<ResponseRandomPassword>, t: Throwable) {
                            Log.e("onFailureRandomPassword", "Err: ${t.message}")
                            cardViewShowPassword.visibility = View.VISIBLE
                            progressbarMain.visibility = View.GONE
                            txtShowPassword.text =
                                "Failed to load Random Password because of internet connection problems"
                            txtShowPassword.setTextColor(Color.RED)
                            btnCopy.visibility = View.GONE
                        }

                    })

                    //Copy the password that we generated into clipBoard =>
                    copyTextToClipboard()
                }

            }
        }
    }

    private fun copyTextToClipboard() {

        binding.apply {

            //btn copy Onclick Listener =>
            btnCopy.setOnClickListener() {

                val textToCopy = txtShowPassword.text
                val clipboardManager =
                    getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                val clipData = ClipData.newPlainText("text", textToCopy)
                clipboardManager.setPrimaryClip(clipData)
                Toast(this@MainActivity).showCustomToastSuccess(
                    "Password has been copied into your clipBoard!",
                    this@MainActivity
                )
            }
        }
    }

}