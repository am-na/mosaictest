package base;
import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.File;
import java.util.ArrayList;

import javax.imageio.ImageIO;


public class mosaicTest extends Frame{

	 BufferedImage test, avgTest, tile1, art;

		int width, height, tileW, tileH;

		public mosaicTest() { // create constructor
			try {
				// load images and make a break system if the images cannot be loaded
				test = ImageIO.read(new File("handsome.jpg"));
				art = ImageIO.read(new File("art.jpg"));
				tile1 = ImageIO.read(new File("tile1.jpg"));

			} catch (Exception e) {
				System.out.println("Cannot load");
			}
			
			this.setTitle("Tile Test"); // set window title
			this.setVisible(true);

			width = 500; // image width
			height = 500; // image height
			
			tileW = 50;
			tileH = 50;

			avgTest = compare(test);

			// Anonymous inner-class listener to terminate program
			this.addWindowListener(new WindowAdapter() {// anonymous class definition
				public void windowClosing(WindowEvent e) {
					System.exit(0);// terminate the program
				}// end windowClosing()
			}// end WindowAdapter
			);// end addWindowListener
		}// end constructor
		
		
		
		public ArrayList<Integer> regionAverage(BufferedImage src) {
			
			int avgCol= 0;

			ArrayList<Integer> results = new ArrayList<Integer>();
			
			
			for (int i = 0; i < width; i += tileW ) {
				for (int j = 0; j <  height; j += tileH) {
					
					int r = 0;
					int g = 0;
					int b = 0;
					
					int avgR = 0;
					int avgG = 0;
					int avgB = 0;
				
					int num = tileW*tileH;
	
					Color averageColor;
		
					for (int u = i; u < i + tileW; u++ ) {
						for(int v = j; v < j + tileH; v++) {
						
							Color tRGB = new Color(test.getRGB(u, v)); // get rgb values of each pixel

							// get separated rgb values of each pixel
							r += tRGB.getRed();
							g += tRGB.getGreen();
							b += tRGB.getBlue();
														
							avgR = (int) (r / num);
							avgG = (int) (g / num);
							avgB = (int) (b / num);

							//System.out.println("R: " + avgR + "G: " + avgG + "B: " + avgB);

							if (avgR > 255)
								avgR = 255;
							if (avgG > 255)
								avgG = 255;
							if (avgB > 255)
								avgB = 255;

							if (avgR < 0)
								avgR = 0;
							if (avgG < 0)
								avgG = 0;
							if (avgB < 0)
								avgB = 0;
							
							averageColor = new Color(avgR, avgG, avgB);
							avgCol = averageColor.getRGB();
//							results.add(avgCol);
							//System.out.println( "FIRST" +results.size());//give 250000 vals

						}
					}
					
					results.add(avgCol);
				}
			}
			return results;
		}
		
		public ArrayList<Integer> tileAverage(BufferedImage src) {
			int c = 0;
			int r = 0;
			int g = 0;
			int b = 0;
			
		
			int avgR = 0;
			int avgG = 0;
			int avgB = 0;
			
			Color averageColor;
			
			ArrayList<Integer> tileVal = new ArrayList<Integer>();

			
			int num = tileW*tileH;
			for (int i = 0; i < tileW; i ++ ) {
				for (int j = 0; j <  tileH; j ++) {
					
					Color tRGB = new Color(src.getRGB(i, j)); // get rgb values of each pixel

					// get separated rgb values of each pixel
					r += tRGB.getRed();
					g += tRGB.getGreen();
					b += tRGB.getBlue();
												
					avgR = (int) (r / num);
					avgG = (int) (g / num);
					avgB = (int) (b / num);

					//System.out.println("R: " + avgR + "G: " + avgG + "B: " + avgB);

					if (avgR > 255)
						avgR = 255;
					if (avgG > 255)
						avgG = 255;
					if (avgB > 255)
						avgB = 255;

					if (avgR < 0)
						avgR = 0;
					if (avgG < 0)
						avgG = 0;
					if (avgB < 0)
						avgB = 0;
					
					averageColor = new Color(avgR, avgG, avgB);
					c = averageColor.getRGB();
					
					
					
				}
			}
			
			tileVal.add(c);
			System.out.println(tileVal);
			
			return tileVal;
		}
		
	public BufferedImage compare(BufferedImage src) {
		WritableRaster wRaster = src.copyData(null);
		BufferedImage img = new BufferedImage(src.getColorModel(), wRaster, src.isAlphaPremultiplied(), null);

		
		ArrayList<Integer> tile = new ArrayList<Integer>();
		tile = tileAverage(tile1);
		
		ArrayList<Integer> region = new ArrayList<Integer>();
		region = regionAverage(src);
		
		System.out.println( "SECTION# "+region.size()); //gives 2500 vals
		System.out.println( "TILE# "+tile.size()); //gives 2500 vals
		
		System.out.println( "TILE AVG " + tile); //gives 2500 vals
		
		for (int w = 0; w < region.size();w++) {
			float record = 256;
			
			//System.out.println("W//"  + w);
			
			for (int k = 0; k < tile.size(); k++) {
				//System.out.println("K//"  + k);

				int try1 = tile.get(k)/50000;
				float try2 = (region.get(w))/50000;
				float diff = Math.abs(try1-try2);
				
//				float diff = (Math.abs((k) - results.get(w)));
				System.out.println("TILE val " + try1 + "  K  " + try2 + "  DIFF " + diff);

//				System.out.println("TILE val " + t + "  K  " + results.get(w) + "  DIFF " + diff);

			boolean swtich = false;
			//System.out.println(swtich);
					if (diff < 150.00) {
						swtich = true;
						System.out.println(swtich);
						
						for (int i = 0; i<width; i++) {
							for (int j=0; j<height; j++) {

								for (int u = w; u < w + tileW; u++ ) {
									for(int v = w; v < w + tileH; v++) {
//										
										img.setRGB(u,v,new Color(0,0,0).getRGB());
										
									}
								}
							}
						}
									
						
				}
			}
		}
		
		return img;

	}
		
		public void paint(Graphics g) {
			// draw all the images and texts
			int w = width ;
			int h = height ;

			this.setSize(width, height+50);
			
			g.drawImage(avgTest, 0, 50, w, h, this);

		}

		public static void main(String[] args) {

			mosaicTest img = new mosaicTest();// instantiate this object
			img.repaint();// render the image

		}// end main
}
