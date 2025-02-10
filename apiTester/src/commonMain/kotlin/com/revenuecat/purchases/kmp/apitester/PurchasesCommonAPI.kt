package com.revenuecat.purchases.kmp.apitester

import arrow.core.Either
import com.revenuecat.purchases.kmp.LogHandler
import com.revenuecat.purchases.kmp.LogLevel
import com.revenuecat.purchases.kmp.Purchases
import com.revenuecat.purchases.kmp.PurchasesConfiguration
import com.revenuecat.purchases.kmp.PurchasesDelegate
import com.revenuecat.purchases.kmp.configure
import com.revenuecat.purchases.kmp.either.FailedPurchase
import com.revenuecat.purchases.kmp.either.awaitEligibleWinBackOffersForPackageEither
import com.revenuecat.purchases.kmp.either.awaitEligibleWinBackOffersForProductEither
import com.revenuecat.purchases.kmp.either.awaitGetProductsEither
import com.revenuecat.purchases.kmp.either.awaitOfferingsEither
import com.revenuecat.purchases.kmp.either.awaitPurchaseEither
import com.revenuecat.purchases.kmp.ktx.SuccessfulPurchase
import com.revenuecat.purchases.kmp.ktx.awaitEligibleWinBackOffersForPackage
import com.revenuecat.purchases.kmp.ktx.awaitEligibleWinBackOffersForProduct
import com.revenuecat.purchases.kmp.ktx.awaitGetProducts
import com.revenuecat.purchases.kmp.ktx.awaitOfferings
import com.revenuecat.purchases.kmp.ktx.awaitPurchase
import com.revenuecat.purchases.kmp.ktx.awaitTrialOrIntroductoryPriceEligibility
import com.revenuecat.purchases.kmp.models.BillingFeature
import com.revenuecat.purchases.kmp.models.CustomerInfo
import com.revenuecat.purchases.kmp.models.DangerousSettings
import com.revenuecat.purchases.kmp.models.EntitlementVerificationMode
import com.revenuecat.purchases.kmp.models.GoogleReplacementMode
import com.revenuecat.purchases.kmp.models.IntroEligibility
import com.revenuecat.purchases.kmp.models.Offerings
import com.revenuecat.purchases.kmp.models.Package
import com.revenuecat.purchases.kmp.models.PurchasesAreCompletedBy
import com.revenuecat.purchases.kmp.models.PurchasesError
import com.revenuecat.purchases.kmp.models.Store
import com.revenuecat.purchases.kmp.models.StoreKitVersion
import com.revenuecat.purchases.kmp.models.StoreProduct
import com.revenuecat.purchases.kmp.models.StoreTransaction
import com.revenuecat.purchases.kmp.models.SubscriptionOption
import com.revenuecat.purchases.kmp.models.WinBackOffer
import com.revenuecat.purchases.kmp.result.awaitEligibleWinBackOffersForPackageResult
import com.revenuecat.purchases.kmp.result.awaitEligibleWinBackOffersForProductResult
import com.revenuecat.purchases.kmp.result.awaitGetProductsResult
import com.revenuecat.purchases.kmp.result.awaitOfferingsResult
import com.revenuecat.purchases.kmp.result.awaitPurchaseResult

@Suppress("unused", "UNUSED_VARIABLE", "UNUSED_ANONYMOUS_PARAMETER", "RedundantExplicitType")
private class PurchasesCommonAPI {
    fun check(
        purchases: Purchases,
    ) {
        val productIds = ArrayList<String>()

        purchases.getOfferings(
            onError = { error: PurchasesError -> },
            onSuccess = { offerings: Offerings -> }
        )

        purchases.getProducts(
            productIds,
            onError = { error: PurchasesError -> },
            onSuccess = { products: List<StoreProduct> -> }
        )

        purchases.restorePurchases(
            onError = { error: PurchasesError -> },
            onSuccess = { customerInfo: CustomerInfo -> }
        )

        val appUserID: String = purchases.appUserID

        purchases.close()

        val updatedCustomerInfoListener: PurchasesDelegate? = purchases.delegate
        purchases.delegate = object : PurchasesDelegate {
            override fun onPurchasePromoProduct(
                product: StoreProduct,
                startPurchase: (onError: (error: PurchasesError, userCancelled: Boolean) -> Unit, onSuccess: (storeTransaction: StoreTransaction, customerInfo: CustomerInfo) -> Unit) -> Unit
            ) {

            }

            override fun onCustomerInfoUpdated(customerInfo: CustomerInfo) {

            }
        }
    }

    fun checkPurchasing(
        purchases: Purchases,
        storeProduct: StoreProduct,
        packageToPurchase: Package,
        subscriptionOption: SubscriptionOption,
        winBackOffer: WinBackOffer,
    ) {
        val oldProductId = "old"
        val replacementMode = GoogleReplacementMode.WITH_TIME_PRORATION
        val isPersonalizedPrice = true

        purchases.purchase(
            packageToPurchase = packageToPurchase,
            onError = { error: PurchasesError, userCancelled: Boolean -> },
            onSuccess = { storeTransaction: StoreTransaction, customerInfo: CustomerInfo -> },
            isPersonalizedPrice = isPersonalizedPrice,
            oldProductId = oldProductId,
            replacementMode = replacementMode,
        )

        purchases.purchase(
            storeProduct = storeProduct,
            onError = { error: PurchasesError, userCancelled: Boolean -> },
            onSuccess = { storeTransaction: StoreTransaction, customerInfo: CustomerInfo -> },
            isPersonalizedPrice = isPersonalizedPrice,
            oldProductId = oldProductId,
            replacementMode = replacementMode,
        )

        purchases.purchase(
            subscriptionOption = subscriptionOption,
            onError = { error: PurchasesError, userCancelled: Boolean -> },
            onSuccess = { storeTransaction: StoreTransaction, customerInfo: CustomerInfo -> },
            isPersonalizedPrice = isPersonalizedPrice,
            oldProductId = oldProductId,
            replacementMode = replacementMode,
        )

        purchases.purchase(
            storeProduct = storeProduct,
            winBackOffer = winBackOffer,
            onError = { error: PurchasesError, userCancelled: Boolean -> },
            onSuccess = { transaction: StoreTransaction, customerInfo: CustomerInfo -> }
        )

        purchases.purchase(
            packageToPurchase = packageToPurchase,
            winBackOffer = winBackOffer,
            onError = { error: PurchasesError, userCancelled: Boolean -> },
            onSuccess = { transaction: StoreTransaction, customerInfo: CustomerInfo -> }
        )
    }

    suspend fun checkCoroutines(
        purchases: Purchases,
        storeProduct: StoreProduct,
        packageToPurchase: Package,
        subscriptionOption: SubscriptionOption,
        winBackOffer: WinBackOffer,
    ) {
        val offerings: Offerings = purchases.awaitOfferings()

        val oldProductId = "old"
        val replacementMode = GoogleReplacementMode.WITH_TIME_PRORATION
        val isPersonalizedPrice = true

        val successfulPurchasePackage: SuccessfulPurchase = purchases.awaitPurchase(
            packageToPurchase = packageToPurchase,
            isPersonalizedPrice = isPersonalizedPrice,
            oldProductId = oldProductId,
            replacementMode = replacementMode,
        )

        val successfulPurchaseProduct: SuccessfulPurchase = purchases.awaitPurchase(
            storeProduct = storeProduct,
            isPersonalizedPrice = isPersonalizedPrice,
            oldProductId = oldProductId,
            replacementMode = replacementMode,
        )

        val successfulPurchaseOption: SuccessfulPurchase = purchases.awaitPurchase(
            subscriptionOption = subscriptionOption,
            isPersonalizedPrice = isPersonalizedPrice,
            oldProductId = oldProductId,
            replacementMode = replacementMode,
        )

        val getProductsResult: List<StoreProduct> = purchases.awaitGetProducts(listOf("product"))

        val eligibleIntroOffers: Map<StoreProduct, IntroEligibility> =
            purchases.awaitTrialOrIntroductoryPriceEligibility(listOf(storeProduct))

        val eligibleWinBackOffersForProduct: List<WinBackOffer> = purchases.awaitEligibleWinBackOffersForProduct(
            storeProduct = storeProduct
        )

        val successfulPurchaseWithWinBackOfferForProduct: SuccessfulPurchase = purchases.awaitPurchase(
            storeProduct = storeProduct,
            winBackOffer = winBackOffer
        )

        val eligibleWinBackOffersForPackage: List<WinBackOffer> = purchases.awaitEligibleWinBackOffersForPackage(
            packageToCheck = packageToPurchase
        )

        val successfulPurchaseWithWinBackOfferForPackage: SuccessfulPurchase = purchases.awaitPurchase(
            packageToPurchase = packageToPurchase,
            winBackOffer = winBackOffer
        )
    }

    suspend fun checkCoroutinesResult(
        purchases: Purchases,
        storeProduct: StoreProduct,
        packageToPurchase: Package,
        subscriptionOption: SubscriptionOption,
        winBackOffer: WinBackOffer,
    ) {
        val offerings: Result<Offerings> = purchases.awaitOfferingsResult()

        val oldProductId = "old"
        val replacementMode = GoogleReplacementMode.WITH_TIME_PRORATION
        val isPersonalizedPrice = true

        val successfulPurchasePackage: Result<SuccessfulPurchase> = purchases.awaitPurchaseResult(
            packageToPurchase = packageToPurchase,
            isPersonalizedPrice = isPersonalizedPrice,
            oldProductId = oldProductId,
            replacementMode = replacementMode,
        )

        val successfulPurchaseProduct: Result<SuccessfulPurchase> = purchases.awaitPurchaseResult(
            storeProduct = storeProduct,
            isPersonalizedPrice = isPersonalizedPrice,
            oldProductId = oldProductId,
            replacementMode = replacementMode,
        )

        val successfulPurchaseOption: Result<SuccessfulPurchase> = purchases.awaitPurchaseResult(
            subscriptionOption = subscriptionOption,
            isPersonalizedPrice = isPersonalizedPrice,
            oldProductId = oldProductId,
            replacementMode = replacementMode,
        )

        val getProductsResult: Result<List<StoreProduct>> =
            purchases.awaitGetProductsResult(listOf("product"))

        val eligibleWinBackOffersForProduct: Result<List<WinBackOffer>> = purchases.awaitEligibleWinBackOffersForProductResult(
            storeProduct = storeProduct
        )

        val successfulPurchaseWithWinBackOfferForProduct: Result<SuccessfulPurchase> = purchases.awaitPurchaseResult(
            storeProduct = storeProduct,
            winBackOffer = winBackOffer
        )

        val eligibleWinBackOffersForPackage: Result<List<WinBackOffer>> = purchases.awaitEligibleWinBackOffersForPackageResult(
            packageToCheck = packageToPurchase
        )

        val successfulPurchaseWithWinBackOfferForPackage: Result<SuccessfulPurchase> = purchases.awaitPurchaseResult(
            packageToPurchase = packageToPurchase,
            winBackOffer = winBackOffer
        )
    }

    suspend fun checkCoroutinesEither(
        purchases: Purchases,
        storeProduct: StoreProduct,
        packageToPurchase: Package,
        subscriptionOption: SubscriptionOption,
        winBackOffer: WinBackOffer,
    ) {
        val offerings: Either<PurchasesError, Offerings> = purchases.awaitOfferingsEither()

        val oldProductId = "old"
        val replacementMode = GoogleReplacementMode.WITH_TIME_PRORATION
        val isPersonalizedPrice = true

        val successfulPurchasePackage: Either<FailedPurchase, SuccessfulPurchase> =
            purchases.awaitPurchaseEither(
                packageToPurchase = packageToPurchase,
                isPersonalizedPrice = isPersonalizedPrice,
                oldProductId = oldProductId,
                replacementMode = replacementMode,
            )

        val successfulPurchaseProduct: Either<FailedPurchase, SuccessfulPurchase> =
            purchases.awaitPurchaseEither(
                storeProduct = storeProduct,
                isPersonalizedPrice = isPersonalizedPrice,
                oldProductId = oldProductId,
                replacementMode = replacementMode,
            )

        val successfulPurchaseOption: Either<FailedPurchase, SuccessfulPurchase> =
            purchases.awaitPurchaseEither(
                subscriptionOption = subscriptionOption,
                isPersonalizedPrice = isPersonalizedPrice,
                oldProductId = oldProductId,
                replacementMode = replacementMode,
            )

        val getProductsResult: Either<PurchasesError, List<StoreProduct>> =
            purchases.awaitGetProductsEither(listOf("product"))

        val eligibleWinBackOffersForProduct: Either<PurchasesError, List<WinBackOffer>> = purchases.awaitEligibleWinBackOffersForProductEither(
            storeProduct = storeProduct
        )

        val successfulPurchaseWithWinBackOfferForProduct: Either<FailedPurchase, SuccessfulPurchase> = purchases.awaitPurchaseEither(
            storeProduct = storeProduct,
            winBackOffer = winBackOffer
        )

        val eligibleWinBackOffersForPackage: Either<PurchasesError, List<WinBackOffer>> = purchases.awaitEligibleWinBackOffersForPackageEither(
            packageToCheck = packageToPurchase
        )

        val successfulPurchaseWithWinBackOfferForPackage: Either<FailedPurchase, SuccessfulPurchase> = purchases.awaitPurchaseEither(
            packageToPurchase = packageToPurchase,
            winBackOffer = winBackOffer
        )
    }

    @Suppress("ForbiddenComment")
    fun checkConfiguration() {
        val features: List<BillingFeature> = ArrayList()
        val configured: Boolean = Purchases.isConfigured

        Purchases.canMakePayments(features) { _: Boolean -> }

        Purchases.logLevel = LogLevel.INFO
        val logLevel: LogLevel = Purchases.logLevel

        Purchases.proxyURL = ""
        val url: String? = Purchases.proxyURL

        val config: PurchasesConfiguration = PurchasesConfiguration(apiKey = "") {
            appUserId = ""
            purchasesAreCompletedBy = PurchasesAreCompletedBy.RevenueCat
            userDefaultsSuiteName = ""
            storeKitVersion = StoreKitVersion.DEFAULT
            showInAppMessagesAutomatically = true
            store = Store.PLAY_STORE
            diagnosticsEnabled = true
            dangerousSettings = DangerousSettings(autoSyncPurchases = true)
            verificationMode = EntitlementVerificationMode.INFORMATIONAL
            pendingTransactionsForPrepaidPlansEnabled = true
        }

        val config2: PurchasesConfiguration = PurchasesConfiguration(apiKey = "") {
            appUserId = ""
            purchasesAreCompletedBy = PurchasesAreCompletedBy.MyApp(StoreKitVersion.DEFAULT)
            userDefaultsSuiteName = ""
            showInAppMessagesAutomatically = true
            store = Store.PLAY_STORE
            diagnosticsEnabled = true
            dangerousSettings = DangerousSettings(autoSyncPurchases = true)
            verificationMode = EntitlementVerificationMode.INFORMATIONAL
        }

        val configuredInstance: Purchases = Purchases.configure(config)
        val otherConfiguredInstance: Purchases = Purchases.configure(apiKey = "") {
            appUserId = ""
            purchasesAreCompletedBy = PurchasesAreCompletedBy.MyApp(StoreKitVersion.DEFAULT)
            userDefaultsSuiteName = ""
            showInAppMessagesAutomatically = true
            store = Store.PLAY_STORE
            diagnosticsEnabled = true
            dangerousSettings = DangerousSettings(autoSyncPurchases = true)
            verificationMode = EntitlementVerificationMode.INFORMATIONAL
        }
        val instance: Purchases = Purchases.sharedInstance
    }
    
    fun checkRecordPurchase() {
        Purchases.sharedInstance.recordPurchase(
            productID = "myProductID",
            onError = { error ->  },
            onSuccess = { storeTransaction ->  }
        )
    }

    fun checkLogHandler() {
        Purchases.logHandler = object : LogHandler {
            override fun v(tag: String, msg: String) {}
            override fun d(tag: String, msg: String) {}
            override fun i(tag: String, msg: String) {}
            override fun w(tag: String, msg: String) {}
            override fun e(tag: String, msg: String, throwable: Throwable?) {}
        }
        val handler = Purchases.logHandler
    }

    fun checkFetchingTrialOrIntroductoryPriceEligibility(
        purchases: Purchases,
        storeProducts: List<StoreProduct>,
    ) {
        purchases.checkTrialOrIntroductoryPriceEligibility(
            products = storeProducts,
            callback = { introEligibilityMap: Map<StoreProduct, IntroEligibility> ->

            }
        )
    }

    fun checkFetchingEligibleWinBackOffers(
        purchases: Purchases,
        storeProduct: StoreProduct,
        packageToCheck: Package
    ) {
        purchases.getEligibleWinBackOffersForProduct(
            storeProduct = storeProduct,
            onError = { error: PurchasesError -> },
            onSuccess = { eligibleWinBackOffers: List<WinBackOffer> -> }
        )

        purchases.getEligibleWinBackOffersForPackage(
            packageToCheck = packageToCheck,
            onError = { error: PurchasesError -> },
            onSuccess = { eligibleWinBackOffers: List<WinBackOffer> -> }
        )
    }
}
