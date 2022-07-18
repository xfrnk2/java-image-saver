import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

import javax.imageio.ImageIO;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

public class Main {

	public static void main(String[] args) throws Exception {
		String imageUrl = "http://www.avajava.com/images/avajavalogo.jpg";
		String destinationFile = "image.jpg";

		saveImage(imageUrl, destinationFile);

		PDDocument document = new PDDocument();

		// 추가할 JPG 파일 읽기
		File oneFile = new File("image.jpg");
		InputStream inputStream = new FileInputStream(oneFile);
		BufferedImage bufferedImage = ImageIO.read(inputStream);
		float width = bufferedImage.getWidth();
		float height = bufferedImage.getHeight();



		// PDF 페이지 객체 1장 생성
		PDPage page = new PDPage(new PDRectangle(width, height));
		document.addPage(page);



		// PDF 문서 객체에 페이지 1장 그리기
		PDImageXObject pdImage = PDImageXObject.createFromFile(oneFile.getAbsolutePath(), document);
		PDPageContentStream contentStream = new PDPageContentStream(document, page);
		contentStream.drawImage(pdImage, 0, 0, width, height);



		// 1장 그릴 때마다 사용한 객체 닫기

		if (contentStream != null) {
			contentStream.close();
		}

		if (inputStream != null) {
			inputStream.close();
		}

		document.save("image.pdf");

		if (document != null) {
			document.close();
		}

	}

	public static void saveImage(String imageUrl, String destinationFile) throws IOException {
		URL url = new URL(imageUrl);
		InputStream is = url.openStream();
		OutputStream os = new FileOutputStream(destinationFile);

		byte[] b = new byte[2048];
		int length;

		while ((length = is.read(b)) != -1) {
			os.write(b, 0, length);
		}

		is.close();
		os.close();
	}

}