package command.lock;

import command.Command;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.framework.recipes.locks.LockInternalsDriver;

/**
 * Created by kz on 2017-11-02.
 */
public class InterProcessMutexCommand implements Command {
    private String path;
    private LockInternalsDriver lockInternalsDriver;
    private InterProcessMutex mutex;

    public InterProcessMutexCommand(String path) {
        this.path = path;
    }

    public InterProcessMutexCommand(String path, LockInternalsDriver lockInternalsDriver) {
        this.path = path;
        this.lockInternalsDriver = lockInternalsDriver;
    }

    public void command(CuratorFramework client)
            throws Exception {
        if (this.lockInternalsDriver == null) {
            this.mutex = new InterProcessMutex(client, this.path);
        } else {
            this.mutex = new InterProcessMutex(client, this.path, this.lockInternalsDriver);
        }
    }

    public InterProcessMutex getMutex() {
        return this.mutex;
    }
}
