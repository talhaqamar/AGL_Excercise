package au.com.agl.kotlincats.data

import au.com.agl.kotlincats.common.Callback
import au.com.agl.kotlincats.data.model.Owner
import au.com.agl.kotlincats.data.model.OwnerItem

interface OwnerRepository {
    fun get(callback: Callback<MutableList<OwnerItem>>)
}