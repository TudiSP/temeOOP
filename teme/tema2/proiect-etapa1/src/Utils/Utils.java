package Utils;

import economics.Contract;
import entities.Consumer;
import entities.Distributor;

import java.util.ArrayList;
import java.util.List;

public class Utils {
    public static List<Contract> distributorIdToContractSearch(List<Contract> contracts,
                                                      final int id) throws IllegalStateException {
        List<Contract> distributorContracts = new ArrayList<>();
        for (Contract contract : contracts) {
            if (contract.getDistributorId() == id) {
                distributorContracts.add(contract);
            }
        }
        return distributorContracts;
    }

    public static Contract consumerIdToContractSearch(List<Contract> contracts,
                                              final int id) throws IllegalStateException {

        for (Contract contract : contracts) {
            if (contract.getConsumerId() == id) {
                return contract;
            }
        }
        throw new IllegalStateException("no Consumer by that id found.");
    }

    public static Consumer idToConsumerSearch(List<Consumer> consumers,
                                              final int id) throws IllegalStateException {

        for (Consumer consumer : consumers) {
            if (consumer.getId() == id) {

                return consumer;
            }
        }
        throw new IllegalStateException("no Consumer by that id found.");
    }

    public static Distributor idToDistributorSearch(List<Distributor> distributors,
                                              final int id) throws IllegalStateException {

        for (Distributor distributor : distributors) {
            if (distributor.getId() == id) {
                return distributor;
            }
        }
        throw new IllegalStateException("no Distributor by that id found.");
    }
}
