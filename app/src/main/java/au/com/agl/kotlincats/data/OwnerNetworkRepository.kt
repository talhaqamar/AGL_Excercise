package au.com.agl.kotlincats.data

import android.util.Log
import au.com.agl.kotlincats.common.Callback
import au.com.agl.kotlincats.data.model.Owner
import au.com.agl.kotlincats.data.model.OwnerItem
import retrofit2.Call
import retrofit2.Response

class OwnerNetworkRepository(private val ownerApi: OwnerApi): OwnerRepository {
    override fun get(callback: Callback<MutableList<OwnerItem>>) {
        ownerApi.getOwners().enqueue (object: retrofit2.Callback<MutableList<OwnerItem>>{
            override fun onResponse(call: Call<MutableList<OwnerItem>>, response: Response<MutableList<OwnerItem>>) {
                if(response.isSuccessful){
                    response.body()?.let(callback::onSuccess)
                } else {
                    Log.e(OwnerNetworkRepository::class.java.simpleName, "${response.code()} ${response.message()}")
                }
            }
            override fun onFailure(call: Call<MutableList<OwnerItem>>, t: Throwable) {
                callback.onError(t)
            }
        })
    }
}