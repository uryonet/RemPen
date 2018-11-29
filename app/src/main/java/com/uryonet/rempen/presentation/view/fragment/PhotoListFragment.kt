package com.uryonet.rempen.presentation.view.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.fuel.moshi.responseObject
import com.uryonet.rempen.R
import com.uryonet.rempen.model.entity.DirName
import com.uryonet.rempen.model.entity.FileUrl
import com.uryonet.rempen.model.entity.PhotoList
import com.uryonet.rempen.model.entity.PhotoListItem
import com.uryonet.rempen.presentation.view.adapter.PhotoListAdapter
import kotlinx.android.synthetic.main.fragment_photo_list.*

class PhotoListFragment : Fragment() {

    var photoUrlList: MutableList<PhotoListItem> = mutableListOf()

    companion object {
        private const val TAG = "PhotoListFragment"

        fun newInstance(): PhotoListFragment {
            return PhotoListFragment()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_photo_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        getPhotoList()
    }

    private fun initRecyclerView() {
        val glm = GridLayoutManager(context, 3)
        glm.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(p0: Int): Int {
                return when (photoUrlList.get(p0)) {
                    is DirName -> 3
                    else -> 1
                }
            }
        }
        rvPhotoList.layoutManager = glm
    }

    private fun getPhotoList() {
        "http://192.168.0.1/v1/photos".httpGet().responseObject<PhotoList>{ req, res, result ->
            val(photoList, err) = result
            println(photoList?.errCode)
            photoList?.dirs?.forEach {
                val dirString = it.name
                val dirName: DirName = DirName(dirString)
                photoUrlList.add(dirName)
                it.files.forEach {
                    val fileUrl: FileUrl = FileUrl(dirString, it)
                    photoUrlList.add(fileUrl)
                }
            }
            println(photoUrlList.size)
            displayPhotoList()
        }
    }

    private fun displayPhotoList() {
        println("displayphotolist")
        if(!photoUrlList.isEmpty()) {
            println("run photourllist")
            rvPhotoList.adapter = PhotoListAdapter(photoUrlList)
        }
    }

}
