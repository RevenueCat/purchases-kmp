import Foundation
@_spi(Internal) import RevenueCat

@objc
public class DangerousSettingsFactory: NSObject {
    @objc
    public static func make(autoSyncPurchases: Bool, useWorkflows: Bool) -> Any {
        if useWorkflows {
            return DangerousSettings(useWorkflows: true)
        }
        return DangerousSettings(autoSyncPurchases: autoSyncPurchases)
    }
}
