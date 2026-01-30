# Contributing to purchases-kmp

Thank you for your interest in contributing to the RevenueCat Kotlin Multiplatform SDK!

## Public API Design Guidelines

### Avoid Kotlin Value Classes in Public APIs

When adding new types to the public API, **do not use Kotlin value classes** (`@JvmInline value class`). While value classes provide performance benefits, they cause [name-mangling](https://kotlinlang.org/docs/inline-classes.html#mangling) in the generated Java bytecode.

**Example of the problem:**

```kotlin
// DON'T: Using a value class
@JvmInline
public value class Currency(public val code: String)

public class Price(
    public val currency: Currency,
    // ...
)
```

This generates mangled Java method names like `getCurrency-XYz123()` instead of `getCurrency()`, making the API difficult to use from Java.

**Correct approach:**

```kotlin
// DO: Use a regular class
public class Currency(public val code: String)
```

This generates clean Java method names like `getCurrency()`.

### Avoid Enums in Public APIs

When adding types that represent a set of known values, **prefer classes with companion object constants over enums**. Enums are closed types that cannot be extended, which causes issues when the backend adds new values that the SDK doesn't know about yet.

**Example of the problem:**

```kotlin
// DON'T: Using an enum
public enum class PurchaseType {
    SUBSCRIPTION,
    ONE_TIME
}
```

If the backend adds a new purchase type, older SDK versions will fail to parse it.

**Correct approach:**

```kotlin
// DO: Use a class with companion object constants
public class PurchaseType(public val value: String) {
    public companion object {
        public val SUBSCRIPTION: PurchaseType = PurchaseType("subscription")
        public val ONE_TIME: PurchaseType = PurchaseType("one_time")
    }
}
```

This allows the SDK to handle unknown values gracefully while still providing type-safe constants for known values.
