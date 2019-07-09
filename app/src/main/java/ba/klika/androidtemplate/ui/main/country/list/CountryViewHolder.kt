package ba.klika.androidtemplate.ui.main.country.list

import androidx.recyclerview.widget.RecyclerView
import ba.klika.androidtemplate.databinding.ItemCountryBinding

/**
 * @author Ensar Sarajčić <ensar.sarajcic@klika.ba>.
 */
class CountryViewHolder(private val binding: ItemCountryBinding) : RecyclerView.ViewHolder(binding.root) {

    fun setModel(rowModel: CountryRowModel) {
        binding.rowModel = rowModel
    }
}
