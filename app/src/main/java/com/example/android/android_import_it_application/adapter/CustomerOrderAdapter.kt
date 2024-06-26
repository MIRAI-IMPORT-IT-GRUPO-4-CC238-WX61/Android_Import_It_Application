package com.example.android.android_import_it_application.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.android.android_import_it_application.R
import com.example.android.android_import_it_application.database.OrderDatabase
import com.example.android.android_import_it_application.models.MyOrder
import com.example.android.android_import_it_application.models.Order
import com.example.android.android_import_it_application.network.ImportItService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class CustomerOrderAdapter (private val customerOrders: List<Order>, private val context: Context): RecyclerView.Adapter<CustomerOrderAdapter.ViewHolder>() {

    class ViewHolder(val view: View): RecyclerView.ViewHolder(view) {
        //val ivCustOrders = view.findViewById<ImageView>(R.id.ivCustOrders)
        val tvTitleProduct = view.findViewById<TextView>(R.id.tvTitleProduct)
        val tvLocationProduct = view.findViewById<TextView>(R.id.tvLocationProduct)
        val tvUrl = view.findViewById<TextView>(R.id.tvUrl)
        val tvPrice = view.findViewById<TextView>(R.id.tvPrice)
        val tvWeight1 = view.findViewById<TextView>(R.id.tvWeight1)
        val tvComision = view.findViewById<TextView>(R.id.tvComision)
        val tvCusName = view.findViewById<TextView>(R.id.tvCusName)
        val btnSelect = view.findViewById<Button>(R.id.btnAdd)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder{
        val view= LayoutInflater.from(context)
            .inflate(R.layout.prototype_customer_order_traveler, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val cusOrder = customerOrders[position]
        holder.tvTitleProduct.text = cusOrder.tittle
        holder.tvUrl.text = cusOrder.url
        holder.tvPrice.text = cusOrder.price
        holder.tvWeight1.text = cusOrder.weight
        holder.tvComision.text = cusOrder.comision
        holder.tvCusName.text = cusOrder.name

        val retrofit = Retrofit.Builder()
            .baseUrl("https://importitbackend-production-fd05.up.railway.app/api/") // Ajusta la URL base según tu API
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        holder.btnSelect.setOnClickListener {
            saveOrder(cusOrder)
            val myOrder = MyOrder(
                order_id = 0, // Set the appropriate value for order_id
                name = cusOrder.name,
                amount = "", // Set the appropriate value for amount
                comision = cusOrder.comision,
                dni = "", // Set the appropriate value for dni
                price = cusOrder.price,
                status = "", // Set the appropriate value for status
                tittle = cusOrder.tittle,
                url = cusOrder.url,
                weight = cusOrder.weight
            )

            val importItService: ImportItService = retrofit.create(ImportItService::class.java)
            val call = importItService.getMyOrders()

            call.enqueue(object : Callback<List<MyOrder>> {
                override fun onResponse(call: Call<List<MyOrder>>, response: Response<List<MyOrder>>) {
                    if (response.isSuccessful) {
                        val existingOrders = response.body()
                        if (existingOrders != null) {
                            val duplicateExists = existingOrders.any { order ->
                                order.dni == myOrder.dni && order.tittle == myOrder.tittle && order.url == myOrder.url
                            }
                            if (duplicateExists) {
                                Toast.makeText(context, "Ya existe la orden en My Orders", Toast.LENGTH_SHORT).show()
                            } else {
                                postOrder(myOrder)
                            }
                        }
                    } else {
                        Toast.makeText(context, "My Orders está vacío", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<List<MyOrder>>, t: Throwable) {
                    Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show()
                }
            })
        }
    }

    private fun postOrder(myOrder: MyOrder) {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://importitbackend-production-fd05.up.railway.app/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val importItService: ImportItService = retrofit.create(ImportItService::class.java)
        val call: Call<Void> = importItService.createOrder(myOrder)

        call.enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    // El pedido se envió correctamente
                    // Realiza las acciones necesarias después de enviar el pedido
                    Toast.makeText(context, "Orden guardada en My Orders", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(context, "No se pudo enviar la orden", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                // El pedido no se pudo enviar debido a un error de conexión u otro error
                // Maneja el error de acuerdo a tus necesidades
                Toast.makeText(context, "Error al enviar el pedido", Toast.LENGTH_SHORT).show()
            }
        })
    }


    override fun getItemCount(): Int {
        return customerOrders.size
    }

    private fun saveOrder(cusOrder: Order) {
        val query = OrderDatabase.getInstance(context).getOrderDAO().getById(cusOrder.order_id)
        if (query.isEmpty()) {
            OrderDatabase.getInstance(context).getOrderDAO().insertOrder(cusOrder)
            Toast.makeText(context, "Orden guardada en Wallet", Toast.LENGTH_SHORT).show()
        }
        else Toast.makeText(context, "Ya se ha guardado previamente esta orden en Wallet", Toast.LENGTH_SHORT).show()
    }




}