package com.revenuecat.purchases.kmp.mappings;

import com.revenuecat.purchases.ads.AdRewardConfiguration;
import com.revenuecat.purchases.ads.ConfiguredAdRewards;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

public class ConfiguredAdRewardsMappingTest {
    @Test
    public void mapsNativePrimaryAndOrderedAdditionalRewards() {
        // Native model constructors are internal to Kotlin, but are accessible to Java test fixtures.
        ConfiguredAdRewards nativeRewards = new ConfiguredAdRewards(
            Arrays.asList(
                new AdRewardConfiguration.VirtualCurrency("coins", 10),
                AdRewardConfiguration.UnsupportedReward.INSTANCE,
                new AdRewardConfiguration.VirtualCurrency("gems", 2)
            )
        );
        com.revenuecat.purchases.kmp.models.ConfiguredAdRewards result =
            ConfiguredAdRewards_androidKt.toConfiguredAdRewards(nativeRewards);

        assertEquals(
            new com.revenuecat.purchases.kmp.models.AdRewardConfiguration.VirtualCurrency("coins", 10),
            result.getReward()
        );
        assertSame(
            com.revenuecat.purchases.kmp.models.AdRewardConfiguration.UnsupportedReward.INSTANCE,
            result.getMoreRewards().get(0)
        );
        assertEquals(
            new com.revenuecat.purchases.kmp.models.AdRewardConfiguration.VirtualCurrency("gems", 2),
            result.getMoreRewards().get(1)
        );
    }

    @Test
    public void preservesResolvedAbsence() {
        assertSame(
            com.revenuecat.purchases.kmp.models.ConfiguredAdRewards.Companion.getNO_CONFIGURED_REWARD(),
            ConfiguredAdRewards_androidKt.toConfiguredAdRewards(new ConfiguredAdRewards(Collections.emptyList()))
        );
    }
}
