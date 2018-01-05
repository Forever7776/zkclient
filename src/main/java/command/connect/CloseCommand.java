package command.connect;

import command.Command;
import org.apache.curator.framework.CuratorFramework;

/**
 * Created by kz on 2017-11-02.
 */
public class CloseCommand implements Command {
    public void command(CuratorFramework client) {
        client.close();
    }
}
