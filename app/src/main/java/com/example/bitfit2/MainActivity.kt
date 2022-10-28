package com.example.bitfit2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.util.Log
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.bottomnavigation.BottomNavigationItemView
import com.google.android.material.bottomnavigation.BottomNavigationMenuView
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    lateinit var addFoodButton: Button
    lateinit var btmNavView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //f1 = fragment 1 for fragment 1
        val f1: Fragment = ListFragment()
        val f2: Fragment = Summary()
        addFoodButton = findViewById(R.id.addFood)
        addFoodButton.setOnClickListener{
            val intent = Intent(this, FoodActivity::class.java)

            this.startActivity(intent)
        }

        btmNavView = findViewById(R.id.bottomNavigationView)
        btmNavView.setOnItemSelectedListener { item ->
            lateinit var fragment: Fragment
            when(item.itemId){
                R.id.listBtn -> fragment = f1
                R.id.sumBtn -> fragment = f2
            }
            replacementFragment(fragment)
            true
        }
        btmNavView.selectedItemId = R.id.listBtn
    }
    private fun replacementFragment(fragment: Fragment){
        val manager = supportFragmentManager
        var transaction = manager.beginTransaction()
        transaction.replace(R.id.frame_layout, fragment)
        transaction.commit()
    }
}
