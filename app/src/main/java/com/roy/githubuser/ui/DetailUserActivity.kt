package com.roy.githubuser.ui

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.roy.githubuser.R
import com.roy.githubuser.adapter.SectionPagerAdapter
import com.roy.githubuser.databinding.ActivityDetailUserBinding
import com.roy.githubuser.api.User
import com.roy.githubuser.viewmodel.DetailUserViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailUserActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailUserBinding
    private lateinit var viewModel: DetailUserViewModel

    private lateinit var user: User
    private var isFavorite = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        user = intent.getParcelableExtra<User>(EXTRA_USER) as User

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        setDataDetail(user)
        setFollows(user)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun setDataDetail(user: User?) {
        showLoading(true)
        viewModel = ViewModelProvider(
            this
        )[DetailUserViewModel::class.java]
        viewModel.setUserDetail(user?.login)
        viewModel.getUserDetail().observe(this) {
            if (it != null) {
                Glide.with(this@DetailUserActivity)
                    .load(it.avatar_url)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .circleCrop()
                    .into(binding.userAvatar)
                binding.apply {
                    tvDetailName.text = it.name
                    tvUserName.text = it.login
                    tvUserLocation.text = it.location
                    tvCompany.text = it.company
                    repositoryNum.text = it.public_repos.toString()
                    followersNum.text = it.followers.toString()
                    followingNum.text = it.following.toString()
                }
            }
            showLoading(false)
        }


        CoroutineScope(Dispatchers.IO).launch {
            val count = viewModel.checkUser(user?.id!!)
            withContext(Dispatchers.Main) {
                if (count != null) {
                    isFavorite = if (count > 0) {
                        binding.fabFavorite.setImageResource(R.drawable.ic_favorite)
                        true
                    } else {
                        binding.fabFavorite.setImageResource(R.drawable.ic_favorite_borderline)
                        false
                    }
                }
            }
        }

        binding.fabFavorite.setOnClickListener {
            isFavorite = !isFavorite
            if (isFavorite) {
                binding.fabFavorite.setImageResource(R.drawable.ic_favorite)
                viewModel.addToFavorite(user?.login.toString(),
                    user?.id!!,
                    user.avatar_url.toString())
                showToast(user.login + " " + getString(R.string.added_to_favorite))
            } else {
                binding.fabFavorite.setImageResource(R.drawable.ic_favorite_borderline)
                viewModel.removeFromFavorite(user?.id!!)
                showToast(user.login + " " + getString(R.string.removed_from_favorite))
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun setFollows(user: User?) {
        val sectionPagerAdapter = SectionPagerAdapter(this)
        sectionPagerAdapter.username = user?.login
        val viewPager: ViewPager2 = binding.viewPager
        viewPager.adapter = sectionPagerAdapter
        val tabs: TabLayout = binding.tabs
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()
        supportActionBar?.elevation = 0f
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    companion object {
        const val EXTRA_USER = "extra_user"

        @StringRes
        private val TAB_TITLES = intArrayOf(R.string.tab_1, R.string.tab_2)
    }
}

