package statistics;

import java.util.List;

public class MonthlyStats {
    private final int month;
    private final List<Integer> distributorIds;

    public  MonthlyStats(final int month,
                         final List<Integer> distributorIds) {
        this.month = month;
        this.distributorIds = distributorIds;
    }

    public int getMonth() {
        return month;
    }

    public List<Integer> getDistributorIds() {
        return distributorIds;
    }
}
