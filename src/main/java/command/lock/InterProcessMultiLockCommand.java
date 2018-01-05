package command.lock;

import command.Command;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.locks.InterProcessLock;
import org.apache.curator.framework.recipes.locks.InterProcessMultiLock;

import java.util.List;

/**
 * Created by kz on 2017-11-02.
 */
public class InterProcessMultiLockCommand implements Command {
    private List<InterProcessLock> locks;
    private InterProcessMultiLock multiLock;

    public InterProcessMultiLockCommand(List<InterProcessLock> locks) {
        this.locks = locks;
    }

    public void command(CuratorFramework client)
            throws Exception {
        this.multiLock = new InterProcessMultiLock(this.locks);
    }

    public InterProcessMultiLock getMultiLock() {
        return this.multiLock;
    }
}