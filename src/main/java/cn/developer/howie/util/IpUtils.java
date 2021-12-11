package cn.developer.howie.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

/**
 * cn.developer.howie.util.IpUtils.java
 *
 * @author Hao, Yujia
 * @version v1.0
 * @since 8/1/2021 12:20 PM
 */
public class IpUtils {

    private static final Logger logger = LoggerFactory.getLogger(IpUtils.class);

    private IpUtils() throws InstantiationException {
        throw new InstantiationException("Prohibited From Instantiating IpUtils.class");
    }

    /**
     * try to acquire local ip address by network interface name
     * if failed, return loop back address
     *
     * @param name network interface
     * @return local ip address
     */
    public static String getByName(String name) {

        String localHostAddress = "127.0.0.1";

        NetworkInterface networkInterface;
        try {
            networkInterface = NetworkInterface.getByName(name.toLowerCase());
            if (null == networkInterface) {
                logger.error("### Can't find network interface: {} ###", name);
                return localHostAddress;
            }
        } catch (SocketException e) {
            logger.error(e.getMessage(), e);
            return localHostAddress;
        }

        Enumeration<InetAddress> inetAddresses = networkInterface.getInetAddresses();
        while (inetAddresses.hasMoreElements()) {
            InetAddress inetAddress = inetAddresses.nextElement();
            if (inetAddress instanceof Inet6Address) {
                continue;
            }
            localHostAddress = inetAddress.getHostAddress();
        }
        return localHostAddress;
    }

}