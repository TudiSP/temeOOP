package factories;

import entities.Consumer;

public final class ConsumerFactory {

    /**
     * Utility class, therefore cannot be instantiated
     */
    private ConsumerFactory() {
    }

    /**
     * Factory method for creating a new consumer
     *
     * @param id
     * @param initialBudget
     * @param income
     * @return
     */
    public static Consumer createConsumer(final int id, final int initialBudget, final int income) {
        return new Consumer(id, initialBudget, income);
    }
}
