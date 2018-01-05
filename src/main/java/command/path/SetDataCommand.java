package command.path;

import command.Command;
import org.apache.curator.framework.CuratorFramework;
import org.apache.zookeeper.data.Stat;

public class SetDataCommand implements Command {
    protected String path;
    protected byte[] data;
    private Stat stat;

    public SetDataCommand(String path, byte[] data) {
        this.path = path;
        this.data = data;
    }

    public void command(CuratorFramework client)
            throws Exception {
        this.stat = ((Stat) client.setData().forPath(this.path, this.data));
    }

    public Stat getStat() {
        return this.stat;
    }
}
