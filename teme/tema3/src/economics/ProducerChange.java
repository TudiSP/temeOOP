package economics;


/**
 * Class used to store data about distributor changes
 */
public final class ProducerChange {
    private final int id;
    private final int energyPerDistributor;

    /**
     * holds monthly changes for a producer(id)
     * @param id
     * @param energyPerDistributor
     */
    public ProducerChange(int id, int energyPerDistributor) {
        this.id = id;
        this.energyPerDistributor = energyPerDistributor;
    }

    public int getId() {
        return id;
    }

    public int getEnergyPerDistributor() {
        return energyPerDistributor;
    }

    @Override
    public String toString() {
        return "ProducerChange{"
                + "id=" + id
                + ", energyPerDistributor=" + energyPerDistributor
                + '}';
    }
}
