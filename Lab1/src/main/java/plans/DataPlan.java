package plans;

import java.util.List;

public abstract class DataPlan {
    private int fee;
    private final String name;
    private final List<Option> options;

    public DataPlan(String name, int fee, List<Option> options) {
        this.name = name;
        this.fee = fee;
        this.options = options;
    }

    public String getName() {
        return name;
    }

    public int getFee() {
        return fee;
    }

    public void setFee(int fee) {
        this.fee = fee;
    }

    public List<Option> getOptions() {
        return options;
    }

    public void addOption(Option option) {
        options.add(option);
    }
}
