package command.path;

import command.Command;
import org.apache.curator.framework.CuratorFramework;

public class ExistsPathCommand implements Command {
    private String path;
    private boolean isExists;

    public ExistsPathCommand(String path) {
        this.path = path;
    }

    public void command(CuratorFramework client)
            throws Exception {
        this.isExists = (client.checkExists().forPath(this.path) != null);
    }

    public boolean isExists() {
        return this.isExists;
    }
}


