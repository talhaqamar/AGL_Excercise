package au.com.agl.kotlincats.presentation

import android.app.Person
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import au.com.agl.kotlincats.R
import au.com.agl.kotlincats.common.Callback
import au.com.agl.kotlincats.data.OwnerApi
import au.com.agl.kotlincats.data.OwnerNetworkRepository
import au.com.agl.kotlincats.data.OwnerRepository
import au.com.agl.kotlincats.data.model.OwnerItem
import au.com.agl.kotlincats.data.model.Pet
import au.com.agl.kotlincats.domain.MainFacade
import au.com.agl.kotlincats.domain.MainUseCases
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val retrofit = Retrofit.Builder()
            .baseUrl("https://agl-developer-test.azurewebsites.net/")
            .addConverterFactory(MoshiConverterFactory.create(Moshi.Builder().add(KotlinJsonAdapterFactory()).build()))
            .build()
        val api = retrofit.create(OwnerApi::class.java)
        val repository: OwnerRepository = OwnerNetworkRepository(api)

        val facade: MainFacade = MainUseCases(repository)
        facade.loadGroupedCats(object : Callback<MutableList<OwnerItem>> {
            override fun onSuccess(data: MutableList<OwnerItem>) {
                // TODO: display visually
                Log.d(MainActivity::class.java.simpleName, data.toString())
                var display: MutableList<OwnerItem> = mutableListOf()
              // display = data
                data.forEachIndexed { index, element ->
                    val filteredList = data[index].pets?.filter { it.type.equals("Cat") }
                    print(filteredList)
                    filteredList?.forEachIndexed { index1, pet ->
                        data[index].pets?.clear()
                        var ownerItem = data[index]
                        ownerItem.pets?.clear()
                        ownerItem.pets?.add(pet)
                        display.add(ownerItem)
                    }
                }
                Log.d("Main", "" + display.size);
            }

            override fun onError(error: Throwable) {
                Log.e(MainActivity::class.java.simpleName, error.message ?: "Unknown error")
            }
        })
    }
}


//data[index].pets?.drop(index)?.forEachIndexed { index1, element1 ->
//    if(element1.type == "Cat")
//    {
//        Log.d("Cat", ""+ element1.name)
//        data[index1]!!.pets?.clear()
//        data[index1]!!.pets?.add(element1)
//        var ownerItem: OwnerItem = data[index1]!!
//        display.add(ownerItem)
//    }
//}