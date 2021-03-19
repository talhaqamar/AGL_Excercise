package au.com.agl.kotlincats.domain

import au.com.agl.kotlincats.common.Callback
import au.com.agl.kotlincats.data.model.Owner
import au.com.agl.kotlincats.data.model.OwnerItem

interface MainFacade {
    fun loadGroupedCats(callback: Callback<MutableList<OwnerItem>>)
}