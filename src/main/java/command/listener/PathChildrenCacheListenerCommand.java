package command.listener;

import command.Command;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheListener;

/**
 * Created by kz on 2017-11-02.
 */
public class PathChildrenCacheListenerCommand implements Command {
    protected String path;
    protected PathChildrenCacheListener listener;
    private PathChildrenCache cache;

    public PathChildrenCacheListenerCommand(String path, PathChildrenCacheListener listener) {
        this.path = path;
        this.listener = listener;
    }

    public void command(CuratorFramework client)
            throws Exception {
        this.cache = new PathChildrenCache(client, this.path, true);
        this.cache.getListenable().addListener(this.listener);
        this.cache.start();
    }

    public PathChildrenCache getCache() {
        return this.cache;
    }
}