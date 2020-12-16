package factories;

import entities.Distributor;

public class DistributorFactory {
    /**
     * creates and returns a new Distributor object based on params
     * @param id
     * @param contractLength
     * @param budget
     * @param infrastuctureCost
     * @param productionCost
     * @return
     */
    public static Distributor createDistributor(final int id, final int contractLength,
                                                final int budget, final int infrastuctureCost,
                                                final int productionCost) {
        return new Distributor(id, contractLength, budget, infrastuctureCost, productionCost);
    }
}
