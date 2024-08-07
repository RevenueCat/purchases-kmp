package com.revenuecat.purchases.kmp

import com.revenuecat.purchases.PurchaseParams
import com.revenuecat.purchases.common.PlatformInfo
import com.revenuecat.purchases.getCustomerInfoWith
import com.revenuecat.purchases.getOfferingsWith
import com.revenuecat.purchases.getProductsWith
import com.revenuecat.purchases.kmp.di.AndroidProvider
import com.revenuecat.purchases.kmp.di.requireActivity
import com.revenuecat.purchases.kmp.di.requireApplication
import com.revenuecat.purchases.kmp.models.BillingFeature
import com.revenuecat.purchases.kmp.models.GoogleReplacementMode
import com.revenuecat.purchases.kmp.models.PromotionalOffer
import com.revenuecat.purchases.kmp.models.StoreMessageType
import com.revenuecat.purchases.kmp.models.StoreProduct
import com.revenuecat.purchases.kmp.models.StoreProductDiscount
import com.revenuecat.purchases.kmp.models.StoreTransaction
import com.revenuecat.purchases.kmp.models.SubscriptionOption
import com.revenuecat.purchases.kmp.strings.ConfigureStrings
import com.revenuecat.purchases.logInWith
import com.revenuecat.purchases.logOutWith
import com.revenuecat.purchases.models.InAppMessageType
import com.revenuecat.purchases.purchaseWith
import com.revenuecat.purchases.restorePurchasesWith
import com.revenuecat.purchases.syncAttributesAndOfferingsIfNeededWith
import com.revenuecat.purchases.syncPurchasesWith
import java.net.URL
import com.revenuecat.purchases.DangerousSettings as AndroidDangerousSettings
import com.revenuecat.purchases.Purchases as AndroidPurchases
import com.revenuecat.purchases.hybridcommon.configure as commonConfigure

public actual class Purchases private constructor(private val androidPurchases: AndroidPurchases) {
    public actual companion object {

        private var _sharedInstance: Purchases? = null

        @JvmStatic
        public actual val sharedInstance: Purchases
            get() = _sharedInstance ?: throw UninitializedPropertyAccessException(
                ConfigureStrings.NO_SINGLETON_INSTANCE
            )

        @JvmStatic
        public actual var logLevel: LogLevel by AndroidPurchases.Companion::logLevel

        @JvmStatic
        public actual var logHandler: LogHandler by AndroidPurchases.Companion::logHandler

        @JvmStatic
        public actual var proxyURL: String?
            get() = AndroidPurchases.proxyURL?.toString()
            set(value) {
                AndroidPurchases.proxyURL = value?.let { URL(it) }
            }

        @JvmStatic
        public actual val isConfigured: Boolean by AndroidPurchases.Companion::isConfigured

        @JvmStatic
        public actual var simulatesAskToBuyInSandbox: Boolean = false

        @JvmStatic
        public actual var forceUniversalAppStore: Boolean = false

        @JvmStatic
        public actual fun configure(configuration: PurchasesConfiguration): Purchases {
            with(configuration) {
                // Using the common configure() call allows us to pass PlatformInfo.
                commonConfigure(
                    context = AndroidProvider.requireApplication(),
                    apiKey = apiKey,
                    appUserID = appUserId,
                    purchasesAreCompletedBy = purchasesAreCompletedBy,
                    platformInfo = PlatformInfo(
                        flavor = BuildKonfig.platformFlavor,
                        version = frameworkVersion,
                    ),
                    store = store ?: Store.PLAY_STORE,
                    dangerousSettings = dangerousSettings.toAndroidDangerousSettings(),
                    shouldShowInAppMessagesAutomatically = showInAppMessagesAutomatically,
                    verificationMode = verificationMode.name,
                )
            }

            return Purchases(AndroidPurchases.sharedInstance).also { _sharedInstance = it }
        }

        @JvmStatic
        public actual fun canMakePayments(
            features: List<BillingFeature>,
            callback: (Boolean) -> Unit,
        ): Unit = AndroidPurchases.canMakePayments(
            context = AndroidProvider.requireApplication(),
            features = features
        ) { result -> callback(result) }

        private fun DangerousSettings.toAndroidDangerousSettings(): AndroidDangerousSettings =
            AndroidDangerousSettings(autoSyncPurchases)
    }

    public actual var purchasesAreCompletedBy: PurchasesAreCompletedBy by androidPurchases::purchasesAreCompletedBy

    public actual val appUserID: String by androidPurchases::appUserID

    public actual var delegate: PurchasesDelegate?
        get() = androidPurchases.updatedCustomerInfoListener?.toPurchasesDelegate()
        set(value) {
            androidPurchases.updatedCustomerInfoListener = value?.toUpdatedCustomerInfoListener()
        }

    public actual val isAnonymous: Boolean by androidPurchases::isAnonymous

    public actual val store: Store by androidPurchases::store

    public actual fun syncPurchases(
        onError: (error: PurchasesError) -> Unit,
        onSuccess: (CustomerInfo) -> Unit,
    ): Unit = androidPurchases.syncPurchasesWith(
        onError = { onError(it.toPurchasesError()) },
        onSuccess = { onSuccess(it) },
    )

    public actual fun syncAmazonPurchase(
        productID: String,
        receiptID: String,
        amazonUserID: String,
        isoCurrencyCode: String?,
        price: Double?,
    ): Unit = androidPurchases.syncAmazonPurchase(
        productID = productID,
        receiptID = receiptID,
        amazonUserID = amazonUserID,
        isoCurrencyCode = isoCurrencyCode,
        price = price
    )

    public actual fun syncAttributesAndOfferingsIfNeeded(
        onError: (error: PurchasesError) -> Unit,
        onSuccess: (offerings: Offerings) -> Unit,
    ): Unit = androidPurchases.syncAttributesAndOfferingsIfNeededWith(
        onError = { error -> onError(error.toPurchasesError()) },
        onSuccess = onSuccess
    )

    public actual fun getOfferings(
        onError: (error: PurchasesError) -> Unit,
        onSuccess: (offerings: Offerings) -> Unit,
    ): Unit = androidPurchases.getOfferingsWith(
        onError = { error -> onError(error.toPurchasesError()) },
        onSuccess = { offerings -> onSuccess(offerings) }
    )

    public actual fun getProducts(
        productIds: List<String>,
        onError: (error: PurchasesError) -> Unit,
        onSuccess: (storeProducts: List<StoreProduct>) -> Unit,
    ): Unit = androidPurchases.getProductsWith(
        productIds = productIds,
        onError = { onError(it.toPurchasesError()) },
        onGetStoreProducts = { onSuccess(it.map { product -> StoreProduct(product) }) },
    )

    public actual fun getPromotionalOffer(
        discount: StoreProductDiscount,
        storeProduct: StoreProduct,
        onError: (error: PurchasesError) -> Unit,
        onSuccess: (offer: PromotionalOffer) -> Unit,
    ): Unit = error(
        "Getting promotional offers is not possible on Android. " +
                "Did you mean StoreProduct.subscriptionOptions?"
    )

    public actual fun purchase(
        storeProduct: StoreProduct,
        onError: (error: PurchasesError, userCancelled: Boolean) -> Unit,
        onSuccess: (storeTransaction: StoreTransaction, customerInfo: CustomerInfo) -> Unit,
        isPersonalizedPrice: Boolean?,
        oldProductId: String?,
        replacementMode: GoogleReplacementMode,
    ): Unit = androidPurchases.purchaseWith(
        purchaseParams = PurchaseParams.Builder(
            AndroidProvider.requireActivity(),
            storeProduct
        ).apply {
            if (isPersonalizedPrice != null) isPersonalizedPrice(isPersonalizedPrice)
            if (oldProductId != null) oldProductId(oldProductId)
        }.googleReplacementMode(replacementMode)
            .build(),
        onError = { error, userCancelled -> onError(error.toPurchasesError(), userCancelled) },
        onSuccess = { purchase, customerInfo -> onSuccess(purchase!!, customerInfo) },
    )

    public actual fun purchase(
        packageToPurchase: Package,
        onError: (error: PurchasesError, userCancelled: Boolean) -> Unit,
        onSuccess: (storeTransaction: StoreTransaction, customerInfo: CustomerInfo) -> Unit,
        isPersonalizedPrice: Boolean?,
        oldProductId: String?,
        replacementMode: GoogleReplacementMode,
    ): Unit = androidPurchases.purchaseWith(
        purchaseParams = PurchaseParams.Builder(
            AndroidProvider.requireActivity(),
            packageToPurchase
        ).apply {
            if (isPersonalizedPrice != null) isPersonalizedPrice(isPersonalizedPrice)
            if (oldProductId != null) oldProductId(oldProductId)
        }.googleReplacementMode(replacementMode)
            .build(),
        onError = { error, userCancelled -> onError(error.toPurchasesError(), userCancelled) },
        onSuccess = { purchase, customerInfo -> onSuccess(purchase!!, customerInfo) },
    )

    public actual fun purchase(
        subscriptionOption: SubscriptionOption,
        onError: (error: PurchasesError, userCancelled: Boolean) -> Unit,
        onSuccess: (storeTransaction: StoreTransaction, customerInfo: CustomerInfo) -> Unit,
        isPersonalizedPrice: Boolean?,
        oldProductId: String?,
        replacementMode: GoogleReplacementMode,
    ): Unit = androidPurchases.purchaseWith(
        purchaseParams = PurchaseParams.Builder(
            AndroidProvider.requireActivity(),
            subscriptionOption
        ).apply {
            if (isPersonalizedPrice != null) isPersonalizedPrice(isPersonalizedPrice)
            if (oldProductId != null) oldProductId(oldProductId)
        }.googleReplacementMode(replacementMode)
            .build(),
        onError = { error, userCancelled -> onError(error.toPurchasesError(), userCancelled) },
        onSuccess = { purchase, customerInfo -> onSuccess(purchase!!, customerInfo) },
    )

    public actual fun purchase(
        storeProduct: StoreProduct,
        promotionalOffer: PromotionalOffer,
        onError: (error: PurchasesError, userCancelled: Boolean) -> Unit,
        onSuccess: (storeTransaction: StoreTransaction, customerInfo: CustomerInfo) -> Unit,
    ): Unit = error(
        "Purchasing a StoreProduct with a PromotionalOffer is not possible on Android. " +
                "Did you mean purchase(SubscriptionOption)?"
    )

    public actual fun purchase(
        packageToPurchase: Package,
        promotionalOffer: PromotionalOffer,
        onError: (error: PurchasesError, userCancelled: Boolean) -> Unit,
        onSuccess: (storeTransaction: StoreTransaction, customerInfo: CustomerInfo) -> Unit,
    ): Unit = error(
        "Purchasing a Package with a PromotionalOffer is not possible on Android. " +
                "Did you mean purchase(SubscriptionOption)?"
    )

    public actual fun restorePurchases(
        onError: (error: PurchasesError) -> Unit,
        onSuccess: (customerInfo: CustomerInfo) -> Unit,
    ): Unit = androidPurchases.restorePurchasesWith(
        onError = { onError(it.toPurchasesError()) },
        onSuccess = { onSuccess(it) },
    )

    public actual fun logIn(
        newAppUserID: String,
        onError: (error: PurchasesError) -> Unit,
        onSuccess: (customerInfo: CustomerInfo, created: Boolean) -> Unit
    ): Unit = androidPurchases.logInWith(
        appUserID = newAppUserID,
        onError = { onError(it.toPurchasesError()) },
        onSuccess = { customerInfo, created -> onSuccess(customerInfo, created) }
    )

    public actual fun logOut(
        onError: (error: PurchasesError) -> Unit,
        onSuccess: (customerInfo: CustomerInfo) -> Unit,
    ): Unit = androidPurchases.logOutWith(
        onError = { onError(it.toPurchasesError()) },
        onSuccess = { onSuccess(it) },
    )

    public actual fun close(): Unit = androidPurchases.close()

    public actual fun getCustomerInfo(
        fetchPolicy: CacheFetchPolicy,
        onError: (error: PurchasesError) -> Unit,
        onSuccess: (customerInfo: CustomerInfo) -> Unit,
    ): Unit = androidPurchases.getCustomerInfoWith(
        fetchPolicy = fetchPolicy,
        onError = { onError(it.toPurchasesError()) },
        onSuccess = { onSuccess(it) }
    )

    public actual fun showInAppMessagesIfNeeded(
        messageTypes: List<StoreMessageType>,
    ): Unit = androidPurchases.showInAppMessagesIfNeeded(
        activity = AndroidProvider.requireActivity(),
        inAppMessageTypes = messageTypes.mapNotNull { it.toInAppMessageTypeOrNull() }
    )

    public actual fun invalidateCustomerInfoCache(): Unit =
        androidPurchases.invalidateCustomerInfoCache()

    public actual fun setAttributes(attributes: Map<String, String?>): Unit =
        androidPurchases.setAttributes(attributes)

    public actual fun setEmail(email: String?): Unit =
        androidPurchases.setEmail(email)

    public actual fun setPhoneNumber(phoneNumber: String?): Unit =
        androidPurchases.setPhoneNumber(phoneNumber)

    public actual fun setDisplayName(displayName: String?): Unit =
        androidPurchases.setDisplayName(displayName)

    public actual fun setPushToken(fcmToken: String?): Unit =
        androidPurchases.setPushToken(fcmToken)

    public actual fun setMixpanelDistinctID(mixpanelDistinctID: String?): Unit =
        androidPurchases.setMixpanelDistinctID(mixpanelDistinctID)

    public actual fun setOnesignalID(onesignalID: String?): Unit =
        androidPurchases.setOnesignalID(onesignalID)

    public actual fun setOnesignalUserID(onesignalUserID: String?): Unit =
        androidPurchases.setOnesignalUserID(onesignalUserID)

    public actual fun setAirshipChannelID(airshipChannelID: String?): Unit =
        androidPurchases.setAirshipChannelID(airshipChannelID)

    public actual fun setFirebaseAppInstanceID(firebaseAppInstanceID: String?): Unit =
        androidPurchases.setFirebaseAppInstanceID(firebaseAppInstanceID)

    public actual fun collectDeviceIdentifiers(): Unit =
        androidPurchases.collectDeviceIdentifiers()

    public actual fun setAdjustID(adjustID: String?): Unit =
        androidPurchases.setAdjustID(adjustID)

    public actual fun setAppsflyerID(appsflyerID: String?): Unit =
        androidPurchases.setAppsflyerID(appsflyerID)

    public actual fun setFBAnonymousID(fbAnonymousID: String?): Unit =
        androidPurchases.setFBAnonymousID(fbAnonymousID)

    public actual fun setMparticleID(mparticleID: String?): Unit =
        androidPurchases.setMparticleID(mparticleID)

    public actual fun setCleverTapID(cleverTapID: String?): Unit =
        androidPurchases.setCleverTapID(cleverTapID)

    public actual fun setMediaSource(mediaSource: String?): Unit =
        androidPurchases.setMediaSource(mediaSource)

    public actual fun setCampaign(campaign: String?): Unit =
        androidPurchases.setCampaign(campaign)

    public actual fun setAdGroup(adGroup: String?): Unit =
        androidPurchases.setAdGroup(adGroup)

    public actual fun setAd(ad: String?): Unit =
        androidPurchases.setAd(ad)

    public actual fun setKeyword(keyword: String?): Unit =
        androidPurchases.setKeyword(keyword)

    public actual fun setCreative(creative: String?): Unit =
        androidPurchases.setCreative(creative)

    private fun StoreMessageType.toInAppMessageTypeOrNull(): InAppMessageType? =
        when (this) {
            StoreMessageType.BILLING_ISSUES -> InAppMessageType.BILLING_ISSUES
            StoreMessageType.GENERIC,
            StoreMessageType.PRICE_INCREASE_CONSENT -> null
        }

}
