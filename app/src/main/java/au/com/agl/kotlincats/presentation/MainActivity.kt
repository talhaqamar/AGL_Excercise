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

                var onePetList: MutableList<OwnerItem> = mutableListOf() // We will get concurrent exception if we manipulate on data

                data.forEachIndexed { index, element -> // Tbh it was easier for me to display the list with other animals hidden but cleaner way is to remove other animals
                    val filteredList = data[index].pets?.filter { it.type.equals("Cat") } // only returns pets list
//                    print(filteredList)
                    filteredList?.forEachIndexed { index1, pet ->
                        data[index].pets?.clear() // clearing other types of animals as we only focusing on cats
                        var ownerItem = data[index] // after clearing pets array we will add only cat object
                        ownerItem.pets?.clear() //  Not needed but just making sure
                        ownerItem.pets?.add(pet) // added cat object
                        onePetList.add(ownerItem) // add the cat and now we have three separate records of cats as in the case of  Fred
                    }
                }

                Log.d("Main", "" + onePetList.size);
                val sortedByGender = onePetList.sortedBy { it.gender } // Sort the data based on Gender
                Log.d("Main", "" + sortedByGender.size);

                sortedByGender.forEach{
                    Log.d("item", it.name + " , " + it.gender +  " , " + it.pets?.get(0)?.type + " , " + it.pets?.get(0)?.name)
                    // Just printing data for displaying in recyclerview We now need to get only male/female and display in sections
                }
            }

            override fun onError(error: Throwable) {
                Log.e(MainActivity::class.java.simpleName, error.message ?: "Unknown error")
            }
        })
    }
}
