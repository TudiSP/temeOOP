package strategies;

import entities.Distributor;
import entities.Producer;

import java.util.List;
import java.util.Map;

public interface DistributorStrategy {

     Map<Integer, Double> chooseProducers(Distributor distributor, List<Producer> producers);
     String getLabel();
}
