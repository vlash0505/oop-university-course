package plans;

import java.util.List;

public class StandardPlan extends DataPlan {

    public StandardPlan(String name, int fee, List<Option> options) {
        super(name, fee, List.of(Option.CALLS, Option.INTERNET));
    }

}
