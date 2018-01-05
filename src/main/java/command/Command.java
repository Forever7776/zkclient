package command;

import org.apache.curator.framework.CuratorFramework;

public abstract interface Command
{
    public abstract void command(CuratorFramework paramCuratorFramework)
            throws Exception;
}
