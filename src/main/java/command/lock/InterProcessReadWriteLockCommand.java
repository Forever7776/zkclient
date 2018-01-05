package command.lock;

import command.Command;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.locks.InterProcessReadWriteLock;

/**
 * Created by kz on 2017-11-02.
 */
public class InterProcessReadWriteLockCommand implements Command {
    private String path;
    private byte[] data;
    private boolean lockData;
    private InterProcessReadWriteLock lock;

    public InterProcessReadWriteLockCommand(String path) {
        this.path = path;
        this.lockData = false;
    }

    public InterProcessReadWriteLockCommand(String path, byte[] data) {
        this.path = path;
        this.data = data;
        this.lockData = true;
    }

    public void command(CuratorFramework client)
            throws Exception {
        if (this.lockData) {
            this.lock = new InterProcessReadWriteLock(client, this.path, this.data);
        } else {
            this.lock = new InterProcessReadWriteLock(client, this.path);
        }
    }

    public InterProcessReadWriteLock getLock() {
        return this.lock;
    }
}
