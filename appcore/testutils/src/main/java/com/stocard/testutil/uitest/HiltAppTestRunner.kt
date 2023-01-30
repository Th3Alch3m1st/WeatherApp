package com.stocard.testutil.uitest

import android.app.Application
import android.content.Context
import androidx.test.runner.AndroidJUnitRunner
import dagger.hilt.android.testing.HiltTestApplication

/**
 * Created by Rafiqul Hasan
 */
class HiltAppTestRunner : AndroidJUnitRunner() {

	override fun newApplication(cl: ClassLoader?, name: String?, context: Context?): Application {
		return super.newApplication(cl, HiltTestApplication::class.java.name, context)
	}
}