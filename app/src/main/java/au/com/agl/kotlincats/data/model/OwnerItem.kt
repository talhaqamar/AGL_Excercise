package au.com.agl.kotlincats.data.model

data class OwnerItem(
    val age: Int,
    val gender: String,
    val name: String,
    val pets: MutableList<Pet>?
)