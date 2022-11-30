package company;

import plans.DataPlan;
import plans.StandardPlan;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class NetworkProvider {
    private final String name;
    private final List<DataPlan> availablePlans;
    private final List<Client> clients;

    public NetworkProvider(String name, List<DataPlan> availablePlans, List<Client> clients) {
        this.name = name;
        this.availablePlans = availablePlans;
        this.clients = clients;
    }

    public void addClient(Client client) {
        clients.add(client);
    }

    public int getNumberOfClients() {
        return clients.size();
    }

    public List<DataPlan> getPlansSortedByFee() {
        List<DataPlan> result = new ArrayList<>(availablePlans);
        result.sort(Comparator.comparingInt(DataPlan::getFee));

        return result;
    }

    public List<Client> getClientsByPlan(DataPlan dataPlan) {
        List<Client> result = new ArrayList<>(clients);
        result.removeIf(e -> e.getDataPlan().getClass() != dataPlan.getClass());

        return result;
    }

    public String getName() {
        return name;
    }

    public List<DataPlan> getAvailablePlans() {
        return availablePlans;
    }

    public List<Client> getClients() {
        return clients;
    }
}
