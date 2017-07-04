package util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.lang.StringUtils;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class ImageUtils {
	/**
	 * @Title: base64ToImg
	 * @Description: TODO(base64字符串转化成图片)
	 * @param imgStr path
	 * @return
	 */
	public static boolean base64ToImg(String imgStr, String path) {
		System.out.println(imgStr);
		if (imgStr == null) // 图像数据为空
			return false;

		// 截取字符串
		String imgB64 = StringUtils.substringAfterLast(imgStr, ",");
		System.out.println(imgB64);
		BASE64Decoder decoder = new BASE64Decoder();
		File file = new File(path);

		createDirForFile(file);

		try {
			// Base64解码
			byte[] b = decoder.decodeBuffer(imgB64);
			for (int i = 0; i < b.length; ++i) {
				if (b[i] < 0) {// 调整异常数据
					b[i] += 256;
				}
			}
			
			OutputStream out = new FileOutputStream(file);//生成图片
			out.write(b);								  //生成图片
			out.flush();
			out.close();
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	/**
	 * @Title: img2Base64
	 * @Description: TODO(图片转化成base64字符串)
	 * @param file
	 * @return
	 */
	public static String img2Base64(File file) {
		byte[] data = null;
		try{
			InputStream	input = new FileInputStream(file);
			data = new byte[input.available()];
			input.read(data);
			input.close();
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		
		BASE64Encoder encoder = new BASE64Encoder();
		
		return encoder.encode(data);
	}
	
	private static void createDirForFile(File file) {
		if (!file.getParentFile().exists()) {
			file.getParentFile().mkdirs();
		}
		try {
			file.createNewFile();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
}
