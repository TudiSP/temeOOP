package inputOutput;

import Utils.Utils;
import economics.Contract;
import entities.Consumer;
import entities.Distributor;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class Output {
    private static int printNumber = 1;
    public static void writeToOutput(String inputPath,
                                    String outputPath,
                                     List<Consumer> consumers,
                                     List<Distributor> distributors,
                                     List<Contract> contracts) throws IOException {

        JSONArray consumerArrayJson = new JSONArray();
        for (Consumer consumer : consumers) {
            JSONObject consumerJson = new JSONObject();
            consumerJson.put("id", consumer.getId());
            consumerJson.put("isBankrupt", consumer.isBankrupt());
            consumerJson.put("budget", consumer.getBudget());
            consumerArrayJson.add(consumerJson);
        }

        JSONArray distributorArrayJson = new JSONArray();
        for(Distributor distributor : distributors) {
            JSONObject distributorJson = new JSONObject();
            List<Contract> distributorContracts = Utils
                    .distributorIdToContractSearch(contracts, distributor.getId());

            distributorJson.put("id", distributor.getId());
            distributorJson.put("budget", distributor.getBudget());
            distributorJson.put("isBankrupt", distributor.isBankrupt());

            JSONArray distributorContractsJson = new JSONArray();
            for (Contract distributorContract : distributorContracts) {
                JSONObject distributorContractJson = new JSONObject();

                distributorContractJson.put("consumerId", distributorContract.getConsumerId());
                distributorContractJson.put("price", distributorContract.getConsumerTax());
                distributorContractJson.put("remainedContractMonths", distributorContract.getMonthsLeft());
                distributorContractsJson.add(distributorContractJson);
            }
            distributorJson.put("contracts", distributorContractsJson);
            distributorArrayJson.add(distributorJson);
        }
        JSONObject output = new JSONObject();
        output.put("consumers", consumerArrayJson);
        output.put("distributors", distributorArrayJson);

        FileWriter fileWriter = new FileWriter(outputPath);
        fileWriter.write(output.toJSONString());
        fileWriter.flush();
    }
}
