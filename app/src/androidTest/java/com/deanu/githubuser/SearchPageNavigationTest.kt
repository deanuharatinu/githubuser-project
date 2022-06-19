package com.deanu.githubuser

import android.view.View
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.isRoot
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.deanu.githubuser.searchuser.presentation.SearchUserFragment
import com.google.common.truth.Truth.assertThat
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.hamcrest.Matcher
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@HiltAndroidTest
class SearchPageNavigationTest {
    private lateinit var scenario: Fragment

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Before
    fun setup() {
        hiltRule.inject()
    }

    @Test
    fun searchPageToFavorite_test() {
        // Given
        val navController = TestNavHostController(ApplicationProvider.getApplicationContext())

        // When
        launchFragment(navController)

        onView(isRoot()).perform(waitFor(700))
        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
            onView(withId(R.id.app_setting)).perform(click())
            launchFragment(navController)
        }

        onView(isRoot()).perform(waitFor(700))
        onView(withId(R.id.favorite_user)).perform(click())

        // Then
        assertThat(navController.currentDestination?.id).isEqualTo(R.id.favoriteUserFragment)
    }

    private fun waitFor(delay: Long): ViewAction {
        return object : ViewAction {
            override fun getConstraints(): Matcher<View> = isRoot()
            override fun getDescription(): String = "wait for $delay milliseconds"
            override fun perform(uiController: UiController, v: View?) {
                uiController.loopMainThreadForAtLeast(delay)
            }
        }
    }

    private fun launchFragment(navController: TestNavHostController) {
        launchFragmentInHiltContainer<SearchUserFragment> {
            navController.setGraph(R.navigation.nav_graph)
            navController.setCurrentDestination(R.id.searchUserFragment)

            this.viewLifecycleOwnerLiveData.observe(viewLifecycleOwner) { viewLifecycleOwner ->
                if (viewLifecycleOwner != null) {
                    Navigation.setViewNavController(this.requireView(), navController)
                }
            }
        }
    }

}