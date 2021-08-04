# ha-spring-boot-starter

> **Description**

This starter is used to configure your service as master-slave mode automatically.

> **Usage**

add below example code in your application.yml

```
ha:
  enable: true
  virtualHostAddress: 192.168.159.111
  hostAddresses:
    - 192.168.159.133
    - 192.168.159.134
  heartbeatPort: 5556
  networkInterface: ens32
  virtualNetworkInterface: ens32:15
  netmask: 255.255.255.0
  heartbeatInterval: 1
  heartbeatTimeout: 30
  initializationTimeWait: 5
```

- **enable** [ true | false ] indicate if current service enable high available function
- **virtualHostAddress**  vip, when service becomes the master node, will bond this value to network interface
- **hostAddresses** a collection of ip, include local ip and other node's ip
- **heartbeatPort** the port used to receive udp message
- **networkInterface** local network interface
- **virtualNetworkInterface** virtual net work interface, when service becomes the master node, will bond vip to this
  interface
- **netmask** same as real interface netmask
- **heartbeatInterval** (s), the interval time of send message,
- **heartbeatTimeout** (s), if the slave node hasn't received udp message over this value, will become master node
- **initializationTimeWait** (s), check if current node has received message which send by other node