package gal.uvigo.mobileTaskManager.ui

import android.os.Bundle
import android.view.MenuItem
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.onNavDestinationSelected
import androidx.navigation.ui.setupWithNavController
import gal.uvigo.mobileTaskManager.R
import gal.uvigo.mobileTaskManager.databinding.ActivityMainBinding

/**
 * The App´s main (and only) activity.
 */
class MainActivity : AppCompatActivity() {

    /**
     * Binding for the layout
     */
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        val navController = findNavController(R.id.fragHost)

        //Configures menus
        val appBarConfiguration = AppBarConfiguration(navController.graph)
        //Theme has .NoActionBar, configures toolbar as the ActionBar so TaskList can inflate the + menu option
        setSupportActionBar(binding.toolbar)
        binding.toolbar.setupWithNavController(navController, appBarConfiguration)
    }

}