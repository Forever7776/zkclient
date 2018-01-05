package registry;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.state.ConnectionState;
import org.apache.curator.framework.state.ConnectionStateListener;

/**
 * Created by 0 on 2017-11-02.
 */
public class ZkStateListener implements ConnectionStateListener {
    private Restful restfulBean;

    public void setRestfulBean(Restful restfulBean)
    {
        this.restfulBean = restfulBean;
    }

    public void stateChanged(CuratorFramework client, ConnectionState newState)
    {
        if (newState == ConnectionState.RECONNECTED) {
            this.restfulBean.start();
        }
    }
}
