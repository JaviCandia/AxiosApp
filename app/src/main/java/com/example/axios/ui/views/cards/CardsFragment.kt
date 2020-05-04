package com.example.axios.ui.views.cards

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.axios.Adapter.MyMovieAdapter
import com.example.axios.Common.Common
import com.example.axios.Interface.RetrofitService
import com.example.axios.Model.Movie
import com.example.axios.R
import dmax.dialog.SpotsDialog
import kotlinx.android.synthetic.main.fragment_cards.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class CardsFragment : Fragment() {

    lateinit var mService: RetrofitService
    lateinit var layoutManager: LinearLayoutManager
    lateinit var adapter: MyMovieAdapter
    lateinit var dialog: AlertDialog

    lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val rootView = inflater.inflate(R.layout.fragment_cards, container, false)
        recyclerView = rootView.findViewById(R.id.recyclerMovieList) as RecyclerView

        // Aquí viene lo de retrofit para Recycler
        mService = Common.retrofitService

        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(activity)
        dialog = SpotsDialog.Builder().setCancelable(false).setContext(activity).build()

        getAllMovieList()

        return rootView
    }

    private fun getAllMovieList() {
        dialog.show()

        mService.getMovieList().enqueue(object: Callback<MutableList<Movie>> {
            override fun onFailure(call: Call<MutableList<Movie>>, t: Throwable) {
                TODO("Not yet implemented")
            }

            override fun onResponse(call: Call<MutableList<Movie>>, response: Response<MutableList<Movie>>) {
                adapter = MyMovieAdapter(response.body() as MutableList<Movie>)
                adapter.notifyDataSetChanged()

                recyclerView.adapter = adapter

                dialog.dismiss()
            }

        })
    }

    // Esto es para jalar los parámetros que vienen en la navegación
    val args: CardsFragmentArgs by navArgs()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Reconozco el textView
            //val tv: TextView = view!!.findViewById(R.id.textView)

        // Le asigno el parametro a una variable de tipo String
            //val titulo = args.titleCards
        // Lo pongo en el tv de la pantalla
            //tv.text = titulo


    }
}