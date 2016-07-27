package com.github.hualuomoli.plugin.mq;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.ObjectMessage;
import javax.jms.Session;

import org.springframework.jms.listener.DefaultMessageListenerContainer;
import org.springframework.jms.listener.SessionAwareMessageListener;
import org.springframework.jms.support.JmsUtils;

public class DefaultMessageListener extends DefaultMessageListenerContainer {

	private MessageDealer dealer;

	public void setDealer(MessageDealer dealer) {
		this.dealer = dealer;
	}

	public DefaultMessageListener() {
		super();
		// 消息的签收情形分两种
		// 如果session带有事务，并且事务成功提交，则消息被自动签收。如果事务回滚，则消息会被再次传送。
		// 不带事务的session的签收方式，取决于session的配置。
		// Session.AUTO_ACKNOWLEDGE 消息自动签收
		// Session.CLIENT_ACKNOWLEDGE 客戶端调用acknowledge方法手动签收
		// Session.DUPS_OK_ACKNOWLEDGE不是必须签收，消息可能会重复发送。
		// 在第二次重新传送消息的时候，消息头的JmsDelivered会被置为true标示当前消息已经传送过一次，客户端需要进行消息的重复处理控制。
		this.setSessionTransacted(false);
		this.setSessionAcknowledgeMode(Session.CLIENT_ACKNOWLEDGE); // 手动提交

		this.setMessageListener(new SessionAwareMessageListener<ObjectMessage>() {

			@Override
			public void onMessage(ObjectMessage message, Session session) throws JMSException {
				if (!dealer.onMessage(message.getObject())) {
					throw new MessageException();
				}
			}
		});
	}

	@Override
	protected void doExecuteListener(Session session, Message message) throws JMSException {
		if (!isAcceptMessagesWhileStopping() && !isRunning()) {
			if (logger.isWarnEnabled()) {
				logger.warn("Rejecting received message because of the listener container " + "having been stopped in the meantime: " + message);
			}
			rollbackIfNecessary(session);
			throw new MessageRejectedWhileStoppingException();
		}
		boolean defined = false;
		try {
			invokeListener(session, message);
		} catch (MessageException ex) {
			defined = true;
		} catch (JMSException ex) {
			rollbackOnExceptionIfNecessary(session, ex);
			throw ex;
		} catch (RuntimeException ex) {
			rollbackOnExceptionIfNecessary(session, ex);
			throw ex;
		} catch (Error err) {
			rollbackOnExceptionIfNecessary(session, err);
			throw err;
		}
		if (defined) {
			JmsUtils.rollbackIfNecessary(session);
			session.recover();
		} else {
			commitIfNecessary(session, message);
		}
	}

	/**
	 * Internal exception class that indicates a rejected message on shutdown.
	 * Used to trigger a rollback for an external transaction manager in that case.
	 */
	@SuppressWarnings("serial")
	private static class MessageRejectedWhileStoppingException extends RuntimeException {

	}

}
