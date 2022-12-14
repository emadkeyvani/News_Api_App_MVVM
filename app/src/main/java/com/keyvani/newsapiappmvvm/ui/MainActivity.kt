package com.keyvani.newsapiappmvvm.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.keyvani.newsapiappmvvm.R
import com.keyvani.newsapiappmvvm.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    //Binding
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //InitViews
        binding.apply {
            navController=findNavController(R.id.navHost)
            bottomNavigationView.setupWithNavController(navController)

            navController.addOnDestinationChangedListener{_,destination,_ ->
                if(destination.id == R.id.detailsFragment ){
                    bottomNavigationView.visibility= View.GONE
                }else{
                    bottomNavigationView.visibility= View.VISIBLE
                }
            }

        }
    }

    override fun onNavigateUp(): Boolean {
        return navController.navigateUp() || super.onNavigateUp()
    }
}