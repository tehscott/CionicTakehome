package com.tehscott.cionictakehome.feed

import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.UiThread
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.tehscott.cionictakehome.R
import com.tehscott.cionictakehome.adapters.FeedListAdapter
import com.tehscott.cionictakehome.databinding.DetailViewBinding
import com.tehscott.cionictakehome.databinding.FeedListFragmentBinding
import com.tehscott.cionictakehome.models.local.FeedItem
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FeedListFragment : Fragment() {
    private lateinit var binding: FeedListFragmentBinding
    private val adapter: FeedListAdapter = FeedListAdapter { feedItem ->
        showFeedItem(feedItem)
    }
    private val viewModel: FeedListViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FeedListFragmentBinding.inflate(layoutInflater, container, false)

        binding.list.adapter = adapter

        binding.swipeRefreshLayout.setOnRefreshListener {
            viewModel.fetchFeed()
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.feed.observe(viewLifecycleOwner) { feed ->
            handleFeed(feed)
        }

        binding.swipeRefreshLayout.isRefreshing = true
        viewModel.fetchFeed()
    }

    private fun handleFeed(feed: List<FeedItem>?) {
        binding.swipeRefreshLayout.isRefreshing = true

        if (feed != null) {
            adapter.submitList(feed)

            binding.list.isVisible = feed.isNotEmpty()
        } else {
            showErrorDialog()
        }

        binding.swipeRefreshLayout.isRefreshing = false
    }

    @UiThread
    private fun showErrorDialog() {
        MaterialAlertDialogBuilder(requireContext())
            .setMessage(getString(R.string.error_message))
            .setPositiveButton(getString(R.string.ok)) { dialog, _ ->
                dialog.dismiss()
            }
            .setNegativeButton(getString(R.string.retry)) { dialog, _ ->
                dialog.dismiss()
                viewModel.fetchFeed()
            }
            .create()
            .show()
    }

    private fun showFeedItem(feedItem: FeedItem) {
        val detailView = DetailViewBinding.inflate(layoutInflater)
        detailView.title.text = feedItem.title
        detailView.body.text = feedItem.body
        detailView.body.movementMethod = ScrollingMovementMethod()

        AlertDialog.Builder(requireContext())
            .setView(detailView.root)
            .setPositiveButton(getString(R.string.dismiss)) { _,_ -> }
            .create()
            .show()
    }
}