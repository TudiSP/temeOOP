package factories;

import economics.MonthlyUpdate;

public final class MonthlyUpdateFactory {

    /**
     * Utility class, therefore cannot be instantiated
     */
    private MonthlyUpdateFactory() {
    }

    /**
     * Factory method for creating a new consumer
     *
     * @return
     */
    public static MonthlyUpdate createMonthlyUpdate() {
        return new MonthlyUpdate();
    }
}
