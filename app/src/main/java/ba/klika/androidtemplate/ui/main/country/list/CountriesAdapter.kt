package ba.klika.androidtemplate.ui.main.country.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ba.klika.androidtemplate.data.country.Country
import ba.klika.androidtemplate.databinding.ItemCountryBinding
import java.util.ArrayList

/**
 * @author Ensar Sarajčić <ensar.sarajcic@klika.ba>.
 */
class CountriesAdapter : RecyclerView.Adapter<CountryViewHolder>(), CountryRowModel.RowClickListener {

    private val rowModels = ArrayList<CountryRowModel>()
    private var rowClickListener: CountryRowModel.RowClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryViewHolder {
        return CountryViewHolder(ItemCountryBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: CountryViewHolder, position: Int) {
        holder.setModel(rowModels[position])
    }

    override fun getItemCount(): Int {
        return rowModels.size
    }

    fun refreshData(countries: List<Country>) {
        this.rowModels.clear()
        this.rowModels.addAll(countries.toRowModels(this))
        this.notifyDataSetChanged()
    }

    fun setRowClickListener(rowClickListener: CountryRowModel.RowClickListener) {
        this.rowClickListener = rowClickListener
    }

    override fun onRowClicked(country: Country) {
        if (rowClickListener != null) {
            rowClickListener!!.onRowClicked(country)
        }
    }
}
