package com.revenuecat.purchases.kmp.models

/**
 * This class represents an error
 * @param code Error code
 * @param underlyingErrorMessage Optional error message with a more detailed explanation of the
 * error that originated this.
 */
public class PurchasesError(
    public val code: PurchasesErrorCode,
    public val underlyingErrorMessage: String? = null,
) {

    public val message: String = code.description

    override fun toString(): String {
        return "PurchasesError(code=$code, underlyingErrorMessage=$underlyingErrorMessage, message='$message')"
    }
}

/**
 * A predefined error type.
 */
public enum class PurchasesErrorCode(public val code: Int, public val description: String) {
    UnknownError(0, "Unknown error."),
    PurchaseCancelledError(1, "Purchase was cancelled."),
    StoreProblemError(2, "There was a problem with the store."),
    PurchaseNotAllowedError(3, "The device or user is not allowed to make the purchase."),
    PurchaseInvalidError(4, "One or more of the arguments provided are invalid."),
    ProductNotAvailableForPurchaseError(5, "The product is not available for purchase."),
    ProductAlreadyPurchasedError(6, "This product is already active for the user."),
    ReceiptAlreadyInUseError(
        7,
        "There is already another active subscriber using the same receipt."
    ),
    InvalidReceiptError(8, "The receipt is not valid."),
    MissingReceiptFileError(9, "The receipt is missing."),
    NetworkError(10, "Error performing request."),
    InvalidCredentialsError(
        11,
        "There was a credentials issue. Check the underlying error for more details."
    ),
    UnexpectedBackendResponseError(12, "Received unexpected response from the backend."),
    ReceiptInUseByOtherSubscriberError(13, "The receipt is in use by another subscriber."),
    InvalidAppUserIdError(14, "The app user id is not valid."),
    OperationAlreadyInProgressError(15, "The operation is already in progress."),
    UnknownBackendError(16, "There was an unknown backend error."),
    InvalidAppleSubscriptionKeyError(
        17,
        "Apple Subscription Key is invalid or not present. " +
                "In order to provide subscription offers, you must first generate a subscription key. " +
                "Please see https://docs.revenuecat.com/docs/ios-subscription-offers for more info.",
    ),
    IneligibleError(18, "The User is ineligible for that action."),
    InsufficientPermissionsError(19, "App does not have sufficient permissions to make purchases."),
    PaymentPendingError(20, "The payment is pending."),
    InvalidSubscriberAttributesError(21, "One or more of the attributes sent could not be saved."),
    LogOutWithAnonymousUserError(22, "Called logOut but the current user is anonymous."),
    ConfigurationError(
        23,
        "There is an issue with your configuration. Check the underlying error for more details."
    ),
    UnsupportedError(
        24,
        "There was a problem with the operation. Looks like we doesn't support " +
                "that yet. Check the underlying error for more details.",
    ),
    EmptySubscriberAttributesError(25, "A request for subscriber attributes returned none."),
    ProductDiscountMissingIdentifierError(
        26,
        "The SKProductDiscount or Product.SubscriptionOffer wrapped by StoreProductDiscount is missing an identifier. This is a required property and likely an AppStore quirk that it is missing."
    ),
    CustomerInfoError(28, "There was a problem related to the customer info."),
    ProductDiscountMissingSubscriptionGroupIdentifierError(
        29,
        "Unable to create a discount offer, the product is missing a subscriptionGroupIdentifier."
    ),
    SystemInfoError(30, "There was a problem related to the system info."),
    BeginRefundRequestError(31, "Error when trying to begin refund request."),
    ProductRequestTimedOut(32, "SKProductsRequest took too long to complete."),
    ApiEndpointBlocked(
        33,
        "Requests to RevenueCat are being blocked. See: https://rev.cat/dnsBlocking for more info."
    ),
    InvalidPromotionalOfferError(
        34,
        "The information associated with this PromotionalOffer is not valid. See https://rev.cat/ios-subscription-offers for more info."
    ),
    OfflineConnectionError(
        35,
        "Error performing request because the internet connection appears to be offline."
    ),
    SignatureVerificationError(
        36,
        "Request failed signature verification. Please see https://rev.cat/trusted-entitlements for more info.",
    ),
    FeatureNotAvailableInCustomEntitlementsComputationMode(
        37,
        "This feature is not available when utilizing the customEntitlementsComputation dangerousSetting."
    ),
    FeatureNotSupportedWithStoreKit1(
        38,
        "This feature is not supported when using StoreKit 1. " +
                "Configure the SDK to use StoreKit 2 to use this feature."
    ),
    InvalidWebPurchaseToken(
        39,
        "The link you provided does not contain a valid purchase token.",
    ),
    PurchaseBelongsToOtherUser(
        40,
        "The web purchase already belongs to other user.",
    ),
    ExpiredWebPurchaseToken(
        41,
        "The link you provided has expired. A new one will be sent to the email used to make the purchase.",
    ),
    TestStoreSimulatedPurchaseError(42, "Purchase failure simulated successfully in Test Store."),
}
