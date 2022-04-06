package com.whattowhere.ui.login

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.facebook.*
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.CommonStatusCodes
import com.google.android.gms.tasks.Task
import com.myvagon.driver.utils.UserPreference
import com.whattowhere.BR
import com.whattowhere.R
import com.whattowhere.common.Constants
import com.whattowhere.common.Constants.FaceBook.FACEBOOK_EMAIL
import com.whattowhere.common.Constants.FaceBook.FACEBOOK_ID
import com.whattowhere.common.Constants.FaceBook.FACEBOOK_NAME
import com.whattowhere.data.remote.ApiResources
import com.whattowhere.databinding.ActivityLoginBinding
import com.whattowhere.extension.*
import com.whattowhere.ui.base.BaseActivity
import com.whattowhere.ui.forgotPassword.ForgotPwdActivity
import com.whattowhere.ui.home.HomeActivity
import com.whattowhere.ui.profile.setupProfile.SetUpProfileActivity
import com.whattowhere.ui.signup.SignUpActivity
import dagger.hilt.android.AndroidEntryPoint
import org.json.JSONObject
import java.util.*


@AndroidEntryPoint
class LoginActivity(
    override val layoutId: Int = R.layout.activity_login,
    override val bindingVariable: Int = BR.viewmodel
) : BaseActivity<ActivityLoginBinding, LoginViewModel>() {
    private lateinit var googleSignInLauncher: ActivityResultLauncher<Intent>
    lateinit var googleSignInClient: GoogleSignInClient
    lateinit var gso: GoogleSignInOptions
    lateinit var callbackManager: CallbackManager
    val TAG = LoginActivity::class.java.name

    override fun setUpObserver() {
        mViewModel.validationStateObserver.observe(this) {
            it.getContentIfNotHandled()?.let { message ->
                showSnackBar(binding.clParent, message, true)
            }
        }
        mViewModel.loginResponseObserver.observe(this) { it ->
            it?.getContentIfNotHandled()?.let {
                when (it.status) {
                    ApiResources.Status.SUCCESS -> {
                        hideLoader()
                        userPreference.saveUserSession(
                            UserPreference.SharedPrefKey.USER_DETAILS, gson.toJson(
                                it.data?.data
                            )
                        )
                     startNewActivityWithClear(HomeActivity::class.java)
                    it.data?.data?.LoginFirst?.let {
                        goToNextScreen(it)
                    }
                    }
                    ApiResources.Status.ERROR -> {
                        hideLoader()
                        showMessageDialog(message = it.message!!)
                    }
                    ApiResources.Status.LOADING -> {
                        showLoader(supportFragmentManager)
                    }
                    ApiResources.Status.NO_INTERNET_CONNECTION -> {}
                    ApiResources.Status.UNKNOWN -> {}
                    ApiResources.Status.SHIMMER_EFFECT -> {}
                }
            }
        }

    }

    override fun init() {
        setStatusBarColor()
        googleSetUp()
        setUpFacebookLogin()
        initListener()
    }

    private fun initListener() {
        binding.tvForgotPwd.setOnClickListener {
            startNewActivity(ForgotPwdActivity::class.java)
        }
        binding.tvSignup.setOnClickListener {
            startNewActivity(SignUpActivity::class.java)
        }
        binding.tvSignIn.setOnClickListener {
//            startNewActivityWithClear(CreatePostActivity::class.java)
            if (isValidDetails()) {
                mViewModel.doLogin(Constants.LoginType.Normal)
            }

        }
        binding.llGoogle.setOnClickListener {
            val signInIntent: Intent = googleSignInClient.signInIntent
            googleSignInLauncher.launch(signInIntent)
        }
        binding.llFb.setOnClickListener {
            LoginManager.getInstance()
                .logInWithReadPermissions(this, Arrays.asList("public_profile", "email"))
        }

    }


    private fun isValidDetails(): Boolean {
        var isValidDetails = true

        binding.tilEmail.isErrorEnabled = false
        binding.tilPassword.isErrorEnabled = false
        if (mViewModel.email.trim().isEmpty()) {
            binding.edtEmail.error = getString(R.string.this_field_is_requried)
            isValidDetails = false
        } else if (!mViewModel.email.trim().isEmailValid()) {
            binding.edtEmail.error = getString(R.string.must_be_valid_email_address)
            isValidDetails = false
        }
        if (mViewModel.password.trim().isEmpty()) {
            binding.edtPassword.error = getString(R.string.enter_your_password)
            isValidDetails = false
        }
        return isValidDetails

    }

    private fun googleSetUp() {
        gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            //1087139904425-dumjm95q1cmpldo5mshv7v3spn7ijf9v.apps.googleusercontent.com
            .build()
        googleSignInClient = GoogleSignIn.getClient(this, gso)
        googleSignInLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
                val task: Task<GoogleSignInAccount> =
                    GoogleSignIn.getSignedInAccountFromIntent(result.data)
                handleSignInResult(task)
            }
    }

    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account = completedTask.getResult(ApiException::class.java)
            Log.e("account name", account?.displayName!!)
            Log.e("account email", account.email!!)
            updateUIForGoogleLogin()

        } catch (e: ApiException) {

            var messageToDisplay: String? = null
            when (e.statusCode) {
                CommonStatusCodes.API_NOT_CONNECTED -> messageToDisplay =
                    "The client attempted to call a method from an API that failed to connect."
                CommonStatusCodes.DEVELOPER_ERROR -> messageToDisplay =
                    "The application is misconfigured."
                CommonStatusCodes.ERROR -> messageToDisplay =
                    "The operation failed with no more detailed information."
                CommonStatusCodes.INTERNAL_ERROR -> messageToDisplay = "An internal error occurred."
                CommonStatusCodes.INVALID_ACCOUNT -> messageToDisplay =
                    "Invalid account name specified."
                CommonStatusCodes.SIGN_IN_REQUIRED -> messageToDisplay =
                    "Please Sign In to continue."
            }

            Log.e(
                TAG,
                "signInResult:failed code=" + e.statusCode + " messageToDisplay \t" + messageToDisplay
            )
        }
    }


    private fun updateUIForGoogleLogin() {
        Log.e(TAG, "updateUIForGoogleLogin")
        val lastSignedInGoogleAccount = GoogleSignIn.getLastSignedInAccount(this)
        if (lastSignedInGoogleAccount != null) {
            //save to shared preference
            val email = lastSignedInGoogleAccount.email
            val profilePic = lastSignedInGoogleAccount.photoUrl
            val firstName = lastSignedInGoogleAccount.givenName
            val lastName = lastSignedInGoogleAccount.familyName
            val socialId = lastSignedInGoogleAccount.id
            Log.e(
                TAG,
                """
                
                socialId	$socialId
                lastName	$lastName
                firstName	$firstName
                profilePic	$profilePic
                email	$email
                """.trimIndent()
            )
            googleSignInClient.signOut()
            if (!TextUtils.isEmpty(email)) {
                mViewModel.email = email!!
                mViewModel.fullName=firstName+lastName
                mViewModel.doSignUp(Constants.LoginType.Google)
                mViewModel.profileUrl=profilePic.toString()
            }
        }
    }

    private fun setUpFacebookLogin() {
        callbackManager = CallbackManager.Factory.create()
        LoginManager.getInstance().registerCallback(callbackManager, getFacebookCallback())

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        callbackManager.onActivityResult(requestCode, resultCode, data)
        super.onActivityResult(requestCode, resultCode, data)
    }

    private fun getFacebookCallback(): FacebookCallback<LoginResult> {
        return object : FacebookCallback<LoginResult> {
            override fun onSuccess(loginResult: LoginResult) {
                // App code
                val request = getRequest(loginResult)
                val parameters = Bundle()
                parameters.putString(
                    "fields",
                    FACEBOOK_EMAIL.toString() + "," + FACEBOOK_NAME + "," + FACEBOOK_ID
                )
                request.parameters = parameters
                request.executeAsync()
                Log.e(
                    TAG,
                    "onSuccess for facebook" + "loginResult" + Constants.getObjectAsJson(loginResult)
                )
            }

            private fun getRequest(loginResult: LoginResult): GraphRequest {
                return GraphRequest.newMeRequest(
                    loginResult.accessToken
                ) { `object`: JSONObject?, response: GraphResponse? ->
                    if (`object` != null) {
                        try {
                            Log.e(
                                TAG,
                                "Graph getRequest" + "object" + Constants.getObjectAsJson(`object`)
                            )
                            val name = `object`.getString(Constants.FaceBook.FACEBOOK_NAME)
                            val email = `object`.getString(Constants.FaceBook.FACEBOOK_EMAIL)
                            val fbUserID = `object`.getString(Constants.FaceBook.FACEBOOK_ID)
                            var fbUserProfilePics: String? = null
                            if (!TextUtils.isEmpty(fbUserID)) {
                                fbUserProfilePics =
                                    "http://graph.facebook.com/$fbUserID/picture?type=large"
                            }
                            Log.e(
                                TAG,
                                """
                                fbUserProfilePics	$fbUserProfilePics
                                fbUserID	$fbUserID
                                email	$email
                                name	$name
                                """.trimIndent()
                            )

                            mViewModel.email = email
                            fbUserProfilePics?.let {
                                mViewModel.profileUrl=it
                            }
                            mViewModel.fullName=name
                            mViewModel.doSignUp(Constants.LoginType.FaceBook)
                            val profilePicUri =
                                Uri.parse(fbUserProfilePics)
                            if (!TextUtils.isEmpty(email)) {
                                /*decideDestinationForSocial(email, profilePicUri, name, null, fbUserID);*/
                            }
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }
                }
            }

            override fun onCancel() {
                // App code
                Log.e(TAG, "facebook login onCancel")
            }

            override fun onError(exception: FacebookException) {
                Log.e(
                    TAG,
                    "onError" + "facebook exception" + Constants.getObjectAsJson(exception)
                )

            }
        }
    }

    fun goToNextScreen(loginFirst:Boolean){
        if (loginFirst){
            startNewActivity(SetUpProfileActivity::class.java)
        }else{
            startNewActivity(HomeActivity::class.java)
        }
    }
}