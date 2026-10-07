package com.revenuecat.purchases.kmp.models

/**
 * Data for tracking when a rewarded ad prompt is shown to the user, such as a
 * "Watch an ad to earn coins" button. This event is manual-only: only the app knows
 * when the prompt is presented.
 *
 * @property mediatorName The name of the ad mediator. See [AdMediatorName] for common values.
 * @property placement The placement of the prompt, if available.
 * @property adUnitId The ad unit ID of the rewarded ad.
 */
public class AdRewardPromptShownData(
    public val mediatorName: AdMediatorName,
    public val placement: String?,
    public val adUnitId: String,
)
