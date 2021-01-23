package entities;

/**
 * holds data and methods for producers
 */
public final class Producer {
    private final int id;
    private final EnergyType energyType;
    private final int maxDistributors;
    private final double priceKW;
    private int energyPerDistributor;

    /**
     * creates and initialises a producer according to parameters
     * @param id
     * @param energyType
     * @param maxDistributors
     * @param priceKW
     * @param energyPerDistributor
     */
    public Producer(final int id, final String energyType, final int maxDistributors,
                    final double priceKW, final int energyPerDistributor) {
        this.id = id;
        switch (energyType) {
            case "WIND" -> this.energyType = EnergyType.WIND;
            case "NUCLEAR" -> this.energyType = EnergyType.NUCLEAR;
            case "HYDRO" -> this.energyType = EnergyType.HYDRO;
            case "SOLAR" -> this.energyType = EnergyType.SOLAR;
            default -> this.energyType = EnergyType.COAL;
        }
        this.maxDistributors = maxDistributors;
        this.priceKW = priceKW;
        this.energyPerDistributor = energyPerDistributor;
    }

    public int getId() {
        return id;
    }

    public EnergyType getEnergyType() {
        return energyType;
    }

    public int getMaxDistributors() {
        return maxDistributors;
    }

    public double getPriceKW() {
        return priceKW;
    }

    public int getEnergyPerDistributor() {
        return energyPerDistributor;
    }

    public void setEnergyPerDistributor(int energyPerDistributor) {
        this.energyPerDistributor = energyPerDistributor;
    }

    @Override
    public String toString() {
        return "Producer{"
                + "id=" + id
                + ", energyType=" + energyType
                + ", maxDistributors=" + maxDistributors
                + ", priceKW=" + priceKW
                + ", energyPerDistributor=" + energyPerDistributor
                + '}';
    }
}
