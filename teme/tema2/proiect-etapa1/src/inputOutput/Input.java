package inputOutput;


import economics.MonthlyUpdate;
import entities.Consumer;
import entities.Distributor;
import factories.ConsumerFactory;
import factories.CostChangeFactory;
import factories.DistributorFactory;
import factories.MonthlyUpdateFactory;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;

/**
 * Input class created using singleton pattern so that
 * it can be easily expanded and we only need one really
 */
public final class Input {

    private static Input input;

    /**
     * private constructor so that objects in this class
     * cannot be instantiated from outside the class
     */
    private Input() {

    }

    /**
     * only one object can exist of this class at one time
     * and this function makes sure that is the case
     *
     * @return
     */
    public static Input getInput() {
        if (input == null) {
            input = new Input();
        }
        return input;
    }

    /**
     * Takes the inputs and populates lists
     *
     * @param inputFile
     * @throws IOException
     * @throws ParseException
     */
    public static int parseInputFromFile(final String inputFile, final List<Consumer> consumers,
                                         final List<Distributor> distributors,
                                         final List<MonthlyUpdate> monthlyUpdates)
            throws IOException, ParseException {
        JSONParser parser = new JSONParser();

        Object obj = parser.parse(new FileReader(inputFile));
        JSONObject jsonObject = (JSONObject) obj;

        int nrOfTurns = ((Long) jsonObject.get("numberOfTurns")).intValue();


        JSONObject initialData = (JSONObject) jsonObject.get("initialData");
        //parsing consumers
        JSONArray consumersRaw = (JSONArray) initialData.get("consumers");
        for (Object consumerRaw : consumersRaw) {
            JSONObject consumerJSON = (JSONObject) consumerRaw;
            int id = ((Long) consumerJSON.get("id")).intValue();
            int initialBudget = ((Long) consumerJSON.get("initialBudget")).intValue();
            int monthlyIncome = ((Long) consumerJSON.get("monthlyIncome")).intValue();
            //populating consumers list using ConsumerFactory class
            consumers.add(ConsumerFactory.createConsumer(id, initialBudget, monthlyIncome));
        }

        //parsing distributors
        JSONArray distributorsRaw = (JSONArray) initialData.get("distributors");
        for (Object distributorRaw : distributorsRaw) {
            JSONObject distributorJSON = (JSONObject) distributorRaw;
            int id = ((Long) distributorJSON.get("id")).intValue();
            int contractLength
                    = ((Long) distributorJSON.get("contractLength")).intValue();
            int initialBudget
                    = ((Long) distributorJSON.get("initialBudget")).intValue();
            int initialInfrastructureCost
                    = ((Long) distributorJSON.get("initialInfrastructureCost")).intValue();
            int initialProductionCost
                    = ((Long) distributorJSON.get("initialProductionCost")).intValue();
            //populating distributor list using DistributorFactory class
            distributors.add(DistributorFactory.createDistributor(id, contractLength,
                    initialBudget, initialInfrastructureCost, initialProductionCost));
        }

        //parsing monthlyUpdates
        JSONArray monthlyUpdatesRaw = (JSONArray) jsonObject.get("monthlyUpdates");
        for (Object monthlyUpdateRaw : monthlyUpdatesRaw) {
            JSONObject monthlyUpdateJSON = (JSONObject) monthlyUpdateRaw;
            monthlyUpdates.add(MonthlyUpdateFactory.createMonthlyUpdate());

            //parsing consumer changes
            JSONArray newConsumersRaw = (JSONArray) monthlyUpdateJSON.get("newConsumers");
            for (Object newConsumerRaw : newConsumersRaw) {
                if (newConsumerRaw != null) {
                    JSONObject newConsumerJSON = (JSONObject) newConsumerRaw;
                    int id = ((Long) newConsumerJSON.get("id")).intValue();
                    int initialBudget = ((Long) newConsumerJSON.get("initialBudget")).intValue();
                    int monthlyIncome = ((Long) newConsumerJSON.get("monthlyIncome")).intValue();
                    //populating newConsumers list using ConsumerFactory class
                    (monthlyUpdates.get(monthlyUpdates.size() - 1))
                            .addNewConsumer(ConsumerFactory.createConsumer(id, initialBudget,
                                    monthlyIncome));
                }
            }

            //parsing cost changes
            JSONArray costChangesRaw = (JSONArray) monthlyUpdateJSON.get("costsChanges");
            for (Object costChangeRaw : costChangesRaw) {
                if (costChangeRaw != null) {
                    JSONObject costChangeJSON = (JSONObject) costChangeRaw;
                    int id = ((Long) costChangeJSON.get("id")).intValue();
                    int infrastructureCost = ((Long) costChangeJSON.get("infrastructureCost"))
                            .intValue();
                    int productionCost = ((Long) costChangeJSON.get("productionCost")).intValue();
                    //populating costChanges list using CostChangeFactory class
                    (monthlyUpdates.get(monthlyUpdates.size() - 1))
                            .addCostChange(CostChangeFactory.createCostChange(id,
                                    infrastructureCost,
                                    productionCost));
                }
            }
        }
        return nrOfTurns;
    }
}
