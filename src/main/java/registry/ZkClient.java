package registry;

import org.apache.curator.framework.recipes.cache.NodeCache;
import org.apache.curator.framework.recipes.cache.NodeCacheListener;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheListener;
import org.apache.curator.framework.recipes.locks.*;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;

import java.util.List;

public abstract interface ZkClient
{
    public abstract void connect();

    public abstract void close();

    public abstract NodeCache nodeListener(String paramString, NodeCacheListener paramNodeCacheListener);

    public abstract PathChildrenCache pathListener(String paramString, PathChildrenCacheListener paramPathChildrenCacheListener);

    public abstract void createPath(String paramString, byte[] paramArrayOfByte, CreateMode paramCreateMode);

    public abstract void createPath(String paramString, byte[] paramArrayOfByte);

    public abstract void createPath(String paramString, CreateMode paramCreateMode);

    public abstract void createPath(String paramString);

    public abstract void deletePath(String paramString);

    public abstract List<String> getChildren(String paramString);

    public abstract Stat setData(String paramString, byte[] paramArrayOfByte);

    public abstract boolean existsPath(String paramString);

    public abstract InterProcessMutex getMutex(String paramString);

    public abstract InterProcessReadWriteLock getReadWriteLock(String paramString, byte[] paramArrayOfByte);

    public abstract InterProcessSemaphoreMutex getSemaphoreMutex(String paramString);

    public abstract InterProcessMultiLock getMultiLock(List<InterProcessLock> paramList);
}
