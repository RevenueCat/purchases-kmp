package io.shortway.kobankat.ktx

internal inline fun <K1, V1, K2, V2> Map<out K1, V1>.mapEntries(
    transform: (Map.Entry<K1, V1>) -> Pair<K2, V2>
): Map<K2, V2> = entries.associate(transform)