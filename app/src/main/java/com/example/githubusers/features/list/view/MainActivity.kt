package com.example.githubusers.features.list.view

import android.content.Intent
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubusers.R
import com.example.githubusers.data.db.entities.User
import com.example.githubusers.features.list.viewmodel.MainActivityViewModel
import com.example.githubusers.features.list.viewmodel.MainActivityViewModelFactory
import com.example.githubusers.features.user.view.UserDetailsActivity
import com.example.githubusers.util.Listener
import com.example.githubusers.util.toast
import kotlinx.android.synthetic.main.activity_main.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance
import java.util.*
import kotlin.collections.ArrayList

/*
* to make our dependencies accessible
* we need to make our class Kodein aware
* by implementing the KodeinAware interface */
class MainActivity : AppCompatActivity(), UsersAdapter.Interaction, KodeinAware, Listener {

    //override the kodein variable and get our dependency by instance
    override val kodein by kodein()
    private val mFactory: MainActivityViewModelFactory by instance()

    private var mViewModel: MainActivityViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initViews()
    }

    // region initViews
    private fun initViews(){
        mViewModel = ViewModelProviders.of(this, mFactory).get(MainActivityViewModel::class.java)
        mViewModel?.listener = this
        mViewModel?.getUsers()!!
    }
    //end region initViews

    private fun fillAdapter(users: ArrayList<User>){
        rv_users.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        val usersAdapter = UsersAdapter(users, this, this)

        rv_users.adapter = usersAdapter
    }

    //recylerview item clickListener
    override fun onItemClick(id: Int) {
        Intent(this, UserDetailsActivity::class.java).also {
            it.putExtra("userId", id)
            startActivity(it)
        }
    }

    //localization //////
    fun changeLanguage(view: View) {
        view as Button
        val locale = Locale(view.text.toString())
        Locale.setDefault(locale)

        val configuration = Configuration()
        configuration.locale = locale

        baseContext.resources.updateConfiguration(
            configuration,
            baseContext.resources.displayMetrics
        )

        //restart app
        Intent(this@MainActivity, MainActivity::class.java).also {
            startActivity(it)
            finish()
        }
    }
    //end localization /////

    override fun onFailure(message: String) {
        toast(message)
    }

    override fun onSuccess(user: Any?) {
        user as ArrayList<User>
        fillAdapter(user)
    }

}