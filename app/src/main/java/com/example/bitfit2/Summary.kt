package com.example.bitfit2

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import java.math.RoundingMode
import java.text.DecimalFormat

class Summary : Fragment() {

    lateinit var avgCalories: TextView
    lateinit var minCalories: TextView
    lateinit var maxCalories: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleScope.launch {
            (activity?.application as DaoApplication).db.UserDao().getAll().collect { dataBaseList ->
                dataBaseList.map { entity ->
                    UserClass(
                        entity.food,
                        entity.calories
                    )
                }.also { mList ->
                    var total = 0.00
                    var min = mList[0].calories as Double
                    var max = mList[0].calories as Double

                    for(i in 0..(mList.size - 1)){
                        total += mList[i].calories as Double

                        if (min >  mList[i].calories as Double){
                            min = mList[i].calories as Double
                        }

                        if (max <  mList[i].calories as Double){
                            max = mList[i].calories as Double
                        }
                    }
                    val avgCal1 = total / mList.size

                    avgCalories.text = roundOffDecimal(avgCal1).toString()
                    minCalories.text = min.toString()
                    maxCalories.text = max.toString()
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_summary, container, false)
        avgCalories = view.findViewById(R.id.averageCal)
        minCalories = view.findViewById(R.id.minCal)
        maxCalories = view.findViewById(R.id.maxCal)
        return view
    }
    private fun roundOffDecimal(number: Double): Double? {
        val df = DecimalFormat("#.##")
        df.roundingMode = RoundingMode.CEILING
        return df.format(number).toDouble()
    }

    companion object {
        // TODO: do this

    }
}