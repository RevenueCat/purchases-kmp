package com.revenuecat.purchases.kmp.watchostester

/**
 * Exposes build-time configuration to the Swift app, which cannot read the generated
 * `BuildKonfig` because buildkonfig declares it `internal`.
 */
public object WatchosTesterConfig {
    public val apiKey: String = BuildKonfig.apiKey
}
