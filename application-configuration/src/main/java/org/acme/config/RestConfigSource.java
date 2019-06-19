package org.acme.config;

import org.eclipse.microprofile.config.spi.ConfigSource;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class RestConfigSource  implements ConfigSource  {

    protected static  Map<String, String> props = new HashMap<>();

    public RestConfigSource() {
        props.put("greeting.name", "Bim");

        //singleton
        ScheduledExecutorService service = Executors.newScheduledThreadPool(5);
        service.scheduleAtFixedRate(this::refresh, 5, 50, TimeUnit.MILLISECONDS);
        System.out.println(Thread.currentThread().getId() + " RestConfigSource created ");
    }

    @Override
    public Map<String, String> getProperties() {
        return props;
    }

    @Override
    public int getOrdinal() {
        return 280;
    }

    @Override
    public String getValue(String propertyName) {

        String val = props.get(propertyName);

        if ("greeting.name".equals(propertyName)) {
            System.out.println(Thread.currentThread().getId() + " getValue: " + val);
        }

        return val;
    }

    synchronized void refresh() {
        if ("Bim".equals(props.get("greeting.name"))) {
            props.put("greeting.name", "Bam");
            System.out.println(Thread.currentThread().getId() + " refreshed: Bam ");
        } else {
            props.put("greeting.name", "Bim");
            System.out.println(Thread.currentThread().getId() + " refreshed: Bim ");
        }
    }

    @Override
    public String getName() {
        return this.getClass().getSimpleName();
    }
}
