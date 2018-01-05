package command.listener;

import command.Command;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.cache.NodeCache;
import org.apache.curator.framework.recipes.cache.NodeCacheListener;

/**
 * Created by kz on 2017-11-02.
 */
public class NodeCacheListenerCommand implements Command {
    protected String path;
    protected NodeCacheListener listener;
    private NodeCache cache;

    public NodeCacheListenerCommand(String path, NodeCacheListener listener)
    {
        this.path = path;
        this.listener = listener;
    }

    public void command(CuratorFramework client)
            throws Exception
    {
        this.cache = new NodeCache(client, this.path);
        this.cache.getListenable().addListener(this.listener);
        this.cache.start();
    }

    public NodeCache getCache()
    {
        return this.cache;
    }
}