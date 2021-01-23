package entities;

import java.util.List;

/**
 * holds data and methods for consumers
 */
public final class Consumer {
    private final int id;
    private final int income;
    private int budget;
    private boolean contractStatus;
    private boolean bankruptStatus;

    /**
     * Creates and initialises a consumer according to parameters
     *
     * @param id
     * @param initialBudget
     * @param income
     */
    public Consumer(final int id, final int initialBudget, final int income) {
        this.id = id;
        this.budget = initialBudget;
        this.income = income;
        this.contractStatus = false;
        this.bankruptStatus = false;
    }

    /**
     * adds each consumer's monthly income to their budget
     *
     * @param consumers
     */
    public static void payAllConsumers(final List<Consumer> consumers) {
        for (Consumer consumer : consumers) {
            consumer.getPaid();
        }
    }

    /**
     * checks to see if a consumer has gone bankrupt
     *
     * @return
     */
    public boolean isBankrupt() {
        return bankruptStatus;
    }

    /**
     * Checks to see if a consumer already has a contract or not
     *
     * @return
     */
    public boolean hasContract() {
        return contractStatus;
    }

    /**
     * method that adds a consumers monthly income to their budget
     */
    public void getPaid() {
        budget += income;
    }

    public int getId() {
        return id;
    }

    public int getBudget() {
        return budget;
    }

    public void setBudget(final int budget) {
        this.budget = budget;
    }

    /**
     * Modifies the value of contractStatus(true or false)
     *
     * @param status
     */
    public void setContractStatus(final boolean status) {
        contractStatus = status;
    }

    /**
     * Modifies the value of bankruptStatus(true or false)
     *
     * @param status
     */
    public void setBankruptStatus(final boolean status) {
        bankruptStatus = status;
    }

    @Override
    public String toString() {
        return "Consumer{" + "id=" + id
                + ", budget=" + budget
                + ", income=" + income
                + ", hasContract=" + hasContract()
                + ", isBankrupt=" + isBankrupt()
                + '}';
    }
}
