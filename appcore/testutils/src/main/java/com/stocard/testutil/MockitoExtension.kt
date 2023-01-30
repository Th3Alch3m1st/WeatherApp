package com.stocard.testutil

import org.mockito.kotlin.whenever

/**
 * Created By Rafiqul Hasan
 */
infix fun Any?.returns(mockValue: Any?) = whenever(this).thenReturn(mockValue)