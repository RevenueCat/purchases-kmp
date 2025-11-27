package com.revenuecat.purchases.kmp

import swiftPMImport.com.revenuecat.purchases.models.IOSAPIAvailabilityChecker
import swiftPMImport.com.revenuecat.purchases.models.RCCommonFunctionality
import swiftPMImport.com.revenuecat.purchases.models.RCCustomerInfo
import swiftPMImport.com.revenuecat.purchases.models.RCIntroEligibility
import swiftPMImport.com.revenuecat.purchases.models.RCPurchaseParamsBuilder
import swiftPMImport.com.revenuecat.purchases.models.RCPurchasesDelegateProtocol
import swiftPMImport.com.revenuecat.purchases.models.RCStoreProduct
import swiftPMImport.com.revenuecat.purchases.models.RCStoreTransaction
import swiftPMImport.com.revenuecat.purchases.models.RCVirtualCurrencies
import swiftPMImport.com.revenuecat.purchases.models.configureWithAPIKey
import swiftPMImport.com.revenuecat.purchases.models.isWebPurchaseRedemptionURL
import swiftPMImport.com.revenuecat.purchases.models.parseAsWebPurchaseRedemptionWithUrlString
import swiftPMImport.com.revenuecat.purchases.models.recordPurchaseForProductID
import swiftPMImport.com.revenuecat.purchases.models.setAirshipChannelID
import swiftPMImport.com.revenuecat.purchases.models.setOnesignalUserID
import swiftPMImport.com.revenuecat.purchases.models.showStoreMessagesForTypes
import com.revenuecat.purchases.kmp.ktx.mapEntriesNotNull
import com.revenuecat.purchases.kmp.mappings.buildStoreTransaction
import com.revenuecat.purchases.kmp.mappings.toCustomerInfo
import com.revenuecat.purchases.kmp.mappings.toHybridString
import com.revenuecat.purchases.kmp.mappings.toIntroEligibilityStatus
import com.revenuecat.purchases.kmp.mappings.toIosCacheFetchPolicy
import com.revenuecat.purchases.kmp.mappings.toIosPackage
import com.revenuecat.purchases.kmp.mappings.toIosPromotionalOffer
import com.revenuecat.purchases.kmp.mappings.toIosStoreProduct
import com.revenuecat.purchases.kmp.mappings.toIosStoreProductDiscount
import com.revenuecat.purchases.kmp.mappings.toIosWinBackOffer
import com.revenuecat.purchases.kmp.mappings.toOfferings
import com.revenuecat.purchases.kmp.mappings.toPromotionalOffer
import com.revenuecat.purchases.kmp.mappings.toPurchasesErrorOrThrow
import com.revenuecat.purchases.kmp.mappings.toStoreProduct
import com.revenuecat.purchases.kmp.mappings.toStoreTransaction
import com.revenuecat.purchases.kmp.mappings.toStorefront
import com.revenuecat.purchases.kmp.mappings.toVirtualCurrencies
import com.revenuecat.purchases.kmp.mappings.toWinBackOffer
import com.revenuecat.purchases.kmp.models.BillingFeature
import com.revenuecat.purchases.kmp.models.CacheFetchPolicy
import com.revenuecat.purchases.kmp.models.CustomerInfo
import com.revenuecat.purchases.kmp.models.DangerousSettings
import com.revenuecat.purchases.kmp.models.IntroEligibilityStatus
import com.revenuecat.purchases.kmp.models.Offerings
import com.revenuecat.purchases.kmp.models.Package
import com.revenuecat.purchases.kmp.models.PromotionalOffer
import com.revenuecat.purchases.kmp.models.PurchasesError
import com.revenuecat.purchases.kmp.models.PurchasesErrorCode
import com.revenuecat.purchases.kmp.models.RedeemWebPurchaseListener
import com.revenuecat.purchases.kmp.models.ReplacementMode
import com.revenuecat.purchases.kmp.models.Store
import com.revenuecat.purchases.kmp.models.StoreMessageType
import com.revenuecat.purchases.kmp.models.StoreProduct
import com.revenuecat.purchases.kmp.models.StoreProductDiscount
import com.revenuecat.purchases.kmp.models.StoreTransaction
import com.revenuecat.purchases.kmp.models.Storefront
import com.revenuecat.purchases.kmp.models.SubscriptionOption
import com.revenuecat.purchases.kmp.models.VirtualCurrencies
import com.revenuecat.purchases.kmp.models.VirtualCurrency
import com.revenuecat.purchases.kmp.models.WebPurchaseRedemption
import com.revenuecat.purchases.kmp.models.WinBackOffer
import com.revenuecat.purchases.kmp.strings.ConfigureStrings
import platform.Foundation.NSError
import platform.Foundation.NSURL
import swiftPMImport.com.revenuecat.purchases.models.RCDangerousSettings as IosDangerousSettings
import swiftPMImport.com.revenuecat.purchases.models.RCPurchases as IosPurchases
import swiftPMImport.com.revenuecat.purchases.models.RCWinBackOffer as NativeIosWinBackOffer

public actual class Purchases private constructor(private val iosPurchases: IosPurchases) {
    public actual companion object {
        private var _sharedInstance: Purchases? = null
        public actual val sharedInstance: Purchases
            get() = _sharedInstance ?: error(ConfigureStrings.NO_SINGLETON_INSTANCE)

        public actual var logLevel: LogLevel
            get() = IosPurchases.logLevel().toLogLevel()
            set(value) = IosPurchases.setLogLevel(value.toRcLogLevel())

        public actual var logHandler: LogHandler
            get() = IosPurchases.logHandler().toLogHandler()
            set(value) {
                IosPurchases.setLogHandler(value.toIosLogHandler())
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
            checkCommonVersion()

            return with(configuration) {
                IosPurchases.configureWithAPIKey(
                    apiKey = apiKey,
                    appUserID = appUserId,
                    purchasesAreCompletedBy = purchasesAreCompletedBy.toHybridString(),
                    userDefaultsSuiteName = userDefaultsSuiteName,
                    platformFlavor = BuildKonfig.platformFlavor,
                    platformFlavorVersion = frameworkVersion,
                    storeKitVersion = storeKitVersion.toHybridString(),
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
            val actual = RCCommonFunctionality.hybridCommonVersion()
            check(actual == expected) {
                "Unexpected version of PurchasesHybridCommon ('$actual'). Make sure to use " +
                        "'$expected' exactly."
            }
        }

        private fun DangerousSettings.toIosDangerousSettings(): IosDangerousSettings =
            IosDangerousSettings(autoSyncPurchases)

        public actual fun parseAsWebPurchaseRedemption(url: String): WebPurchaseRedemption? {
            return if (RCCommonFunctionality.isWebPurchaseRedemptionURL(url)) {
                WebPurchaseRedemption(url)
            } else {
                null
            }
        }
    }

    public actual val appUserID: String
        get() = iosPurchases.appUserID()

    /**
     * Making sure we keep a strong reference to our delegate wrapper, as iosPurchases only keeps
     * a weak one. This avoids the wrapper from being deallocated the first chance it gets. This
     * behavior matches the Android platform behavior.
     */
    private var _delegateWrapper: RCPurchasesDelegateProtocol? = null
    public actual var delegate: PurchasesDelegate?
        get() = iosPurchases.delegate()?.toPurchasesDelegate()
        set(value) {
            _delegateWrapper = value.toRcPurchasesDelegate()
            iosPurchases.setDelegate(_delegateWrapper)
        }

    public actual val isAnonymous: Boolean
        get() = iosPurchases.isAnonymous()

    public actual val store: Store
        get() = Store.APP_STORE

    public actual fun getStorefront(callback: ((Storefront?) -> Unit)) {
        iosPurchases.getStorefrontWithCompletionHandler { storefront ->
            if (storefront == null) {
                callback(null)
            } else {
                callback(storefront.toStorefront())
            }
        }
    }

    public actual fun syncPurchases(
        onError: (error: PurchasesError) -> Unit,
        onSuccess: (CustomerInfo) -> Unit,
    ): Unit = iosPurchases.syncPurchasesWithCompletionHandler { customerInfo, error ->
        if (error != null) onError(error.toPurchasesErrorOrThrow())
        else onSuccess(customerInfo?.toCustomerInfo() ?: error("Expected a non-null RCCustomerInfo"))
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
        else onSuccess(offerings?.toOfferings() ?: error("Expected a non-null RCOfferings"))
    }

    public actual fun getOfferings(
        onError: (error: PurchasesError) -> Unit,
        onSuccess: (offerings: Offerings) -> Unit,
    ): Unit = iosPurchases.getOfferingsWithCompletion { offerings, error ->
        if (error != null) onError(error.toPurchasesErrorOrThrow())
        else onSuccess(offerings?.toOfferings() ?: error("Expected a non-null RCOfferings"))
    }

    public actual fun getProducts(
        productIds: List<String>,
        onError: (error: PurchasesError) -> Unit,
        onSuccess: (storeProducts: List<StoreProduct>) -> Unit,
    ): Unit = iosPurchases.getProductsWithIdentifiers(
        productIdentifiers = productIds,
        completion = {
            onSuccess(it.orEmpty().map { product -> (product as RCStoreProduct).toStoreProduct() })
        },
    )

    public actual fun getPromotionalOffer(
        discount: StoreProductDiscount,
        storeProduct: StoreProduct,
        onError: (error: PurchasesError) -> Unit,
        onSuccess: (offer: PromotionalOffer) -> Unit,
    ): Unit = iosPurchases.getPromotionalOfferForProductDiscount(
        discount = discount.toIosStoreProductDiscount(),
        withProduct = storeProduct.toIosStoreProduct(),
    ) { offer, error ->
        if (error != null) onError(error.toPurchasesErrorOrThrow())
        else onSuccess(
            offer?.toPromotionalOffer()
            ?: error("Expected a non-null RCPromotionalOffer"))
    }

    public actual fun purchase(
        storeProduct: StoreProduct,
        onError: (error: PurchasesError, userCancelled: Boolean) -> Unit,
        onSuccess: (storeTransaction: StoreTransaction, customerInfo: CustomerInfo) -> Unit,
        isPersonalizedPrice: Boolean?,
        oldProductId: String?,
        replacementMode: ReplacementMode?,
    ): Unit = iosPurchases.purchaseProduct(
        storeProduct.toIosStoreProduct()
    ) { transaction, customerInfo, error, userCancelled ->
        if (error != null) onError(error.toPurchasesErrorOrThrow(), userCancelled)
        else onSuccess(
            transaction?.toStoreTransaction()
                ?: error("Expected a non-null RCStoreTransaction"),
            customerInfo?.toCustomerInfo() ?: error("Expected a non-null RCCustomerInfo")
        )
    }

    public actual fun purchase(
        packageToPurchase: Package,
        onError: (error: PurchasesError, userCancelled: Boolean) -> Unit,
        onSuccess: (storeTransaction: StoreTransaction, customerInfo: CustomerInfo) -> Unit,
        isPersonalizedPrice: Boolean?,
        oldProductId: String?,
        replacementMode: ReplacementMode?,
    ): Unit = iosPurchases.purchasePackage(
        packageToPurchase.toIosPackage(),
    ) { transaction, customerInfo, error, userCancelled ->
        if (error != null) onError(error.toPurchasesErrorOrThrow(), userCancelled)
        else onSuccess(
            transaction?.toStoreTransaction()
                ?: error("Expected a non-null RCStoreTransaction"),
            customerInfo?.toCustomerInfo() ?: error("Expected a non-null RCCustomerInfo")
        )
    }

    public actual fun purchase(
        subscriptionOption: SubscriptionOption,
        onError: (error: PurchasesError, userCancelled: Boolean) -> Unit,
        onSuccess: (storeTransaction: StoreTransaction, customerInfo: CustomerInfo) -> Unit,
        isPersonalizedPrice: Boolean?,
        oldProductId: String?,
        replacementMode: ReplacementMode?,
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
        storeProduct.toIosStoreProduct(),
        promotionalOffer.toIosPromotionalOffer()
    ) { transaction, customerInfo, error, userCancelled ->
        if (error != null) onError(error.toPurchasesErrorOrThrow(), userCancelled)
        else onSuccess(
            transaction?.toStoreTransaction()
                ?: error("Expected a non-null RCStoreTransaction"),
            customerInfo?.toCustomerInfo() ?: error("Expected a non-null RCCustomerInfo")
        )
    }

    public actual fun purchase(
        packageToPurchase: Package,
        promotionalOffer: PromotionalOffer,
        onError: (error: PurchasesError, userCancelled: Boolean) -> Unit,
        onSuccess: (storeTransaction: StoreTransaction, customerInfo: CustomerInfo) -> Unit,
    ): Unit = iosPurchases.purchasePackage(
        packageToPurchase.toIosPackage(),
        promotionalOffer.toIosPromotionalOffer()
    ) { transaction, customerInfo, error, userCancelled ->
        if (error != null) onError(error.toPurchasesErrorOrThrow(), userCancelled)
        else onSuccess(
            transaction?.toStoreTransaction()
                ?: error("Expected a non-null RCStoreTransaction"),
            customerInfo?.toCustomerInfo() ?: error("Expected a non-null RCCustomerInfo")
        )
    }

    public actual fun restorePurchases(
        onError: (error: PurchasesError) -> Unit,
        onSuccess: (customerInfo: CustomerInfo) -> Unit,
    ): Unit = iosPurchases.restorePurchasesWithCompletion { customerInfo, error ->
        if (error != null) onError(error.toPurchasesErrorOrThrow())
        else onSuccess(customerInfo?.toCustomerInfo() ?: error("Expected a non-null RCCustomerInfo"))
    }

    public actual fun recordPurchase(
        productID: String,
        onError: (error: PurchasesError) -> Unit,
        onSuccess: (storeTransaction: StoreTransaction) -> Unit,
    ) {
        RCCommonFunctionality.recordPurchaseForProductID(
            productID,
            completion = { storeTransactionMap, error ->
                if (error != null) {
                    onError(error.error().toPurchasesErrorOrThrow())
                    return@recordPurchaseForProductID
                }

                if (storeTransactionMap == null) {
                    onError(
                        PurchasesError(
                            code = PurchasesErrorCode.UnknownError,
                            underlyingErrorMessage =
                            "Expected storeTransactionMap to be non-null when error is non-null."
                        )
                    )
                } else {
                    val storeTransactionMappingResult = buildStoreTransaction(
                        storeTransactionMap = storeTransactionMap
                    )
                    storeTransactionMappingResult.onSuccess {
                        onSuccess(it)
                    }
                    storeTransactionMappingResult.onFailure {
                        onError(
                            PurchasesError(
                                code = PurchasesErrorCode.UnknownError,
                                underlyingErrorMessage = it.message
                            )
                        )
                    }
                }
            }
        )
    }

    public actual fun checkTrialOrIntroPriceEligibility(
        products: List<StoreProduct>,
        callback: (Map<StoreProduct, IntroEligibilityStatus>) -> Unit,
    ) {
        val productsById = products.associateBy { it.id }
        iosPurchases.checkTrialOrIntroDiscountEligibility(productsById.keys.toList()) { map ->
            val eligibilityByProduct =
                map.orEmpty().mapEntriesNotNull { (productId, iosEligibility) ->
                    productsById[productId as String]?.let { product ->
                        product to (iosEligibility as RCIntroEligibility).toIntroEligibilityStatus()
                    }
                }

            callback(eligibilityByProduct)
        }
    }

    public actual fun getEligibleWinBackOffersForProduct(
        storeProduct: StoreProduct,
        onError: (error: PurchasesError) -> Unit,
        onSuccess: (List<WinBackOffer>) -> Unit,
    ) {
        // API availability checks must be performed here at the KMP level, since the KMP/ObjC/Swift
        // interoperability drops the @available(osVersion) requirements, and you can technically
        // call functions with an @available from any OS version in KMP
        if (!IOSAPIAvailabilityChecker().isWinBackOfferAPIAvailable()) {
            onError(
                PurchasesError(
                    PurchasesErrorCode.UnsupportedError,
                    underlyingErrorMessage = "getEligibleWinBackOffersForProduct is only available on iOS 18.0+"
                )
            )
            return
        }

        iosPurchases.eligibleWinBackOffersForProduct(
            product = storeProduct.toIosStoreProduct(),
            completion = { eligibleWinBackOffers, error ->

                if (error != null) {
                    try {
                        onError(error.toPurchasesErrorOrThrow())
                    } catch(e: IllegalStateException) {
                        onError(
                            PurchasesError(
                                code = PurchasesErrorCode.UnknownError,
                                underlyingErrorMessage =
                                "An unknown error occurred. Could not convert error to a PurchasesError."
                            )
                        )
                    }
                    return@eligibleWinBackOffersForProduct
                }

                val typedEligibleWinBackOffers = eligibleWinBackOffers?.mapNotNull {
                    (it as? NativeIosWinBackOffer)?.toWinBackOffer()
                } ?: emptyList()

                onSuccess(typedEligibleWinBackOffers)
            }
        )
    }

    public actual fun getEligibleWinBackOffersForPackage(
        packageToCheck: Package,
        onError: (error: PurchasesError) -> Unit,
        onSuccess: (List<WinBackOffer>) -> Unit,
    ) {
        // API availability checks must be performed here at the KMP level, since the KMP/ObjC/Swift
        // interoperability drops the @available(osVersion) requirements, and you can technically
        // call functions with an @available from any OS version in KMP
        if (!IOSAPIAvailabilityChecker().isWinBackOfferAPIAvailable()) {
            onError(
                PurchasesError(
                    PurchasesErrorCode.UnsupportedError,
                    underlyingErrorMessage = "getEligibleWinBackOffersForPackage is only available on iOS 18.0+"
                )
            )
            return
        }

        iosPurchases.eligibleWinBackOffersForPackage(
            packageToCheck.toIosPackage(),
            completion = { eligibleWinBackOffers, error ->

                if (error != null) {
                    try {
                        onError(error.toPurchasesErrorOrThrow())
                    } catch(e: IllegalStateException) {
                        onError(
                            PurchasesError(
                                code = PurchasesErrorCode.UnknownError,
                                underlyingErrorMessage =
                                "An unknown error occurred. Could not convert error to a PurchasesError."
                            )
                        )
                    }
                    return@eligibleWinBackOffersForPackage
                }

                val typedEligibleWinBackOffers = eligibleWinBackOffers?.mapNotNull {
                    (it as? NativeIosWinBackOffer)?.toWinBackOffer()
                } ?: emptyList()

                onSuccess(typedEligibleWinBackOffers)
            }
        )
    }

    public actual fun purchase(
        storeProduct: StoreProduct,
        winBackOffer: WinBackOffer,
        onError: (error: PurchasesError, userCancelled: Boolean) -> Unit,
        onSuccess: (transaction: StoreTransaction, customerInfo: CustomerInfo) -> Unit,
    ) {
        // API availability checks must be performed here at the KMP level, since the KMP/ObjC/Swift
        // interoperability drops the @available(osVersion) requirements, and you can technically
        // call functions with an @available from any OS version in KMP
        if (!IOSAPIAvailabilityChecker().isWinBackOfferAPIAvailable()) {
            onError(
                PurchasesError(
                    PurchasesErrorCode.UnsupportedError,
                    underlyingErrorMessage = "purchase(product:winBackOffer:onError:onSuccess:) is only available on iOS 18.0+"
                ),
                false
            )
            return
        }

        val purchaseParams = RCPurchaseParamsBuilder(product = storeProduct.toIosStoreProduct())
            .withWinBackOffer(winBackOffer.toIosWinBackOffer())
            .build()

        iosPurchases.purchaseWithParams(
            params = purchaseParams,
            completion = {
                transaction: RCStoreTransaction?,
                customerInfo: RCCustomerInfo?,
                error: NSError?,
                userCancelled: Boolean ->

                if (error != null) {
                    try {
                        onError(error.toPurchasesErrorOrThrow(), userCancelled)
                    } catch(e: IllegalStateException) {
                        onError(
                            PurchasesError(
                                code = PurchasesErrorCode.UnknownError,
                                underlyingErrorMessage =
                                "An unknown error occurred. Could not convert error to a PurchasesError."
                            ),
                            false
                        )
                    }
                    return@purchaseWithParams
                }

                onSuccess(
                    transaction?.toStoreTransaction()
                        ?: error("Expected a non-null RCStoreTransaction"),
                    customerInfo?.toCustomerInfo() ?: error("Expected a non-null RCCustomerInfo")
                )
            }
        )
    }

    public actual fun purchase(
        packageToPurchase: Package,
        winBackOffer: WinBackOffer,
        onError: (error: PurchasesError, userCancelled: Boolean) -> Unit,
        onSuccess: (transaction: StoreTransaction, customerInfo: CustomerInfo) -> Unit,
    ) {
        // API availability checks must be performed here at the KMP level, since the KMP/ObjC/Swift
        // interoperability drops the @available(osVersion) requirements, and you can technically
        // call functions with an @available from any OS version in KMP
        if (!IOSAPIAvailabilityChecker().isWinBackOfferAPIAvailable()) {
            onError(
                PurchasesError(
                    PurchasesErrorCode.UnsupportedError,
                    underlyingErrorMessage = "purchase(packageToPurchase:winBackOffer:onError:onSuccess:) is only available on iOS 18.0+"
                ),
                false
            )
            return
        }

        val purchaseParams = RCPurchaseParamsBuilder(`package` = packageToPurchase.toIosPackage())
            .withWinBackOffer(winBackOffer.toIosWinBackOffer())
            .build()

        iosPurchases.purchaseWithParams(
            params = purchaseParams,
            completion = {
                transaction: RCStoreTransaction?,
                customerInfo: RCCustomerInfo?,
                error: NSError?,
                userCancelled: Boolean ->

                if (error != null) {
                    try {
                        onError(error.toPurchasesErrorOrThrow(), userCancelled)
                    } catch(e: IllegalStateException) {
                        onError(
                            PurchasesError(
                                code = PurchasesErrorCode.UnknownError,
                                underlyingErrorMessage =
                                "An unknown error occurred. Could not convert error to a PurchasesError."
                            ),
                            false
                        )
                    }
                    return@purchaseWithParams
                }

                onSuccess(
                    transaction?.toStoreTransaction()
                        ?: error("Expected a non-null RCStoreTransaction"),
                    customerInfo?.toCustomerInfo() ?: error("Expected a non-null RCCustomerInfo")
                )
            }
        )
    }

    public actual fun logIn(
        newAppUserID: String,
        onError: (error: PurchasesError) -> Unit,
        onSuccess: (customerInfo: CustomerInfo, created: Boolean) -> Unit
    ): Unit = iosPurchases.logIn(
        appUserID = newAppUserID,
        completion = { customerInfo, created, error ->
            if (error != null) onError(error.toPurchasesErrorOrThrow())
            else onSuccess(customerInfo?.toCustomerInfo() ?: error("Expected a non-null RCCustomerInfo"), created)
        }
    )

    public actual fun logOut(
        onError: (error: PurchasesError) -> Unit,
        onSuccess: (customerInfo: CustomerInfo) -> Unit,
    ): Unit = iosPurchases.logOutWithCompletion { customerInfo, error ->
        if (error != null) onError(error.toPurchasesErrorOrThrow())
        else onSuccess(customerInfo?.toCustomerInfo() ?: error("Expected a non-null RCCustomerInfo"))
    }

    public actual fun close() {
        delegate = null
    }

    public actual fun getCustomerInfo(
        fetchPolicy: CacheFetchPolicy,
        onError: (error: PurchasesError) -> Unit,
        onSuccess: (customerInfo: CustomerInfo) -> Unit,
    ): Unit = iosPurchases.getCustomerInfoWithFetchPolicy(
        fetchPolicy.toIosCacheFetchPolicy()
    ) { customerInfo, error ->
        if (error != null) onError(error.toPurchasesErrorOrThrow())
        else onSuccess(customerInfo?.toCustomerInfo() ?: error("Expected a non-null RCCustomerInfo"))
    }

    public actual fun showInAppMessagesIfNeeded(
        messageTypes: List<StoreMessageType>,
    ): Unit = RCCommonFunctionality.showStoreMessagesForTypes(
        rawValues = messageTypes.mapTo(mutableSetOf()) { type ->
            when (type) {
                StoreMessageType.BILLING_ISSUES -> 0
                StoreMessageType.PRICE_INCREASE_CONSENT -> 1
                StoreMessageType.GENERIC -> 2
                StoreMessageType.WIN_BACK_OFFER -> 3
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

    public actual fun enableAdServicesAttributionTokenCollection() {
        if (IOSAPIAvailabilityChecker().isEnableAdServicesAttributionTokenCollectionAPIAvailable())
            RCCommonFunctionality.enableAdServicesAttributionTokenCollection()
        else logHandler.d(
            tag = "Purchases",
            msg = "`enableAdServicesAttributionTokenCollection()` is only available on iOS 14.3 " +
                    "and up."
        )
    }

    public actual fun redeemWebPurchase(
        webPurchaseRedemption: WebPurchaseRedemption,
        listener: RedeemWebPurchaseListener,
    ) {
        val nativeWebPurchaseRedemption = RCCommonFunctionality.parseAsWebPurchaseRedemptionWithUrlString(
            webPurchaseRedemption.redemptionUrl,
        )
        if (nativeWebPurchaseRedemption == null) {
            listener.handleResult(RedeemWebPurchaseListener.Result.Error(
                PurchasesError(
                    code = PurchasesErrorCode.ConfigurationError,
                    underlyingErrorMessage = "Invalid web purchase redemption URL."
                )
            ))
            return
        }
        iosPurchases.redeemWebPurchaseWithWebPurchaseRedemption(
            nativeWebPurchaseRedemption,
        ) { rcCustomerInfo, nsError ->
            if (nsError != null) {
                val errorCode = nsError.code.toInt()
                val result = when (errorCode) {
                    PurchasesErrorCode.InvalidWebPurchaseToken.code ->
                        RedeemWebPurchaseListener.Result.InvalidToken
                    PurchasesErrorCode.PurchaseBelongsToOtherUser.code ->
                        RedeemWebPurchaseListener.Result.PurchaseBelongsToOtherUser
                    PurchasesErrorCode.ExpiredWebPurchaseToken.code ->
                        RedeemWebPurchaseListener.Result.Expired(
                            nsError.userInfo["rc_obfuscated_email"] as String? ?: ""
                        )
                    else ->
                        RedeemWebPurchaseListener.Result.Error(nsError.toPurchasesErrorOrThrow())
                }
                listener.handleResult(result)
                return@redeemWebPurchaseWithWebPurchaseRedemption
            }
            if (rcCustomerInfo == null) {
                listener.handleResult(
                    RedeemWebPurchaseListener.Result.Error(
                        PurchasesError(
                            code = PurchasesErrorCode.UnknownError,
                            underlyingErrorMessage = "Expected a non-null RCCustomerInfo when error is null."
                        )
                    )
                )
                return@redeemWebPurchaseWithWebPurchaseRedemption
            }
            listener.handleResult(
                RedeemWebPurchaseListener.Result.Success(
                    rcCustomerInfo.toCustomerInfo()
                )
            )
        }
    }

    public actual fun getVirtualCurrencies(
        onError: (error: PurchasesError) -> Unit,
        onSuccess: (virtualCurrencies: VirtualCurrencies) -> Unit,
    ): Unit = iosPurchases.getVirtualCurrenciesWithCompletion { virtualCurrencies, error ->
        if (error != null) { onError(error.toPurchasesErrorOrThrow()) }
        else onSuccess(virtualCurrencies?.toVirtualCurrencies() ?: error("Expected a non-null RCVirtualCurrencies"))
    }

    public actual fun invalidateVirtualCurrenciesCache(): Unit = iosPurchases.invalidateVirtualCurrenciesCache()

    public actual fun getCachedVirtualCurrencies(): VirtualCurrencies? {
        val cachedVirtualCurrencies: RCVirtualCurrencies? = iosPurchases.cachedVirtualCurrencies()
        return cachedVirtualCurrencies?.toVirtualCurrencies()
    }
}
