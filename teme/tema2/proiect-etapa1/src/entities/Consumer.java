package entities;

public class Consumer {
    private final int id;
    private int budget;
    private final int income;
    private int expenses;

    public Consumer(final int id, final int initialBudget, final int income) {
        this.id = id;
        this.budget = initialBudget;
        this.income = income;
        this.expenses = 0;
    }

    public void calculateMonthlyBudget() {

    }

    public boolean isBankrupt() {
        if(budget <= 0) {
            return true;
        }
        return false;
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

    public int getExpenses() {
        return expenses;
    }

    public void setExpenses(int expenses) {
        this.expenses = expenses;
    }

    @Override
    public String toString() {
        return "Consumer{" + "id=" + id
                + ", budget=" + budget
                + ", income=" + income
                + ", expenses=" + expenses
                + '}';
    }
}
