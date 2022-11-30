package plans;

import java.util.ArrayList;
import java.util.List;

public class ExtendablePlan extends DataPlan {

    public ExtendablePlan(String name, int fee) {
        super(name, fee, new ArrayList<>(List.of(Option.CALLS_EXTENDED)));
    }

    public void addOption(Option option) {
        if (options.contains(option)) {
            return;
        }
        options.add(option);
    }

    public void removeOption(Option option) {
        if (!options.contains(option)) {
            return;
        }
        options.remove(option);
    }

}
