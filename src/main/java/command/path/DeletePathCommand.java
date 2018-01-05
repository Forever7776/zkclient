package command.path;

import command.Command;
import org.apache.curator.framework.CuratorFramework;

public class DeletePathCommand implements Command {
    protected String path;

    public DeletePathCommand(String path) {
        this.path = path;
    }

    public void command(CuratorFramework client)
            throws Exception {
        client.delete().forPath(this.path);
    }
}
