package cn.developer.howie.service;

/**
 * cn.developer.howie.service.HighAvailableService.java
 *
 * @author Hao, Yujia
 * @version v1.0
 * @since 8/1/2021 12:34 PM
 */
public interface HighAvailableService {

    /**
     * service has started, check current node status
     *
     * @throws InterruptedException Thrown when a thread is waiting, sleeping, or otherwise occupied,
     *                              and the thread is interrupted, either before or during the activity
     */
    void ready() throws InterruptedException;

    /**
     * when node becomes master
     */
    void onMaster();

    /**
     * when node becomes slave
     */
    void onSlave();

}