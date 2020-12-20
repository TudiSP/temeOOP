package economics;

/**
 * Class used to store data about cost changes
 */
public final class CostChange {
    private final int id;
    private final int infrastuctureCost;
    private final int productionCost;

    /**
     * holds the monthly cost changes for distributors
     *
     * @param id                distributor id
     * @param infrastuctureCost
     * @param productionCost
     */
    public CostChange(final int id, final int infrastuctureCost, final int productionCost) {
        this.id = id;
        this.infrastuctureCost = infrastuctureCost;
        this.productionCost = productionCost;
    }

    public int getId() {
        return id;
    }

    public int getInfrastuctureCost() {
        return infrastuctureCost;
    }

    public int getProductionCost() {
        return productionCost;
    }

    @Override
    public String toString() {
        return "CostChange{"
                + "id=" + id
                + ", infrastuctureCost=" + infrastuctureCost
                + ", productionCost=" + productionCost
                + '}';
    }
}
