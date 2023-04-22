package com.jahangiri.passwordgenerator

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.jahangiri.passwordgenerator.databinding.ActivityMainBinding
import com.jahangiri.passwordgenerator.model.ResponseRandomFacts
import com.jahangiri.passwordgenerator.server.ApiClient
import com.jahangiri.passwordgenerator.server.ApiServices
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    //binding =>
    private lateinit var binding: ActivityMainBinding

    //ApiServices =>
    private val api: ApiServices by lazy {
        ApiClient().getClient().create(ApiServices::class.java)
    }

    //Call RandomFacts Api ->
    private val callRandomFactsApi by lazy {
        api.getRandomFacts()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Init views =>
        binding.apply {
            txtFact.text = "loading Fact..."
            txtFact.setTextColor(Color.RED)
            txtDayOfWeek.text = "Loading the day of week"
            txtDayOfWeek.setTextColor(Color.RED)

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
                    Log.e("onFailure", "Err: ${t.message}")
                    txtFact.text =
                        "Failed to load Random Facts because of internet connection problems"
                    txtFact.setTextColor(Color.RED)
                }

            })
        }

    }
}