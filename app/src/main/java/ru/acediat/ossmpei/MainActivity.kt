package ru.acediat.ossmpei

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.yandex.mapkit.MapKitFactory
import ru.acediat.core_android.BottomNavigationHolder
import ru.acediat.core_android.YANDEX_MAP_API_KEY
import ru.acediat.core_navigation.ACTIVITY_AUTH
import ru.acediat.core_navigation.ActivityNavigator
import ru.acediat.ossmpei.auth.AuthActivity
import ru.acediat.ossmpei.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), BottomNavigationHolder, ActivityNavigator {

    private val binding : ActivityMainBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val navController : NavController by lazy { findNavController(R.id.navFragment) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.mainBottomNavigation.setupWithNavController(navController)
    }

    override fun getBottomNavigationView(): BottomNavigationView = binding.mainBottomNavigation

    override fun navigate(id: Int) = when(id){
        ACTIVITY_AUTH -> startActivity(Intent(this, AuthActivity::class.java))
        else -> throw IllegalArgumentException()
    }
}