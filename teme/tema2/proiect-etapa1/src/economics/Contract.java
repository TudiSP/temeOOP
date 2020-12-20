package economics;

import Utils.Utils;
import entities.Consumer;
import entities.Distributor;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Observable;

public class Contract {
    protected final int consumerId;
    protected final int distributorId;
    protected int consumerTax;
    protected final int distributorRevenue;
    protected final int contractLength;
    protected int monthsLeft;

    public Contract(int consumerId, int distributorId, int consumerTax,
                    int distributorRevenue, final int contractLength) {
        this.consumerId = consumerId;
        this.distributorId = distributorId;
        this.consumerTax = consumerTax;
        this.distributorRevenue = distributorRevenue;
        this.contractLength = contractLength;
        this.monthsLeft = contractLength;
    }

    public void applyContract(Consumer consumer, Distributor distributor,
                              List<DelayedContract> delayedContracts) {
        if (consumer.getBudget() - consumerTax < 0) {
                delayedContracts.add(delayTax());
        } else {
            consumer.setBudget(consumer.getBudget() - consumerTax);
            distributor.setBudget(distributor.getBudget() + consumerTax);
        }

        monthsLeft--;
    }

    /**
     * a method called each month to apply the contracts and dissolve some if expired
     * @param consumers
     * @param distributors
     * @param contracts
     */
    public static void applyContracts(final List<Consumer> consumers,
                                      final List<Distributor> distributors,
                                      List<Contract> contracts,
                                      List<DelayedContract> delayedContracts) {
        Iterator<Contract> i = contracts.iterator();
        while (i.hasNext()){
            Contract contract = i.next();

            Consumer consumer = Utils.idToConsumerSearch(consumers, contract.consumerId);
            Distributor distributor = Utils.idToDistributorSearch(distributors, contract.distributorId);
            if (!consumer.isBankrupt()) {
                contract.applyContract(consumer, distributor, delayedContracts);
            }

            if (contract.isExpired() || consumer.isBankrupt()) {
                consumer.setContractStatus(false);
                distributor.setNrOfContracts(distributor.getNrOfContracts() - 1);
                if(consumer.isBankrupt()) {
                    distributor.setFormerNrOfContracts(distributor.getFormerNrOfContracts() - 1);
                    distributor.setBudget(distributor.getBudget() - distributor.getProductionCost());
                    i.remove();
                }

            }

        }
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



    public static void renewContracts(List<Distributor> distributors,
                                      List<Consumer> consumers,
                                      List<Contract> contracts) {
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
        int nrOfContracts = winnerDistributor.getNrOfContracts();
        for (Consumer consumer : consumers) {
            int distributorRevenue = 0;
            if (!consumer.hasContract()) {
                nrOfContracts++;

                distributorRevenue = ((Long) Math.round(Math.floor(0.2
                        * winnerDistributor.getProductionCost()))).intValue();

                //modify the status of the consumers
                consumer.setContractStatus(true);

                //add new contract to the contract list
                contracts.add(Distributor.createContract(consumer.getId(), winnerDistributor.getId(),
                        winnerDistributor.getContractPriceProposal(),
                        distributorRevenue, winnerDistributor.getContractLength()));
            }
        }
        winnerDistributor.setNrOfContracts(nrOfContracts);

        for (Distributor distributor : distributors) {
            distributor.setFormerNrOfContracts(distributor.getNrOfContracts());
        }

    }

    public  static void dissolveContracts(List<Contract> contracts) {
       Iterator<Contract> i = contracts.iterator();
       while (i.hasNext()) {
           Contract contract = i.next();
           if (contract.isExpired()) {
               i.remove();
           }
       }
    }

    public  static void dissolveContractsForDistributor(int distributorId, List<Distributor> distributors,
                                                        List<Consumer> consumers,
                                                        List<Contract> contracts) {
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
    public DelayedContract delayTax() {
        return new DelayedContract(consumerId, distributorId,
                (int) Math.round(1.2 * consumerTax), distributorRevenue, 1);
    }
    public boolean isExpired() {
        if (monthsLeft == 0) {
            return true;
        }
        return false;
    }

    public int getConsumerTax() {
        return consumerTax;
    }

    public int getConsumerId() {
        return consumerId;
    }

    public int getDistributorId() {
        return distributorId;
    }

    public int getMonthsLeft() {
        return monthsLeft;
    }

    @Override
    public String toString() {
        return "Contract{"
                + "consumerId=" + consumerId
                + ", distributorId=" + distributorId
                + ", consumerTax=" + consumerTax
                + ", distributorRevenue=" + distributorRevenue
                + ", contractLength=" + contractLength
                + '}';
    }
}
