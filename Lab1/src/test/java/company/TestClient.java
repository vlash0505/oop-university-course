package company;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import plans.DataPlan;
import plans.MinimalPlan;
import plans.StandardPlan;

public class TestClient {

    @Test
    public void testClient() {
        DataPlan minimalPlan  = new MinimalPlan("Sample minimal name", 50);
        DataPlan standardPlan = new StandardPlan("Sample standard name", 100);

        Client client = new Client("John Doe", minimalPlan);
        Assertions.assertEquals(client.getDataPlan().getClass(), MinimalPlan.class);

        //change client's plan
        client.setDataPlan(standardPlan);
        Assertions.assertEquals(client.getDataPlan().getClass(), StandardPlan.class);
    }

}
