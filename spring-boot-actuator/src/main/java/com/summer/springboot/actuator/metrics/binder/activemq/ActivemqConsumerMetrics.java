package com.summer.springboot.actuator.metrics.binder.activemq;

import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.binder.MeterBinder;
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
public class ActivemqConsumerMetrics implements MeterBinder {
  private static final String CONNECTOR_PORT = "19001";
  private static final String CONNECTOR_PATH = "/jmxrmi";
  private  BrokerViewMBean mBean;
  private MBeanServerConnection connection;

  public ActivemqConsumerMetrics() {
    try {
      JMXServiceURL url = new JMXServiceURL("service:jmx:rmi:///jndi/rmi://localhost:" + CONNECTOR_PORT + CONNECTOR_PATH);
      JMXConnector connector = JMXConnectorFactory.connect(url);
      connector.connect();
      connection = connector.getMBeanServerConnection();
      // 这里面的localhost就是节点名称
      ObjectName name = new ObjectName("org.apache.activemq:brokerName=localhost,type=Broker");
      mBean = MBeanServerInvocationHandler
          .newProxyInstance(connection, name, BrokerViewMBean.class, true);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Override
  public void bindTo(MeterRegistry meterRegistry) {
    for (ObjectName queueName : mBean.getQueues()) {
      QueueViewMBean queueMBean = MBeanServerInvocationHandler
          .newProxyInstance(connection, queueName, QueueViewMBean.class, true);
      Gauge.builder("activemq.queue.size", queueMBean::getQueueSize)
          .tags("name", queueMBean.getName())
          .description("Number of messages on this destination, including any that have been dispatched but not acknowledged")
          .baseUnit("count")
          .register(meterRegistry);
      Gauge.builder("activemq.dequeue.count", queueMBean::getDequeueCount)
          .tags("name", queueMBean.getName())
          .description("Number of messages that has been acknowledged (and removed) from the destination.")
          .baseUnit("count")
          .register(meterRegistry);
    }
  }
}
