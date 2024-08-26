@file:Suppress("EXTENSION_SHADOWED_BY_MEMBER")

package com.revenuecat.purchases.kmp

import com.revenuecat.purchases.Offerings as RcOfferings

public actual typealias Offerings = RcOfferings

public actual val Offerings.current: Offering?
    get() = current

public actual val Offerings.all: Map<String, Offering>
    get() = all
