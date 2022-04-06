package com.whattowhere.ui.signup

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
import com.whattowhere.data.remote.ApiResources
import com.whattowhere.databinding.ActivitySignUpBinding
import com.whattowhere.extension.*
import com.whattowhere.ui.base.BaseActivity
import com.whattowhere.ui.home.HomeActivity
import com.whattowhere.ui.login.LoginActivity
import com.whattowhere.ui.profile.setupProfile.SetUpProfileActivity
import dagger.hilt.android.AndroidEntryPoint
import org.json.JSONObject
import java.util.*

@AndroidEntryPoint
class SignUpActivity(
    override val layoutId: Int = R.layout.activity_sign_up,
    override val bindingVariable: Int = BR.viewmodel
) : BaseActivity<ActivitySignUpBinding, SignUpViewModel>() {
    private lateinit var googleSignInLauncher: ActivityResultLauncher<Intent>
    lateinit var googleSignInClient: GoogleSignInClient
    lateinit var gso: GoogleSignInOptions
    lateinit var callbackManager: CallbackManager
    val TAG = LoginActivity::class.java.name
    override fun setUpObserver() {
        mViewModel.signUpResponseObserver.observe(this) { it ->
            it?.getContentIfNotHandled()?.let {
                when (it.status) {
                    ApiResources.Status.SUCCESS -> {
                        hideLoader()
                        userPreference.saveUserSession(
                            UserPreference.SharedPrefKey.USER_DETAILS, gson.toJson(
                                it.data?.data
                            )
                        )
                        if(it.message!=null){
                            showAlertDialog(     message = it.message!!,
                            title = getString(R.string.app_name),
                            positiveClick = {
                                it.data?.data?.LoginFirst?.let { isFirstTime -> goToNextScreen(isFirstTime) }
                            })
                        }else{
                            it.data?.data?.LoginFirst?.let { isFirstTime -> goToNextScreen(isFirstTime) }
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

    fun goToNextScreen(loginFirst:Boolean){
        if (loginFirst){
            startNewActivity(SetUpProfileActivity::class.java)
        }else{
            startNewActivity(HomeActivity::class.java)
        }
    }

    override fun init() {
        setStatusBarColor()
        binding.tvSignIn.setOnClickListener {
            startNewActivityWithClear(LoginActivity::class.java)
        }
        binding.tvSignUp.setOnClickListener {
            if (isValidDetails()) {
                mViewModel.doSignUp(Constants.LoginType.Normal)
            }
        }
        binding.llGoogle.setOnClickListener {
            val signInIntent: Intent = googleSignInClient.signInIntent
            googleSignInLauncher.launch(signInIntent)
        }
        binding.llFb.setOnClickListener {
            LoginManager.getInstance()
                .logInWithReadPermissions(
                    this,
                    Arrays.asList(
                        Constants.FaceBook.FACEBOOK_PERMISSION_PUBLIC_PROFILE,
                        Constants.FaceBook.FACEBOOK_PERMISSION_EMAIL
                    )
                )
        }
        googleSetUp()
        setUpFacebookLogin()
    }


    private fun isValidDetails(): Boolean {
        var isValidDetails = true
        if (mViewModel.fullName.trim().isEmpty()) {
            binding.edtUserName.error = getString(R.string.this_field_is_requried)
            isValidDetails = false
        }
        if (mViewModel.emailAddress.trim().isEmpty()) {
            isValidDetails = false
            binding.edtEmail.error = getString(R.string.please_enter_email_address)
        } else if (!mViewModel.emailAddress.trim().isEmailValid()) {
            binding.edtEmail.error = getString(R.string.please_enter_valid_email_address)
            isValidDetails = false
        }
        if (mViewModel.password.trim().isEmpty()) {
            binding.edtPassword.error = getString(R.string.please_enter_password)
            isValidDetails = false
        } else if (mViewModel.password.trim().length < 6) {
            binding.edtPassword.error = getString(R.string.lbl_must_be_6_char)
            isValidDetails = false
        } else if (mViewModel.confirmPassword.trim().isEmpty()) {
            binding.edtConfirmPassword.error = getString(R.string.please_enter_confirm_password)
            isValidDetails = false
        } else if (mViewModel.password.trim() != mViewModel.confirmPassword.trim()) {
            binding.edtConfirmPassword.error =
                getString(R.string.password_and_confirm_password_not_same)
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
                mViewModel.fullName=firstName+lastName
                mViewModel.emailAddress = email!!
                mViewModel.doSignUp(Constants.LoginType.Google)
                //TODO: Api integration for social google login
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
                    Constants.FaceBook.FACEBOOK_EMAIL.toString() + "," + Constants.FaceBook.FACEBOOK_NAME + "," + Constants.FaceBook.FACEBOOK_ID
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
                            mViewModel.fullName=name
                            mViewModel.emailAddress = email
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
}