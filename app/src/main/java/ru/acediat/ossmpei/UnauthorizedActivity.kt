package ru.acediat.ossmpei

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.yandex.mapkit.MapKitFactory
import ru.acediat.core_android.YANDEX_MAP_API_KEY
import ru.acediat.feature_map.MapFragment
import ru.acediat.feature_timetable.TimetableFragment
import ru.acediat.ossmpei.databinding.ActivityUnauthorizedBinding

const val MODE_EXTRA = "mode"
const val MAPS = "maps"
const val TIMETABLE = "timetable"

class UnauthorizedActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUnauthorizedBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUnauthorizedBinding.inflate(layoutInflater)
        intent.extras?.getString(MODE_EXTRA)?.let {
            when(it){
                MAPS -> addFragment(MapFragment::class.java)
                TIMETABLE -> addFragment(TimetableFragment::class.java)
                else -> {}
            }
        }
        setContentView(R.layout.activity_unauthorized)
    }

    private fun addFragment(fragment: Class<out Fragment>) = supportFragmentManager.beginTransaction()
        .add(R.id.container, fragment, null)
        .commit()
}