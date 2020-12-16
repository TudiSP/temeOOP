package economics;

import entities.Consumer;
import entities.Distributor;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

public class Contract extends Observable {
    private final int consumerId;
    private final int distributorId;
    private int consumerTax;
    private final int distributorRevenue;
    private final int contractLength;
    private int monthsLeft;

    public Contract(int consumerId, int distributorId, int consumerTax,
                    int distributorRevenue, final int contractLength) {
        this.consumerId = consumerId;
        this.distributorId = distributorId;
        this.consumerTax = consumerTax;
        this.distributorRevenue = distributorRevenue;
        this.contractLength = contractLength;
        this.monthsLeft = contractLength;
    }

    /**
     * function that initialises the simulation before any monthly changes are made
     * @param consumers
     * @param distributors
     * @return
     */
    public static List<Contract> initialiseContracts(final List<Consumer> consumers,
                                                     final List<Distributor> distributors) {
        List<Contract> contracts = new ArrayList<>();

        // we assume that there's at least one distributor
        int lowestContractPriceProposal = distributors.get(0).getContractPriceProposal();
        Distributor winnerDistributor = distributors.get(0);
        // find the best offer
        for (int i = 1; i < distributors.size(); i++) {
            int distributorContractPriceProposal = distributors.get(i).getContractPriceProposal();
            if (lowestContractPriceProposal > distributorContractPriceProposal) {
                lowestContractPriceProposal = distributorContractPriceProposal;
                winnerDistributor = distributors.get(i);
            }
        }
        for (Consumer consumer : consumers) {
            int distributorRevenue = ((Long) Math.round(Math.floor(0.2
                    * winnerDistributor.getProductionCost()))).intValue();

            //add new contract to the contract list
            contracts.add(Distributor.createContract(consumer.getId(), winnerDistributor.getId(),
                    winnerDistributor.getContractPriceProposal(),
                    distributorRevenue, winnerDistributor.getContractLength()));
        }

        return contracts;
    }

    public static void renewContracts(final List<Consumer> consumers,
                                      final List<Distributor> distributors,
                                      final List<MonthlyUpdate> monthlyUpdates,
                                      List<Contract> contracts) {

    }
}
