package com.example.githubusers.data.db

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.githubusers.data.db.dao.UserDao
import com.example.githubusers.data.db.entities.User
import junit.framework.TestCase
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class AppDatabaseTest : TestCase() {

    private lateinit var db : AppDatabase
    private lateinit var userDao : UserDao


    @Before
    public override fun setUp() {
        //creation of the DB -in memory-
        db = Room.inMemoryDatabaseBuilder(
                ApplicationProvider.getApplicationContext(),
                AppDatabase::class.java).build()

        userDao = db.getUserDao()

    }

    @After
    public override fun tearDown() {
        db.close()
    }

    @Test
    fun textInsertionAndSelection() = runBlocking {
        val user = User(
            "https://avatars.githubusercontent.com/u/1?v=4",
            "bio",
            "https://github.com/mojombo",
            "@chatterbugapp",
            "amal@gmail.com",
            10,
            10,
            "https://github.com/mojombo",
            1,
            "location",
            "username",
            "name",
            10,
            10,
            "twitter",
            "2021-03-18T22:50:53Z",
            "https://api.github.com/users/mojombo"
        )

        userDao.addUser(user)

        val selectedUser = userDao.getUser(1)
        //test if the specific selection works
        assertEquals(selectedUser, user)

        val users = userDao.getUsers()
        assertEquals(listOf(user), users)
    }
}