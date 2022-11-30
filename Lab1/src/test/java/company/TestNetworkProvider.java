package company;

import company.Client;
import company.NetworkProvider;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import plans.DataPlan;
import plans.MinimalPlan;
import plans.PremiumPlan;
import plans.StandardPlan;

import java.util.ArrayList;
import java.util.List;

public class TestNetworkProvider {

    @Test
    public void testNetworkProvider() {
        DataPlan minimalPlan  = new MinimalPlan("Sample minimal name", 50);
        DataPlan standardPlan = new StandardPlan("Sample standard name", 100);
        DataPlan premiumPlan  = new PremiumPlan("Sample premium name", 150);

        Client client1 = new Client("John Doe", minimalPlan);
        Client client2 = new Client("Jack Green", standardPlan);
        Client client3 = new Client("Joe Brown", premiumPlan);
        Client client4 = new Client("Brad White", standardPlan);
        Client client5 = new Client("Mike Black", standardPlan);

        NetworkProvider networkProvider = new NetworkProvider(
                "Provider1",
                List.of(minimalPlan, standardPlan, premiumPlan),
                new ArrayList<>(List.of(client1, client2, client3, client4, client5))
        );

        Assertions.assertEquals(networkProvider.getNumberOfClients(), 5);
        Assertions.assertEquals(networkProvider.getClientsByPlan(standardPlan).size(), 3);

        List<DataPlan> providerPlans = networkProvider.getPlansSortedByFee();
        Assertions.assertEquals(providerPlans.get(0).getFee(), 50);
        Assertions.assertEquals(providerPlans.get(1).getFee(), 100);
        Assertions.assertEquals(providerPlans.get(2).getFee(), 150);

        networkProvider.addClient(new Client("Donald Red", standardPlan));
        Assertions.assertEquals(networkProvider.getNumberOfClients(), 6);
        Assertions.assertEquals(networkProvider.getClientsByPlan(standardPlan).size(), 4);
    }

}
