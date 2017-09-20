/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nitinsurana.activemqexample;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jms.*;
import org.apache.activemq.*;

/**
 *
 * @author hp
 */
public class ObjectConsumer extends Thread {

    public static final Logger LOG = Logger.getLogger(ObjectConsumer.class.getName());
    // URL of the JMS server
    private static String url = ActiveMQConnection.DEFAULT_BROKER_URL;
    // Name of the queue we will receive messages from
    private static String subject = "TESTQUEUE-object";

    @Override
    public void run() {
        while (true) {
            try {
                // Getting JMS connection from the server
                ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(url);
                Connection connection = connectionFactory.createConnection();
                connection.start();

                // Creating session for seding messages
                Session session = connection.createSession(false,
                        Session.AUTO_ACKNOWLEDGE);

                // Getting the queue 'TESTQUEUE'
                Destination destination = session.createQueue(subject);

                // MessageConsumer is used for receiving (consuming) messages
                MessageConsumer consumer = session.createConsumer(destination);

                // Here we receive the message.
                // By default this call is blocking, which means it will wait
                // for a message to arrive on the queue.
                javax.jms.ObjectMessage message;
                message = (ObjectMessage) consumer.receive();

                // There are many types of Message and TextMessage
                // is just one of them. Producer sent us a TextMessage
                // so we must cast to it to get access to its .getText()
                // method.
                if (message instanceof ObjectMessage) {
                    Bean b = (Bean) message.getObject();
                    System.out.println("Received message '" + ((Bean) message.getObject()).getName() + "'");
                }
                connection.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            try {
                System.out.println("Consuming Message Every Few Seconds");
                Thread.sleep(200);
            } catch (InterruptedException ex) {
                Logger.getLogger(ObjectConsumer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
