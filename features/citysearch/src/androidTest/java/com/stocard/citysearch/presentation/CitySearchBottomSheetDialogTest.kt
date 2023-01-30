package com.stocard.citysearch.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.pressImeActionButton
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.stocard.citysearch.fakeusecase.FakeCityUseCaseImpl
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import javax.inject.Inject
import com.stocard.citysearch.R
import com.stocard.testutil.uitest.launchFragmentInHiltContainer
import org.hamcrest.CoreMatchers.not
import org.junit.Assert.assertEquals
import java.lang.Thread.sleep


/**
 * Created by Rafiqul Hasan
 */
@RunWith(AndroidJUnit4ClassRunner::class)
@HiltAndroidTest
class CitySearchBottomSheetDialogTest {
	companion object{
		const val WAIT_TIME = 500L
	}

	@get:Rule(order = 0)
	var hiltRule = HiltAndroidRule(this)

	@get:Rule(order = 1)
	val taskExecutorRule = InstantTaskExecutorRule()

	@Inject
	lateinit var fakeCityUseCaseImpl: FakeCityUseCaseImpl

	@Before
	fun setUp() {
		hiltRule.inject()
	}

	/*
    *   Test spec
    *   name: fragment launch correctly and city list should be visible. No empty UI is present
    *   steps:
    *       - [Action] launch fragment
    *       - [Assert] rv_cities view should be visible
    *       - [Assert] view_empty view should be invisible
    */
	@Test
	fun test_initialView_displayed() {
		//open fragment
		openCitySearchBottomSheetFragment()

		//verifying cases
		onView(withId(R.id.rv_cities)).check(matches(isDisplayed()))
		onView(withId(R.id.view_empty)).check(matches(not(isDisplayed())))
	}

	/*
    *   Test spec
    *   name: fragment launch correctly and city list should be visible and city list should have 7 item
    *   steps:
    *       - [Action] launch fragment
    *       - [Assert] recycler view has 7 items
    */
	@Test
	fun test_correct_number_of_item_are_showing() {
		//open fragment
		openCitySearchBottomSheetFragment()

		//verifying itemCount is correct
		onView(withId(R.id.rv_cities)).check { view, noViewFoundException ->
			if (noViewFoundException != null) {
				throw noViewFoundException
			}
			val recyclerView = view as RecyclerView
			assertEquals(FakeCityUseCaseImpl.NUMBER_OF_ITEMS, recyclerView.adapter?.itemCount)
		}
	}

	/*
    *   Test spec
    *   name: fragment launch correctly and city list should be visible. And when "Berlin is typed it should
    *   filter the list and show two cities which matches with the typed string"
    *   steps:
    *       - [Action] launch fragment
    *       - [Action] type berlin
    *       - [Assert] two item is visible
    */
	@Test
	fun test_itemLoadedAfterTyping_correctNumberOfItemAreShowing() {
		//open fragment
		openCitySearchBottomSheetFragment()

		//creating scenario for valid response
		onView(withId(androidx.appcompat.R.id.search_src_text)).perform(
			typeText(FakeCityUseCaseImpl.TRIGGER_SEARCH),
			pressImeActionButton()
		)
		Espresso.closeSoftKeyboard()

		sleep(WAIT_TIME)
		//verifying itemCount is correct in search result
		onView(withId(R.id.rv_cities)).check { view, noViewFoundException ->
			if (noViewFoundException != null) {
				throw noViewFoundException
			}
			val recyclerView = view as RecyclerView
			assertEquals(FakeCityUseCaseImpl.NUMBER_OF_ITEM_AFTER_SEARCH, recyclerView.adapter?.itemCount)
		}
	}
	/*
    *   Test spec
    *   name: fragment launch correctly and city list should be visible. And when "DHAKA is typed it should
    *   filter the list and show empty ui since no cities matches with the name"
    *   steps:
    *       - [Action] launch fragment
    *       - [Action] type DHAKA
    *       - [Assert] rv_cities does not have any item
    *       - [Assert] view_empty is visible
    *       - [Assert] tv_title is visible
    *       - [Assert] iv_search is visible
    */
	@Test
	fun test_itemLoadedAfterTyping_EmptyViewAreShowingAndCorrectViewAreShowing() {
		//open fragment
		openCitySearchBottomSheetFragment()

		//creating scenario for valid response
		onView(withId(androidx.appcompat.R.id.search_src_text)).perform(
			typeText(FakeCityUseCaseImpl.TRIGGER_EMPTY_RESPONSE),
			pressImeActionButton()
		)
		Espresso.closeSoftKeyboard()

		sleep(WAIT_TIME)
		//verifying correct view are showing
		onView(withId(R.id.rv_cities)).check(matches(not(hasDescendant(withId(R.id.tv_city_name)))))
		onView(withId(R.id.view_empty)).check(matches(isDisplayed()))
		onView(withId(com.stocard.core.R.id.tv_title)).check(matches(isDisplayed()))
		onView(withId(com.stocard.core.R.id.iv_search)).check(matches(isDisplayed()))
	}

	/*
    *   Test spec
    *   name: fragment launch correctly and city list should be visible. And when "DHAKA is typed it should
    *   filter the list and show empty ui since no cities matches with the name and should show correct tile"
    *   steps:
    *       - [Action] launch fragment
    *       - [Action] type DHAKA
    *       - [Assert] tv_title text is showing "No City Found"
    */
	@Test
	fun test_itemLoadedAfterTyping_EmptyViewAreShowingAndCorrectErrorTextAreShowing() {
		//open fragment
		openCitySearchBottomSheetFragment()

		//creating scenario for valid response
		onView(withId(androidx.appcompat.R.id.search_src_text)).perform(
			typeText(FakeCityUseCaseImpl.TRIGGER_EMPTY_RESPONSE),
			pressImeActionButton()
		)
		Espresso.closeSoftKeyboard()

		sleep(WAIT_TIME)
		//verifying correct view are showing and correct title are showing
		onView(withId(com.stocard.core.R.id.tv_title)).check(matches(withText(FakeCityUseCaseImpl.MSG_NOT_CITY_FOUNT)))
	}

	/*
    *   Test spec
    *   name: fragment launch correctly and if any parsing error happen city list should be not be visible.
    *   Error UI is visible and should show correct tile"
    *   steps:
    *       - [Action] launch fragment
    *       - [Action] parsing error happen
    *       - [Assert] rv_cities does not have any item
    *       - [Assert] view_empty is visible
    *       - [Assert] tv_title is visible
    *       - [Assert] iv_search is visible
    *       - [Assert] tv_title text is showing "Can't parse the city list"
    */
	@Test
	fun test_itemLoadedAfterError_ErrorViewAreShowingAndCorrectErrorTextAreShowing() {
		//creating scenario for valid response
		fakeCityUseCaseImpl.isTestError = true

		//open fragment
		openCitySearchBottomSheetFragment()

		Espresso.closeSoftKeyboard()

		sleep(WAIT_TIME)

		//verifying all error view are showing and recyclerview doesn't have any item
		onView(withId(R.id.rv_cities)).check(matches(not(hasDescendant(withId(R.id.tv_city_name)))))
		onView(withId(R.id.view_empty)).check(matches(isDisplayed()))
		onView(withId(com.stocard.core.R.id.tv_title)).check(matches(isDisplayed()))
		onView(withId(com.stocard.core.R.id.iv_search)).check(matches(isDisplayed()))
		onView(withId(com.stocard.core.R.id.tv_title)).check(matches(withText(FakeCityUseCaseImpl.MSG_ERROR)))
	}


	private fun openCitySearchBottomSheetFragment(){
		val mockNavController = Mockito.mock(NavController::class.java)
		launchFragmentInHiltContainer<CitySearchBottomSheetDialog>(
			null,
			com.stocard.style.R.style.BottomSheetDialog
		) {
			viewLifecycleOwnerLiveData.observeForever { viewLifecycleOwner ->
				if (viewLifecycleOwner != null) {
					Navigation.setViewNavController(requireView(), mockNavController)
				}
			}
		}
	}

}