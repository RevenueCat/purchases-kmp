package com.revenuecat.purchases.kmp.ui.revenuecatui

import com.revenuecat.purchases.kmp.models.Package

/**
 * Interface for handling in-app purchases and restorations directly by the application
 * rather than by RevenueCat. These suspend methods are called by a RevenueCat Paywall
 * in order to execute your app's custom purchase/restore code. These functions are only
 * called when `purchasesAreCompletedBy` is set to `MY_APP`.
 */
public interface PaywallPurchaseLogic {
    /**
     * Performs an in-app purchase for the specified package.
     *
     * If a purchase is successful, `syncPurchases` will automatically be called by RevenueCat.
     *
     * @param rcPackage The package representing the in-app product that the user intends
     * to purchase.
     * @return A [PurchaseLogicResult] indicating the outcome of the purchase operation.
     */
    public suspend fun performPurchase(rcPackage: Package): PurchaseLogicResult

    /**
     * Restores previously completed purchases.
     *
     * If restoration is successful, `syncPurchases` will automatically be called by RevenueCat.
     *
     * @return A [PurchaseLogicResult] indicating the outcome of the restoration process.
     */
    public suspend fun performRestore(): PurchaseLogicResult
}

/**
 * Represents the result of a purchase or restore attempt made by custom app-based code
 * (not RevenueCat).
 */
public sealed interface PurchaseLogicResult {
    /**
     * Indicates a successful purchase or restore.
     */
    public object Success : PurchaseLogicResult

    /**
     * Indicates the purchase or restore was cancelled.
     */
    public object Cancellation : PurchaseLogicResult

    /**
     * Indicates an error occurred during the purchase or restore attempt.
     *
     * @property errorMessage An optional message describing the error. If provided, an error
     * dialog will be shown to the user.
     */
    public class Error(public val errorMessage: String? = null) : PurchaseLogicResult
}
