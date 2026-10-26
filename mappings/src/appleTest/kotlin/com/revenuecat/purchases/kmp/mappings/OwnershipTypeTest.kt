package com.revenuecat.purchases.kmp.mappings

import com.revenuecat.purchases.kmp.models.OwnershipType
import com.revenuecat.purchases.kn.core.RCPurchaseOwnershipTypeAssigned
import com.revenuecat.purchases.kn.core.RCPurchaseOwnershipTypeFamilyShared
import com.revenuecat.purchases.kn.core.RCPurchaseOwnershipTypePurchased
import com.revenuecat.purchases.kn.core.RCPurchaseOwnershipTypeUnknown
import kotlin.test.Test
import kotlin.test.assertEquals

class OwnershipTypeTest {

    @Test
    fun `maps every iOS ownership type`() {
        assertEquals(OwnershipType.PURCHASED, RCPurchaseOwnershipTypePurchased.toOwnershipType())
        assertEquals(OwnershipType.FAMILY_SHARED, RCPurchaseOwnershipTypeFamilyShared.toOwnershipType())
        assertEquals(OwnershipType.UNKNOWN, RCPurchaseOwnershipTypeUnknown.toOwnershipType())
        assertEquals(OwnershipType.ASSIGNED, RCPurchaseOwnershipTypeAssigned.toOwnershipType())
    }
}
