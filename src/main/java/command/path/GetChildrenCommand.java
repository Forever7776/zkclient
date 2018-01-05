package command.path;

import java.util.List;

import command.Command;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.api.GetChildrenBuilder;

public class GetChildrenCommand implements Command {
    protected String path;
    private List<String> children;

    public GetChildrenCommand(String path) {
        this.path = path;
    }

    public void command(CuratorFramework client)
            throws Exception {
        this.children = ((List) client.getChildren().forPath(this.path));
    }

    public List<String> getChildren() {
        return this.children;
    }
}
