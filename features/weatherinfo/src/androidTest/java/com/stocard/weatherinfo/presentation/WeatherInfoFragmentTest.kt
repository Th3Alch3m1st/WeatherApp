package com.stocard.weatherinfo.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import androidx.test.rule.GrantPermissionRule
import com.stocard.testutil.uitest.launchFragmentInHiltContainer
import com.stocard.testutil.uitest.withIndex
import com.stocard.weatherinfo.fake.FakeLocationTrackerImpl
import com.stocard.weatherinfo.fake.FakeSettingPreferenceImpl
import com.stocard.weatherinfo.fake.FakeWeatherInfoUseCaseImpl
import dagger.hilt.android.testing.HiltAndroidRule
import com.stocard.weatherinfo.R
import com.stocard.weatherinfo.fake.FakeLocationTrackerImpl.Companion.MSG_LOCATION_ERROR
import dagger.hilt.android.testing.HiltAndroidTest
import org.hamcrest.CoreMatchers.not
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import java.lang.Thread.sleep
import javax.inject.Inject

/**
 * Created by Rafiqul Hasan
 */
@RunWith(AndroidJUnit4ClassRunner::class)
@HiltAndroidTest
class WeatherInfoFragmentTest {
	companion object {
		const val WAIT_TIME = 200L
	}

	@get:Rule(order = 0)
	var hiltRule = HiltAndroidRule(this)

	@get:Rule(order = 1)
	val taskExecutorRule = InstantTaskExecutorRule()

	@get:Rule
	val permissionRule =
		GrantPermissionRule.grant(android.Manifest.permission.ACCESS_COARSE_LOCATION)

	@Inject
	lateinit var fakeLocationTracker: FakeLocationTrackerImpl

	@Inject
	lateinit var fakePreference: FakeSettingPreferenceImpl

	@Inject
	lateinit var fakeUseCase: FakeWeatherInfoUseCaseImpl

	@Before
	fun setUp() {
		hiltRule.inject()
	}

	/*
    *   Test spec
    *   name: fragment launch correctly and current weather, hourly and daily forecast is visible.
    *   And error UI is visible
    *   steps:
    *       - [Action] launch fragment
    *       - [Assert] layout_current_weather_info view should be visible
    *       - [Assert] layout_hourly_forecast view should be visible
    *       - [Assert] layout_daily_forecast view should be visible
    *       - [Assert] view_empty view should be invisible
    */
	@Test
	fun test_initialView_displayed() {
		//open fragment
		openFragment()


		sleep(WAIT_TIME)
		//verifying cases
		onView(withId(R.id.layout_current_weather_info)).check(matches(isDisplayed()))
		onView(withId(R.id.layout_hourly_forecast)).check(matches(isDisplayed()))
		onView(withId(R.id.layout_daily_forecast)).check(matches(isDisplayed()))
		onView(withId(R.id.view_empty)).check(matches(not(isDisplayed())))
	}

	/*
     *   Test spec
     *   name: during initial loading, error may happen. UI should display error reason with retry button.
     *   steps:
     *       - [Action] mock error scenario by setting testError to true
     *       - [Assert] it should show error UI
     */
	@Test
	fun test_errorResponse_displayed() {
		//open fragment
		fakeUseCase.isTestError = true
		openFragment()

		//verifying cases
		sleep(WAIT_TIME)
		onView(withId(R.id.view_empty))
			.check(matches(isDisplayed()))
		onView(withId(com.stocard.core.R.id.tv_title))
			.check(matches(withText(FakeWeatherInfoUseCaseImpl.MSG_ERROR)))
	}

	/*
     *   Test spec
     *   name: during initial loading, error may happen. UI should display error reason with retry button. Click on retry should fetch data again.
     *   steps:
     *       - [Action] mock error scenario by setting isTestError to true
     *       - [Assert] it should show error UI
     *       - [Action] setting isTestError to false
     *       - [Action] click on try again button
     *       - [Assert] error ui should be gone and list should be visible
     */
	@Test
	fun test_errorResponse_displayed_retry_should_fetch_data() {
		//open fragment
		fakeUseCase.isTestError = true
		openFragment()

		fakeUseCase.isTestError = false
		onView(withId(com.stocard.core.R.id.btn_try_again)).perform(ViewActions.click())

		//verifying cases
		sleep(WAIT_TIME)
		onView(withId(R.id.layout_daily_forecast)).check(matches(isDisplayed()))
		onView(withId(R.id.layout_hourly_forecast)).check(matches(isDisplayed()))

	}

	/*
    *   Test spec
    *   name: Initial item loaded. UI should display 5 in daily and hourly weather info section
    *   steps:
    *       - [Action] launch fragment
    *       - [Assert] it should show 5 items in hourly weather info recyclerview
    *       - [Assert] it should show 5 items in daily weather info recyclerview
    */

	@Test
	fun test_itemLoaded_rightItemCount() {
		//open fragment
		openFragment()

		//verifying itemCount is correct
		sleep(WAIT_TIME)
		onView(withIndex(withId(R.id.rv_weather_forecast), 0)).check { view, noViewFoundException ->
			if (noViewFoundException != null) {
				throw noViewFoundException
			}
			val recyclerView = view as RecyclerView
			Assert.assertEquals(5, recyclerView.adapter?.itemCount)
		}
		onView(withIndex(withId(R.id.rv_weather_forecast), 1)).check { view, noViewFoundException ->
			if (noViewFoundException != null) {
				throw noViewFoundException
			}
			val recyclerView = view as RecyclerView
			Assert.assertEquals(5, recyclerView.adapter?.itemCount)
		}
	}

	/*
    *   Test spec
    *   name: In some cases location may return null and error UI is displayed
    *   steps:
    *       - [Action] mock null by setting isTestError to true
    *       - [Action] launch fragment
    *       - [Assert] it should show error UI
    */
	@Test
	fun test_locationIsNull_ErrorUiIsDisplayed() {
		//open fragment
		fakeLocationTracker.isTestError = true
		openFragment()

		//verifying itemCount is correct
		sleep(WAIT_TIME)
		onView(withId(R.id.view_empty)).check(matches(isDisplayed()))
		onView(withId(com.stocard.core.R.id.tv_title)).check(matches(withText(MSG_LOCATION_ERROR)))
	}

	private fun openFragment() {
		val mockNavController = Mockito.mock(NavController::class.java)
		launchFragmentInHiltContainer<WeatherInfoFragment>(
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