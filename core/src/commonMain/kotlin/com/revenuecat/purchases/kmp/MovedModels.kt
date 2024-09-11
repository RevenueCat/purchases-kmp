package com.revenuecat.purchases.kmp


// This file contains deprecated type aliases of our model classes that used to live outside of the
// models package. This way, we're staying source compatible, and developers can migrate at their
// own pace.


@Deprecated(
    message = "This class has moved to the models package.",
    replaceWith = ReplaceWith(
        expression = "CacheFetchPolicy",
        imports = ["com.revenuecat.purchases.kmp.models.CacheFetchPolicy"]
    )
)
public typealias CacheFetchPolicy = com.revenuecat.purchases.kmp.models.CacheFetchPolicy

@Deprecated(
    message = "This class has moved to the models package.",
    replaceWith = ReplaceWith(
        expression = "EntitlementVerificationMode",
        imports = ["com.revenuecat.purchases.kmp.models.EntitlementVerificationMode"]
    )
)
public typealias EntitlementVerificationMode =
        com.revenuecat.purchases.kmp.models.EntitlementVerificationMode

@Deprecated(
    message = "This class has moved to the models package.",
    replaceWith = ReplaceWith(
        expression = "ProductType",
        imports = ["com.revenuecat.purchases.kmp.models.ProductType"]
    )
)
public typealias ProductType = com.revenuecat.purchases.kmp.models.ProductType

@Deprecated(
    message = "This class has moved to the models package.",
    replaceWith = ReplaceWith(
        expression = "StoreKitVersion",
        imports = ["com.revenuecat.purchases.kmp.models.StoreKitVersion"]
    )
)
public typealias StoreKitVersion = com.revenuecat.purchases.kmp.models.StoreKitVersion

@Deprecated(
    message = "This class has moved to the models package.",
    replaceWith = ReplaceWith(
        expression = "VerificationResult",
        imports = ["com.revenuecat.purchases.kmp.models.VerificationResult"]
    )
)
public typealias VerificationResult = com.revenuecat.purchases.kmp.models.VerificationResult
