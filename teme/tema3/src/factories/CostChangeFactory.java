package factories;

import economics.CostChange;

public final class CostChangeFactory {

    /**
     * Utility class, therefore cannot be instantiated
     */
    private CostChangeFactory() {
    }

    /**
     * Factory method for creating a new cost change
     *
     * @param id
     * @param infrastuctureCost
     * @param productionCost
     * @return
     */
    public static CostChange createCostChange(final int id, final int infrastuctureCost,
                                              final int productionCost) {
        return new CostChange(id, infrastuctureCost, productionCost);
    }
}
