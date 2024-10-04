package com.ranking.trivia.latam.presentation.utils

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.net.Uri
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.activity.ComponentActivity
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.ranking.trivia.latam.MainActivity
import com.ranking.trivia.latam.R
import com.ranking.trivia.latam.presentation.screens.home.HomeViewModel

fun openUrl(context: Context, url: String) {
    val uri = Uri.parse(url)
    val intent = Intent(Intent.ACTION_VIEW, uri)
    context.startActivity(intent)
}

fun Context.getActivity(): Activity? = when (this) {
    is ComponentActivity -> this
    is ContextWrapper -> baseContext.getActivity()
    else -> null
}

fun verifyNewerVersion(
    viewModel: HomeViewModel,
    context: Context,
    onResponse: (Boolean) -> Unit
) {
    @Suppress("DEPRECATION")
    viewModel.gameHasNewerVersion(
        (context.packageManager.getPackageInfo(context.packageName, 0).versionCode)
    ) { gameHasTheNewestVersion ->
        onResponse(gameHasTheNewestVersion)
    }
}

fun loadAndShowAd(
    context: Context,
    adUnitId: String,
    onAdDismissed: () -> Unit,
    onAdFailedToLoad: (() -> Unit)? = null,
) {
    InterstitialAd.load(
        context,
        adUnitId,
        AdRequest.Builder().build(),
        object : InterstitialAdLoadCallback() {
            override fun onAdFailedToLoad(adError: LoadAdError) {
                println("AQUI: Ad failed to load")
                onAdFailedToLoad?.invoke()
                showToast(context, context.getString(R.string.error_loading_ad), Toast.LENGTH_LONG)
            }

            override fun onAdLoaded(interstitialAd: InterstitialAd) {
                context.getActivity()?.let {
                    interstitialAd.show(it)
                    println("AQUI: Ad loaded")

                    interstitialAd.fullScreenContentCallback = object : FullScreenContentCallback() {
                        override fun onAdClicked() {
                            println("AQUI: Ad was clicked.")
                        }

                        override fun onAdDismissedFullScreenContent() {
                            println("AQUI: Ad dismissed fullscreen content.")
                            onAdDismissed()
                        }

                        override fun onAdImpression() {
                            println("AQUI: Ad recorded an impression.")
                        }

                        override fun onAdShowedFullScreenContent() {
                            println("AQUI: Ad showed fullscreen content.")
                        }

                        override fun onAdFailedToShowFullScreenContent(adError: AdError) {
                            println("AQUI: Ad failed to show fullscreen content: $adError")
                        }
                    }
                }
            }
        }
    )
}

fun Context.resetApplication(delay: Long = 0L) {
    Handler(Looper.getMainLooper()).postDelayed({
        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                or Intent.FLAG_ACTIVITY_NEW_TASK
                or Intent.FLAG_ACTIVITY_CLEAR_TASK
                or Intent.FLAG_ACTIVITY_NO_ANIMATION
        )

        startActivity(intent)
        (this as? MainActivity)?.finishAffinity()

        if (this is Activity) {
            overridePendingTransition(0, 0)
        }
    }, delay)
}

fun showToast(context: Context, message: String?, duration: Int = Toast.LENGTH_SHORT) {
    if (!message.isNullOrEmpty()) {
        Toast.makeText(context, message, duration).show()
    }
}