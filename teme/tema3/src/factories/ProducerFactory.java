package factories;

import entities.Producer;

public final class ProducerFactory {

    /**
     * Utility class, therefore cannot be instantiated
     */
    private ProducerFactory() {
    }


    /**
     * factory method used to instantiate a Producer class object
     * @param id
     * @param energyType
     * @param maxDistributors
     * @param priceKW
     * @param energyPerDistributor
     * @return
     */
    public static Producer createProducer(final int id, final String energyType,
                                          final int maxDistributors,
                                          final double priceKW, final int energyPerDistributor) {
        return new Producer(id, energyType, maxDistributors, priceKW, energyPerDistributor);
    }
}
