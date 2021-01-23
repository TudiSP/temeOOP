package factories;

import entities.Producer;

public final class ProducerFactory {

    /**
     * Utility class, therefore cannot be instantiated
     */
    private ProducerFactory() {
    }


    public static Producer createProducer(final int id, final String energyType, final int maxDistributors,
                                             final double priceKW, final int energyPerDistributor) {
        return new Producer(id, energyType, maxDistributors, priceKW, energyPerDistributor);
    }
}
