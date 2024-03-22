import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.graphics.image.LosslessFactory;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ImageToPDFConverter {
    public static void createPagesWithImages(List<String> imagePaths){

        String pdfPath = "C:\\Users\\mehul\\Downloads\\blank.pdf";
        int desiredWidth = 400; // Desired width of the image in PDF
        int desiredHeight = 400; // Desired height of the image in PDF

        try {
            PDDocument document = new PDDocument();

            for(String imagePath : imagePaths){
                PDPage page = new PDPage();
                document.addPage(page);

                PDImageXObject pdImage = createPDImageFromPath(imagePath, desiredWidth, desiredHeight, document);

                try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
                    float x = (page.getMediaBox().getWidth() - desiredWidth) / 2;
                    float y = (page.getMediaBox().getHeight() - desiredHeight) / 2;
                    contentStream.drawImage(pdImage, x, y, desiredWidth, desiredHeight); // Adjust x, y position as needed
                }
                System.out.println(imagePath + " has been added successfully");
            }
            document.save(pdfPath);
            document.close();
            System.out.println("PDF created successfully!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<String> getAllImagePathsFromFolder(File sourceFolder){
        List<String> imagePaths = new ArrayList<>();
        for(String fileName : sourceFolder.list()){
            imagePaths.add(sourceFolder.getPath() + "\\" + fileName);
        }
        return imagePaths;
    }

    private static PDImageXObject createPDImageFromPath(String imagePath, int desiredWidth, int desiredHeight,
                                                        PDDocument document) throws IOException {
        try {
            BufferedImage image = ImageIO.read(new File(imagePath));
            BufferedImage scaledImage = new BufferedImage(desiredWidth, desiredHeight, BufferedImage.TYPE_INT_RGB);
            scaledImage.getGraphics().drawImage(image.getScaledInstance(desiredWidth, desiredHeight, BufferedImage.SCALE_SMOOTH), 0, 0, null);
            return LosslessFactory.createFromImage(document, scaledImage);
        } catch (IOException e) {
            throw new IOException("Error loading image: " + e.getMessage());
        }
    }
}
