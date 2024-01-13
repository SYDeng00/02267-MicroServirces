//package Resources;
//
//import org.acme.Domains.Message;
//import org.acme.Resoures.EventPublisher;
//import org.acme.Resoures.EventSubscriber;
//
//import Domains.DTUPayAccount;
//import jakarta.ws.rs.Consumes;
//import jakarta.ws.rs.POST;
//import jakarta.ws.rs.Path;
//import jakarta.ws.rs.core.MediaType;
//import jakarta.ws.rs.core.Response;
//
//@Path("/")
//public class AccountResources {
//	@POST
//	@Path("accounts")
//	@Consumes(MediaType.APPLICATION_JSON)
//	public Response registerAccount(DTUPayAccount account) {
//		try {
//			EventPublisher publisher = new EventPublisher();
//			publisher.publishEvent(new Message(AccountConfig.REGISTER, "AccountBroker",
//					new Object[] { account }));
//
//			
//			return Response.status(201).entity("The Account was successful - ").build();
//		} catch (Exception err) {
//			return Response.status(400).entity(err.getMessage()).build();
//		}
//	}
//
//}

package Resources;

import org.acme.Domains.Message;
import org.acme.Interfaces.IEventSubscriber;
import org.acme.Resoures.EventPublisher;
import org.acme.Resoures.EventSubscriber;
import com.google.gson.Gson;

import Domains.DTUPayAccount;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/")
public class AccountResources implements IEventSubscriber {
    EventPublisher publisher = new EventPublisher();
    EventSubscriber subscriber = new EventSubscriber(this); // 假设 EventSubscriber 能够订阅消息队列
    String receivedId; // 用于存储接收到的ID

    public AccountResources() {
        // 订阅事件
        try {
			subscriber.subscribeEvent("AccountResources");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    @POST
    @Path("accounts")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response registerAccount(DTUPayAccount account) {
        try {
            publisher.publishEvent(new Message(AccountConfig.REGISTER, "AccountBroker",
                    new Object[] { account }));

            // 等待或检索接收到的ID（需要一种同步或异步的机制）
            // 这里的实现依赖于您的具体需求和架构
            String id = waitForIdOrRetrieveIt();

            return Response.status(201).entity("The Account was successful - ID: " + id).build();
        } catch (Exception err) {
            return Response.status(400).entity(err.getMessage()).build();
        }
    }

    @Override
    public void subscribeEvent(Message message) {
        // 解析消息并提取ID
        if (message.getEventType().equals(AccountConfig.RETURN_ID) && message.getService().equals("AccountResources")) {
            Gson gson = new Gson();
            receivedId = gson.fromJson(gson.toJson(message.getPayload()[0]), String.class);
        }
    }

    private String waitForIdOrRetrieveIt() {
        // 实现等待或检索ID的逻辑
        return receivedId;
    }
}

