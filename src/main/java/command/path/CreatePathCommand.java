package command.path;

import command.Command;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.api.ACLBackgroundPathAndBytesable;
import org.apache.curator.utils.EnsurePath;
import org.apache.curator.utils.ZKPaths;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;
import support.ZkClientException;

public class CreatePathCommand implements Command {
    protected String path;
    protected byte[] data;
    protected CreateMode mode = CreateMode.PERSISTENT;

    public CreatePathCommand(String path) {
        this.path = path;
    }

    public CreatePathCommand(String path, byte[] data) {
        this.path = path;
        this.data = data;
    }

    public CreatePathCommand(String path, byte[] data, CreateMode mode) {
        this.path = path;
        this.data = data;
        this.mode = mode;
    }

    public void command(CuratorFramework client)
            throws Exception {
        Stat stat = (Stat) client.checkExists().forPath(this.path);
        if (stat != null) {
            throw new ZkClientException("节点已存在" + this.path);
        }
        ensureParent(client);
        if (this.data != null) {
            ((ACLBackgroundPathAndBytesable) client.create().withMode(this.mode)).forPath(this.path, this.data);
        } else {
            ((ACLBackgroundPathAndBytesable) client.create().withMode(this.mode)).forPath(this.path);
        }
    }

    private void ensureParent(CuratorFramework client)
            throws Exception {
        String parentPath = ZKPaths.getPathAndNode(this.path).getPath();
        EnsurePath parent = new EnsurePath(parentPath);
        parent.ensure(client.getZookeeperClient());
    }
}



