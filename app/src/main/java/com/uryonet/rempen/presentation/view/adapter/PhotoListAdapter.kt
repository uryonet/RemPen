package com.uryonet.rempen.presentation.view.adapter

import android.app.Service
import android.graphics.Point
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.uryonet.rempen.R
import com.uryonet.rempen.app.App
import com.uryonet.rempen.model.entity.DirName
import com.uryonet.rempen.model.entity.FileUrl
import com.uryonet.rempen.model.entity.PhotoListItem


class PhotoListAdapter(val photoUrlList: MutableList<PhotoListItem>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    class DirNameHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvTitle: TextView = view.findViewById(R.id.tvTitle)
    }

    class FileUrlHolder(view: View) : RecyclerView.ViewHolder(view) {
        val ivPhoto: ImageView = view.findViewById(R.id.ivPhoto)
        val tvPhoto: TextView = view.findViewById(R.id.tvPhoto)
    }

    companion object {
        private const val HEADER_ITEM = 0
        private const val URL_ITEM = 1
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(p0.context)
        return when (p1) {
            HEADER_ITEM -> DirNameHolder(inflater.inflate(R.layout.section_header, p0, false))
            URL_ITEM -> FileUrlHolder(inflater.inflate(R.layout.row_photo, p0, false))
            else -> FileUrlHolder(inflater.inflate(R.layout.row_photo, p0, false))
        }
    }

    override fun onBindViewHolder(p0: RecyclerView.ViewHolder, p1: Int) {
        val photoListItem: PhotoListItem = photoUrlList.get(p1)
        when (p0) {
            is DirNameHolder -> {
                val dirName = photoListItem as DirName
                p0.tvTitle.setText(dirName.dirName)
            }
            is FileUrlHolder -> {
                val fileUrl = photoListItem as FileUrl
                val gridWidth = getGridWidth()
                val photoPath = fileUrl.dirName + "/" + fileUrl.fileUrl
                p0.tvPhoto.setText(photoPath)
                Glide.with(App.instance).load("http://192.168.0.1/v1/photos/" + photoPath + "?size=thumb").apply(
                    RequestOptions().override(gridWidth, gridWidth)
                ).into(p0.ivPhoto)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        val photoListItem: PhotoListItem = photoUrlList.get(position)
        return when (photoListItem) {
            is DirName -> HEADER_ITEM
            is FileUrl -> URL_ITEM
            else -> super.getItemViewType(position)
        }
    }

    override fun getItemCount(): Int {
        return photoUrlList.size
    }

    private fun getGridWidth(): Int {
        val wm = App.instance.getSystemService(Service.WINDOW_SERVICE) as WindowManager
        val disp = wm.defaultDisplay
        val size = Point()
        disp.getSize(size)

        val screenWidth = size.x
        return screenWidth / 3 - 6
    }

}
