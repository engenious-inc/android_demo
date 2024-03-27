package com.github.tarcv.orderme.app.ui

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.FacebookSdk
import com.facebook.login.LoginResult
import com.github.tarcv.orderme.app.App
import com.github.tarcv.orderme.app.R
import com.github.tarcv.orderme.app.databinding.ActivityMainBinding
import com.github.tarcv.orderme.app.ui.base.BaseActivity
import com.github.tarcv.orderme.core.ApiClient
import com.github.tarcv.orderme.core.data.response.LoginResponse
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Inject

class SplashActivity : BaseActivity() {

    @Inject
    lateinit var apiClient: ApiClient

    private lateinit var binding: ActivityMainBinding

    private val callbackManager = CallbackManager.Factory.create()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FacebookSdk.setClientToken("371530346532127")
        FacebookSdk.sdkInitialize(this)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        App.component.inject(this)

        if (! App.sharedPreferences.getString(App.LOGIN_TOKEN, "").isNullOrEmpty()) {
            startActivity()
        }

        binding.contentMain.loginLaterButton.setOnClickListener {
            startActivity()
        }

        binding.contentMain.loginButton.registerCallback(callbackManager,
            object : FacebookCallback<LoginResult> {

            override fun onSuccess(loginResult: LoginResult) {
                Timber.i("LoginResult: onSuccess token = ${loginResult.accessToken.token}")
                loginOnServer(loginResult.accessToken.token)
            }

            override fun onCancel() {
                Timber.i("LoginResult: onCancel")
            }

            override fun onError(exception: FacebookException) {
                Timber.i("LoginResult: onError")
                exception.printStackTrace()
            }
        })
    }

    override fun onResume() {
        super.onResume()
        updateButtonText()
    }

    private fun updateButtonText() {
        if (! App.sharedPreferences.getString(App.LOGIN_TOKEN, "").isNullOrEmpty()) {
            binding.contentMain.loginLaterButton.text = this.getString(R.string.continue_logged_in)
        } else {
            binding.contentMain.loginLaterButton.text = this.getString(R.string.login_later)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        callbackManager.onActivityResult(requestCode, resultCode, data)
        super.onActivityResult(requestCode, resultCode, data)
    }

    fun loginOnServer(token: String) {
        apiClient.login(token)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onNext,
                        this::onError)
    }

    @SuppressLint("ApplySharedPref")
    private fun onNext(response: LoginResponse) {
        Timber.i("loginOnServer: onNext name = ${response.name} token = ${response.token}")
        apiClient.setToken(response.token)
        App.sharedPreferences.edit().apply {
            putString(App.LOGIN_TOKEN, response.token)
            putString(App.LOGIN_NAME, response.name)
            putInt(App.LOGIN_ID, response.id)
            putString(App.LOGIN_USER_ID, response.userId)
        }.commit()
        updateButtonText()
        startActivity()
    }

    private fun onError(throwable: Throwable?) {
        Timber.i("loginOnServer: onError")
        throwable?.printStackTrace()
        val builder = AlertDialog.Builder(this@SplashActivity)
        builder.setTitle(R.string.error)
                .setMessage(R.string.connection_error)
                .setNegativeButton(R.string.ok, { dialog, _ -> dialog.cancel() })
                .create()
                .show()
    }

    private fun startActivity() {
        val intent = Intent(this, TabBarActivity::class.java)
        startActivity(intent)
    }
}
