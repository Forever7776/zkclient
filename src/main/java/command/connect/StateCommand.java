package command.connect;

import command.Command;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.imps.CuratorFrameworkState;

/**
 * Created by kz on 2017-11-02.
 */
public class StateCommand implements Command {
    private CuratorFrameworkState state;

    public void command(CuratorFramework client)
            throws Exception {
        this.state = client.getState();
    }

    public CuratorFrameworkState getState() {
        return this.state;
    }
}
