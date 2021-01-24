package economics;

import Utils.Utils;
import entities.Consumer;
import entities.Distributor;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * holds data regarding Consumer-Distributor interaction
 * and has methods that modifies each entity
 */
public class Contract {
    protected final int consumerId;
    protected final int distributorId;
    protected final int consumerTax;
    protected final int distributorRevenue;
    protected final int contractLength;
    protected int monthsLeft;

    /**
     * Initialises a new Contract using given parameters
     *
     * @param consumerId
     * @param distributorId
     * @param consumerTax
     * @param distributorRevenue
     * @param contractLength
     */
    public Contract(final int consumerId, final int distributorId, final int consumerTax,
                    final int distributorRevenue, final int contractLength) {
        this.consumerId = consumerId;
        this.distributorId = distributorId;
        this.consumerTax = consumerTax;
        this.distributorRevenue = distributorRevenue;
        this.contractLength = contractLength;
        this.monthsLeft = contractLength;
    }

    /**
     * a method called each month to apply the contracts and dissolve
     * them if consumer goes bankrupt. Also it checks to see if contract
     * is expired and the consumer can sign a new one
     *
     * @param consumers
     * @param distributors
     * @param contracts
     */
    public static void applyContracts(final List<Consumer> consumers,
                                      final List<Distributor> distributors,
                                      final List<Contract> contracts,
                                      final List<DelayedContract> delayedContracts) {
        Iterator<Contract> i = contracts.iterator();
        while (i.hasNext()) {
            Contract contract = i.next();

            //find the consumer and distributor by their id to make changes
            Consumer consumer = Utils.idToConsumerSearch(consumers, contract.consumerId);
            Distributor distributor = Utils.idToDistributorSearch(distributors,
                    contract.distributorId);

            //applies contract only if consumer is not bankrupt
            if (!consumer.isBankrupt()) {
                contract.applyContract(consumer, distributor, delayedContracts);
            }

            if (contract.isExpired() || consumer.isBankrupt()) {
                consumer.setContractStatus(false);
                distributor.setNrOfContracts(distributor.getNrOfContracts() - 1);
                //if consumer is bankrupt then the distributor still needs to pay for
                // the production cost for that month and will take it into consideration
                // for future offers
                if (consumer.isBankrupt()) {
                    distributor.setFormerNrOfContracts(distributor.getFormerNrOfContracts() - 1);
                    distributor.setBudget(distributor.getBudget()
                            - distributor.getProductionCost());
                    i.remove();
                }

            }

        }
    }

    /**
     * function that initialises the simulation before any monthly changes are made
     *
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

            //modify the status of the consumers
            consumer.setContractStatus(true);

            //add new contract to the contract list
            contracts.add(Distributor.createContract(consumer.getId(), winnerDistributor.getId(),
                    winnerDistributor.getContractPriceProposal(),
                    distributorRevenue, winnerDistributor.getContractLength()));
        }
        winnerDistributor.setNrOfContracts(consumers.size());
        winnerDistributor.setFormerNrOfContracts(consumers.size());

        return contracts;
    }

    /**
     * renews all contracts or creates others after the monthly changes are made
     *
     * @param distributors
     * @param consumers
     * @param contracts
     */
    public static void renewContracts(final List<Distributor> distributors,
                                      final List<Consumer> consumers,
                                      final List<Contract> contracts) {
        //the distributors each propose new prices based on their former contracts
        Distributor.createProposalsAllDistributors(distributors);

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

        //adds new contracts to the distributor with the lowest price(winnerDistributor)
        // if that is the case
        int nrOfContracts = winnerDistributor.getNrOfContracts();
        for (Consumer consumer : consumers) {
            int distributorRevenue = 0;
            //if we find a consumer with no contracts make them sign a new one
            if (!consumer.hasContract()) {
                nrOfContracts++;

                distributorRevenue = ((Long) Math.round(Math.floor(0.2
                        * winnerDistributor.getProductionCost()))).intValue();

                //modify the status of the consumers
                consumer.setContractStatus(true);

                //add new contract to the contract list
                contracts.add(Distributor.createContract(consumer.getId(),
                        winnerDistributor.getId(),
                        winnerDistributor.getContractPriceProposal(),
                        distributorRevenue, winnerDistributor.getContractLength()));
            }
        }
        winnerDistributor.setNrOfContracts(nrOfContracts);

        //updates their former number of contracts for a next proposals since
        //the actual number of contracts in the objects will change
        for (Distributor distributor : distributors) {
            distributor.setFormerNrOfContracts(distributor.getNrOfContracts());
        }

    }

    /**
     * dissolves expired contracts(removes them from the list)
     *
     * @param contracts
     */
    public static void dissolveContracts(final List<Contract> contracts) {
        Iterator<Contract> i = contracts.iterator();
        while (i.hasNext()) {
            Contract contract = i.next();
            if (contract.isExpired()) {
                i.remove();
            }
        }
    }

    /**
     * dissolves the contracts in which a distributor is signed
     * and modifies the status of the signed consumers to false
     * so they can sign a new contract next month
     *
     * @param distributorId
     * @param distributors
     * @param consumers
     * @param contracts
     */
    public static void dissolveContractsForDistributor(final int distributorId,
                                                       final List<Distributor> distributors,
                                                       final List<Consumer> consumers,
                                                       final List<Contract> contracts) {
        Iterator<Contract> contractIterator = contracts.iterator();
        while (contractIterator.hasNext()) {
            Contract contract = contractIterator.next();
            if (contract.distributorId == distributorId) {
                for (Consumer consumer : consumers) {
                    if (contract.getConsumerId() == consumer.getId()) {
                        consumer.setContractStatus(false);
                    }
                }
                contractIterator.remove();
            }
        }
    }

    /**
     * applies the terms of a single contract
     *
     * @param consumer
     * @param distributor
     * @param delayedContracts
     */
    public void applyContract(final Consumer consumer, final Distributor distributor,
                              final List<DelayedContract> delayedContracts) {
        if (consumer.getBudget() - consumerTax < 0) {
            //if consumer cannot pay it's end of the contract he creates a new
            //delayedContract which keeps track of it's debt
            delayedContracts.add(delayTax());
        } else {
            consumer.setBudget(consumer.getBudget() - consumerTax);
            distributor.setBudget(distributor.getBudget() + consumerTax);
        }
        monthsLeft--;
    }

    /**
     * factory method used to create delayed contracts
     *
     * @return
     */
    public DelayedContract delayTax() {
        return new DelayedContract(consumerId, distributorId,
                (int) Math.round(1.2 * consumerTax), distributorRevenue, 1);
    }

    /**
     * checks to see if contract is expired
     *
     * @return
     */
    public boolean isExpired() {
        return monthsLeft == 0;
    }

    public final int getConsumerTax() {
        return consumerTax;
    }

    public final int getConsumerId() {
        return consumerId;
    }

    public final int getDistributorId() {
        return distributorId;
    }

    public final int getMonthsLeft() {
        return monthsLeft;
    }

    @Override
    public final String toString() {
        return "Contract{"
                + "consumerId=" + consumerId
                + ", distributorId=" + distributorId
                + ", consumerTax=" + consumerTax
                + ", distributorRevenue=" + distributorRevenue
                + ", contractLength=" + contractLength
                + '}';
    }
}
