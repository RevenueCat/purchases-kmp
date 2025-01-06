import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.revenuecat.purchases.kmp.Purchases
import com.revenuecat.purchases.kmp.models.CustomerInfo
import com.revenuecat.purchases.kmp.models.PurchasesError
import com.revenuecat.purchases.kmp.models.StoreProduct
import com.revenuecat.purchases.kmp.models.StoreTransaction

//import com.revenuecat.purchases.Package
//import com.revenuecat.purchases.Purchases
//import com.revenuecat.purchases.PurchasesStoreProduct
//import com.revenuecat.purchases.PurchasesWinBackOffer

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
            // Fetch Product implementation
            val productIds = ArrayList<String>()
            productIds.add("com.revenuecat.monthly_4.99")

            Purchases.sharedInstance.getProducts(
                productIds,
                onError = { error: PurchasesError ->
                    println("Error: Could not fetch products")
                },
                onSuccess = { products: List<StoreProduct> ->
                    println("Found products: $products")
                    if(products.isEmpty()) {
                        println("Products is empty!")
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
            // Fetch Product implementation
            val productIds = ArrayList<String>()
            productIds.add("com.revenuecat.monthly_4.99")

            Purchases.sharedInstance.getProducts(
                productIds,
                onError = { error: PurchasesError ->
                    println("Error: Could not fetch products")
                },
                onSuccess = { products: List<StoreProduct> ->
                    println("Found products: $products")
                    if(products.isEmpty()) {
                        println("Products is empty!")
                        return@getProducts
                    }

                    println("Calling getEligibleWinBackOffersForProduct")
                    Purchases.sharedInstance.getEligibleWinBackOffersForProduct(
                        products[0],
                        onError = { error ->
                            println("Error: $error")
                        },
                        onSuccess = { eligibleWinBackOffers ->
                            if (eligibleWinBackOffers.isEmpty()) {
                                println("No eligible win-back offers found.")
                                return@getEligibleWinBackOffersForProduct
                            }
                        }
                    )
                }
            )
        }) {
            Text("Fetch and Redeem WinBack Offers For Product")
        }


    }
}
