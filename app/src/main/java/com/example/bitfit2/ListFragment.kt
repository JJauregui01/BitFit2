package com.example.bitfit2

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.ListFragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class ListFragment : Fragment() {

    private val foodz = mutableListOf<UserClass>()
    lateinit var foodListRv: RecyclerView
    lateinit var adapterHere: BitFitAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val foodData = activity?.intent?.getSerializableExtra("food") as UserClass?
        lifecycleScope.launch {
            (activity?.application as DaoApplication).db.UserDao().getAll().collect { dataBaseList ->
                dataBaseList.map { entity ->
                    UserClass(
                        entity.food,
                        entity.calories
                    )
                }.also { mList ->
                    foodz.clear()
                    foodz.addAll(mList)
                    adapterHere.notifyDataSetChanged()
                }
            }
        }
        if (foodData != null){
            lifecycleScope.launch(Dispatchers.IO){
                (activity?.application as DaoApplication).db.UserDao().insert(
                    UserData(
                        food = foodData.foods,
                        calories = foodData.calories
                    )
                )
            }
            activity?.intent?.removeExtra("food")
        }
        else{
            Log.d("MainActivity", "no extra")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_list, container, false)
        foodListRv = view.findViewById(R.id.recycleDisplay)
        foodListRv.layoutManager = LinearLayoutManager(context)
        adapterHere = BitFitAdapter(view.context, foodz)
        foodListRv.adapter = adapterHere

        return view
    }

    companion object {
        fun newInstance(): ListFragment{
            return ListFragment()
        }

    }
}