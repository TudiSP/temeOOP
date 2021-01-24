package Utils;

import economics.Contract;
import entities.Consumer;
import entities.Distributor;
import entities.Producer;

import java.util.ArrayList;
import java.util.List;

/**
 * a class which holds utility methods
 */
public class Utils {
    /**
     * searches and returns a list of contracts based on a distributor id
     *
     * @param contracts
     * @param id
     * @return
     * @throws IllegalStateException
     */
    public static List<Contract> distributorIdToContractSearch(final List<Contract> contracts,
                                                               final int id) {
        List<Contract> distributorContracts = new ArrayList<>();
        for (Contract contract : contracts) {
            if (contract.getDistributorId() == id) {
                distributorContracts.add(contract);
            }
        }
        return distributorContracts;
    }

    /**
     * searches and returns a contract based on a consumer id
     *
     * @param contracts
     * @param id
     * @return
     * @throws IllegalStateException
     */
    public static Contract consumerIdToContractSearch(final List<Contract> contracts,
                                                      final int id) throws IllegalStateException {

        for (Contract contract : contracts) {
            if (contract.getConsumerId() == id) {
                return contract;
            }
        }
        throw new IllegalStateException("no Contract by that id found.");
    }

    /**
     * searches and returns a consumer based on an id
     *
     * @param consumers
     * @param id
     * @return
     * @throws IllegalStateException
     */
    public static Consumer idToConsumerSearch(final List<Consumer> consumers,
                                              final int id) throws IllegalStateException {

        for (Consumer consumer : consumers) {
            if (consumer.getId() == id) {

                return consumer;
            }
        }
        throw new IllegalStateException("no Consumer by that id found.");
    }

    /**
     * searches and returns a distributor based on an id
     *
     * @param distributors
     * @param id
     * @return
     * @throws IllegalStateException
     */
    public static Distributor idToDistributorSearch(final List<Distributor> distributors,
                                                    final int id) throws IllegalStateException {

        for (Distributor distributor : distributors) {
            if (distributor.getId() == id) {
                return distributor;
            }
        }
        throw new IllegalStateException("no Distributor by that id found.");
    }

    /**
     * searches and returns a producer based on an id
     * @param producers
     * @param id
     * @return
     * @throws IllegalStateException
     */
    public static Producer idToProducerSearch(final List<Producer> producers,
                                                 final int id) throws IllegalStateException {

        for (Producer producer : producers) {
            if (producer.getId() == id) {
                return producer;
            }
        }
        throw new IllegalStateException("no producer by that id found.");
    }

    public static void sortProducersById(final List<Producer> producers) {
        for (int i = 0; i < producers.size() - 1; i++) {
            for (int j = i + 1; j < producers.size(); j++) {
                if (producers.get(i).getId() > producers.get(j).getId()) {
                    Producer aux = producers.get(i);
                    producers.set(i, producers.get(j));
                    producers.set(j, aux);
                }
            }
        }

    }
}
