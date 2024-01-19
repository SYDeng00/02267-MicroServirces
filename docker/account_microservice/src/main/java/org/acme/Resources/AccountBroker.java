package org.acme.Resources;

import org.acme.Domains.Message;
import org.acme.Interfaces.IEventSubscriber;
import org.acme.Resoures.EventPublisher;
import org.acme.Resoures.EventSubscriber;
import com.google.gson.Gson;
import org.acme.Domains.DTUPayAccount;

import java.util.HashMap;
import java.util.UUID;

/*
Author: Siyuan Deng s232275  Collaborators：Jiahe Liu s232276
*/

public class AccountBroker implements IEventSubscriber {
	EventPublisher eventPublisher = new EventPublisher();
	private static HashMap<UUID, DTUPayAccount> accounts = new HashMap<>();
	private static HashMap<String, UUID> cprToIdMap = new HashMap<>();

	public void subscribeEvent(Message message) throws Exception {
		String event = message.getEventType();
		Object[] payload = message.getPayload();

		switch (event) {
		case AccountConfig.REGISTER:
			System.out.println("in REGISTER");
			DTUPayAccount account = typeTransfer(payload[0], DTUPayAccount.class);
			String cpr = account.getCpr();

			UUID ID = UUID.randomUUID();
			accounts.put(ID, account);
			cprToIdMap.put(cpr, ID); // Update CPR map
			eventPublisher.publishEvent(new Message(AccountConfig.RETURN_ID, "AccountResources", new Object[] { ID }));

			break;

		case AccountConfig.SEND_REQUEST_BANK_ACCOUNTS:

			UUID paymentID = typeTransfer(payload[0], UUID.class);
			UUID merchantUuid = typeTransfer(payload[1], UUID.class);
			UUID customerUuid = typeTransfer(payload[2], UUID.class);
			String additionalInfo = (String) payload[3];
			System.out.println("in SEND_REQUEST_BANK_ACCOUNTS");
			DTUPayAccount merchantAccount = accounts.get(merchantUuid);
			DTUPayAccount customerAccount = accounts.get(customerUuid);

			if (merchantAccount != null && customerAccount != null) {
				eventPublisher
						.publishEvent(new Message(AccountConfig.RECEIVE_GET_ACCOUNTS, "PaymentBroker", new Object[] {
								paymentID, merchantAccount.getBankID(), customerAccount.getBankID(), additionalInfo }));
			} else {
				// Construct error message
				String errorMessage = "Account information not found for ";
				if (merchantAccount == null) {
					errorMessage += "merchant with UUID: " + merchantUuid;
				}
				if (customerAccount == null) {
					if (merchantAccount == null) {
						errorMessage += " and ";
					}
					errorMessage += "customer with UUID: " + customerUuid;
				}

				// Error event
				eventPublisher
						.publishEvent(new Message(AccountConfig.ERROR, "PaymentBroker", new Object[] { errorMessage }));

				System.out.println(errorMessage);
			}
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

	public DTUPayAccount getAccount(String ID) {
		return accounts.get(ID);
	}
}