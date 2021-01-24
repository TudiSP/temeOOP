package factories;

import economics.DistributorChange;

public final class DistributorChangeFactory {

    /**
     * Utility class, therefore cannot be instantiated
     */
    private DistributorChangeFactory() {
    }

    /**
     * Factory method for creating a new distributor change
     *
     * @param id
     * @param infrastuctureCost
     * @return
     */
    public static DistributorChange createDistributorChange(final int id,
                                                            final int infrastuctureCost) {
        return new DistributorChange(id, infrastuctureCost);
    }
}
