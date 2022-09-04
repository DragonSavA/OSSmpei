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
import ru.acediat.feature_map.di.MapComponent

class MapFragment : BaseFragment<FragmentMapBinding, MapViewModel>() {

    override val viewModel: MapViewModel = ViewModelProvider
        .NewInstanceFactory()
        .create(MapViewModel::class.java)

    override fun instanceBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentMapBinding = FragmentMapBinding.inflate(inflater, container, false)

    override fun inject() = with(MapComponent.init()){
        inject(this@MapFragment)
        inject(viewModel)
    }

    override fun prepareViewModel() = with(viewModel){
        addBuildingsObserver(viewLifecycleOwner, ::onBuildingsReceived)
    }

    override fun prepareViews(): Unit = with(binding){
        mapView.map.move(CameraPosition(viewModel.MPEI_POINT, 16f, 0f, 0f))
        viewModel.getBuildingsMarks()
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

    private fun onBuildingsReceived(buildings: ArrayList<Placemark>) = buildings.forEach {
        binding.mapView.addPlacemark(it)
    }
}