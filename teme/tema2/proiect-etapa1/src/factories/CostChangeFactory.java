package factories;

import economics.CostChange;

public class CostChangeFactory {
    public static CostChange createCostChange(final int id, final int infrastuctureCost,
                                              final int productionCost) {
        return new CostChange(id, infrastuctureCost, productionCost);
    }
}
