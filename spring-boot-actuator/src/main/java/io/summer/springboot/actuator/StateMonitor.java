package io.summer.springboot.actuator;

import javax.management.MBeanServerConnection;
import javax.management.MBeanServerInvocationHandler;
import javax.management.ObjectName;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;
import org.apache.activemq.broker.jmx.BrokerViewMBean;
import org.apache.activemq.broker.jmx.QueueViewMBean;

/**
 * @author yi.liu@bmsoft.com.cn
 * @date 2019/6/3
 */
public class StateMonitor {
  private static final String CONNECTOR_PORT = "19001";
  private static final String CONNECTOR_PATH = "/jmxrmi";

  public static void main(String[] args) throws Exception {
    JMXServiceURL url = new JMXServiceURL("service:jmx:rmi:///jndi/rmi://localhost:" + CONNECTOR_PORT + CONNECTOR_PATH);
    JMXConnector connector = JMXConnectorFactory.connect(url);
    connector.connect();
    MBeanServerConnection connection = connector.getMBeanServerConnection();
    // 这里面的localhost就是节点名称
    ObjectName name = new ObjectName("org.apache.activemq:brokerName=localhost,type=Broker");
    BrokerViewMBean mBean = MBeanServerInvocationHandler
        .newProxyInstance(connection, name, BrokerViewMBean.class, true);

    for (ObjectName queueName : mBean.getQueues()) {
      QueueViewMBean queueMBean = MBeanServerInvocationHandler.newProxyInstance(connection, queueName, QueueViewMBean.class, true);

      System.out.println("\n------------------------------\n");

      // 消息队列名称
      System.out.println("Queue Name --- " + queueMBean.getName());

      // 队列中剩余的消息数
      System.out.println("Queue Size --- " + queueMBean.getQueueSize());

      // 消费者数
      System.out.println("Number of Consumers --- " + queueMBean.getConsumerCount());

      // 出队数
      System.out.println("Number of Dequeue ---" + queueMBean.getDequeueCount());
    }
  }
}
