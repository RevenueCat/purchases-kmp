import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.revenuecat.purchases.kmp.Purchases
import com.revenuecat.purchases.kmp.models.CustomerInfo
import com.revenuecat.purchases.kmp.models.PurchasesError
import com.revenuecat.purchases.kmp.models.StoreProduct
import com.revenuecat.purchases.kmp.models.StoreTransaction

@Composable
fun WinBackTestingScreen() {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        Text("Use this screen to fetch eligible win-back offers, purchase products without a win-back offer, and purchase products with an eligible win-back offer.")
        
        Text("This test relies on products and offers defined in the SKConfig file, so be sure to launch the PurchaseTester app from Xcode with the SKConfig file configured.")

        Spacer(modifier = Modifier.height(16.dp))
        
        Button(onClick = {
            Purchases.sharedInstance.getProducts(
                listOf("com.revenuecat.monthly_4.99"),
                onError = { error: PurchasesError ->
                    println("Error: Could not fetch products: $error")
                },
                onSuccess = { products: List<StoreProduct> ->
                    if(products.isEmpty()) {
                        println("No products were found. Are you running on Android? Win-backs are only supported on iOS.")
                        return@getProducts
                    }

                    Purchases.sharedInstance.purchase(
                        products[0],
                        onError = { error: PurchasesError, userCancelled: Boolean ->
                            println("Error: $error, userCancelled: $userCancelled")
                        },
                        onSuccess = { transaction: StoreTransaction, customerInfo: CustomerInfo ->
                            println("onSuccess: $transaction, customerInfo: $customerInfo")
                        }
                    )
                }
            )
        }) {
            Text("Fetch and Purchase Product for WinBack Testing")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            Purchases.sharedInstance.getProducts(
                listOf("com.revenuecat.monthly_4.99"),
                onError = { error: PurchasesError ->
                    println("Error getting products: $error")
                },
                onSuccess = { products: List<StoreProduct> ->
                    if(products.isEmpty()) {
                        println("No products were found. Are you running on Android? Win-backs are only supported on iOS.")
                        return@getProducts
                    }

                    Purchases.sharedInstance.getEligibleWinBackOffersForProduct(
                        products[0],
                        onError = { error ->
                            println("Error: $error")
                            return@getEligibleWinBackOffersForProduct
                        },
                        onSuccess = { eligibleWinBackOffers ->
                            if (eligibleWinBackOffers.isEmpty()) {
                                println("No eligible win-back offers found.")
                                return@getEligibleWinBackOffersForProduct
                            }

                            val winBackOffer = eligibleWinBackOffers[0]
                            println("Got a win-back offer! winBackOffer.toString(): $winBackOffer")
                            println("winBackOffer.discount: ${winBackOffer.discount}")
                            println("winBackOffer.discount.price: ${winBackOffer.discount.price}")
                            println("winBackOffer.discount.numberOfPeriods: ${winBackOffer.discount.numberOfPeriods}")
                            println("winBackOffer.discount.offerIdentifier: ${winBackOffer.discount.offerIdentifier}")
                            println("winBackOffer.discount.paymentMode: ${winBackOffer.discount.paymentMode.name}")
                            println("winBackOffer.discount.subscriptionPeriod.unit.name: ${winBackOffer.discount.subscriptionPeriod.unit.name}")
                            println("winBackOffer.discount.subscriptionPeriod.value: ${winBackOffer.discount.subscriptionPeriod.value}")
                            println("winBackOffer.discount.type: ${winBackOffer.discount.type.name}")

                            Purchases.sharedInstance.purchase(
                                products[0],
                                winBackOffer = winBackOffer,
                                onError = { error, userCancelled ->
                                    println("An error occurred while making the purchase")
                                    println("Error: ${error}, userCancelled: $userCancelled")
                                },
                                onSuccess = { transaction, customerInfo ->
                                    println("Successful purchase!!")
                                    println("transaction: ${transaction}, customerInfo: ${customerInfo}")
                                }
                            )
                        }
                    )
                }
            )
        }) {
            Text("Fetch and Redeem WinBack Offers For Product")
        }

        Spacer(modifier = Modifier.height(32.dp))

        Button(onClick = {
            Purchases.sharedInstance.getOfferings(
                onError = { error -> println(error) },
                onSuccess = { offerings ->
                    val currentOffering = offerings.current!!

                    val packageToPurchase = currentOffering.availablePackages.firstOrNull {
                        it.storeProduct.id == "com.revenuecat.monthly_4.99.1_week_intro"
                    }

                    if (packageToPurchase != null) {
                        Purchases.sharedInstance.purchase(
                            packageToPurchase,
                            onError = { error: PurchasesError, userCancelled: Boolean ->
                                println("Error: $error, userCancelled: $userCancelled")
                            },
                            onSuccess = { transaction: StoreTransaction, customerInfo: CustomerInfo ->
                                println("onSuccess: $transaction, customerInfo: $customerInfo")
                            }
                        )
                    } else {
                        println("No packages with matching products were found. Are you running on Android? Win-backs are only supported on iOS.")
                    }
                }
            )
        }) {
            Text("Fetch and Purchase Package for WinBack Testing")
        }

        Button(onClick = {
            Purchases.sharedInstance.getOfferings(
                onError = { error -> println(error) },
                onSuccess = { offerings ->
                    val currentOffering = offerings.current!!

                    val packageToPurchase = currentOffering.availablePackages.firstOrNull {
                        it.storeProduct.id == "com.revenuecat.monthly_4.99.1_week_intro"
                    }

                    if (packageToPurchase != null) {
                        Purchases.sharedInstance.getEligibleWinBackOffersForPackage(
                            packageToCheck = packageToPurchase,
                            onError = { error -> println(error) },
                            onSuccess = { eligibleWinBackOffers ->
                                if (eligibleWinBackOffers.isEmpty()) {
                                    println("No eligible win-back offers found.")
                                    return@getEligibleWinBackOffersForPackage
                                }

                                val winBackOffer = eligibleWinBackOffers[0]
                                println("Got a win-back offer! winBackOffer.toString(): $winBackOffer")
                                println("winBackOffer.discount: ${winBackOffer.discount}")
                                println("winBackOffer.discount.price: ${winBackOffer.discount.price}")
                                println("winBackOffer.discount.numberOfPeriods: ${winBackOffer.discount.numberOfPeriods}")
                                println("winBackOffer.discount.offerIdentifier: ${winBackOffer.discount.offerIdentifier}")
                                println("winBackOffer.discount.paymentMode: ${winBackOffer.discount.paymentMode.name}")
                                println("winBackOffer.discount.subscriptionPeriod.unit.name: ${winBackOffer.discount.subscriptionPeriod.unit.name}")
                                println("winBackOffer.discount.subscriptionPeriod.value: ${winBackOffer.discount.subscriptionPeriod.value}")
                                println("winBackOffer.discount.type: ${winBackOffer.discount.type.name}")

                                Purchases.sharedInstance.purchase(
                                    packageToPurchase,
                                    winBackOffer = winBackOffer,
                                    onError = { error, userCancelled ->
                                        println("An error occurred while making the purchase")
                                        println("Error: ${error}, userCancelled: $userCancelled")
                                    },
                                    onSuccess = { transaction, customerInfo ->
                                        println("Successful purchase!!")
                                        println("transaction: ${transaction}, customerInfo: ${customerInfo}")
                                    }
                                )
                            }
                        )
                    } else {
                        println("No packages with matching products were found. Are you running on Android? Win-backs are only supported on iOS.")
                    }


                }
            )
        }) {
            Text("Fetch and Redeem WinBack Offers For Package")
        }
    }
}
