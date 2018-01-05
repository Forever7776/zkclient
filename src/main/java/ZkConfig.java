
import java.io.Serializable;

public class ZkConfig implements Serializable {
    private String connectionString;
    private int baseSleepTimeMs = 1000;
    private int maxRetries = 2147483647;
    private boolean blockUntilConnectedOrTimedOut = true;

    public String getConnectionString() {
        return this.connectionString;
    }

    public void setConnectionString(String connectionString) {
        this.connectionString = connectionString;
    }

    public int getBaseSleepTimeMs() {
        return this.baseSleepTimeMs;
    }

    public void setBaseSleepTimeMs(int baseSleepTimeMs) {
        this.baseSleepTimeMs = baseSleepTimeMs;
    }

    public int getMaxRetries() {
        return this.maxRetries;
    }

    public void setMaxRetries(int maxRetries) {
        this.maxRetries = maxRetries;
    }

    public boolean isBlockUntilConnectedOrTimedOut() {
        return this.blockUntilConnectedOrTimedOut;
    }

    public void setBlockUntilConnectedOrTimedOut(boolean blockUntilConnectedOrTimedOut) {
        this.blockUntilConnectedOrTimedOut = blockUntilConnectedOrTimedOut;
    }
}
