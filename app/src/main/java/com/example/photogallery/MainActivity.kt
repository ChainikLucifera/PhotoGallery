package com.example.photogallery

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.photogallery.databinding.ActivityMainBinding
import com.example.photogallery.fragments.ImageDetailsFragment
import com.example.photogallery.fragments.SavedFragment
import com.example.photogallery.fragments.SearchFragment
import com.example.photogallery.models.Result

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater).also {
            setContentView(it.root)
        }

        setupBottomNavigation()
    }

    private fun setupBottomNavigation() {
        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when(item.itemId){
                R.id.navigation_search -> {
                    showSearchFragment()
                    true
                }

                R.id.navigation_saved ->{
                    showSavedFragment()
                    true
                }

                else -> false
            }
        }
    }

    private fun showSearchFragment() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.main_content_container, SearchFragment())
            .commit()
    }

    private fun showSavedFragment() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.main_content_container, SavedFragment())
            .commit()
    }

    fun showImageDetails(image: Result) {
        val fragment = ImageDetailsFragment.newInstance(image)
        supportFragmentManager.beginTransaction()
            .setCustomAnimations(
                R.anim.fragment_slide_up,
                R.anim.fragment_slide_down,
                R.anim.fragment_slide_up,
                R.anim.fragment_slide_down
            )
            .replace(R.id.main_content_container, fragment)
            .addToBackStack(null)
            .commit()
    }
}