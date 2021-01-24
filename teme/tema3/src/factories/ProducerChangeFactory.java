package factories;

import economics.ProducerChange;

public final class ProducerChangeFactory {

    /**
     * factory objects cannot be instantiated
     */
    private ProducerChangeFactory() {
    }

    /**
     * factory method for creating a new ProducerChange object
     *
     * @param id
     * @param energyPerDistributor
     * @return
     */
    public static ProducerChange createProducerChange(final int id,
                                                      final int energyPerDistributor) {
        return new ProducerChange(id, energyPerDistributor);

    }
}
