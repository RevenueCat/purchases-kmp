package io.shortway.kobankat.apitester

import arrow.core.Either
import com.revenuecat.purchases.kmp.CustomerInfo
import com.revenuecat.purchases.kmp.DangerousSettings
import com.revenuecat.purchases.kmp.EntitlementVerificationMode
import com.revenuecat.purchases.kmp.LogHandler
import com.revenuecat.purchases.kmp.LogLevel
import com.revenuecat.purchases.kmp.Offerings
import com.revenuecat.purchases.kmp.Package
import com.revenuecat.purchases.kmp.Purchases
import com.revenuecat.purchases.kmp.PurchasesConfiguration
import com.revenuecat.purchases.kmp.PurchasesDelegate
import com.revenuecat.purchases.kmp.PurchasesError
import com.revenuecat.purchases.kmp.PurchasesFactory
import com.revenuecat.purchases.kmp.appUserID
import com.revenuecat.purchases.kmp.close
import com.revenuecat.purchases.kmp.configure
import com.revenuecat.purchases.kmp.delegate
import com.revenuecat.purchases.kmp.getOfferings
import com.revenuecat.purchases.kmp.getProducts
import com.revenuecat.purchases.kmp.ktx.SuccessfulPurchase
import com.revenuecat.purchases.kmp.ktx.awaitGetProducts
import com.revenuecat.purchases.kmp.ktx.awaitOfferings
import com.revenuecat.purchases.kmp.ktx.awaitPurchase
import com.revenuecat.purchases.kmp.models.BillingFeature
import com.revenuecat.purchases.kmp.models.GoogleReplacementMode
import com.revenuecat.purchases.kmp.models.StoreProduct
import com.revenuecat.purchases.kmp.models.StoreTransaction
import com.revenuecat.purchases.kmp.models.SubscriptionOption
import com.revenuecat.purchases.kmp.purchase
import com.revenuecat.purchases.kmp.restorePurchases
import io.shortway.kobankat.either.FailedPurchase
import io.shortway.kobankat.either.awaitGetProductsEither
import io.shortway.kobankat.either.awaitOfferingsEither
import io.shortway.kobankat.either.awaitPurchaseEither
import io.shortway.kobankat.result.awaitGetProductsResult
import io.shortway.kobankat.result.awaitOfferingsResult
import io.shortway.kobankat.result.awaitPurchaseResult

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
    }

    suspend fun checkCoroutines(
        purchases: Purchases,
        storeProduct: StoreProduct,
        packageToPurchase: Package,
        subscriptionOption: SubscriptionOption,
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
    }

    suspend fun checkCoroutinesResult(
        purchases: Purchases,
        storeProduct: StoreProduct,
        packageToPurchase: Package,
        subscriptionOption: SubscriptionOption,
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
    }

    suspend fun checkCoroutinesEither(
        purchases: Purchases,
        storeProduct: StoreProduct,
        packageToPurchase: Package,
        subscriptionOption: SubscriptionOption,
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
    }

    @Suppress("ForbiddenComment")
    fun checkConfiguration() {
        val features: List<BillingFeature> = ArrayList()
        val configured: Boolean = PurchasesFactory.isConfigured

        PurchasesFactory.canMakePayments(features) { _: Boolean -> }

        PurchasesFactory.logLevel = LogLevel.INFO
        val logLevel: LogLevel = PurchasesFactory.logLevel

        PurchasesFactory.proxyURL = ""
        val url: String? = PurchasesFactory.proxyURL

        val config: PurchasesConfiguration = PurchasesConfiguration(apiKey = "") {
            appUserId = ""
            observerMode = true
            showInAppMessagesAutomatically = true
            dangerousSettings = DangerousSettings(autoSyncPurchases = true)
            verificationMode = EntitlementVerificationMode.INFORMATIONAL
        }

        val configuredInstance: Purchases = PurchasesFactory.configure(config)
        val otherConfiguredInstance: Purchases = PurchasesFactory.configure(apiKey = "") {
            appUserId = ""
            observerMode = true
            showInAppMessagesAutomatically = true
            dangerousSettings = DangerousSettings(autoSyncPurchases = true)
            verificationMode = EntitlementVerificationMode.INFORMATIONAL
        }
        val instance: Purchases = PurchasesFactory.sharedInstance
    }

    fun checkLogHandler() {
        PurchasesFactory.logHandler = object : LogHandler {
            override fun v(tag: String, msg: String) {}
            override fun d(tag: String, msg: String) {}
            override fun i(tag: String, msg: String) {}
            override fun w(tag: String, msg: String) {}
            override fun e(tag: String, msg: String, throwable: Throwable?) {}
        }
        val handler = PurchasesFactory.logHandler
    }
}
