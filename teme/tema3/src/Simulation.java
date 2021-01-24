import Utils.Utils;
import economics.Contract;
import economics.DelayedContract;
import economics.MonthlyUpdate;
import entities.Consumer;
import entities.Distributor;
import entities.Producer;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Class constructed using singleton pattern to free space in main function
 * and to be used for ease of expansion of code if needed
 */
public final class Simulation {
    private static Simulation simulation = null;

    /**
     * private constructor so that it cannot be instatiated outside the class
     */
    private Simulation() {
    }

    /**
     * getInstance() makes sure that only one instance of this class
     * can be instatiated at one time
     *
     * @return
     */
    public static Simulation getInstance() {
        if (simulation == null) {
            simulation = new Simulation();
        }
        return simulation;
    }

    /**
     * runs the simulation
     *
     * @param consumers
     * @param distributors
     * @param contracts
     * @param monthlyUpdates
     * @param nrOfTurns
     */
    public void runSimulation(final List<Consumer> consumers,
                              final List<Distributor> distributors,
                              final List<Producer> producers,
                              List<Contract> contracts,
                              final List<MonthlyUpdate> monthlyUpdates,
                              final int nrOfTurns) {
        List<DelayedContract> delayedContracts = new ArrayList<>();

        //we assume that all consumers and distributors are not bankrupt from the start
        List<Consumer> eligibleConsumers = new ArrayList<>(consumers);
        List<Distributor> eligibleDistributors = new ArrayList<>(distributors);

        //turn 0 of the simulation
        Distributor.chooseProducersAndCalculateProductionCostsAll(distributors, producers);
        Distributor.createProposalsAllDistributors(distributors);
        contracts.addAll(Contract.initialiseContracts(consumers, distributors));
        Consumer.payAllConsumers(eligibleConsumers);
        Contract.applyContracts(eligibleConsumers, eligibleDistributors,
                contracts, delayedContracts);
        Distributor.taxAllDistributors(eligibleDistributors);

        //now we begin to make monthly changes
        for (int i = 0; i < nrOfTurns; i++) {
            //dissolve expired contracts
            Contract.dissolveContracts(contracts);

            //make changes for the month
            monthlyUpdates.get(i).updateMonth(distributors, producers,
                    eligibleConsumers, consumers);
            Consumer.payAllConsumers(eligibleConsumers);
            Contract.renewContracts(eligibleDistributors, eligibleConsumers, contracts);

            //apply all contracts
            DelayedContract.applyDelays(eligibleConsumers, eligibleDistributors,
                    contracts, delayedContracts);
            Contract.applyContracts(eligibleConsumers, eligibleDistributors,
                    contracts, delayedContracts);

            //tax distributors monthly
            Distributor.taxAllDistributors(eligibleDistributors);

            Utils.sortProducersById(producers);
            Producer.notifyMonthlyChangeAll(distributors, producers);

            //remove bankrupt consumers from simulation
            Iterator<Consumer> consumerIterator = eligibleConsumers.iterator();
            while (consumerIterator.hasNext()) {
                Consumer consumer = consumerIterator.next();
                if (consumer.isBankrupt()) {
                    consumerIterator.remove();
                }
            }

            //remove bankrupt distributors from simulation
            Iterator<Distributor> distributorIterator = eligibleDistributors.iterator();
            while (distributorIterator.hasNext()) {
                Distributor distributor = distributorIterator.next();
                if (distributor.isBankrupt()) {
                    //if a distributor is bankrupt dissolve all their contracts
                    Contract.dissolveContractsForDistributor(distributor.getId(),
                            eligibleDistributors, eligibleConsumers, contracts);

                    distributorIterator.remove();
                }
            }
            Producer.addMonthlyStatsAll(producers, i + 1);
        }
    }

}
