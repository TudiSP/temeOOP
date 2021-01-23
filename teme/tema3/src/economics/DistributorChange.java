package economics;

/**
 * Class used to store data about distributor changes
 */
public final class DistributorChange {
    private final int id;
    private final int infrastuctureCost;

    /**
     * holds the monthly cost changes for distributors
     *
     * @param id                distributor id
     * @param infrastuctureCost
     */
    public DistributorChange(final int id, final int infrastuctureCost) {
        this.id = id;
        this.infrastuctureCost = infrastuctureCost;
    }

    public int getId() {
        return id;
    }

    public int getInfrastuctureCost() {
        return infrastuctureCost;
    }


    @Override
    public String toString() {
        return "CostChange{"
                + "id=" + id
                + ", infrastuctureCost=" + infrastuctureCost
                + '}';
    }
}
