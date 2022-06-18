package com.deanu.githubuser

import androidx.navigation.Navigation
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.deanu.githubuser.searchuser.presentation.SearchUserFragment
import com.google.common.truth.Truth.assertThat
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@HiltAndroidTest
class SearchPageNavigationTest {

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
        launchFragmentInHiltContainer<SearchUserFragment> {
            navController.setGraph(R.navigation.nav_graph)
            navController.setCurrentDestination(R.id.searchUserFragment)
            this.viewLifecycleOwnerLiveData.observeForever { viewLifecycleOwner ->
                if (viewLifecycleOwner != null) {
                    Navigation.setViewNavController(this.requireView(), navController)
                }
            }
        }
        onView(withId(R.id.favorite_user)).perform(click())

        // Then
        assertThat(navController.currentDestination?.id).isEqualTo(R.id.favoriteUserFragment)
    }
}