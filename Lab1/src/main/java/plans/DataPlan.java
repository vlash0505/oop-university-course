package plans;

import java.util.List;

public abstract class DataPlan {
    protected final int fee;
    protected final String name;
    protected final List<Option> options;

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

    public List<Option> getOptions() {
        return options;
    }

    @Override
    public String toString() {
        return "DataPlan{" +
                "fee=" + fee +
                ", name='" + name + '\'' +
                ", options=" + options +
                '}';
    }
}
