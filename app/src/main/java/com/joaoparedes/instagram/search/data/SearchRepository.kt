package com.joaoparedes.instagram.search.data

import com.joaoparedes.instagram.common.base.RequestCallback
import com.joaoparedes.instagram.common.model.UserAuth

class SearchRepository(private val dataSource: SearchDataSource) {

    fun fetchUsers(name: String, callback: RequestCallback<List<UserAuth>>) {
        dataSource.fetchUsers(name, object : RequestCallback<List<UserAuth>> {
            override fun onSuccess(data: List<UserAuth>) {
                callback.onSuccess(data)
            }

            override fun onFailure(message: String) {
                callback.onFailure(message)
            }

            override fun onComplete() {
                callback.onComplete()
            }

        })
    }

}