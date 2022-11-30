package plans;

import java.util.List;

public class PremiumPlan extends DataPlan {

    public PremiumPlan(String name, int fee, List<Option> options) {
        super(name, fee, List.of(Option.CALLS_EXTENDED, Option.INTERNET_EXTENDED));
    }

}
