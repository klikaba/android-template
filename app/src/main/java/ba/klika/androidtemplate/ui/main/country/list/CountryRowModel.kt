package ba.klika.androidtemplate.ui.main.country.list

import ba.klika.androidtemplate.data.country.Country

/**
 * @author Ensar Sarajčić <ensar.sarajcic@klika.ba>.
 */
class CountryRowModel(private val country: Country, private val rowClickListener: RowClickListener?) {

    val name: String
        get() = country.name

    val code: String
        get() = country.code

    fun onClick() {
        rowClickListener?.onRowClicked(country)
    }

    interface RowClickListener {

        fun onRowClicked(country: Country)

    }
}
