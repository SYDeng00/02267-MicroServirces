package Resources;

import org.acme.Domains.Message;
import org.acme.Interfaces.IEventSubscriber;
import org.acme.Resoures.EventPublisher;
import org.acme.Resoures.EventSubscriber;

import com.google.gson.Gson;

import Domains.DTUPayAccount;

public class AccountBroker implements IEventSubscriber {
	EventPublisher eventPublisher = new EventPublisher();

	public void subscribeEvent(Message message) throws Exception {
		String event = message.getEventType();
		Object[] payload = message.getPayload();

		switch (event) {

		case AccountConfig.REGISTER:
			DTUPayAccount account = typeTransfer(payload[0],DTUPayAccount.class);;
//			System.out.println(account.getFirstName());
//			System.out.println("in account broker1");
			String ID = "test DTUPayID" + account.getFirstName();
			eventPublisher.publishEvent(new Message(AccountConfig.RETURN_ID, "AccountResources",
					new Object[] { ID }));
			break;
        default:
            break;
		}


		
	}

	public void received() throws Exception {
		try {
			EventSubscriber subscriber = new EventSubscriber(new AccountBroker());
			subscriber.subscribeEvent(this.getClass().getSimpleName());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static <T> T typeTransfer(Object payload, Class<T> objectClass) {
		Gson gson = new Gson();
		String json = gson.toJson(payload);
		return gson.fromJson(json, objectClass);
	}

}
