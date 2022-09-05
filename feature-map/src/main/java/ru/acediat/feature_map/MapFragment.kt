package ru.acediat.feature_map

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.CameraPosition
import com.yandex.mapkit.map.MapObject
import ru.acediat.core_android.BaseFragment
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
        addPlacemarksObserver(viewLifecycleOwner, ::onPlacemarksReceived)
    }

    override fun prepareViews(): Unit = with(binding){
        mapView.map.move(CameraPosition(viewModel.MPEI_POINT, 16f, 0f, 0f))
        buildings.setOnClickListener { viewModel.getBuildingsMarks() }
        food.setOnClickListener { viewModel.getFoodPlacemarks() }
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

    private fun onPlacemarksReceived(placemarks: ArrayList<Placemark>) =
        binding.mapView.setPlacemarks(placemarks, ::onPlacemarkClick)

    private fun onPlacemarkClick(mapObject: MapObject, point: Point){
        val p = viewModel.findPlacemark(point)
        Toast.makeText(requireContext(), p?.description ?: "empty point", Toast.LENGTH_SHORT).show()
        //TODO: сделать отображение подробного описания места, диалоговое окно или что-то типа того
    }
}