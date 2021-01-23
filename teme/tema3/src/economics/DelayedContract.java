package economics;

import Utils.Utils;
import entities.Consumer;
import entities.Distributor;

import java.util.Iterator;
import java.util.List;

/**
 * holds data for consumers in debt, functions mostly
 * like a regular contract but exists only for a single month
 */
public final class DelayedContract extends Contract {

    /**
     * initialises a delayed contract
     *
     * @param consumerId
     * @param distributorId
     * @param consumerTax
     * @param distributorRevenue
     * @param contractLength
     */
    public DelayedContract(final int consumerId, final int distributorId,
                           final int consumerTax, final int distributorRevenue,
                           final int contractLength) {
        super(consumerId, distributorId, consumerTax, distributorRevenue, contractLength);
    }

    /**
     * applies the terms of the delayed contract to the consumer
     * in debt and the respective distributor
     *
     * @param consumers
     * @param distributors
     * @param contracts
     * @param delayedContracts
     */
    public static void applyDelays(final List<Consumer> consumers,
                                   final List<Distributor> distributors,
                                   final List<Contract> contracts,
                                   final List<DelayedContract> delayedContracts) {

        Iterator<DelayedContract> i = delayedContracts.iterator();
        while (i.hasNext()) {
            DelayedContract delayedContract = i.next();

            //find the original contract
            Contract contract = Utils.consumerIdToContractSearch(contracts,
                    delayedContract.consumerId);
            //find the signed consumer and distributor
            Consumer consumer = Utils.idToConsumerSearch(consumers,
                    delayedContract.consumerId);
            Distributor distributor = Utils.idToDistributorSearch(distributors,
                    delayedContract.distributorId);

            //applies the delays
            delayedContract.applyDelay(consumer, distributor, contract);

            i.remove();
        }
    }

    /**
     * applies the terms of the delayed contract to each parties;
     * if consumer can't pay his current tax and this debt he goes
     * bankrupt
     *
     * @param consumer
     * @param distributor
     * @param contract
     */
    public void applyDelay(final Consumer consumer,
                           final Distributor distributor,
                           final Contract contract) {
        //if consumer has a contract with the same distributor
        if (contract.distributorId == distributorId) {
            if (consumer.getBudget() - consumerTax - contract.consumerTax < 0) {
                //consumer goes bankrupt if he can't pay all his taxes(this debt and his current tax)
                consumer.setBankruptStatus(true);
            } else {
                consumer.setBudget(consumer.getBudget() - consumerTax);
                distributor.setBudget(distributor.getBudget() + consumerTax);
            }
            //else consumer has a contract with a new distributor
        } else {
            //he has to pay only his debt
            if (consumer.getBudget() - consumerTax < 0) {
                //consumer goes bankrupt if he can't pay only his debt
                consumer.setBankruptStatus(true);
            } else {
                consumer.setBudget(consumer.getBudget() - consumerTax);
                distributor.setBudget(distributor.getBudget() + consumerTax);
            }
        }
        monthsLeft--;
    }
}
