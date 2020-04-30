import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

public class CustomListener implements MessageListener {

    public void onMessage(Message m) {
        try {
            TextMessage msg = (TextMessage) m;
            System.out.println("message received: " + msg.getText());
        } catch (JMSException e) {
            System.out.println(e);
        }
    }
}
