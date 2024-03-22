import java.io.File;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        ImageToPDFConverter imageToPDFConverter = new ImageToPDFConverter();
        File sourceFolder = new File("C:\\Users\\mehul\\Downloads\\Images");
        List<String> allImagePaths = imageToPDFConverter.getAllImagePathsFromFolder(sourceFolder);

        imageToPDFConverter.createPagesWithImages(allImagePaths);
    }
}
