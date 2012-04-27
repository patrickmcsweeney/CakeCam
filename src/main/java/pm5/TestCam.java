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
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeBodyPart;

import org.bridj.Pointer;
import org.bridj.PointerIO;
import org.openimaj.image.DisplayUtilities;
import org.openimaj.image.ImageUtilities;
import org.openimaj.image.MBFImage;
import org.openimaj.image.colour.ColourSpace;
import org.openimaj.image.colour.RGBColour;
import org.openimaj.image.processing.convolution.FGaussianConvolve;
import org.openimaj.image.typography.hershey.HersheyFont;
import org.openimaj.video.VideoDisplay;
import org.openimaj.video.VideoDisplayListener;
import org.openimaj.video.capture.Device;
import org.openimaj.video.capture.VideoCapture;

/**
 * OpenIMAJ Hello world!
 *
 */
public class TestCam {
    public static void main( String[] args ) {
    	
    	//Create an image
        MBFImage image = new MBFImage(320,70, ColourSpace.RGB);
        image.fill(RGBColour.WHITE);
        //Render some test into the image
        image.drawText("Hello World", 10, 60, HersheyFont.CURSIVE, 50, RGBColour.BLACK);

        //Apply a Gaussian blur
        image.processInline(new FGaussianConvolve(2f));
        
        VideoCapture vc = null;
		try {
			vc = new VideoCapture(640,480);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        VideoDisplay<MBFImage> vd = VideoDisplay.createOffscreenVideoDisplay(vc);
        
        VideoDisplayListener listener = new VideoDisplayListener<MBFImage>() {
        	String fileName = "/tmp/cake.png";
			@Override
			public void beforeUpdate(MBFImage frame) {
				// TODO Auto-generated method stub
				
				DisplayUtilities.displayName(frame, "foo");
				
				
			}
			
			@Override
			public void afterUpdate(VideoDisplay<MBFImage> display) {
				// TODO Auto-generated method stub
				
			}
		};
		vd.addVideoListener(listener);
        
        
        //Display the image
        DisplayUtilities.display(image);
    }
}
