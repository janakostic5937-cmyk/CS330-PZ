package com.example.cs330_pz_jana_kosti_5937.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.cs330_pz_jana_kosti_5937.domain.BannerModel
import com.example.cs330_pz_jana_kosti_5937.domain.CategoryModel
import com.example.cs330_pz_jana_kosti_5937.domain.PopularModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class MainRepository {

    private val firebaseDatabase = FirebaseDatabase.getInstance(
        "https://cs330-pz-jana-kostic-5937-default-rtdb.europe-west1.firebasedatabase.app"
    )

    fun loadCategory(): LiveData<MutableList<CategoryModel>> {
        val liveData = MutableLiveData<MutableList<CategoryModel>>()
        val ref = firebaseDatabase.getReference("Category")

        ref.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val list = mutableListOf<CategoryModel>()

                for (childSnapshot in snapshot.children) {
                    val category = CategoryModel(
                        title = childSnapshot.child("title").getValue(String::class.java) ?: "",
                        id = childSnapshot.child("id").getValue(Int::class.java) ?: 0
                    )

                    if (category.title.isNotBlank()) {
                        list.add(category)
                    }
                    if (list.size == 5) {
                        break
                    }
                }

                liveData.value = list
            }

            override fun onCancelled(error: DatabaseError) {
                liveData.value = mutableListOf()
            }
        })

        return liveData
    }

    fun loadBanner(): LiveData<MutableList<BannerModel>> {
        val liveData = MutableLiveData<MutableList<BannerModel>>()
        val ref = firebaseDatabase.getReference("Banner")

        ref.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val list = mutableListOf<BannerModel>()

                for (childSnapshot in snapshot.children) {
                    val banner = BannerModel(
                        url = childSnapshot.child("url").getValue(String::class.java) ?: ""
                    )

                    if (banner.url.isNotBlank()) {
                        list.add(banner)
                    }
                }

                liveData.value = list
            }

            override fun onCancelled(error: DatabaseError) {
                liveData.value = mutableListOf()
            }
        })

        return liveData
    }

    fun loadPopular(): LiveData<MutableList<PopularModel>> {
        val liveData = MutableLiveData<MutableList<PopularModel>>()
        val ref = firebaseDatabase.getReference("Popular")

        ref.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val list = mutableListOf<PopularModel>()

                for (childSnapshot in snapshot.children) {
                    val pics = mutableListOf<String>()
                    for (picSnapshot in childSnapshot.child("picUrl").children) {
                        val url = picSnapshot.getValue(String::class.java)
                        if (!url.isNullOrBlank()) {
                            pics.add(url)
                        }
                    }

                    val popular = PopularModel(
                        title = childSnapshot.child("title").getValue(String::class.java) ?: "",
                        extra = childSnapshot.child("extra").getValue(String::class.java) ?: "",
                        description = childSnapshot.child("description").getValue(String::class.java)
                            ?: "",
                        price = childSnapshot.child("price").getValue(Double::class.java) ?: 0.0,
                        rating = childSnapshot.child("rating").getValue(Double::class.java) ?: 0.0,
                        picUrl = pics
                    )

                    if (popular.title.isNotBlank()) {
                        list.add(popular)
                    }
                }

                liveData.value = list
            }

            override fun onCancelled(error: DatabaseError) {
                liveData.value = mutableListOf()
            }
        })

        return liveData
    }

    fun loadCappuccinoItems(): LiveData<MutableList<PopularModel>> {
        val liveData = MutableLiveData<MutableList<PopularModel>>()
        val ref = firebaseDatabase.getReference("Items")
        val selectedIndexes = listOf(2, 3, 4, 5, 8, 9)

        ref.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val orderedList = mutableListOf<PopularModel>()

                for (selectedIndex in selectedIndexes) {
                    val childSnapshot = snapshot.child(selectedIndex.toString())
                    if (!childSnapshot.exists()) {
                        continue
                    }

                    val pics = mutableListOf<String>()
                    for (picSnapshot in childSnapshot.child("picUrl").children) {
                        val url = picSnapshot.getValue(String::class.java)
                        if (!url.isNullOrBlank()) {
                            pics.add(url)
                        }
                    }

                    val item = PopularModel(
                        title = childSnapshot.child("title").getValue(String::class.java) ?: "",
                        extra = childSnapshot.child("extra").getValue(String::class.java) ?: "",
                        description = childSnapshot.child("description").getValue(String::class.java)
                            ?: "",
                        price = childSnapshot.child("price").getValue(Double::class.java) ?: 0.0,
                        rating = childSnapshot.child("rating").getValue(Double::class.java) ?: 0.0,
                        picUrl = pics
                    )

                    if (item.title.isNotBlank()) {
                        orderedList.add(item)
                    }
                }

                liveData.value = orderedList
            }

            override fun onCancelled(error: DatabaseError) {
                liveData.value = mutableListOf()
            }
        })

        return liveData
    }
}
