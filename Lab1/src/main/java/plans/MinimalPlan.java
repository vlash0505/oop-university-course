package plans;

import java.util.List;

public class MinimalPlan extends DataPlan {

    public MinimalPlan(String name, int fee) {
        super(name, fee, List.of(Option.CALLS));
    }

}
