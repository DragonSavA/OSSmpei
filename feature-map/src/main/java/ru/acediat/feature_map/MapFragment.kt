package ru.acediat.feature_map

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.yandex.mapkit.Animation
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.map.CameraPosition
import ru.acediat.core_android.BaseFragment
import ru.acediat.core_android.YANDEX_MAP_API_KEY
import ru.acediat.feature_map.databinding.FragmentMapBinding

class MapFragment : BaseFragment<FragmentMapBinding, MapViewModel>() {

    override val viewModel: MapViewModel = ViewModelProvider
        .NewInstanceFactory()
        .create(MapViewModel::class.java)

    override fun instanceBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentMapBinding = FragmentMapBinding.inflate(inflater, container, false)

    override fun inject() {
        super.inject()
    }

    override fun prepareViewModel() {

    }

    override fun prepareViews() {
        binding.mapView.map.move(
            CameraPosition(viewModel.MPEI_POINT, 16f, 0f, 0f)
        )
        //binding.mapView.map.mapObjects.addPlacemark()
    }

    override fun refresh() {
        super.refresh()
    }

    override fun onStop() {
        binding.mapView.onStop()
        MapKitFactory.getInstance().onStop()
        super.onStop()
    }

    override fun onStart() {
        binding.mapView.onStart()
        MapKitFactory.getInstance().onStart()
        super.onStart()
    }
}