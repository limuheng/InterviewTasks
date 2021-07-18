package com.muheng.media17task

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import com.muheng.media17task.adapters.GithubUserAdapter
import com.muheng.media17task.viewmodels.UserListModel
import com.muheng.media17task.databinding.ActivityMainBinding

const val MAX_SPAN_COUNT = 2

class MainActivity : AppCompatActivity(), SearchView.OnQueryTextListener {

    private val Tag = MainActivity::class.java.simpleName

    lateinit var viewModel: UserListModel
    lateinit var binding: ActivityMainBinding
    lateinit var adapter: GithubUserAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Obtain ViewModel from ViewModelProviders
        viewModel = ViewModelProvider(this).get(UserListModel::class.java)

        // Obtain binding
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        binding.userList = viewModel

        binding.searchView.setOnQueryTextListener(this)

        // Handle Layout
        val layoutMgr = GridLayoutManager(this, MAX_SPAN_COUNT)
        layoutMgr.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return when (position % 10) {
                    2, 3, 6, 7 -> 1
                    else -> MAX_SPAN_COUNT
                }
            }
        }
        binding.list.layoutManager = layoutMgr
        binding.list.addItemDecoration(DividerItemDecoration(this, layoutMgr.orientation))

        // Bind layout with ViewModel
        adapter = GithubUserAdapter()
        binding.list.adapter = adapter

        // LiveData needs the lifecycle owner
        binding.lifecycleOwner = this

        // Initial search
        viewModel.setQueryString("john")
        subscribeUi(adapter)
    }

    private fun subscribeUi(adapter: GithubUserAdapter) {
        viewModel.getUsers()?.observe(this, Observer { users ->
            adapter.submitList(users)
        })
    }

    private fun unsubscribe() {
        viewModel.getUsers()?.removeObservers(this)
    }

    override fun onQueryTextSubmit(newText: String?): Boolean {
        if (newText?.isNotBlank() == true) {
            unsubscribe()
            viewModel.setQueryString(newText)
            subscribeUi(adapter)
        }
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        return true
    }
}
