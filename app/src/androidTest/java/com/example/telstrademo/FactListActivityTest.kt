package com.example.telstrademo

import android.content.Intent
import androidx.appcompat.app.ActionBar
import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.example.telstrademo.ui.main.view.FactListActivity
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class FactListActivityTest {

    @get:Rule
    val activityRule = ActivityTestRule(FactListActivity::class.java, false, false)

    @Before
    fun setUp() {
        val intent = Intent()
        activityRule.launchActivity(intent)
    }

    @Test
    fun appLaunchesSuccessfully() {
        ActivityScenario.launch(FactListActivity::class.java)
    }

    @Test
    fun onLaunchCheckProgressBarIsDisplayed() {
        onView(withId(R.id.progressBar))
            .check(matches(isDisplayed()))
    }

    @Test
    fun onLaunchCheckActionBarTitleIsDisplayed() {
        val actionBar: ActionBar? = activityRule.activity.supportActionBar
        Assert.assertNotNull(actionBar?.title)
    }

    @Test
    fun onLaunchCheckRecyclerViewIsDisplayed() {
        Thread.sleep(3000)
        val recyclerView: RecyclerView = activityRule.activity.findViewById(R.id.recyclerView)
        onView(withId(R.id.recyclerView)).check(
            matches(
                isDisplayed()
            )
        )
        Assert.assertNotSame(0, recyclerView.adapter?.itemCount)
    }
}