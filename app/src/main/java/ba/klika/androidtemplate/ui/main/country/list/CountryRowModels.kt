package ba.klika.androidtemplate.ui.main.country.list

import ba.klika.androidtemplate.data.country.Country
import java.util.ArrayList

/**
 * @author Ensar Sarajčić <ensar.sarajcic@klika.ba>.
 */
fun List<Country>.toRowModels(rowClickListener: CountryRowModel.RowClickListener): List<CountryRowModel> {
    val countryRowModels = ArrayList<CountryRowModel>()

    for (country in this) {
        countryRowModels.add(country.toRowModel(rowClickListener))
    }
    return countryRowModels
}

fun Country.toRowModel(rowClickListener: CountryRowModel.RowClickListener): CountryRowModel {
    return CountryRowModel(this, rowClickListener)
}
