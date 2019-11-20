package cn.demi.base.system.webscoket;

import java.io.IOException;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.atomic.AtomicInteger;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

/**
 * <strong>Create on : 2016年11月2日 下午4:12:03 </strong> <br>
 * <strong>Description : 网页聊天消息推送</strong> <br>
 * @version <strong> v 1.0.0 </strong> <br>
 * @author <strong>Dave Yu</strong><br>
 */
// 定义一个WebSocket服务端
@ServerEndpoint(value = "/echoServer/chat")
public class ChatAnnotation {

	private static final String GUEST_PREFIX = "Guest";
	private static final AtomicInteger connectionIds = new AtomicInteger(0);
	private static final Set<ChatAnnotation> connections = new CopyOnWriteArraySet<ChatAnnotation>();

	private final String nickname;
	private Session session;

	public ChatAnnotation() {
		nickname = GUEST_PREFIX + connectionIds.getAndIncrement();
		//System.out.println(nickname);
	}

	/**
	 * <strong>Create on : 2016年11月2日 下午4:12:56 </strong> <br>
	 * <strong>Description : 连接创建时调用的方法</strong> <br>
	 * @param session session
	 */
	@OnOpen
	public void start(Session session) {
		this.session = session;
		//System.out.println("conn ok!!!");
		connections.add(this);
		String message = String.format("* %s %s", nickname, "has joined.");
		//System.out.println("信息：" + message);
		broadcast(message);
	}

	/**
	 * <strong>Create on : 2016年11月2日 下午4:13:23 </strong> <br>
	 * <strong>Description : 连接关闭时调用的方法 </strong> <br>
	 */
	@OnClose
	public void end() {
		connections.remove(this);
		String message = String
				.format("* %s %s", nickname, "has disconnected.");
		broadcast(message);
	}

	/**
	 * <strong>Create on : 2016年11月2日 下午4:13:35 </strong> <br>
	 * <strong>Description : 传输信息过程中调用的方法</strong> <br>
	 * @param message message
	 */
	@OnMessage
	public void incoming(String message) {
		//System.out.println("信息：" + message);
		broadcast(message);
	}

	/**
	 * <strong>Create on : 2016年11月2日 下午4:13:51 </strong> <br>
	 * <strong>Description : 发生错误时调用的方法 </strong> <br>
	 * @param t t
	 * @throws Throwable
	 */
	@OnError
	public void onError(Throwable t) throws Throwable {
		//System.out.println("Chat Error: " + t.toString());
	}

	/**
	 * <strong>Create on : 2016年11月2日 下午4:14:07 </strong> <br>
	 * <strong>Description : 通过connections，对所有其他用户推送信息的方法 </strong> <br>
	 * @param msg msg
	 */
	private static void broadcast(String msg) {
		broadcast("msg:" + msg);
		for (ChatAnnotation client : connections) {
			try {
				synchronized (client) {
					client.session.getBasicRemote().sendText(msg);
				}
			} catch (IOException e) {
				System.out
						.println("Chat Error: Failed to send message to client");
				connections.remove(client);
				try {
					client.session.close();
				} catch (IOException e1) {
					// Ignore
				}
				String message = String.format("* %s %s", client.nickname,
						"has been disconnected.");
				//System.out.println("xiaoxi:" + message);

			}
		}
	}
}
