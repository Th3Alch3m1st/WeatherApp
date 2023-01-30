package com.stocard.weatherapp

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.lifecycleScope
import com.stocard.core.fragment.FragmentCommunicator
import com.stocard.core.util.ThemeHelper
import com.stocard.style.R.*
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint

/**
 * Created By Rafiqul Hasan
 */
@AndroidEntryPoint
class MainActivity : AppCompatActivity(), FragmentCommunicator {
	private val viewModel: MainViewModel by viewModels()
	private var loaderDialog: AlertDialog?=null
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)
		initAlertDialog()
		viewModel.getSelectedTheme()
		initTheme()
	}

	override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
		if (currentFocus != null) {
			val inputMethodManager =
				getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
			inputMethodManager.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
		}
		return super.dispatchTouchEvent(ev)
	}
	override fun onOptionsItemSelected(item: MenuItem): Boolean {
		return when (item.itemId) {
			android.R.id.home -> {
				onBackPressedDispatcher.onBackPressed()
				true
			}
			else -> super.onOptionsItemSelected(item)
		}
	}

	override fun onDestroy() {
		super.onDestroy()
		loaderDialog = null
	}

	override fun setActionBar(toolbar: Toolbar, enableBackButton: Boolean) {
		setSupportActionBar(toolbar)
		if (enableBackButton) {
			supportActionBar?.setDisplayHomeAsUpEnabled(true)
			supportActionBar?.setHomeButtonEnabled(true)
		}
	}

	override fun showLoader() {
		runOnUiThread {
			if (loaderDialog?.isShowing == false) {
				loaderDialog?.show()
			}
		}
	}

	override fun hideLoader() {
		runOnUiThread {
			if (loaderDialog?.isShowing == true) {
				loaderDialog?.dismiss()
			}
		}
	}

	private fun initTheme() {
		lifecycleScope.launchWhenStarted {
			viewModel.selectedTheme.collect {
				ThemeHelper.applyTheme(it)
			}
		}
	}

	private fun initAlertDialog(){
		val builder = MaterialAlertDialogBuilder(this@MainActivity, style.LoaderDialog)
		val dialogView = LayoutInflater.from(this@MainActivity)
			.inflate(R.layout.dialog_loader, findViewById(android.R.id.content), false)
		builder.setView(dialogView)
		builder.setCancelable(false)

		loaderDialog = builder.create().apply {
			window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
		}
	}
}