package ba.klika.androidtemplate.ui.main.country

import android.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import ba.klika.androidtemplate.BR
import ba.klika.androidtemplate.R
import ba.klika.androidtemplate.data.country.Country
import ba.klika.androidtemplate.ui.base.di.viewmodel.ViewModelKey
import ba.klika.androidtemplate.ui.base.view.BaseBoundFragment
import ba.klika.androidtemplate.ui.main.country.list.CountriesAdapter
import ba.klika.androidtemplate.ui.main.country.list.CountryRowModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import kotlinx.android.synthetic.main.fragment_countries.*

/**
 * @author Ensar Sarajčić <ensar.sarajcic@klika.ba>.
 */
class CountriesFragment : BaseBoundFragment<CountriesViewModel>() {

    override val layoutRId: Int
        get() = R.layout.fragment_countries
    override val viewModelNameRId: Int
        get() = BR.viewModel
    override val viewModelClass: Class<CountriesViewModel>
        get() = CountriesViewModel::class.java

    override fun bindToViewModel() {
        val countriesAdapter = CountriesAdapter()
        countriesList.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        countriesList.layoutManager = LinearLayoutManager(context)
        countriesList.adapter = countriesAdapter

        viewModel.countries.observe(
            this,
            Observer {
                countries ->
                countriesAdapter.refreshData(countries)
            }
        )

        countriesAdapter.setRowClickListener(object : CountryRowModel.RowClickListener {
            override fun onRowClicked(country: Country) {
                AlertDialog.Builder(context)
                    .setTitle(country.code)
                    .setMessage(country.name)
                    .show()
            }
        })
    }
}

@Module
abstract class CountriesModule {

    @Binds
    @IntoMap
    @ViewModelKey(CountriesViewModel::class)
    abstract fun provideCountriesViewModel(countriesViewModel: CountriesViewModel): ViewModel
}
