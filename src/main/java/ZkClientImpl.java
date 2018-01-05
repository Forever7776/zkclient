import command.Command;
import command.connect.CloseCommand;
import command.listener.NodeCacheListenerCommand;
import command.listener.PathChildrenCacheListenerCommand;
import command.lock.InterProcessMultiLockCommand;
import command.lock.InterProcessMutexCommand;
import command.lock.InterProcessReadWriteLockCommand;
import command.lock.InterProcessSemaphoreMutexCommand;
import command.path.*;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.NodeCache;
import org.apache.curator.framework.recipes.cache.NodeCacheListener;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheListener;
import org.apache.curator.framework.recipes.locks.*;
import org.apache.curator.framework.state.ConnectionStateListener;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import support.ZkClientException;

import java.util.List;

public class ZkClientImpl
        implements ZkClient {
    private Logger logger = LoggerFactory.getLogger(ZkClientImpl.class);
    private CuratorFramework client;
    private ZkConfig config;
    private ConnectionStateListener connectionStateListener;

    public CuratorFramework getClient() {
        return this.client;
    }

    public ZkClientImpl(String connectionString) {
        this.config = new ZkConfig();
        this.config.setConnectionString(connectionString);
    }

    public ZkClientImpl(ZkConfig config) {
        this.config = config;
    }

    public ZkClientImpl(String connectionString, ConnectionStateListener connectionStateListener) {
        this.config = new ZkConfig();
        this.config.setConnectionString(connectionString);
        this.connectionStateListener = connectionStateListener;
    }

    public ZkClientImpl(ZkConfig config, ConnectionStateListener connectionStateListener) {
        this.config = config;
        this.connectionStateListener = connectionStateListener;
    }

    protected <T extends Command> T execute(T command) {
        try {
            if (this.client == null) {
                throw new ZkClientException("未建立连接");
            }
            command.command(this.client);
            return command;
        } catch (Exception e) {
            if ((e instanceof ZkClientException)) {
                throw ((ZkClientException) e);
            }
            throw new ZkClientException(e);
        }
    }

    public void connect() {
        try {
            ExponentialBackoffRetry retryPolicy = new ExponentialBackoffRetry(this.config.getBaseSleepTimeMs(), this.config.getMaxRetries());
            this.client = CuratorFrameworkFactory.newClient(this.config.getConnectionString(), retryPolicy);
            if (this.connectionStateListener != null) {
                this.client.getConnectionStateListenable().addListener(this.connectionStateListener);
            }
            this.client.start();
            if (this.config.isBlockUntilConnectedOrTimedOut()) {
                this.client.getZookeeperClient().blockUntilConnectedOrTimedOut();
            }
            this.logger.info("连接{}成功", this.config.getConnectionString());
        } catch (InterruptedException e) {
            throw new ZkClientException("zookeeper服务器连接失败!", e);
        }
    }

    public void close() {
        execute(new CloseCommand());
    }

    public NodeCache nodeListener(String path, NodeCacheListener listener) {
        return ((NodeCacheListenerCommand) execute(new NodeCacheListenerCommand(path, listener))).getCache();
    }

    public PathChildrenCache pathListener(String path, PathChildrenCacheListener listener) {
        return ((PathChildrenCacheListenerCommand) execute(new PathChildrenCacheListenerCommand(path, listener))).getCache();
    }

    public void createPath(String path, byte[] data, CreateMode mode) {
        execute(new CreatePathCommand(path, data, mode));
    }

    public void createPath(String path, byte[] data) {
        execute(new CreatePathCommand(path, data));
    }

    public void createPath(String path, CreateMode mode) {
        execute(new CreatePathCommand(path, null, mode));
    }

    public void createPath(String path) {
        execute(new CreatePathCommand(path));
    }

    public void deletePath(String path) {
        execute(new DeletePathCommand(path));
    }

    public List<String> getChildren(String path) {
        return ((GetChildrenCommand) execute(new GetChildrenCommand(path))).getChildren();
    }

    public Stat setData(String path, byte[] data) {
        return ((SetDataCommand) execute(new SetDataCommand(path, data))).getStat();
    }

    public boolean existsPath(String path) {
        return ((ExistsPathCommand) execute(new ExistsPathCommand(path))).isExists();
    }

    public InterProcessReadWriteLock getReadWriteLock(String path, byte[] data) {
        return ((InterProcessReadWriteLockCommand) execute(new InterProcessReadWriteLockCommand(path, data))).getLock();
    }

    public InterProcessSemaphoreMutex getSemaphoreMutex(String path) {
        return ((InterProcessSemaphoreMutexCommand) execute(new InterProcessSemaphoreMutexCommand(path))).getMutex();
    }

    public InterProcessMutex getMutex(String path) {
        return ((InterProcessMutexCommand) execute(new InterProcessMutexCommand(path))).getMutex();
    }

    public InterProcessMultiLock getMultiLock(List<InterProcessLock> locks) {
        return ((InterProcessMultiLockCommand) execute(new InterProcessMultiLockCommand(locks))).getMultiLock();
    }
}
