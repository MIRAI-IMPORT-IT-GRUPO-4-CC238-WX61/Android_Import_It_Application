package com.example.android.android_import_it_application.controllers.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.android.android_import_it_application.R
import com.example.android.android_import_it_application.adapter.CustomerOrderAdapter
import com.example.android.android_import_it_application.adapter.OrdersAdapter
import com.example.android.android_import_it_application.models.Order
import com.example.android.android_import_it_application.network.ImportItService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class OrdersFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var orders: List<Order>
    private var dni: String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_orders, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = view.findViewById(R.id.rvOrders)

        val bundle = arguments
        if (dni.isEmpty()) {
            dni = bundle?.getString("DNI", "") ?: ""
        }

        loadOrders(view.context)
    }

    private fun loadOrders(context: Context) {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://importitbackend-production-fd05.up.railway.app/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val importItService: ImportItService = retrofit.create(ImportItService::class.java)
        val request = importItService.getOrders()

        request.enqueue(object : Callback<List<Order>> {
            override fun onFailure(call: Call<List<Order>>, t: Throwable) {
                Log.d("Activity Fail", "Error: $t")
            }

            override fun onResponse(
                call: Call<List<Order>>,
                response: Response<List<Order>>
            ) {
                if (response.isSuccessful) {
                    val allOrders: List<Order> = response.body()!!
                    orders = allOrders.filter { it.dni == dni }  // Filtrar los pedidos por el valor de dni

                    recyclerView.layoutManager = LinearLayoutManager(context)
                    recyclerView.adapter = OrdersAdapter(orders, context)
                } else {
                    Log.d("Activity fail", "Error: " + response.code())
                }
            }
        })
    }
}