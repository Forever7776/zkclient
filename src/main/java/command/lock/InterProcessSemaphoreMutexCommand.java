package command.lock;

import command.Command;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.locks.InterProcessSemaphoreMutex;

/**
 * Created by kz on 2017-11-02.
 */
public class InterProcessSemaphoreMutexCommand implements Command {
    private String path;
    private InterProcessSemaphoreMutex mutex;

    public InterProcessSemaphoreMutexCommand(String path) {
        this.path = path;
    }

    public void command(CuratorFramework client)
            throws Exception {
        this.mutex = new InterProcessSemaphoreMutex(client, this.path);
    }

    public InterProcessSemaphoreMutex getMutex() {
        return this.mutex;
    }
}
