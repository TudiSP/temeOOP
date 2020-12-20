package entities;

import economics.Contract;

import java.util.List;

public class Distributor {
    private final int id;
    private final int contractLength;
    private int budget;
    private int infrastuctureCost;
    private int productionCost;
    private int contractPriceProposal;
    private int nrOfContracts;
    private boolean bankruptStatus;
    private int formerNrOfContracts;

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
        this.formerNrOfContracts = 0;
        this.bankruptStatus = false;
    }

    /**
     * Factory type function, Distributor creates a new Contract between it and a Consumer
     * using given parameters
     *
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

    public static void createProposalsAllDistributors(List<Distributor> distributors) {
        for (Distributor distributor : distributors) {
            distributor.createNewPriceProposal();
        }
    }

    public void createNewPriceProposal() {
        if(formerNrOfContracts != 0) {
            contractPriceProposal = (int) (Math.round(Math.floor(infrastuctureCost / formerNrOfContracts)
                    + productionCost) + 0.2 * productionCost);
        } else {
            contractPriceProposal = infrastuctureCost
                    + productionCost
                    + ((Long) Math.round(Math.floor(0.2 * productionCost))).intValue();
        }
    }

    public static void taxAllDistributors(List<Distributor> distributors) {
        for (Distributor distributor : distributors) {
            if (distributor.getBudget() >= 0) {
                distributor.payTax();
            }
            if (distributor.getBudget() < 0) {
                distributor.setBankruptStatus(true);
            }
        }
    }

    public void payTax() {
        budget -= infrastuctureCost + productionCost * formerNrOfContracts;
    }

    /**
     * check to see if distributor is bankrupt
     * @return
     */
    public boolean isBankrupt() {
        return bankruptStatus;
    }

    public void setBankruptStatus(boolean status) {
        bankruptStatus = status;
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

    public void setBudget(int budget) {
        this.budget = budget;
    }

    public void setInfrastuctureCost(int infrastuctureCost) {
        this.infrastuctureCost = infrastuctureCost;
    }

    public void setProductionCost(int productionCost) {
        this.productionCost = productionCost;
    }

    public void setContractPriceProposal(int contractPriceProposal) {
        this.contractPriceProposal = contractPriceProposal;
    }

    public void setNrOfContracts(int nrOfContracts) {
        this.nrOfContracts = nrOfContracts;
    }

    public int getFormerNrOfContracts() {
        return formerNrOfContracts;
    }

    public void setFormerNrOfContracts(int formerNrOfContracts) {
        this.formerNrOfContracts = formerNrOfContracts;
    }

    @Override
    public String toString() {
        return "Distributor{" +
                "id=" + id
                + ", contractLength="
                + contractLength + ", budget="
                + budget + ", infrastuctureCost="
                + infrastuctureCost + ", productionCost="
                + productionCost + ", nrOfContracts="
                + nrOfContracts + ", formerNrOfContracts="
                + formerNrOfContracts + "}";
    }
}


