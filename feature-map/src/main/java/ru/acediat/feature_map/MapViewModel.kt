package ru.acediat.feature_map

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import com.yandex.mapkit.geometry.Point
import io.reactivex.rxjava3.disposables.Disposable
import ru.acediat.core_android.BaseViewModel
import javax.inject.Inject

class MapViewModel: BaseViewModel() {

    val MPEI_POINT = Point(55.755373, 37.708753)

    @Inject lateinit var repository: MapRepository

    private val placemarks = MutableLiveData<ArrayList<Placemark>>()

    fun addPlacemarksObserver(lifecycleOwner: LifecycleOwner, observer: (ArrayList<Placemark>) -> Unit) =
        placemarks.observe(lifecycleOwner, observer)

    fun getBuildingsMarks(): Disposable = repository.getBuidings()
        .subscribe({
            placemarks.postValue(it)
        }, {})

    fun getFoodPlacemarks(): Disposable = repository.getFood()
        .subscribe({
            placemarks.postValue(it)
        }, {})
}