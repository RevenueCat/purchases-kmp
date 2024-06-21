package com.revenuecat.purchases.kmp.apitester

import com.revenuecat.purchases.kmp.PurchasesErrorCode

@Suppress("unused")
private class PurchasesErrorCodeAPI {
    fun check(code: PurchasesErrorCode) {
        when (code) {
            PurchasesErrorCode.UnknownError,
            PurchasesErrorCode.PurchaseCancelledError,
            PurchasesErrorCode.StoreProblemError,
            PurchasesErrorCode.PurchaseNotAllowedError,
            PurchasesErrorCode.PurchaseInvalidError,
            PurchasesErrorCode.ProductNotAvailableForPurchaseError,
            PurchasesErrorCode.ProductAlreadyPurchasedError,
            PurchasesErrorCode.ReceiptAlreadyInUseError,
            PurchasesErrorCode.InvalidReceiptError,
            PurchasesErrorCode.MissingReceiptFileError,
            PurchasesErrorCode.NetworkError,
            PurchasesErrorCode.InvalidCredentialsError,
            PurchasesErrorCode.UnexpectedBackendResponseError,
            PurchasesErrorCode.ReceiptInUseByOtherSubscriberError,
            PurchasesErrorCode.InvalidAppUserIdError,
            PurchasesErrorCode.OperationAlreadyInProgressError,
            PurchasesErrorCode.UnknownBackendError,
            PurchasesErrorCode.InvalidAppleSubscriptionKeyError,
            PurchasesErrorCode.IneligibleError,
            PurchasesErrorCode.InsufficientPermissionsError,
            PurchasesErrorCode.PaymentPendingError,
            PurchasesErrorCode.InvalidSubscriberAttributesError,
            PurchasesErrorCode.LogOutWithAnonymousUserError,
            PurchasesErrorCode.ConfigurationError,
            PurchasesErrorCode.UnsupportedError,
            PurchasesErrorCode.EmptySubscriberAttributesError,
            PurchasesErrorCode.ProductDiscountMissingIdentifierError,
            PurchasesErrorCode.CustomerInfoError,
            PurchasesErrorCode.ProductDiscountMissingSubscriptionGroupIdentifierError,
            PurchasesErrorCode.SystemInfoError,
            PurchasesErrorCode.BeginRefundRequestError,
            PurchasesErrorCode.ProductRequestTimedOut,
            PurchasesErrorCode.ApiEndpointBlocked,
            PurchasesErrorCode.InvalidPromotionalOfferError,
            PurchasesErrorCode.OfflineConnectionError,
            PurchasesErrorCode.SignatureVerificationError,
            PurchasesErrorCode.FeatureNotAvailableInCustomEntitlementsComputationMode -> {
            }
        }.exhaustive
    }
}
