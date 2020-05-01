import javax.jms.*;
import javax.naming.InitialContext;

public class TopicReceiver {

    public static void main(String[] args) {
        try {
            //1) Create and start connection
            InitialContext ctx = new InitialContext();
            TopicConnectionFactory f = (TopicConnectionFactory) ctx.lookup("topicConnectionFactory");
            TopicConnection con = f.createTopicConnection();
            con.start();
            //2) create topic session
            TopicSession ses = con.createTopicSession(false, Session.AUTO_ACKNOWLEDGE);
            //3) get the Topic object
            Topic t = (Topic) ctx.lookup("topicQueue");
            //4)create TopicSubscriber
            TopicSubscriber receiver = ses.createSubscriber(t);

            //5) create listener object
            CustomListener listener = new CustomListener();

            //6) register the listener object with subscriber
            receiver.setMessageListener(listener);

            System.out.println("Subscriber1 is ready, waiting for messages...");
            System.out.println("press Ctrl+C to shutdown...");
            while (true) {
                Thread.sleep(1000);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
