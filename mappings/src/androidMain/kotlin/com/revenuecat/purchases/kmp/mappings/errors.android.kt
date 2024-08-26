package com.revenuecat.purchases.kmp.mappings

import com.revenuecat.purchases.kmp.PurchasesError
import com.revenuecat.purchases.kmp.PurchasesErrorCode
import com.revenuecat.purchases.PurchasesError as RcPurchasesError
import com.revenuecat.purchases.PurchasesErrorCode as RcPurchasesErrorCode

public fun RcPurchasesError.toPurchasesError(): PurchasesError =
    PurchasesError(
        code = code.toPurchasesErrorCode(),
        underlyingErrorMessage = underlyingErrorMessage
    )

internal fun RcPurchasesErrorCode.toPurchasesErrorCode(): PurchasesErrorCode =
    when (this) {
        RcPurchasesErrorCode.UnknownError -> PurchasesErrorCode.UnknownError
        RcPurchasesErrorCode.PurchaseCancelledError -> PurchasesErrorCode.PurchaseCancelledError
        RcPurchasesErrorCode.StoreProblemError -> PurchasesErrorCode.StoreProblemError
        RcPurchasesErrorCode.PurchaseNotAllowedError -> PurchasesErrorCode.PurchaseNotAllowedError
        RcPurchasesErrorCode.PurchaseInvalidError -> PurchasesErrorCode.PurchaseInvalidError
        RcPurchasesErrorCode.ProductNotAvailableForPurchaseError -> PurchasesErrorCode.ProductNotAvailableForPurchaseError
        RcPurchasesErrorCode.ProductAlreadyPurchasedError -> PurchasesErrorCode.ProductAlreadyPurchasedError
        RcPurchasesErrorCode.ReceiptAlreadyInUseError -> PurchasesErrorCode.ReceiptAlreadyInUseError
        RcPurchasesErrorCode.InvalidReceiptError -> PurchasesErrorCode.InvalidReceiptError
        RcPurchasesErrorCode.MissingReceiptFileError -> PurchasesErrorCode.MissingReceiptFileError
        RcPurchasesErrorCode.NetworkError -> PurchasesErrorCode.NetworkError
        RcPurchasesErrorCode.InvalidCredentialsError -> PurchasesErrorCode.InvalidCredentialsError
        RcPurchasesErrorCode.UnexpectedBackendResponseError -> PurchasesErrorCode.UnexpectedBackendResponseError
        RcPurchasesErrorCode.InvalidAppUserIdError -> PurchasesErrorCode.InvalidAppUserIdError
        RcPurchasesErrorCode.OperationAlreadyInProgressError -> PurchasesErrorCode.OperationAlreadyInProgressError
        RcPurchasesErrorCode.UnknownBackendError -> PurchasesErrorCode.UnknownBackendError
        RcPurchasesErrorCode.InvalidAppleSubscriptionKeyError -> PurchasesErrorCode.InvalidAppleSubscriptionKeyError
        RcPurchasesErrorCode.IneligibleError -> PurchasesErrorCode.IneligibleError
        RcPurchasesErrorCode.InsufficientPermissionsError -> PurchasesErrorCode.InsufficientPermissionsError
        RcPurchasesErrorCode.PaymentPendingError -> PurchasesErrorCode.PaymentPendingError
        RcPurchasesErrorCode.InvalidSubscriberAttributesError -> PurchasesErrorCode.InvalidSubscriberAttributesError
        RcPurchasesErrorCode.LogOutWithAnonymousUserError -> PurchasesErrorCode.LogOutWithAnonymousUserError
        RcPurchasesErrorCode.ConfigurationError -> PurchasesErrorCode.ConfigurationError
        RcPurchasesErrorCode.UnsupportedError -> PurchasesErrorCode.UnsupportedError
        RcPurchasesErrorCode.EmptySubscriberAttributesError -> PurchasesErrorCode.EmptySubscriberAttributesError
        RcPurchasesErrorCode.CustomerInfoError -> PurchasesErrorCode.CustomerInfoError
        RcPurchasesErrorCode.SignatureVerificationError -> PurchasesErrorCode.SignatureVerificationError
    }
