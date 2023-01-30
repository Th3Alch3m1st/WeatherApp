package com.stocard.core.network

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.Response
import java.io.IOException
import okhttp3.ResponseBody
import org.json.JSONTokener
import retrofit2.HttpException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

/**
 * Created By Rafiqul Hasan
 */
abstract class BaseSource {
	companion object {
		const val SOCKET_TIME_OUT_EXCEPTION =
			"Please check your internet connection"
		const val UNKNOWN_NETWORK_EXCEPTION =
			"Please check your network connection and try again."
		const val CONNECT_EXCEPTION =
			"Please check your internet connection"
		const val EMPTY_BODY_EXCEPTION =
			"Response body can not be empty"
		const val UNKNOWN_HOST_EXCEPTION =
			"Couldn't connect to the server at the moment. Please try again in a few minutes."
	}

	protected suspend fun <T : Any> safeApiCall(
		dispatcher: CoroutineDispatcher = Dispatchers.IO,
		call: suspend () -> Response<T>
	): Resource<T> {
		var response: Response<T>? = null
		return withContext(dispatcher) {
			try {
				response = call()
			} catch (connectException: Exception) {
				return@withContext Resource.Error(
					RequestException(CONNECT_EXCEPTION),
					response?.code() ?: -1
				)
			} catch (connectException: UnknownHostException) {
				return@withContext Resource.Error(
					RequestException(UNKNOWN_HOST_EXCEPTION),
					response?.code() ?: -1
				)
			} catch (connectException: SocketTimeoutException) {
				return@withContext Resource.Error(
					RequestException(SOCKET_TIME_OUT_EXCEPTION),
					response?.code() ?: -1
				)
			} catch (ioException: IOException) {
				return@withContext Resource.Error(
					RequestException(CONNECT_EXCEPTION),
					response?.code() ?: -1
				)
			} catch (ioException: HttpException) {
				return@withContext Resource.Error(
					RequestException(UNKNOWN_NETWORK_EXCEPTION),
					response?.code() ?: -1
				)
			} catch (throwable: Throwable) {
				return@withContext Resource.Error(
					RequestException(UNKNOWN_NETWORK_EXCEPTION),
					response?.code() ?: -1
				)
			}
			response?.let { res ->
				if (res.isSuccessful) {
					response?.body()?.let { result ->
						Resource.Success(result)
					} ?: Resource.Error(RequestException(EMPTY_BODY_EXCEPTION), res.code())
				} else {
					val errorMessage = getParsedErrorMessage(res.errorBody()) ?: res.message()
					Resource.Error(RequestException(errorMessage), res.code())
				}
			} ?: Resource.Error(RequestException(EMPTY_BODY_EXCEPTION), response?.code() ?: -1)
		}
	}

	private fun getParsedErrorMessage(responseBody: ResponseBody?): String? {
		try {
			val message = "message"
			responseBody?.string()?.let {
				val json = JSONTokener(it).nextValue()
				if (json is JSONObject) {
					if (json.has(message)) {
						return json.getString(message)
					}
				} else if (json is JSONArray) {
					for (i in 0..json.length()) {
						val jsonObject = json.getJSONObject(i)
						if (jsonObject.has(message)) {
							return jsonObject.getString(message)
						}
					}
				}
			}
		} catch (ex: Exception) {
			return null
		}

		return null
	}
}

