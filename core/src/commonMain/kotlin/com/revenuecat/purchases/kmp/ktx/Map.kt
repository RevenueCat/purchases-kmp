package com.revenuecat.purchases.kmp.ktx

internal inline fun <K1, V1, K2, V2> Map<out K1, V1>.mapEntries(
    transform: (Map.Entry<K1, V1>) -> Pair<K2, V2>
): Map<K2, V2> = entries.associate(transform)

internal inline fun <K1, V1, K2, V2> Map<out K1, V1>.mapEntriesNotNull(
    transform: (Map.Entry<K1, V1>) -> Pair<K2, V2>?
): Map<K2, V2> {
    val destination = LinkedHashMap<K2, V2>(initialCapacity = size)
    for (element in this) {
        val transformed = transform(element)
        if (transformed != null) {
            destination += transformed
        }
    }
    return destination
}
