	package Resources;

	import org.acme.Domains.Message;
	import org.acme.Interfaces.IEventSubscriber;
	import org.acme.Resoures.EventPublisher;
	import org.acme.Resoures.EventSubscriber;

	import com.google.gson.Gson;

	import Domains.DTUPayAccount;
	import java.util.HashMap;
	import java.util.UUID;

	public class AccountBroker implements IEventSubscriber {
		EventPublisher eventPublisher = new EventPublisher();
		private HashMap<UUID, DTUPayAccount> accounts = new HashMap<UUID, DTUPayAccount>();
		private HashMap<String, UUID> cprToIdMap = new HashMap<String, UUID>();

		public void subscribeEvent(Message message) throws Exception {
			String event = message.getEventType();
			Object[] payload = message.getPayload();


		switch (event) {

			case AccountConfig.REGISTER:

				DTUPayAccount account = typeTransfer(payload[0],DTUPayAccount.class);

				String cpr = account.getCpr();

				if (cprToIdMap.containsKey(cpr)) {
					// if cpr already exist
					UUID existingId = cprToIdMap.get(cpr);
					eventPublisher.publishEvent(new Message(AccountConfig.RETURN_ID, "AccountResources", new Object[] { existingId, "User already registered" }));
				} else {
					// if cpr is not exist
					//String ID = "DTUPay ID_" + accounts.size();
					UUID ID = UUID.randomUUID();
					accounts.put(ID, account);
					cprToIdMap.put(cpr, ID); // update cpr map
					eventPublisher.publishEvent(new Message(AccountConfig.RETURN_ID, "AccountResources", new Object[] { ID }));

				}
				break;

			case AccountConfig.SEND_REQUEST_BANK_ACCOUNTS:
	            UUID paymentID = (UUID) payload[0];
	            UUID merchantUuid = (UUID) payload[1];
	            UUID customerUuid = (UUID) payload[2];
	            String additionalInfo = (String) payload[3]; 
	            DTUPayAccount merchantAccount = accounts.get(merchantUuid); 
	            DTUPayAccount customerAccount = accounts.get(customerUuid);
	            if (merchantAccount != null && customerAccount != null) {
	            	eventPublisher.publishEvent(new Message(AccountConfig.RECEIVE_GET_ACCOUNTS, "PaymentBroker", 
	            			new Object[] {paymentID,merchantAccount.getBankID(),customerAccount.getBankID(),additionalInfo} ));
	            }else {
	                // Construct an error message
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

	                // Publish the error event
	                eventPublisher.publishEvent(new Message(AccountConfig.ERROR, "PaymentBroker", 
	                        new Object[] { errorMessage }));

	                // Optionally, log the error or handle it as required by your application
	                System.out.println(errorMessage);
	            }
	            


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
		// Method to retrieve stored account information (if needed)
		public DTUPayAccount getAccount(String ID) {
			return accounts.get(ID);
		}

	}
