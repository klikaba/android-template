package ba.klika.androidtemplate.ui.main.country

import androidx.lifecycle.MutableLiveData
import ba.klika.androidtemplate.data.country.CountriesRepository
import ba.klika.androidtemplate.data.country.Country
import ba.klika.androidtemplate.scheduling.DispatcherProvider
import ba.klika.androidtemplate.ui.base.viewmodel.BaseViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.plus
import java.lang.Exception
import javax.inject.Inject

/**
 * @author Ensar Sarajčić <ensar.sarajcic@klika.ba>.
 */
class CountriesViewModel
@Inject constructor(
    private val countriesRepository: CountriesRepository,
    dispatcherProvider: DispatcherProvider
) : BaseViewModel(dispatcherProvider) {

    val countries = MutableLiveData<List<Country>>()

    init {
        runIo {
            try {
                countriesRepository.all().collect {
                    countries.postValue(it)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
