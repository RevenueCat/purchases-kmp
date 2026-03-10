package com.revenuecat.purchases.kmp.ui.revenuecatui

import com.revenuecat.purchases.kmp.models.CustomerInfo
import com.revenuecat.purchases.kmp.models.Package
import com.revenuecat.purchases.kmp.models.PurchasesError

/**
 * Interface for handling in-app purchases and restorations directly by the application
 * rather than by RevenueCat. These suspend methods are called by a RevenueCat Paywall
 * in order to execute your app's custom purchase/restore code. These functions are only
 * called when `purchasesAreCompletedBy` is set to `MY_APP`.
 */
public interface PaywallPurchaseLogic {
    /**
     * Performs an in-app purchase with the given purchase params.
     *
     * The [PaywallPurchaseLogicParams] contains the package to purchase along with product change
     * information and the specific subscription option (offer) configured in the paywall.
     *
     * If a purchase is successful, `syncPurchases` will automatically be called by RevenueCat.
     *
     * @param params The purchase params containing the package, product change information,
     * and offer details.
     * @return A [PurchaseLogicResult] indicating the outcome of the purchase operation.
     */
    public suspend fun performPurchase(params: PaywallPurchaseLogicParams): PurchaseLogicResult

    /**
     * Restores previously completed purchases for the given customer.
     *
     * If restoration is successful, `syncPurchases` will automatically be called by RevenueCat.
     *
     * @param customerInfo An object containing information about the customer.
     * @return A [PurchaseLogicResult] indicating the outcome of the restoration process.
     */
    public suspend fun performRestore(customerInfo: CustomerInfo): PurchaseLogicResult
}

/**
 * Parameters provided to [PaywallPurchaseLogic] when a paywall initiates a purchase.
 *
 * @property rcPackage The package representing the in-app product that the user intends
 * to purchase.
 */
public class PaywallPurchaseLogicParams internal constructor(
    public val rcPackage: Package,
)

/**
 * Represents the result of a purchase or restore attempt made by custom app-based code
 * (not RevenueCat).
 */
public abstract class PurchaseLogicResult internal constructor() {
    /**
     * Indicates a successful purchase or restore.
     */
    public class Success : PurchaseLogicResult()

    /**
     * Indicates the purchase or restore was cancelled.
     */
    public class Cancellation : PurchaseLogicResult()

    /**
     * Indicates an error occurred during the purchase or restore attempt.
     *
     * @property errorDetails Details of the error that occurred. If provided, an error
     * dialog will be shown to the user.
     */
    public class Error(public val errorDetails: PurchasesError? = null) : PurchaseLogicResult()
}
