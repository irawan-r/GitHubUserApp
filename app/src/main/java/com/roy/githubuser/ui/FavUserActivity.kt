package com.roy.githubuser.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.roy.githubuser.R
import com.roy.githubuser.adapter.UserAdapter
import com.roy.githubuser.databinding.ActivityFavUserBinding
import com.roy.githubuser.api.User
import com.roy.githubuser.db.model.FavoriteUser
import com.roy.githubuser.viewmodel.FavUserViewModel

class FavUserActivity : AppCompatActivity() {
    private lateinit var adapter: UserAdapter
    private lateinit var viewModel: FavUserViewModel
    private lateinit var binding: ActivityFavUserBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = getString(R.string.title_fav_user)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        adapter = UserAdapter()
        showRecyclerList()
        faViewModel()
    }

    private fun faViewModel() {
        adapter.notifyDataSetChanged()
        viewModel = ViewModelProvider(this)[FavUserViewModel::class.java]
        viewModel.getFavoriteUser().observe(this) {
            if (it != null) {
                val list = mapList(it)
                adapter.setList(list)
            } else {
                binding.logEmptyFav.visibility = View.VISIBLE
            }
        }
    }

    private fun mapList(users: List<FavoriteUser>): ArrayList<User> {
        val listUsers = ArrayList<User>()
        for (user in users) {
            val userMapped = User(
                user.id,
                user.login,
                user.avatar_url)
            listUsers.add(userMapped)
        }
        if (listUsers.size > 0) {
            binding.logEmptyFav.visibility = View.GONE
        } else {
            binding.logEmptyFav.visibility = View.VISIBLE
        }
        return listUsers
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun showRecyclerList() {
        binding.apply {
            rvFav.adapter = adapter
            rvFav.setHasFixedSize(true)
            rvFav.layoutManager = LinearLayoutManager(this@FavUserActivity)
        }

        adapter.setOnItemClickCallback(object : UserAdapter.OnItemCallback {
            override fun onItemClicked(data: User) {
                showSelectedUser(data)
            }
        })
    }

    private fun showSelectedUser(user: User) {
        val intent = Intent(this@FavUserActivity, DetailUserActivity::class.java)
        intent.putExtra(DetailUserActivity.EXTRA_USER, user)
        startActivity(intent)
    }
}