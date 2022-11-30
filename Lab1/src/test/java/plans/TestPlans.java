package plans;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TestPlans {

    @Test
    public void testPlans() {
        DataPlan minimalPlan  = new MinimalPlan("Sample minimal name", 50);
        DataPlan standardPlan = new StandardPlan("Sample standard name", 100);
        DataPlan premiumPlan  = new PremiumPlan("Sample premium name", 150);

        Assertions.assertTrue(minimalPlan.getOptions().contains(Option.CALLS));
        Assertions.assertFalse(minimalPlan.getOptions().contains(Option.ROAMING));

        Assertions.assertTrue(standardPlan.getOptions().contains(Option.CALLS));
        Assertions.assertTrue(standardPlan.getOptions().contains(Option.INTERNET));
        Assertions.assertFalse(standardPlan.getOptions().contains(Option.INTERNET_EXTENDED));

        Assertions.assertTrue(premiumPlan.getOptions().contains(Option.CALLS_EXTENDED));
        Assertions.assertTrue(premiumPlan.getOptions().contains(Option.INTERNET_EXTENDED));
        Assertions.assertFalse(premiumPlan.getOptions().contains(Option.ROAMING));
    }

    @Test
    public void testExtendablePlan() {
        ExtendablePlan extendablePlan  = new ExtendablePlan("Sample extendable name", 150);

        Assertions.assertEquals(extendablePlan.getOptions().size(), 1);
        extendablePlan.addOption(Option.INTERNET);
        Assertions.assertEquals(extendablePlan.getOptions().size(), 2);
        extendablePlan.addOption(Option.INTERNET);
        Assertions.assertEquals(extendablePlan.getOptions().size(), 2);
        extendablePlan.removeOption(Option.INTERNET);
        Assertions.assertEquals(extendablePlan.getOptions().size(), 1);
        extendablePlan.removeOption(Option.INTERNET);
        Assertions.assertEquals(extendablePlan.getOptions().size(), 1);
        extendablePlan.addOption(Option.CALLS_EXTENDED);
        Assertions.assertEquals(extendablePlan.getOptions().size(), 1);
    }

}
