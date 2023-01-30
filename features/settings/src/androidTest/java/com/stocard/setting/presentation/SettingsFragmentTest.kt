package com.stocard.setting.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.stocard.setting.R
import com.stocard.testutil.uitest.launchFragmentInHiltContainer
import com.stocard.testutil.uitest.withIndex
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import java.lang.Thread.sleep

/**
 * Created by Rafiqul Hasan
 */

@RunWith(AndroidJUnit4ClassRunner::class)
@HiltAndroidTest
class SettingsFragmentTest {
	companion object {
		const val WAIT_TIME = 200L
		const val TITLE_THEME = "Theme"
		const val TITLE_UNIT = "Unit Of Measurement"
		const val SELECTED_THEME = "Dark"
		const val SELECTED_UNIT = "Imperial"
	}

	@get:Rule(order = 0)
	var hiltRule = HiltAndroidRule(this)

	@get:Rule(order = 1)
	val taskExecutorRule = InstantTaskExecutorRule()

	@Before
	fun setUp() {
		hiltRule.inject()
	}

	/*
    *   Test spec
    *   name: fragment launch correctly and theme and unit related view should be visible correctly
    *   steps:
    *       - [Action] launch fragment
    *       - [Assert] toolbar view should be visible
    *       - [Assert] ll_theme view should be visible
    *       - [Assert] ll_unit_of_measurement view should be visible
    */
	@Test
	fun test_initialView_displayed() {
		//open fragment
		openFragment()


		sleep(WAIT_TIME)
		//verifying cases
		onView(withId(com.stocard.core.R.id.toolbar))
			.check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
		onView(withId(R.id.ll_theme))
			.check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
		onView(withId(R.id.ll_unit_of_measurement))
			.check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
	}

	/*
    *   Test spec
    *   name: UI screen should show correct title and selected info
    *   steps:
    *       - [Action] launch fragment
    *       - [Assert] it should show 5 items in hourly weather info recyclerview
    *       - [Assert] it should show 5 items in daily weather info recyclerview
    */

	@Test
	fun test_view_is_showing_correct_title_and_selected_info() {
		//open fragment
		openFragment()

		//verifying itemCount is correct
		sleep(WAIT_TIME)
		onView(withIndex(withId(R.id.tv_setting_name),0) )
			.check(ViewAssertions.matches(ViewMatchers.withText(TITLE_THEME)))
		onView(withIndex(withId(R.id.tv_setting_name),1) )
			.check(ViewAssertions.matches(ViewMatchers.withText(TITLE_UNIT)))

		onView(withIndex(withId(R.id.tv_selected_setting),0) )
			.check(ViewAssertions.matches(ViewMatchers.withText(SELECTED_THEME)))
		onView(withIndex(withId(R.id.tv_selected_setting),1) )
			.check(ViewAssertions.matches(ViewMatchers.withText(SELECTED_UNIT)))
	}

	private fun openFragment() {
		val mockNavController = Mockito.mock(NavController::class.java)
		launchFragmentInHiltContainer<SettingsFragment>(
			null,
			com.stocard.style.R.style.Theme_BaseAndroidApplication
		) {
			viewLifecycleOwnerLiveData.observeForever { viewLifecycleOwner ->
				if (viewLifecycleOwner != null) {
					Navigation.setViewNavController(requireView(), mockNavController)
				}
			}
		}
	}
}