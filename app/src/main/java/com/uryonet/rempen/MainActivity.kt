package com.uryonet.rempen

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.fuel.moshi.responseObject
import com.uryonet.rempen.model.entity.DirName
import com.uryonet.rempen.model.entity.FileUrl
import com.uryonet.rempen.model.entity.PhotoList
import com.uryonet.rempen.model.entity.PhotoListItem
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var photoUrlList: MutableList<PhotoListItem> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            getPhotoListData()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun getPhotoListData() {
        "http://192.168.0.1/v1/photos".httpGet().responseObject<PhotoList>{ req, res, result ->
            val(photoList, err) = result
            println(photoList?.errCode)
            photoList?.dirs?.forEach {
                val dirName: DirName = DirName(it.name)
                photoUrlList.add(dirName)
                it.files.forEach {
                    val fileUrl: FileUrl = FileUrl(it)
                    photoUrlList.add(fileUrl)
                }
            }
            println(photoUrlList.size)
        }
    }
}
