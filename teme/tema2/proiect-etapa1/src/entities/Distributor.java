package entities;

import economics.Contract;

public class Distributor {
    private final int id;
    private final int contractLength;
    private int budget;
    private int infrastuctureCost;
    private int productionCost;
    private int contractPriceProposal;
    private int nrOfContracts;

    public Distributor(final int id, final int contractLength, final int budget,
                       final int infrastuctureCost, final int productionCost) {
        this.id = id;
        this.contractLength = contractLength;
        this.budget = budget;
        this.infrastuctureCost = infrastuctureCost;
        this.productionCost = productionCost;
        //we initialise the contractPriceProposal considering that
        //a new distributor doesn't have customers from the start
        this.contractPriceProposal = infrastuctureCost
                + productionCost
                + ((Long) Math.round(Math.floor(0.2 * productionCost))).intValue();
        this.nrOfContracts = 0;
    }

    /**
     * Factory type function, Distributor creates a new Contract between it and a Consumer
     * using given parameters
     * @param consumerId
     * @param distributorId
     * @param contractPrice
     * @param revenue
     * @param contractLength
     * @return
     */
    public static Contract createContract(final int consumerId, final int distributorId,
                                          final int contractPrice, final int revenue,
                                          final int contractLength) {
        return new Contract(consumerId, distributorId, contractPrice, revenue, contractLength);
    }

    public int getId() {
        return id;
    }

    public int getContractLength() {
        return contractLength;
    }

    public int getBudget() {
        return budget;
    }

    public int getInfrastuctureCost() {
        return infrastuctureCost;
    }

    public int getProductionCost() {
        return productionCost;
    }

    public int getContractPriceProposal() {
        return contractPriceProposal;
    }

    public int getNrOfContracts() {
        return nrOfContracts;
    }

    @Override
    public String toString() {
        return "Distributor{" +
                "id=" + id
                + ", contractLength="
                + contractLength + ", budget="
                + budget + ", infrastuctureCost="
                + infrastuctureCost + ", productionCost="
                + productionCost + '}';
    }
}
