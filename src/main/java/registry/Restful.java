package registry;

import java.util.List;

/**
 * Created by 0 on 2017-11-02.
 */
public abstract interface Restful
{
    public abstract void start();

    public abstract List<String> getRestUrls(String paramString);

    public abstract String getRestUrl(String paramString);

    public abstract void listener(String paramString, RestfulListener paramRestfulListener);
}