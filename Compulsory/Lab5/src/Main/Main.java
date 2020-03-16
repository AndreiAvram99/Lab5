package Main;
import java.io.IOException;

/**
 * Clasa Main:
    * Se pot instantia obiecte de tipul Document si Catalog

 * Contine metodele .testSaveCatalog, .testLoadCatalog si .testViewDocument,
    * ce se pot folosi cu ajutorul clasei CatalogController

 * @author avram
 */

public class Main {

    public static void main(String[] args) throws IOException, UniqueIdException {

        Main mainInstance = new Main();
        Catalog catalog = new Catalog("Diverse", "d:/catalog.ser");

        // Creez un document ce contine tag-uri si il adaug in catalog
        Document docFiiTimeTable = new Document("orar1", "Orar 2A6",
                "https://profs.info.uaic.ro/~orar/participanti/orar_I2A6.html", DocumentPathType.URL);
        docFiiTimeTable.addTag("promotion: ", "2019-2020");
        docFiiTimeTable.addTag("semester: ", "second");
        catalog.addToCatalog(docFiiTimeTable);

        // Creez un document  fara tag-uri si care are tipul de FILE si il adaug in catalog
        Document docWithoutTags = new Document("diverse1", "Something interesting",
                "d:/9377.jpg", DocumentPathType.FILE);
        catalog.addToCatalog(docWithoutTags);

        // Incerc sa adaug un element cu acelasi ID (va da eroare deoarece nu pot exista doua id-uri la fel in catalog)
        /*
        Document docWithoutTagsSecond = new Document("diverse1", "Something interesting",
                "d:/9377.jpg", DocumentType.FILE);
        catalog.addToCatalog(docWithoutTagsSecond);
        */

        mainInstance.testSaveCatalog(catalog);

        Catalog newCatalog = mainInstance.testLoadCatalog();
        System.out.println(newCatalog.toString());

        mainInstance.testViewDocument(docWithoutTags);
    }

    //Incerc sa salvez un Catalog, iar daca este aruncata o eroare o prind si afisez mesajul corespunzator
    public void testSaveCatalog(Catalog catalog){
        try {
            CatalogController.save(catalog);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Incerc sa incarc un Catalog, iar daca este aruncata o eroare o prind si afisez mesajul corespunzator
    public Catalog testLoadCatalog(){
        try {
            return CatalogController.load("d:/catalog.ser");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    //Incerc sa deschid un document, iar daca este aruncata o eroare o prind si afisez mesajul corespunzator
    public void testViewDocument(Document document){
        try {
            CatalogController.view(document);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
