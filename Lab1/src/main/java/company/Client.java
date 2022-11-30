package company;

import plans.DataPlan;

public class Client {
    private final String fullName;
    private DataPlan dataPlan;

    public Client(String fullName, DataPlan dataPlan) {
        this.fullName = fullName;
        this.dataPlan = dataPlan;
    }

    public String getFullName() {
        return fullName;
    }

    public DataPlan getDataPlan() {
        return dataPlan;
    }

    public void setDataPlan(DataPlan dataPlan) {
        this.dataPlan = dataPlan;
    }

    @Override
    public String toString() {
        return "company.Client{" +
                ", fullName='" + fullName + '\'' +
                ", dataPlan=" + dataPlan +
                '}';
    }
}
