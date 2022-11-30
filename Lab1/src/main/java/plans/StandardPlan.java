package plans;

import java.util.List;

public class StandardPlan extends DataPlan {

    public StandardPlan(String name, int fee) {
        super(name, fee, List.of(Option.CALLS, Option.INTERNET));
    }

}
