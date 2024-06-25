package com.revenuecat.purchases.kmp

import cocoapods.PurchasesHybridCommon.RCCommonFunctionality
import cocoapods.PurchasesHybridCommon.RCStoreProduct
import cocoapods.PurchasesHybridCommon.configureWithAPIKey
import cocoapods.PurchasesHybridCommon.setAirshipChannelID
import cocoapods.PurchasesHybridCommon.setOnesignalUserID
import cocoapods.PurchasesHybridCommon.showStoreMessagesForTypes
import com.revenuecat.purchases.kmp.PurchasesAreCompletedBy.MY_APP
import com.revenuecat.purchases.kmp.PurchasesAreCompletedBy.REVENUECAT
import com.revenuecat.purchases.kmp.models.BillingFeature
import com.revenuecat.purchases.kmp.models.GoogleReplacementMode
import com.revenuecat.purchases.kmp.models.PromotionalOffer
import com.revenuecat.purchases.kmp.models.StoreMessageType
import com.revenuecat.purchases.kmp.models.StoreProduct
import com.revenuecat.purchases.kmp.models.StoreProductDiscount
import com.revenuecat.purchases.kmp.models.StoreTransaction
import com.revenuecat.purchases.kmp.models.SubscriptionOption
import com.revenuecat.purchases.kmp.strings.ConfigureStrings
import platform.Foundation.NSURL
import cocoapods.PurchasesHybridCommon.RCDangerousSettings as IosDangerousSettings
import cocoapods.PurchasesHybridCommon.RCPurchases as IosPurchases

public actual class Purchases private constructor(private val iosPurchases: IosPurchases) {
    public actual companion object {
        private var _sharedInstance: Purchases? = null
        public actual val sharedInstance: Purchases
            get() = _sharedInstance ?: error(ConfigureStrings.NO_SINGLETON_INSTANCE)

        public actual var logLevel: LogLevel
            get() = IosPurchases.logLevel().toLogLevel()
            set(value) = IosPurchases.setLogLevel(value.toRcLogLevel())

        private var _logHandler: LogHandler? = null
        public actual var logHandler: LogHandler
            get() = _logHandler
                ?: IosPurchases.logHandler().toLogHandler().also { _logHandler = it }
            set(value) {
                _logHandler = value
                IosPurchases.setLogHandler(value.toRcLogHandler())
            }

        public actual var proxyURL: String?
            get() = IosPurchases.proxyURL()?.absoluteString()
            set(value) = IosPurchases.setProxyURL(value?.let { NSURL(string = it) })

        public actual val isConfigured: Boolean
            get() = IosPurchases.isConfigured()

        public actual var simulatesAskToBuyInSandbox: Boolean
            get() = IosPurchases.simulatesAskToBuyInSandbox()
            set(value) = IosPurchases.setSimulatesAskToBuyInSandbox(value)

        public actual var forceUniversalAppStore: Boolean
            get() = IosPurchases.forceUniversalAppStore()
            set(value) = IosPurchases.setForceUniversalAppStore(value)

        public actual fun configure(
            configuration: PurchasesConfiguration
        ): Purchases {
            // TODO Enable this in SDK-3301 checkCommonVersion()

            return with(configuration) {
                IosPurchases.configureWithAPIKey(
                    apiKey = apiKey,
                    appUserID = appUserId,
                    observerMode = when (configuration.purchasesAreCompletedBy) {
                        REVENUECAT -> false
                        MY_APP -> true
                    },
                    userDefaultsSuiteName = userDefaultsSuiteName,
                    platformFlavor = BuildKonfig.platformFlavor,
                    platformFlavorVersion = frameworkVersion,
                    // In Flutter it's deprecated & defaults to false.
                    usesStoreKit2IfAvailable = false,
                    dangerousSettings = dangerousSettings.toIosDangerousSettings(),
                    shouldShowInAppMessagesAutomatically = showInAppMessagesAutomatically,
                    verificationMode = verificationMode.name,
                )
            }.let { Purchases(it) }
                .also { _sharedInstance = it }
        }

        public actual fun canMakePayments(
            features: List<BillingFeature>,
            callback: (Boolean) -> Unit
        ) {
            callback(IosPurchases.canMakePayments())
        }

        private fun checkCommonVersion() {
            val expected = BuildKonfig.revenuecatCommonVersion
            val actual = IosPurchases.frameworkVersion()
            check(actual == expected) {
                "Unexpected version of PurchasesHybridCommon ('$actual'). Make sure to use " +
                        "'$expected' exactly."
            }
        }

        private fun DangerousSettings.toIosDangerousSettings(): IosDangerousSettings =
            IosDangerousSettings(autoSyncPurchases)
    }

    public actual var purchasesAreCompletedBy: PurchasesAreCompletedBy
        get() = when (iosPurchases.finishTransactions()) {
            true -> REVENUECAT
            false -> MY_APP
        }
        set(value) {
            iosPurchases.setFinishTransactions(
                when (value) {
                    REVENUECAT -> true
                    MY_APP -> false
                }
            )
        }

    public actual val appUserID: String
        get() = iosPurchases.appUserID()

    public actual var delegate: PurchasesDelegate?
        get() = iosPurchases.delegate()?.toPurchasesDelegate()
        set(value) = iosPurchases.setDelegate(value?.toRcPurchasesDelegate())

    public actual val isAnonymous: Boolean
        get() = iosPurchases.isAnonymous()

    public actual val store: Store
        get() = Store.APP_STORE

    public actual fun syncPurchases(
        onError: (error: PurchasesError) -> Unit,
        onSuccess: (CustomerInfo) -> Unit,
    ): Unit = iosPurchases.syncPurchasesWithCompletionHandler { customerInfo, error ->
        if (error != null) onError(error.toPurchasesErrorOrThrow())
        else onSuccess(customerInfo ?: error("Expected a non-null RCCustomerInfo"))
    }

    public actual fun syncAmazonPurchase(
        productID: String,
        receiptID: String,
        amazonUserID: String,
        isoCurrencyCode: String?,
        price: Double?,
    ) {
        // No-op on iOS
    }

    public actual fun syncAttributesAndOfferingsIfNeeded(
        onError: (error: PurchasesError) -> Unit,
        onSuccess: (offerings: Offerings) -> Unit,
    ): Unit = iosPurchases.syncAttributesAndOfferingsIfNeededWithCompletion { offerings, error ->
        if (error != null) onError(error.toPurchasesErrorOrThrow())
        else onSuccess(offerings ?: error("Expected a non-null RCOfferings"))
    }

    public actual fun getOfferings(
        onError: (error: PurchasesError) -> Unit,
        onSuccess: (offerings: Offerings) -> Unit,
    ): Unit = iosPurchases.getOfferingsWithCompletion { offerings, error ->
        if (error != null) onError(error.toPurchasesErrorOrThrow())
        else onSuccess(offerings ?: error("Expected a non-null RCOfferings"))
    }

    public actual fun getProducts(
        productIds: List<String>,
        onError: (error: PurchasesError) -> Unit,
        onSuccess: (storeProducts: List<StoreProduct>) -> Unit,
    ): Unit = iosPurchases.getProductsWithIdentifiers(
        productIdentifiers = productIds,
        completion = {
            onSuccess(it.orEmpty().map { product -> (product as RCStoreProduct) })
        },
    )

    public actual fun getPromotionalOffer(
        discount: StoreProductDiscount,
        storeProduct: StoreProduct,
        onError: (error: PurchasesError) -> Unit,
        onSuccess: (offer: PromotionalOffer) -> Unit,
    ): Unit = iosPurchases.getPromotionalOfferForProductDiscount(
        discount = discount,
        withProduct = storeProduct
    ) { offer, error ->
        if (error != null) onError(error.toPurchasesErrorOrThrow())
        else onSuccess(offer ?: error("Expected a non-null RCPromotionalOffer"))
    }

    public actual fun purchase(
        storeProduct: StoreProduct,
        onError: (error: PurchasesError, userCancelled: Boolean) -> Unit,
        onSuccess: (storeTransaction: StoreTransaction, customerInfo: CustomerInfo) -> Unit,
        isPersonalizedPrice: Boolean?,
        oldProductId: String?,
        replacementMode: GoogleReplacementMode,
    ): Unit = iosPurchases.purchaseProduct(
        storeProduct
    ) { transaction, customerInfo, error, userCancelled ->
        if (error != null) onError(error.toPurchasesErrorOrThrow(), userCancelled)
        else onSuccess(
            transaction ?: error("Expected a non-null RCStoreTransaction"),
            customerInfo ?: error("Expected a non-null RCCustomerInfo")
        )
    }

    public actual fun purchase(
        packageToPurchase: Package,
        onError: (error: PurchasesError, userCancelled: Boolean) -> Unit,
        onSuccess: (storeTransaction: StoreTransaction, customerInfo: CustomerInfo) -> Unit,
        isPersonalizedPrice: Boolean?,
        oldProductId: String?,
        replacementMode: GoogleReplacementMode,
    ): Unit = iosPurchases.purchasePackage(
        packageToPurchase
    ) { transaction, customerInfo, error, userCancelled ->
        if (error != null) onError(error.toPurchasesErrorOrThrow(), userCancelled)
        else onSuccess(
            transaction ?: error("Expected a non-null RCStoreTransaction"),
            customerInfo ?: error("Expected a non-null RCCustomerInfo")
        )
    }

    public actual fun purchase(
        subscriptionOption: SubscriptionOption,
        onError: (error: PurchasesError, userCancelled: Boolean) -> Unit,
        onSuccess: (storeTransaction: StoreTransaction, customerInfo: CustomerInfo) -> Unit,
        isPersonalizedPrice: Boolean?,
        oldProductId: String?,
        replacementMode: GoogleReplacementMode,
    ): Unit = error(
        "Purchasing a SubscriptionOption is not possible on iOS. " +
                "Did you mean purchase(StoreProduct, PromotionalOffer) or " +
                "Purchases.purchase(Package, PromotionalOffer)?"
    )

    public actual fun purchase(
        storeProduct: StoreProduct,
        promotionalOffer: PromotionalOffer,
        onError: (error: PurchasesError, userCancelled: Boolean) -> Unit,
        onSuccess: (storeTransaction: StoreTransaction, customerInfo: CustomerInfo) -> Unit,
    ): Unit = iosPurchases.purchaseProduct(
        storeProduct,
        promotionalOffer
    ) { transaction, customerInfo, error, userCancelled ->
        if (error != null) onError(error.toPurchasesErrorOrThrow(), userCancelled)
        else onSuccess(
            transaction ?: error("Expected a non-null RCStoreTransaction"),
            customerInfo ?: error("Expected a non-null RCCustomerInfo")
        )
    }

    public actual fun purchase(
        packageToPurchase: Package,
        promotionalOffer: PromotionalOffer,
        onError: (error: PurchasesError, userCancelled: Boolean) -> Unit,
        onSuccess: (storeTransaction: StoreTransaction, customerInfo: CustomerInfo) -> Unit,
    ): Unit = iosPurchases.purchasePackage(
        packageToPurchase,
        promotionalOffer
    ) { transaction, customerInfo, error, userCancelled ->
        if (error != null) onError(error.toPurchasesErrorOrThrow(), userCancelled)
        else onSuccess(
            transaction ?: error("Expected a non-null RCStoreTransaction"),
            customerInfo ?: error("Expected a non-null RCCustomerInfo")
        )
    }

    public actual fun restorePurchases(
        onError: (error: PurchasesError) -> Unit,
        onSuccess: (customerInfo: CustomerInfo) -> Unit,
    ): Unit = iosPurchases.restorePurchasesWithCompletion { customerInfo, error ->
        if (error != null) onError(error.toPurchasesErrorOrThrow())
        else onSuccess(customerInfo ?: error("Expected a non-null RCCustomerInfo"))
    }

    public actual fun logIn(
        newAppUserID: String,
        onError: (error: PurchasesError) -> Unit,
        onSuccess: (customerInfo: CustomerInfo, created: Boolean) -> Unit
    ): Unit = iosPurchases.logIn(
        appUserID = newAppUserID,
        completion = { customerInfo, created, error ->
            if (error != null) onError(error.toPurchasesErrorOrThrow())
            else onSuccess(customerInfo ?: error("Expected a non-null RCCustomerInfo"), created)
        }
    )

    public actual fun logOut(
        onError: (error: PurchasesError) -> Unit,
        onSuccess: (customerInfo: CustomerInfo) -> Unit,
    ): Unit = iosPurchases.logOutWithCompletion { customerInfo, error ->
        if (error != null) onError(error.toPurchasesErrorOrThrow())
        else onSuccess(customerInfo ?: error("Expected a non-null RCCustomerInfo"))
    }

    public actual fun close() {
        iosPurchases.setDelegate(null)
    }

    public actual fun getCustomerInfo(
        fetchPolicy: CacheFetchPolicy,
        onError: (error: PurchasesError) -> Unit,
        onSuccess: (customerInfo: CustomerInfo) -> Unit,
    ): Unit = iosPurchases.getCustomerInfoWithFetchPolicy(
        fetchPolicy.toRCCacheFetchPolicy()
    ) { customerInfo, error ->
        if (error != null) onError(error.toPurchasesErrorOrThrow())
        else onSuccess(customerInfo ?: error("Expected a non-null RCCustomerInfo"))
    }

    public actual fun showInAppMessagesIfNeeded(
        messageTypes: List<StoreMessageType>,
    ): Unit = RCCommonFunctionality.showStoreMessagesForTypes(
        rawValues = messageTypes.mapTo(mutableSetOf()) { type ->
            when (type) {
                StoreMessageType.BILLING_ISSUES -> 0
                StoreMessageType.PRICE_INCREASE_CONSENT -> 1
                StoreMessageType.GENERIC -> 2
            }
        },
        completion = { }
    )

    public actual fun invalidateCustomerInfoCache(): Unit =
        iosPurchases.invalidateCustomerInfoCache()

    public actual fun setAttributes(attributes: Map<String, String?>): Unit =
        iosPurchases.setAttributes(attributes.mapKeys { (key, _) -> key })

    public actual fun setEmail(email: String?): Unit =
        iosPurchases.setEmail(email)

    public actual fun setPhoneNumber(phoneNumber: String?): Unit =
        iosPurchases.setPhoneNumber(phoneNumber)

    public actual fun setDisplayName(displayName: String?): Unit =
        iosPurchases.setDisplayName(displayName)

    public actual fun setPushToken(fcmToken: String?): Unit =
        iosPurchases.setPushTokenString(fcmToken)

    public actual fun setMixpanelDistinctID(mixpanelDistinctID: String?): Unit =
        iosPurchases.setMixpanelDistinctID(mixpanelDistinctID)

    public actual fun setOnesignalID(onesignalID: String?): Unit =
        iosPurchases.setOnesignalID(onesignalID)

    public actual fun setOnesignalUserID(onesignalUserID: String?): Unit =
        RCCommonFunctionality.setOnesignalUserID(onesignalUserID)

    public actual fun setAirshipChannelID(airshipChannelID: String?): Unit =
        RCCommonFunctionality.setAirshipChannelID(airshipChannelID)

    public actual fun setFirebaseAppInstanceID(firebaseAppInstanceID: String?): Unit =
        iosPurchases.setFirebaseAppInstanceID(firebaseAppInstanceID)

    public actual fun collectDeviceIdentifiers(): Unit =
        iosPurchases.collectDeviceIdentifiers()

    public actual fun setAdjustID(adjustID: String?): Unit =
        iosPurchases.setAdjustID(adjustID)

    public actual fun setAppsflyerID(appsflyerID: String?): Unit =
        iosPurchases.setAppsflyerID(appsflyerID)

    public actual fun setFBAnonymousID(fbAnonymousID: String?): Unit =
        iosPurchases.setFBAnonymousID(fbAnonymousID)

    public actual fun setMparticleID(mparticleID: String?): Unit =
        iosPurchases.setMparticleID(mparticleID)

    public actual fun setCleverTapID(cleverTapID: String?): Unit =
        iosPurchases.setCleverTapID(cleverTapID)

    public actual fun setMediaSource(mediaSource: String?): Unit =
        iosPurchases.setMediaSource(mediaSource)

    public actual fun setCampaign(campaign: String?): Unit =
        iosPurchases.setCampaign(campaign)

    public actual fun setAdGroup(adGroup: String?): Unit =
        iosPurchases.setAdGroup(adGroup)

    public actual fun setAd(ad: String?): Unit =
        iosPurchases.setAd(ad)

    public actual fun setKeyword(keyword: String?): Unit =
        iosPurchases.setKeyword(keyword)

    public actual fun setCreative(creative: String?): Unit =
        iosPurchases.setCreative(creative)
}
