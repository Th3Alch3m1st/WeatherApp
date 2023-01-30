package com.stocard.core.network

/**
 * Created By Rafiqul Hasan
 */
class RequestException(
	override var message: String = "",
) : Exception(message)
