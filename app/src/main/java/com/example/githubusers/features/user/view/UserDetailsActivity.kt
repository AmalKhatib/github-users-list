package com.example.githubusers.features.user.view

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Html
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.example.githubusers.R
import com.example.githubusers.data.db.entities.User
import com.example.githubusers.databinding.ActivityUserDetailsBinding
import com.example.githubusers.features.user.viewmodel.UserDetailsViewModel
import com.example.githubusers.features.user.viewmodel.UserDetailsViewModelFactory
import com.example.githubusers.util.Listener
import com.example.githubusers.util.toast
import kotlinx.android.synthetic.main.activity_user_details.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance

/*
* to make our dependencies accessible
* we need to make our class Kodein aware
* by implementing the KodeinAware interface */
class UserDetailsActivity : AppCompatActivity(), KodeinAware, Listener {

    //override the kodein variable and get our dependency by instance
    override val kodein by kodein()
    private val mFactory: UserDetailsViewModelFactory by instance()

    private var mViewModel: UserDetailsViewModel? = null
    private var mBinding: ActivityUserDetailsBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_user_details)

        initViews()
    }

    // region initViews
    private fun initViews(){
        val userId = intent.getIntExtra("userId", 1)

        mViewModel = ViewModelProviders.of(this, mFactory).get(UserDetailsViewModel::class.java)
        mViewModel?.listener = this
        mViewModel?.getUserDetails(userId)
    }
    //end region initViews

    //region assigning values
    private fun bindViews(user: User){
        mBinding?.user = user

        //the following data cannot be bind via dbinding in the XML because it is customized..
        Glide.with(this).load(user.avatar_url).into(img_user)
        tv_followers.text = Html.fromHtml("<b>" + user.followers + "</b> " + getString(R.string.followers))
        tv_following.text = Html.fromHtml("<b>" + user.following + "</b> " + getString(R.string.following))
        tv_repo.text = Html.fromHtml("<b>" + user.public_repos + "</b> " +getString(R.string.repo))
        tv_gists.text = Html.fromHtml("<b>" + user.public_gists + "</b> " + getString(R.string.gists))
    }
    //end region assigning values

    //region click handlers ///////
    fun onTwitterClicked(view: View){
        val uri: Uri = Uri.parse("https://twitter.com/" + mBinding?.user?.twitter_username) // missing 'http://' will cause crashed
        val intent = Intent(Intent.ACTION_VIEW, uri)
        startActivity(intent)
    }

    fun onGithubUsernameClicked(view: View){
        val uri: Uri = Uri.parse(mBinding?.user?.html_url) // missing 'http://' will cause crashed
        val intent = Intent(Intent.ACTION_VIEW, uri)
        startActivity(intent)
    }

    fun onEmailClicked(view: View){
        val email = Intent(Intent.ACTION_SEND)
        email.putExtra(Intent.EXTRA_EMAIL, arrayOf(mBinding?.user?.email))
        email.type = "message/rfc822"
    }

    fun onBlogClicked(view: View){
        val uri: Uri = Uri.parse(mBinding?.user?.blog) // missing 'http://' will cause crashed
        val intent = Intent(Intent.ACTION_VIEW, uri)
        startActivity(intent)
    }
    // end region click listeners ////////

    override fun onFailure(message: String) {
        toast(message)
    }

    //when data is invoked, bind the values to the views
    override fun onSuccess(user: Any?) {
        bindViews(user as User)
    }
    //end region click handlers
}