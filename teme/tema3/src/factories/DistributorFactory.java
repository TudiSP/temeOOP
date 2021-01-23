package factories;

import entities.Distributor;

public final class DistributorFactory {

    /**
     * Utility class, therefore cannot be instantiated
     */
    private DistributorFactory() {
    }

    /**
     * Factory method for creating a new distributor
     *
     * @param id
     * @param contractLength
     * @param budget
     * @param infrastuctureCost
     * @return
     */
    public static Distributor createDistributor(final int id, final int contractLength,
                                                final int budget, final int infrastuctureCost,
                                                final int energyNeededKW,
                                                final String producerStrategy) {
        return new Distributor(id, contractLength, budget, infrastuctureCost,
                energyNeededKW, producerStrategy);
    }
}
