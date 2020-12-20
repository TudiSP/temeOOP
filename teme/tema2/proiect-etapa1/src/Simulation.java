import economics.Contract;
import economics.DelayedContract;
import economics.MonthlyUpdate;
import entities.Consumer;
import entities.Distributor;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Class constructed using singleton pattern to free space in main function
 * and to be used for ease of expansion of code if needed
 */
public class Simulation {
    private static Simulation simulation = null;

    /**
     * private constructor so that it cannot be instatiated outside the class
     */
    private Simulation() {}

    /**
     * getInstance() makes sure that only one instance of this class
     * can be instatiated at one time
     * @return
     */
    public static  Simulation getInstance() {
        if (simulation == null) {
            simulation = new Simulation();
        }
        return simulation;
    }

    /**
     * runs the simulation
     * @param consumers
     * @param distributors
     * @param contracts
     * @param monthlyUpdates
     * @param nrOfTurns
     */
    public void runSimulation(List<Consumer> consumers,  List<Distributor> distributors,
                              List<Contract> contracts, List<MonthlyUpdate> monthlyUpdates, final int nrOfTurns) {
        List<DelayedContract> delayedContracts = new ArrayList<>();

        //we assume that all consumers and distributors are not bankrupt from the start
        List<Consumer> eligibleConsumers = new ArrayList<>(consumers);
        List<Distributor> eligibleDistributors = new ArrayList<>(distributors);


        Consumer.payAllConsumers(eligibleConsumers);
        Contract.applyContracts(eligibleConsumers, eligibleDistributors, contracts, delayedContracts);
        Distributor.taxAllDistributors(eligibleDistributors);
        for (int i = 0; i < nrOfTurns; i++) {
            Contract.dissolveContracts(contracts);

            //make changes for the month
            monthlyUpdates.get(i).updateMonth(distributors, eligibleConsumers, consumers);
            Consumer.payAllConsumers(eligibleConsumers);
            Contract.renewContracts(eligibleDistributors, eligibleConsumers, contracts);

            DelayedContract.applyDelays(eligibleConsumers, eligibleDistributors,
                    contracts, delayedContracts);
            Contract.applyContracts(eligibleConsumers, eligibleDistributors,
                    contracts, delayedContracts);
            Distributor.taxAllDistributors(eligibleDistributors);

            //remove bankrupt customers from simulation
            Iterator<Consumer> consumerIterator = eligibleConsumers.iterator();
            while (consumerIterator.hasNext()) {
                Consumer consumer = consumerIterator.next();
                if(consumer.isBankrupt()) {
                    consumerIterator.remove();
                }
            }

            //remove bankrupt distributors from simulation
            Iterator<Distributor> distributorIterator = eligibleDistributors.iterator();
            while (distributorIterator.hasNext()) {
                Distributor distributor = distributorIterator.next();
                if(distributor.isBankrupt()) {
                    Contract.dissolveContractsForDistributor(distributor.getId(),
                            eligibleDistributors, eligibleConsumers, contracts);
                    distributorIterator.remove();
                }
            }
        }

    }
}
