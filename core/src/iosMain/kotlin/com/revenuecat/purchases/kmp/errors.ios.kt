package com.revenuecat.purchases.kmp

import platform.Foundation.NSError


public fun NSError.toPurchasesErrorOrThrow(): PurchasesError =
    PurchasesError(
        code = code().toPurchasesErrorCode(),
        underlyingErrorMessage = localizedDescription(),
    )

private fun Long.toPurchasesErrorCode(): PurchasesErrorCode =
    when (this) {
        0L -> PurchasesErrorCode.UnknownError
        1L -> PurchasesErrorCode.PurchaseCancelledError
        2L -> PurchasesErrorCode.StoreProblemError
        3L -> PurchasesErrorCode.PurchaseNotAllowedError
        4L -> PurchasesErrorCode.PurchaseInvalidError
        5L -> PurchasesErrorCode.ProductNotAvailableForPurchaseError
        6L -> PurchasesErrorCode.ProductAlreadyPurchasedError
        7L -> PurchasesErrorCode.ReceiptAlreadyInUseError
        8L -> PurchasesErrorCode.InvalidReceiptError
        9L -> PurchasesErrorCode.MissingReceiptFileError
        10L -> PurchasesErrorCode.NetworkError
        11L -> PurchasesErrorCode.InvalidCredentialsError
        12L -> PurchasesErrorCode.UnexpectedBackendResponseError
        13L -> PurchasesErrorCode.ReceiptInUseByOtherSubscriberError
        14L -> PurchasesErrorCode.InvalidAppUserIdError
        15L -> PurchasesErrorCode.OperationAlreadyInProgressError
        16L -> PurchasesErrorCode.UnknownBackendError
        17L -> PurchasesErrorCode.InvalidAppleSubscriptionKeyError
        18L -> PurchasesErrorCode.IneligibleError
        19L -> PurchasesErrorCode.InsufficientPermissionsError
        20L -> PurchasesErrorCode.PaymentPendingError
        21L -> PurchasesErrorCode.InvalidSubscriberAttributesError
        22L -> PurchasesErrorCode.LogOutWithAnonymousUserError
        23L -> PurchasesErrorCode.ConfigurationError
        24L -> PurchasesErrorCode.UnsupportedError
        25L -> PurchasesErrorCode.EmptySubscriberAttributesError
        26L -> PurchasesErrorCode.ProductDiscountMissingIdentifierError
        28L -> PurchasesErrorCode.ProductDiscountMissingSubscriptionGroupIdentifierError
        29L -> PurchasesErrorCode.CustomerInfoError
        30L -> PurchasesErrorCode.SystemInfoError
        31L -> PurchasesErrorCode.BeginRefundRequestError
        32L -> PurchasesErrorCode.ProductRequestTimedOut
        33L -> PurchasesErrorCode.ApiEndpointBlocked
        34L -> PurchasesErrorCode.InvalidPromotionalOfferError
        35L -> PurchasesErrorCode.OfflineConnectionError
        36L -> PurchasesErrorCode.FeatureNotAvailableInCustomEntitlementsComputationMode
        37L -> PurchasesErrorCode.SignatureVerificationError
        else -> error("Unexpected ErrorCode: $this")
}
