package com.uryonet.rempen.presentation.view.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import com.uryonet.rempen.model.entity.PhotoListItem

class PhotoListAdapter(photoUrlList: List<PhotoListItem>) : RecyclerView.Adapter<RecyclerView.PhotoListViewHolder> {

    companion object {
        private const val HEADER_ITEM = 0
        private const val URL_ITEM = 1
    }

}
