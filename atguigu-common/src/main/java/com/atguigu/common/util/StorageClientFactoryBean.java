package com.atguigu.common.util;

import com.luhuiguo.fastdfs.conn.ConnectionManager;
import com.luhuiguo.fastdfs.conn.FdfsConnectionPool;
import com.luhuiguo.fastdfs.conn.TrackerConnectionManager;
import com.luhuiguo.fastdfs.service.DefaultFastFileStorageClient;
import com.luhuiguo.fastdfs.service.DefaultTrackerClient;
import com.luhuiguo.fastdfs.service.FastFileStorageClient;
import com.luhuiguo.fastdfs.service.TrackerClient;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

public class StorageClientFactoryBean implements FactoryBean<FastFileStorageClient> {

    private List<String> trackers;
    private String tracker;

    @Override
    public FastFileStorageClient getObject() throws Exception {
        FdfsConnectionPool pool = new FdfsConnectionPool();

        if (CollectionUtils.isEmpty(trackers)) {
            trackers = new ArrayList<>();
            if (tracker == null)
                throw new RuntimeException("没有配置 tracker 服务器");
            trackers.add(tracker);
        }

        TrackerConnectionManager tcm = new TrackerConnectionManager(pool, trackers);
        TrackerClient trackerClient = new DefaultTrackerClient(tcm);

        ConnectionManager cm = new ConnectionManager(pool);

        return new DefaultFastFileStorageClient(trackerClient, cm);
    }

    @Override
    public Class<?> getObjectType() {
        return FastFileStorageClient.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

    public List<String> getTrackers() {
        return trackers;
    }

    public void setTrackers(List<String> trackers) {
        this.trackers = trackers;
    }

    public String getTracker() {
        return tracker;
    }

    public void setTracker(String tracker) {
        this.tracker = tracker;
    }
}
