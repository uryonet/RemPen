package com.uryonet.rempen.presentation.contract

interface PhotoListContract {

    interface View {
        fun displayPhotoList()
        fun displayError()
    }

    interface Presenter {
        fun getPhotoList()
    }

}
