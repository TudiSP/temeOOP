package entities;

import Utils.Utils;
import economics.Contract;
import strategies.DistributorStrategy;
import strategies.GreenStrategy;
import strategies.PriceStrategy;
import strategies.QuantityStrategy;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * holds data and methods for distributors
 */
public final class Distributor {
    private final int id;
    private final int contractLength;
    private final int energyNeededKW;
    private final DistributorStrategy producerStrategy;
    private int budget;
    private int infrastuctureCost;
    // key = producer id
    // value = producer cost
    private Map<Integer, Double> producerCosts;
    private int productionCost;
    private int contractPriceProposal;
    private int nrOfContracts;
    private boolean bankruptStatus;
    private int formerNrOfContracts;

    /**
     * Creates and initialises a distributor according to parameters
     *
     * @param id
     * @param contractLength
     * @param budget
     * @param infrastuctureCost
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
        this.energyNeededKW = energyNeededKW;
        switch (producerStrategy) {
            case "GREEN" -> this.producerStrategy = new GreenStrategy();
            case "PRICE" -> this.producerStrategy = new PriceStrategy();
            default -> this.producerStrategy = new QuantityStrategy();
        }
        //we initialise the contractPriceProposal considering that
        //a new distributor doesn't have customers from the start
        //no contracts signed yet
        this.nrOfContracts = 0;
        this.formerNrOfContracts = 0;
        this.bankruptStatus = false;
    }

    /**
     * Method to be used at round 0 of simulation to choose producers
     * and calculate production costs for all distributors
     *
     * @param distributors
     * @param producers
     */
    public static void chooseProducersAndCalculateProductionCostsAll
    (final List<Distributor> distributors,
     final List<Producer> producers) {
        for (Producer producer : producers) {
            producer.setObserverDistributors(new ArrayList<>());
        }
        for (Distributor distributor : distributors) {
            // choose the producers
            distributor.setProducerCosts(distributor
                    .getProducerStrategy().chooseProducers(distributor, producers));
            // calculate the production cost
            distributor.calculateProductionCost();
        }
    }

    /**
     * method to be used each month for every distributor affected by
     * their producers monthly changes
     *
     * @param distributor
     * @param producers
     */
    public static void updateStrategiesAndRecalculateProductionCosts
    (final Distributor distributor, final List<Producer> producers) {
        // choose the producers
        distributor.setProducerCosts(distributor
                .getProducerStrategy().chooseProducers(distributor, producers));
        // calculate the production cost
        distributor.calculateProductionCost();
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
     * update observer with new data, recalculate strategies
     *
     * @param distributor
     * @param producers
     */
    public void update(final Distributor distributor,
                       final List<Producer> producers) {
        Utils.purgeDistributorFromObserverLists(distributor, producers);
        Distributor.updateStrategiesAndRecalculateProductionCosts(distributor, producers);
    }

    /**
     * calculate the production cost based on producers costs
     */
    public void calculateProductionCost() {
        Double totalCost = 0.0;
        for (Map.Entry<Integer, Double> cost : producerCosts.entrySet()) {
            totalCost += cost.getValue();
        }
        setProductionCost(((Long) Math.round(Math.floor(totalCost / 10))).intValue());
    }

    /**
     * creates a new price proposal using various data;
     * only the former number of contracts from last proposal
     * is taken into account
     */
    public void createNewPriceProposal() {
        if (formerNrOfContracts != 0) {
            contractPriceProposal = (int) (Math.round(Math.floor(infrastuctureCost
                    / formerNrOfContracts)
                    + productionCost) + 0.2 * productionCost);
        } else {
            //if there were no contracts signed last in last proposal
            contractPriceProposal = (infrastuctureCost + productionCost
                    + ((Long) Math.round(Math.floor(0.2 * productionCost))).intValue());
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

    public Map<Integer, Double> getProducerCosts() {
        return producerCosts;
    }

    public void setProducerCosts(Map<Integer, Double> producerCosts) {
        this.producerCosts = producerCosts;
    }

    public int getEnergyNeededKW() {
        return energyNeededKW;
    }

    public DistributorStrategy getProducerStrategy() {
        return producerStrategy;
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


