package entities;

import java.util.List;

public class Consumer {
    private final int id;
    private int budget;
    private final int income;
    private boolean contractStatus;
    private boolean bankruptStatus;
    private boolean debtStatus;


    public Consumer(final int id, final int initialBudget, final int income) {
        this.id = id;
        this.budget = initialBudget;
        this.income = income;
        this.contractStatus = false;
        this.bankruptStatus = false;
        this.debtStatus = false;
    }


    /**
     * checks to see if a consumer has gone bankrupt
     * @return
     */
    public boolean isBankrupt() {
        return bankruptStatus;
    }

    /**
     * checks to see if a consumer is in debt
     * @return
     */
    public boolean isInDebt() {
        return debtStatus;
    }

    /**
     * Checks to see if a consumer already has a contract or not
     * @return
     */
    public boolean hasContract() {
        return contractStatus;
    }

    public static void payAllConsumers(List<Consumer> consumers) {
        for (Consumer consumer : consumers) {
            consumer.getPaid();
        }
    }

    public void getPaid() {
        budget += income;
    }

    public int getId() {
        return id;
    }

    public int getBudget() {
        return budget;
    }

    public void setBudget(int budget) {
        this.budget = budget;
    }

    public int getIncome() {
        return income;
    }

    /**
     * Modifies the value of contract(true or false)
     * @param status
     */
    public void setContractStatus(boolean status) {
        contractStatus = status;
    }

    public void setBankruptStatus(boolean status) {
        bankruptStatus = status;
    }

    public void setDebtStatus(boolean status) {
        debtStatus = status;
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
