package com.revenuecat.purchases.kmp.mappings

import com.revenuecat.purchases.kmp.models.OwnershipType
import kotlin.test.Test
import kotlin.test.assertEquals
import com.revenuecat.purchases.OwnershipType as AndroidOwnershipType

class OwnershipTypeTest {

    @Test
    fun `maps every Android ownership type`() {
        assertEquals(OwnershipType.PURCHASED, AndroidOwnershipType.PURCHASED.toOwnershipType())
        assertEquals(OwnershipType.FAMILY_SHARED, AndroidOwnershipType.FAMILY_SHARED.toOwnershipType())
        assertEquals(OwnershipType.UNKNOWN, AndroidOwnershipType.UNKNOWN.toOwnershipType())
        assertEquals(OwnershipType.ASSIGNED, AndroidOwnershipType.ASSIGNED.toOwnershipType())
    }

    @Test
    fun `all Android ownership types are covered`() {
        assertEquals(OwnershipType.entries.size, AndroidOwnershipType.entries.size)
    }
}
