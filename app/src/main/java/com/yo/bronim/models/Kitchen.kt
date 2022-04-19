package com.yo.bronim.models

import com.yo.bronim.R

data class Kitchen(
    val name: String,
    val icon: Int,
)

val kitchens = arrayOf(
    Kitchen("японская", R.drawable.ic_kitchen),
    Kitchen("русская", R.drawable.ic_kitchen),
    Kitchen("итальянская", R.drawable.ic_kitchen),
    Kitchen("китайская", R.drawable.ic_kitchen),
    Kitchen("грузинская", R.drawable.ic_kitchen),
    Kitchen("французская", R.drawable.ic_kitchen),
    Kitchen("американская", R.drawable.ic_kitchen),
    Kitchen("мексиканская", R.drawable.ic_kitchen),
    Kitchen("турецкая", R.drawable.ic_kitchen),
    Kitchen("тайская", R.drawable.ic_kitchen),
    Kitchen("греческая", R.drawable.ic_kitchen),
)
