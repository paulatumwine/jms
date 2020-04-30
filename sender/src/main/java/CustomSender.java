import javax.jms.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Properties;

public class CustomSender {

    public static void main(String[] args) {
        try {
            //Create and start connection
            Properties env = System.getProperties();
            env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.enterprise.naming.SerialInitContextFactory");
            Context ctx = new InitialContext(env);
//            Context ctx = new InitialContext();
            QueueConnectionFactory f = (QueueConnectionFactory) ctx.lookup("ptpQueueConnectionFactory");
            QueueConnection con = f.createQueueConnection();
            con.start();

            //2) create queue session
            QueueSession ses = con.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
            //3) get the Queue object
            Queue t = (Queue) ctx.lookup("ptpQueue");
            //4)create QueueSender object
            QueueSender sender = ses.createSender(t);
            //5) create TextMessage object
            TextMessage msg = ses.createTextMessage();

            //6) write message
            BufferedReader b = new BufferedReader(new InputStreamReader(System.in));
            while (true) {
                System.out.println("Enter message, 'end' to terminate:");
                String s = b.readLine();
                if (s.equals("end")) break;
                msg.setText(s);
                //7) send message
                sender.send(msg);
                System.out.println("Message successfully sent");
            }
            //8) connection close
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
