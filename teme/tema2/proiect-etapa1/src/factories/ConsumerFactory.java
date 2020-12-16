package factories;

import entities.Consumer;

public class ConsumerFactory {
    public static Consumer createConsumer(final int id, final int initialBudget, final int income) {
        return new Consumer(id, initialBudget, income);
    }
}
