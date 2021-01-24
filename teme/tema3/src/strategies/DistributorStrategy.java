package strategies;

import entities.Distributor;
import entities.Producer;

import java.util.List;
import java.util.Map;

public interface DistributorStrategy {

    /**
     * choose producers method for universal use
     * @param distributor
     * @param producers
     * @return
     */
    Map<Integer, Double> chooseProducers(Distributor distributor, List<Producer> producers);

    /**
     * method used to get the name of the strategy without casting
     * @return
     */
    String getLabel();
}
