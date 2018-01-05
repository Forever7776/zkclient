package registry;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheEvent;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheListener;
import org.apache.curator.utils.ZKPaths;
import org.apache.zookeeper.CreateMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Created by 0 on 2017-11-02.
 */
public class RestfulBean implements Restful {
    private Logger logger = LoggerFactory.getLogger(RestfulBean.class);
    private String root;
    private Map<String, String> businessMap;
    private ZkClient zkClient;

    public String getRoot()
    {
        return this.root;
    }

    public void setRoot(String root)
    {
        this.root = root;
    }

    public Map<String, String> getBusinessMap()
    {
        return this.businessMap;
    }

    public void setBusinessMap(Map<String, String> businessMap)
    {
        this.businessMap = businessMap;
    }

    public ZkClient getZkClient()
    {
        return this.zkClient;
    }

    public void setZkClient(ZkClient zkClient)
    {
        this.zkClient = zkClient;
    }

    public void start()
    {
        if ((this.root == null) || ("".equals(this.root))) {
            throw new RuntimeException("没有定义根节点");
        }
        if (this.businessMap == null) {
            throw new RuntimeException("没有定义restful服务");
        }
        try
        {
            for (String businessKey : this.businessMap.keySet())
            {
                String url = (String)this.businessMap.get(businessKey);
                url = url.replace("{server}", NetUtils.getLocalHost());
                url = URLEncoder.encode(url, "UTF-8");
                String path = ZKPaths.makePath(this.root, businessKey, new String[] { url });
                try
                {
                    this.zkClient.deletePath(path);
                }
                catch (Exception e) {}
                this.zkClient.createPath(path, CreateMode.EPHEMERAL);
                this.logger.info("发布reftful服务[{}]: {} 成功", businessKey, url);
            }
        }
        catch (Exception e)
        {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public List<String> getRestUrls(String businessKey)
    {
        try
        {
            List<String> children = this.zkClient.getChildren(ZKPaths.makePath(this.root, businessKey));
            Lists.transform(children, new Function()
            {
                public Object apply(Object s)
                {
                    try
                    {
                        return URLDecoder.decode(s.toString(), "UTF-8");
                    }
                    catch (UnsupportedEncodingException e)
                    {
                        throw new RuntimeException(e.getMessage(), e);
                    }
                }
            });
        }
        catch (Exception e)
        {
            this.logger.error(e.getMessage(), e);
        }
        return null;
    }

    public String getRestUrl(String businessKey)
    {
        try
        {
            List<String> restUrls = getRestUrls(businessKey);
            if ((restUrls != null) && (restUrls.size() > 0)) {
                return (String)restUrls.get(new Random().nextInt(restUrls.size()));
            }
        }
        catch (Exception e)
        {
            this.logger.error(e.getMessage(), e);
        }
        return null;
    }

    public void listener(final String businessKey, final RestfulListener listener)
    {
        try
        {
            this.zkClient.pathListener(ZKPaths.makePath(this.root, businessKey), new PathChildrenCacheListener()
            {
                public void childEvent(CuratorFramework client, PathChildrenCacheEvent event)
                        throws Exception
                {
                    listener.childChange(businessKey);
                }
            });
        }
        catch (Exception e)
        {
            this.logger.error(e.getMessage(), e);
        }
    }
}
