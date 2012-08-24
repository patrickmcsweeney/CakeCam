package pm5;

import java.io.IOException;

import org.openimaj.image.DisplayUtilities;
import org.openimaj.image.MBFImage;
import org.openimaj.image.colour.ColourSpace;
import org.openimaj.image.colour.RGBColour;
import org.openimaj.image.processing.convolution.FGaussianConvolve;
import org.openimaj.image.typography.hershey.HersheyFont;
import org.openimaj.video.VideoDisplay;
import org.openimaj.video.VideoDisplayListener;
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
        image.processInplace(new FGaussianConvolve(2f));
        
        VideoCapture vc = null;
		try {
			vc = new VideoCapture(640,480);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        VideoDisplay<MBFImage> vd = VideoDisplay.createOffscreenVideoDisplay(vc);
        
        VideoDisplayListener<MBFImage> listener = new VideoDisplayListener<MBFImage>() {
//        	String fileName = "/tmp/cake.png";
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
