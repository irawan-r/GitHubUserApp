package com.roy.githubuser.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.roy.githubuser.R
import com.roy.githubuser.adapter.FollowersAdapter
import com.roy.githubuser.adapter.FollowingAdapter
import com.roy.githubuser.adapter.UserAdapter
import com.roy.githubuser.databinding.FragmentFollowsBinding
import com.roy.githubuser.api.User
import com.roy.githubuser.viewmodel.FollowersViewModel
import com.roy.githubuser.viewmodel.FollowingViewModel

class FollowsFragment : Fragment() {

    private var _binding: FragmentFollowsBinding? = null
    private val binding get() = _binding!!

    private lateinit var followersAdapter: FollowersAdapter
    private lateinit var followingAdapter: FollowingAdapter

    private lateinit var followersViewModel: FollowersViewModel
    private lateinit var followingViewModel: FollowingViewModel

    companion object {
        private const val ARG_USERNAME = "username"
        private const val ARG_SECTION_NUMBER = "section_number"

        fun newInstance(index: Int, username: String?): FollowsFragment {
            val fragment = FollowsFragment()
            val bundle = Bundle()
            bundle.putString(ARG_USERNAME, username)
            bundle.putInt(ARG_SECTION_NUMBER, index)

            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_follows, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentFollowsBinding.bind(view)
        val username = arguments?.getString(ARG_USERNAME)

        binding.rvFollows.setHasFixedSize(true)
        binding.rvFollows.layoutManager = LinearLayoutManager(activity)

        showLoading(true)
        if (arguments != null) {
            when (arguments?.getInt(ARG_SECTION_NUMBER, 0) ?: 1) {
                1 -> {
                    followersAdapter = FollowersAdapter()
                    binding.rvFollows.adapter = followersAdapter

                    followersViewModel = ViewModelProvider(
                        this, ViewModelProvider.NewInstanceFactory()
                    )[FollowersViewModel::class.java]
                    followersViewModel.setListFollower(username.toString())
                    followersViewModel.getListFollowers().observe(viewLifecycleOwner) { user ->
                        if (user != null) {
                            followersAdapter.setList(user)
                            showLoading(false)
                        }
                    }
                    followersAdapter.setOnItemClickCallback(object : UserAdapter.OnItemCallback {
                        override fun onItemClicked(data: User) {
                            showSelectedUser(data)
                        }
                    })
                }
                2 -> {
                    followingAdapter = FollowingAdapter()
                    binding.rvFollows.adapter = followingAdapter
                    followingViewModel = ViewModelProvider(
                        this,
                        ViewModelProvider.NewInstanceFactory()
                    )[FollowingViewModel::class.java]
                    followingViewModel.setListFollowing(username.toString())
                    followingViewModel.getListFollowing().observe(viewLifecycleOwner) { user ->
                        if (user != null) {
                            followingAdapter.setList(user)
                            showLoading(false)
                        }
                    }
                    followingAdapter.setItemClickCallback(object: UserAdapter.OnItemCallback {
                        override fun onItemClicked(data: User) {
                            showSelectedUser(data)
                        }
                    })
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun showSelectedUser(user: User) {
        val intent = Intent(context, DetailUserActivity::class.java)
        intent.putExtra(DetailUserActivity.EXTRA_USER, user)
        startActivity(intent)
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}