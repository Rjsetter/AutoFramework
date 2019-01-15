package cn.yq.util;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
public class CaptureScreen {

    public static void captureScreen(String fileAddress, String fileName) throws Exception {

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Rectangle screenRectangle = new Rectangle(screenSize);
        Robot robot = new Robot();
        BufferedImage image = robot.createScreenCapture(screenRectangle);
        //保存路径
        File screenFile = new File(fileAddress);
        if (!screenFile.exists()) {
            screenFile.mkdir();
        }
        File f = new File(screenFile, fileName);

        ImageIO.write(image, "png", f);
        //自动打开
//        if (Desktop.isDesktopSupported()
//                && Desktop.getDesktop().isSupported(Desktop.Action.OPEN))
//            Desktop.getDesktop().open(f);
    }

    public static void main(String[] args) {
        try {//sdsdsdsdds
            captureScreen("C:\\Users\\sx_yeqiang\\Downloads\\AutoFramework\\screenshot","1.png");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
