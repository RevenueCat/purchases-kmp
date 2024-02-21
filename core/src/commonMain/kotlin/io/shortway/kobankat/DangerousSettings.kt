package io.shortway.kobankat

/**
 * Only use a Dangerous Setting if suggested by RevenueCat support team.
 */
public data class DangerousSettings(
    /**
     * Disable or enable syncing purchases automatically. If this is disabled, RevenueCat will not
     * sync any purchase automatically, and you will have to call [syncPurchases] whenever a new
     * purchase is completed in order to send it to the RevenueCat's backend. Auto syncing of
     * purchases is enabled by default.
     */
    public val autoSyncPurchases: Boolean = true,
)