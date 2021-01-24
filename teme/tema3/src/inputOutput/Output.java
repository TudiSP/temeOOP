package inputOutput;

import Utils.Utils;
import economics.Contract;
import entities.Consumer;
import entities.Distributor;
import entities.Producer;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import statistics.MonthlyStats;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 * Output class created using singleton pattern so that
 * it can be easily expanded and we only need one really
 */
public final class Output {
    private static Output output;

    /**
     * private constructor so that objects in this class
     * cannot be instantiated from outside the class
     */
    private Output() {
    }

    /**
     * only one object can exist of this class at one time
     * and this function makes sure that is the case
     *
     * @return
     */
    public static Output getInstance() {
        if (output == null) {
            output = new Output();
        }
        return output;
    }

    /**
     * Function that writes to file in JSON format
     *
     * @param outputPath
     * @param consumers
     * @param distributors
     * @param contracts
     * @throws IOException
     */
    public static void writeToOutput(final String outputPath,
                                     final List<Consumer> consumers,
                                     final List<Distributor> distributors,
                                     final List<Producer> producers,
                                     final List<Contract> contracts) throws IOException {


        //create the consumer json array
        JSONArray consumerArrayJson = new JSONArray();
        for (Consumer consumer : consumers) {
            JSONObject consumerJson = new JSONObject();
            consumerJson.put("id", consumer.getId());
            consumerJson.put("isBankrupt", consumer.isBankrupt());
            consumerJson.put("budget", consumer.getBudget());
            consumerArrayJson.add(consumerJson);
        }

        //create the distributor json array
        JSONArray distributorArrayJson = new JSONArray();
        for (Distributor distributor : distributors) {
            JSONObject distributorJson = new JSONObject();
            List<Contract> distributorContracts = Utils
                    .distributorIdToContractSearch(contracts, distributor.getId());

            distributorJson.put("id", distributor.getId());
            distributorJson.put("energyNeededKW", distributor.getEnergyNeededKW());
            distributorJson.put("contractCost", distributor.getContractPriceProposal());
            distributorJson.put("budget", distributor.getBudget());
            distributorJson.put("producerStrategy", distributor.getProducerStrategy().getLabel());
            distributorJson.put("isBankrupt", distributor.isBankrupt());

            JSONArray distributorContractsJson = new JSONArray();
            for (Contract distributorContract : distributorContracts) {
                JSONObject distributorContractJson = new JSONObject();

                distributorContractJson.put("consumerId",
                        distributorContract.getConsumerId());
                distributorContractJson.put("price",
                        distributorContract.getConsumerTax());
                distributorContractJson.put("remainedContractMonths",
                        distributorContract.getMonthsLeft());
                distributorContractsJson.add(distributorContractJson);
            }
            distributorJson.put("contracts", distributorContractsJson);
            distributorArrayJson.add(distributorJson);
        }

        JSONArray producerArrayJson = new JSONArray();
        for (Producer producer : producers) {
            JSONObject producerJson = new JSONObject();

            producerJson.put("id", producer.getId());
            producerJson.put("maxDistributors", producer.getMaxDistributors());
            producerJson.put("priceKW", producer.getPriceKW());
            producerJson.put("energyType", producer.getEnergyType().getLabel());
            producerJson.put("energyPerDistributor", producer.getEnergyPerDistributor());


            JSONArray producerMonthlyStatsJson = new JSONArray();
            if (producer.getMonthlyStats() != null) {
                for (MonthlyStats monthlyStats : producer.getMonthlyStats()) {
                    JSONObject monthlyStatsJson = new JSONObject();

                    monthlyStatsJson.put("month", monthlyStats.getMonth());
                    monthlyStatsJson.put("distributorsIds", monthlyStats.getDistributorIds());
                    producerMonthlyStatsJson.add(monthlyStatsJson);
                }
            }
            producerJson.put("monthlyStats", producerMonthlyStatsJson);
            producerArrayJson.add(producerJson);
        }

        //assemble final json object
        JSONObject output = new JSONObject();
        output.put("consumers", consumerArrayJson);
        output.put("distributors", distributorArrayJson);
        output.put("energyProducers", producerArrayJson);

        //write final json object to file
        FileWriter fileWriter = new FileWriter(outputPath);
        fileWriter.write(output.toJSONString());
        fileWriter.flush();
    }
}
