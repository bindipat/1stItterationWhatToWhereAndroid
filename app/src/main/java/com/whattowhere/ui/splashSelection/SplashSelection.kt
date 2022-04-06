package com.whattowhere.ui.splashSelection

import android.content.Intent
import android.graphics.Typeface
import android.net.Uri
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.TextUtils
import android.text.style.ForegroundColorSpan
import android.text.style.RelativeSizeSpan
import android.text.style.TextAppearanceSpan
import android.util.Log
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
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
import com.whattowhere.databinding.ActivitySplashSelectionBinding
import com.whattowhere.extension.*
import com.whattowhere.ui.base.BaseActivity
import com.whattowhere.ui.customView.CustomTypefaceSpan
import com.whattowhere.ui.home.HomeActivity
import com.whattowhere.ui.login.LoginActivity
import com.whattowhere.ui.signup.SignUpActivity
import dagger.hilt.android.AndroidEntryPoint
import org.json.JSONObject
import java.util.*


@AndroidEntryPoint
class SplashSelection(
    override val layoutId: Int = R.layout.activity_splash_selection,
    override val bindingVariable: Int = BR.viewmodel
) : BaseActivity<ActivitySplashSelectionBinding, SplashSelectionViewModel>() {

    private lateinit var googleSignInLauncher: ActivityResultLauncher<Intent>
    lateinit var googleSignInClient: GoogleSignInClient
    lateinit var gso: GoogleSignInOptions
    lateinit var callbackManager: CallbackManager
    val TAG = SplashSelection::class.java.name
    fun setIntroText(){
        val dalimeFont: Typeface = ResourcesCompat.getFont(activity, R.font.dalime)!!
        val oochBabyFont: Typeface = ResourcesCompat.getFont(activity, R.font.ooch_baby_regular)!!
        val ssDecline =
            SpannableStringBuilder(getString(R.string.lbl_confidence_is_the_best_dressed))

        ssDecline.setSpan(
            CustomTypefaceSpan("", dalimeFont),
            0,
            18,
            Spanned.SPAN_EXCLUSIVE_INCLUSIVE
        )
        ssDecline.setSpan(RelativeSizeSpan(1f), 0, 18, 0)
        ssDecline.setSpan(
            CustomTypefaceSpan("", oochBabyFont),
            18,
            23,
            Spanned.SPAN_EXCLUSIVE_INCLUSIVE
        )
        ssDecline.setSpan(
            TextAppearanceSpan(activity, R.style.style1),
            18,
            23,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        ssDecline.setSpan(
            ForegroundColorSpan(
                ContextCompat.getColor(
                    activity,
                    R.color.color_primary
                )
            ), 18, 23, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        ssDecline.setSpan(
            CustomTypefaceSpan("", dalimeFont),
            23,
            31,
            Spanned.SPAN_EXCLUSIVE_INCLUSIVE
        )
        ssDecline.setSpan(RelativeSizeSpan(1f), 23, 31, 0)
        binding.tvIntro.text = ssDecline
    }

    override fun setUpObserver() {

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
        binding.tvSignIn.setOnClickListener {
            startNewActivity(LoginActivity::class.java)
        }
        binding.tvSignUp.setOnClickListener {
            startNewActivity(SignUpActivity::class.java)
        }


        binding.ivGoogle.setOnClickListener {
            val signInIntent: Intent = googleSignInClient.signInIntent
            googleSignInLauncher.launch(signInIntent)
        }
        binding.ivFb.setOnClickListener {
            LoginManager.getInstance()
                .logInWithReadPermissions(this, Arrays.asList("public_profile", "email"))
        }

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

                            mViewModel.email = email

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
}