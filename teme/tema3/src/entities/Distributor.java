package entities;

import economics.Contract;
import strategies.EnergyChoiceStrategyType;

import java.util.List;

/**
 * holds data and methods for distributors
 */
public final class Distributor {
    private final int id;
    private final int contractLength;
    private int budget;
    private int infrastuctureCost;
    private int productionCost;
    private int contractPriceProposal;
    private int nrOfContracts;
    private boolean bankruptStatus;
    private int formerNrOfContracts;
    private final int energyNeededKW;
    private final EnergyChoiceStrategyType producerStrategy;

    /**
     * Creates and initialises a distributor according to parameters
     *  @param id
     * @param contractLength
     * @param budget
     * @param infrastuctureCost
     * @param productionCost
     * @param energyNeededKW
     * @param producerStrategy
     */
    public Distributor(final int id, final int contractLength, final int budget,
                       final int infrastuctureCost, final int energyNeededKW,
                       final String producerStrategy) {
        this.id = id;
        this.contractLength = contractLength;
        this.budget = budget;
        this.infrastuctureCost = infrastuctureCost;
        this.productionCost = 0; //modify with Producer
        this.energyNeededKW = energyNeededKW;
        switch (producerStrategy) {
            case "GREEN" -> this.producerStrategy = EnergyChoiceStrategyType.GREEN;
            case "PRICE" -> this.producerStrategy = EnergyChoiceStrategyType.PRICE;
            default -> this.producerStrategy = EnergyChoiceStrategyType.QUANTITY;
        }
        //we initialise the contractPriceProposal considering that
        //a new distributor doesn't have customers from the start
        this.contractPriceProposal = infrastuctureCost
                + productionCost
                + ((Long) Math.round(Math.floor(0.2 * productionCost))).intValue();
        //no contracts signed yet
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

    /**
     * creates new price proposals for future contracts for all distributors
     *
     * @param distributors
     */
    public static void createProposalsAllDistributors(final List<Distributor> distributors) {
        for (Distributor distributor : distributors) {
            distributor.createNewPriceProposal();
        }
    }

    /**
     * makes all distributors pay their taxes and marks them as bankrupt
     * if they go below their budget
     *
     * @param distributors
     */
    public static void taxAllDistributors(final List<Distributor> distributors) {
        for (Distributor distributor : distributors) {
            if (distributor.getBudget() >= 0) {
                distributor.payTax();
            }
            if (distributor.getBudget() < 0) {
                distributor.setBankruptStatus(true);
            }
        }
    }

    /**
     * creates a new price proposal using various data;
     * only the former number of contracts from last proposal
     * is taken into account
     */
    public void createNewPriceProposal() {
        if (formerNrOfContracts != 0) {
            contractPriceProposal = (int) (Math.round(Math.floor(infrastuctureCost / formerNrOfContracts)
                    + productionCost) + 0.2 * productionCost);
        } else {
            //if there were no contracts signed last in last proposal
            contractPriceProposal = infrastuctureCost
                    + productionCost
                    + ((Long) Math.round(Math.floor(0.2 * productionCost))).intValue();
        }
    }

    /**
     * method for subtracting a distributor's taxes form their budget
     */
    public void payTax() {
       budget -= infrastuctureCost + productionCost * formerNrOfContracts;
    }

    /**
     * check to see if distributor is bankrupt
     *
     * @return
     */
    public boolean isBankrupt() {
        return bankruptStatus;
    }

    /**
     * sets the bankrupt status
     *
     * @param status
     */
    public void setBankruptStatus(final boolean status) {
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

    public void setBudget(final int budget) {
        this.budget = budget;
    }

    public int getProductionCost() {
        return productionCost;
    }

    public void setProductionCost(final int productionCost) {
        this.productionCost = productionCost;
    }

    public int getContractPriceProposal() {
        return contractPriceProposal;
    }

    public int getNrOfContracts() {
        return nrOfContracts;
    }

    public void setNrOfContracts(final int nrOfContracts) {
        this.nrOfContracts = nrOfContracts;
    }

    public void setInfrastuctureCost(final int infrastuctureCost) {
        this.infrastuctureCost = infrastuctureCost;
    }

    public int getFormerNrOfContracts() {
        return formerNrOfContracts;
    }

    public void setFormerNrOfContracts(final int formerNrOfContracts) {
        this.formerNrOfContracts = formerNrOfContracts;
    }

    @Override
    public String toString() {
        return "Distributor{" + "id="
                + id + ", contractLength="
                + contractLength + ", budget="
                + budget + ", infrastuctureCost="
                + infrastuctureCost + ", productionCost="
                + productionCost + ", nrOfContracts="
                + nrOfContracts + ", formerNrOfContracts="
                + formerNrOfContracts + "}";
    }
}


