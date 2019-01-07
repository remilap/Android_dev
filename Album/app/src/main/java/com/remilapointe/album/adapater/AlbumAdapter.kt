package com.remilapointe.album.adapater

import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import com.remilapointe.album.entity.Album
import com.remilapointe.album.model.AlbumViewHolder

/**
 * A simple PagedListAdapter that binds Cheese items into CardViews.
 * <p>
 * PagedListAdapter is a RecyclerView.Adapter base class which can present the content of PagedLists
 * in a RecyclerView. It requests new pages as the user scrolls, and handles new PagedLists by
 * computing list differences on a background thread, and dispatching minimal, efficient updates to
 * the RecyclerView to ensure minimal UI thread work.
 * <p>
 * If you want to use your own Adapter base class, try using a PagedListAdapterHelper inside your
 * adapter instead.
 *
 * @see android.arch.paging.PagedListAdapter
 * @see android.arch.paging.AsyncPagedListDiffer
 */
class AlbumAdapter(val clickListener: ((Album) -> Unit)?) : PagedListAdapter<Album, AlbumViewHolder>(diffCallback) {

    override fun onBindViewHolder(holder: AlbumViewHolder, position: Int) {
        holder.bindTo(getItem(position)!!, clickListener!!)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumViewHolder =
        AlbumViewHolder(parent)

    companion object {
        /**
         * This diff callback informs the PagedListAdapter how to compute list differences when new
         * PagedLists arrive.
         * <p>
         * When you add a Cheese with the 'Add' button, the PagedListAdapter uses diffCallback to
         * detect there's only a single item difference from before, so it only needs to animate and
         * rebind a single view.
         *
         * @see android.support.v7.util.DiffUtil
         */
        private val diffCallback = object : DiffUtil.ItemCallback<Album>() {
            override fun areItemsTheSame(oldItem: Album, newItem: Album): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Album, newItem: Album): Boolean =
                oldItem == newItem
        }
    }

}
