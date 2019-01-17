package cn.yq.opencv;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
public class testOpencv{

    public static void main(String[] args) {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        String originalImgPath = "D:test.png";
        Mat mat = Imgcodecs.imread(originalImgPath);
        ShowImage window = new ShowImage(mat);
        window.getFrame().setVisible(true);
    }

}
