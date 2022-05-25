package com.yo.bronim.models

import com.yo.bronim.R

data class Kitchen(
    val name: String,
    val icon: Int,
)

val kitchens = arrayOf(
    Kitchen("японская", R.drawable.ic_japan),
    Kitchen("русская", R.drawable.ic_rus),
    Kitchen("итальянская", R.drawable.ic_ital),
    Kitchen("китайская", R.drawable.ic_china),
    Kitchen("грузинская", R.drawable.ic__georg),
    Kitchen("французская", R.drawable.ic_french),
    Kitchen("американская", R.drawable.ic_american),
    Kitchen("мексиканская", R.drawable.ic_mexico),
    Kitchen("греческая", R.drawable.ic_greek),
)
