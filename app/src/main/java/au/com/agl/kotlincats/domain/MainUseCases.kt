package au.com.agl.kotlincats.domain

import au.com.agl.kotlincats.common.Callback
import au.com.agl.kotlincats.data.OwnerRepository
import au.com.agl.kotlincats.data.model.Owner
import au.com.agl.kotlincats.data.model.OwnerItem

class MainUseCases(private val ownerRepository: OwnerRepository): MainFacade {
    override fun loadGroupedCats(callback: Callback<MutableList<OwnerItem>>) {
        ownerRepository.get(object: Callback<MutableList<OwnerItem>> {
            override fun onSuccess(data: MutableList<OwnerItem>) {
                // TODO: manipulate data as appropriate and feed into callback
                callback.onSuccess(data)
            }

            override fun onError(error: Throwable) {
                callback.onError(error)
            }
        })
    }
}
