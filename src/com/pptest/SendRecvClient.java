package com.pptest;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueReceiver;
import javax.jms.QueueSender;
import javax.jms.QueueSession;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 * A complete JMS client example program that sends a TextMessage to a Queue and
 * asynchronously receives the message from the same Queue.
 *
 * @author Scott.Stark@jboss.org
 * @version $Revision: 1.9 $
 */
public class SendRecvClient {

	QueueConnection conn;
	QueueSession session;
	Queue que;

	public static class ExListener implements MessageListener {
		@Override
		public void onMessage(Message msg) {
			final TextMessage tm = (TextMessage) msg;
			try {
				// save to db
				final String delims = " ";

				final String[] tokens = tm.getText().split(delims);
				final int tokenCount = tokens.length;
				if (tokens.length > 2) {
					final TestConnection test = new TestConnection();
					test.saveGCD(Integer.parseInt(tokens[0]),
							Integer.parseInt(tokens[1]),
							Integer.parseInt(tokens[2]));
				}
			} catch (final Throwable t) {
				t.printStackTrace();
			}
		}
	}

	public void setupPTP() throws JMSException, NamingException {
		final InitialContext iniCtx = new InitialContext();
		final Object tmp = iniCtx.lookup("ConnectionFactory");
		final QueueConnectionFactory qcf = (QueueConnectionFactory) tmp;
		conn = qcf.createQueueConnection();
		que = (Queue) iniCtx.lookup("queue/testQueue");
		session = conn.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
		conn.start();
	}

	public void sendRecvAsync(String text) throws JMSException, NamingException {
		// Setup the PTP connection, session
		setupPTP();

		// Set the async listener
		final QueueReceiver recv = session.createReceiver(que);
		recv.setMessageListener(new ExListener());

		// Send a text msg
		final QueueSender send = session.createSender(que);
		final TextMessage tm = session.createTextMessage(text);
		send.send(tm);
		send.close();
	}

	public void stop() throws JMSException {
		conn.stop();
		session.close();
		conn.close();
	}

	public static void main(String args[]) throws Exception {
		final SendRecvClient client = new SendRecvClient();
		client.sendRecvAsync("A text msg");
		client.stop();
		System.exit(0);
	}
}