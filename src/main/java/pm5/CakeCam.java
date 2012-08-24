package pm5;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.openimaj.image.DisplayUtilities;
import org.openimaj.image.ImageUtilities;
import org.openimaj.image.MBFImage;
import org.openimaj.video.VideoDisplay;
import org.openimaj.video.VideoDisplayListener;
import org.openimaj.video.capture.VideoCapture;

/**
 * OpenIMAJ Hello world!
 *
 */
public class CakeCam {
    public static void main( String[] args ) {
        
        VideoCapture vc = null;
		try {
			vc = new VideoCapture(640,480);
		} catch (IOException e) {
			// TODO Auto-generated catch block	
			e.printStackTrace();
		}
        VideoDisplay<MBFImage> vd = VideoDisplay.createOffscreenVideoDisplay(vc);
        
        VideoDisplayListener<MBFImage> listener = new VideoDisplayListener<MBFImage>() {
        	String fileName = "/tmp/cake.png";
        	int frameCount = 0;
        	
			@Override
			public void beforeUpdate(MBFImage frame) {

				DisplayUtilities.displayName(frame, "CakeCam");
				if(frameCount<200){
					frameCount++;
					return;
				}
				
				File file = new File(fileName);
				try {
					ImageUtilities.write(frame, file);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				 Properties properties = System.getProperties();

			      // Setup mail server
			      properties.setProperty("mail.smtp.host", "smtp.ecs.soton.ac.uk");

			      // Get the default Session object.
			      Session session = Session.getDefaultInstance(properties);

			      try{
			         MimeMessage message = new MimeMessage(session);

			         message.setFrom(new InternetAddress("pm5@ecs.soton.ac.uk", "CakeCam"));
			         
			         message.addRecipient(Message.RecipientType.TO,
			                                  new InternetAddress("pm5@ecs.soton.ac.uk"));

			         message.setSubject("[CakeCam] - Cake in Building 32 coffee room");

			         BodyPart messageBodyPart = new MimeBodyPart();

			         // Fill the message
			         messageBodyPart.setText("This cake! or food can be found in the building 32 coffee room.");

			         Multipart multipart = new MimeMultipart();
			         multipart.addBodyPart(messageBodyPart);

			         // Part two is attachment
			         messageBodyPart = new MimeBodyPart();
			         DataSource source = new FileDataSource(fileName);
			         messageBodyPart.setDataHandler(new DataHandler(source));
			         messageBodyPart.setFileName(fileName);
			         multipart.addBodyPart(messageBodyPart);

			         // Put parts in message
			         message.setContent(multipart);


			         Transport.send(message);
			         System.out.println("Sent message successfully....");
			      }catch (MessagingException mex) {
			         mex.printStackTrace();
			      } catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				System.exit(0);
				
			}
			
			@Override
			public void afterUpdate(VideoDisplay<MBFImage> display) {
				// TODO Auto-generated method stub
				
			}
		};
		vd.addVideoListener(listener);

    }
}
