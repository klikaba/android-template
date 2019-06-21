package ba.klika.androidtemplate.ui.main.country

import androidx.lifecycle.MutableLiveData
import ba.klika.androidtemplate.data.country.CountriesRepository
import ba.klika.androidtemplate.data.country.Country
import ba.klika.androidtemplate.scheduling.SchedulingProvider
import ba.klika.androidtemplate.ui.base.viewmodel.BaseViewModel
import javax.inject.Inject

/**
 * @author Ensar Sarajčić <ensar.sarajcic@klika.ba>.
 */
class CountriesViewModel
@Inject constructor(
    private val countriesRepository: CountriesRepository,
    schedulingProvider: SchedulingProvider
) : BaseViewModel(schedulingProvider) {

    val countries = MutableLiveData<List<Country>>()

    init {
        countriesRepository.all().asIOCall()
                .subscribe(
                        {
                            countries.postValue(it)
                        },
                        {
                            it.printStackTrace()
                        }
                ).disposeOnClear()
    }
}
