package com.dpappdev.deeptalkscouples.ads

import android.app.Activity
import android.util.Log
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.rewardedinterstitial.RewardedInterstitialAd
import com.google.android.gms.ads.rewardedinterstitial.RewardedInterstitialAdLoadCallback

private const val TAG = "RewardedAdManager"

// Test ad unit ID: ca-app-pub-3940256099942544/5354046379
// My AdMob unit ID: ca-app-pub-9315374730551337/4012802744
private const val AD_UNIT_ID = "ca-app-pub-9315374730551337/4012802744"

class RewardedAdManager(private val activity: Activity) {

    var isAdReady = false
        private set

    private var rewardedAd: RewardedInterstitialAd? = null
    private var isLoading = false

    // Called by MainActivity after MobileAds.initialize() completes
    fun startLoading() {
        loadAd()
    }

    private fun loadAd() {
        if (isLoading) return
        isLoading = true
        RewardedInterstitialAd.load(
            activity,
            AD_UNIT_ID,
            AdRequest.Builder().build(),
            object : RewardedInterstitialAdLoadCallback() {
                override fun onAdLoaded(ad: RewardedInterstitialAd) {
                    Log.d(TAG, "Ad loaded successfully")
                    rewardedAd = ad
                    isAdReady = true
                    isLoading = false
                }

                override fun onAdFailedToLoad(error: LoadAdError) {
                    Log.e(TAG, "Ad failed to load: ${error.message}")
                    rewardedAd = null
                    isAdReady = false
                    isLoading = false
                }
            }
        )
    }

    /**
     * Shows the rewarded interstitial ad. Calls [onRewarded] when the user earns the reward.
     * If no ad is ready, [onRewarded] is called immediately as a fallback so the user
     * is never hard-blocked.
     */
    fun showAd(onRewarded: () -> Unit) {
        val ad = rewardedAd
        if (ad == null || !isAdReady) {
            Log.d(TAG, "No ad available — granting reward as fallback")
            onRewarded()
            return
        }

        isAdReady = false

        ad.fullScreenContentCallback = object : FullScreenContentCallback() {
            override fun onAdDismissedFullScreenContent() {
                Log.d(TAG, "Ad dismissed — preloading next ad")
                rewardedAd = null
                loadAd()
            }

            override fun onAdFailedToShowFullScreenContent(error: AdError) {
                Log.e(TAG, "Ad failed to show: ${error.message}")
                rewardedAd = null
                loadAd()
                onRewarded()
            }
        }

        ad.show(activity) { _ ->
            // UserEarnedRewardListener — user completed the ad
            onRewarded()
        }
    }
}
