package com.joaoparedes.instagram.profile.presenter

import android.util.Patterns
import com.joaoparedes.instagram.R
import com.joaoparedes.instagram.common.base.RequestCallback
import com.joaoparedes.instagram.common.model.Database
import com.joaoparedes.instagram.common.model.Post
import com.joaoparedes.instagram.common.model.User
import com.joaoparedes.instagram.common.model.UserAuth
import com.joaoparedes.instagram.profile.Profile
import com.joaoparedes.instagram.profile.data.ProfileRepository
import java.lang.RuntimeException

class ProfilePresenter(
    private var view: Profile.View?,
    private var repository: ProfileRepository
) : Profile.Presenter {

    override fun fetchUserProfile(uuid: String?){
        view?.showProgress(true)
        repository.fetchUserProfile(uuid, object : RequestCallback<Pair<User, Boolean?>> {
            override fun onSuccess(data: Pair<User, Boolean?>) {
                view?.displayUserProfile(data)
            }

            override fun onFailure(message: String) {
                view?.displayRequestFailure(message)
            }

            override fun onComplete() {
            }

        })
    }

    override fun fetchUserPosts(uuid: String?) {
        repository.fetchUserPosts(uuid, object : RequestCallback<List<Post>> {
            override fun onSuccess(data: List<Post>) {
                if (data.isEmpty()) {
                    view?.displayEmptyPosts()
                } else {
                    view?.displayFullPosts(data)
                }
            }

            override fun onFailure(message: String) {
                view?.displayRequestFailure(message)
            }

            override fun onComplete() {
                view?.showProgress(false)
            }

        })
    }

    override fun followUser(uuid: String?, follow: Boolean) {
        repository.followUser(uuid, follow, object : RequestCallback<Boolean> {
            override fun onSuccess(data: Boolean) {
                fetchUserProfile(uuid)

                if(data) {
                    view?.followUpdated()
                }
            }

            override fun onFailure(message: String) { }

            override fun onComplete() { }

        })
    }

    override fun clear() {
        repository.clearCache()
    }


    override fun onDestroy() {
        view = null
    }


}