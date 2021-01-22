package com.martafode.feature.main.ui.rates.view

import com.martafode.feature.main.BR
import com.martafode.feature.main.R
import com.martafode.lib.ui.DataBindingAdapter
import com.martafode.lib.ui.DataBindingViewHolder

class QuotesAdapter : DataBindingAdapter<String>() {

    override fun onBindViewHolder(holder: DataBindingViewHolder<String>, position: Int) {
        super.onBindViewHolder(holder, position)
        holder.binding.setVariable(BR.item, holder.bindingItem)
        holder.binding.executePendingBindings()
    }

    override fun getItemViewType(position: Int): Int = R.layout.item_quote
}
