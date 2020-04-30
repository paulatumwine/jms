import javax.jms.*;
import javax.naming.InitialContext;

public class CustomReceiver {

    public static void main(String[] args) {
        try {
            //1) Create and start connection
            InitialContext ctx = new InitialContext();
            QueueConnectionFactory f = (QueueConnectionFactory) ctx.lookup("ptpQueueConnectionFactory");
            QueueConnection con = f.createQueueConnection();
            con.start();

            //2) create Queue session
            QueueSession ses = con.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
            //3) get the Queue object
            Queue t = (Queue) ctx.lookup("ptpQueue");
            //4)create QueueReceiver
            QueueReceiver receiver = ses.createReceiver(t);

            //5) create listener object
            CustomListener listener = new CustomListener();

            //6) register the listener object with receiver
            receiver.setMessageListener(listener);

            System.out.println("Receiver ready, waiting for messages");
            System.out.println("press Ctrl+C to shutdown...");
            while (true) {
                Thread.sleep(1000);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
