package Main;
import org.jetbrains.annotations.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *Clasa Catalog
    * Fiecarui Catalog ii este asociat un nume si un path unde va putea fi salvat in calculator
         *Sunt tratate exceptiile in care:
            *Se incearca sa se adauge un document cu acelasi id de aceea
            * este adaugata exceptia UniqueIdException(creata de mine) la signatura metodei .addToCatalog()

            *Se incearca sa se obtina lista de documente dar aceasta este vida de aceea
            * este adaugata exceptia  EmptyCatalogException(creata de mine) la signatura metodei .getAllDocuments()

            *Se incearca sa se ceara returnarea unui document care nu exista (dupa id, nume sau path) de aceea
            * este adaugata exceptia  DocumentNotFoundException(creata de mine) la signatura metodelor
            *.getDocumentById(), .getDocumentByName(), .getDocumentByPath()
 *
    * Trebuie sa implementeze interfata Serializable pentru a putea fi serializat obiectul
 *
 * @author avram
 */

public class Catalog implements Serializable {

    private String name = "";
    private String path = "";
    private List<Document> documentsList = new ArrayList<>();

    Catalog(String name, String path){
        this.name = name;
        this.path = path;
    }

    //Getter-re si Setter-re pentru atribute
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getPath() {
        return path;
    }
    public void setPath(String path) {
        this.path = path;
    }


    //Adauga un document in catalog sau arunca o exceptie daca mai exista un document cu acelasi id
    public void addToCatalog(@NotNull Document ... documents) throws UniqueIdException {
        for(Document document:documents){
            for(Document localDocument:documentsList){
                if(document.getId().equals(localDocument.getId()) || document.getPath().equals(localDocument.getPath()))
                { throw new UniqueIdException("A document have the same id with one already in the list"); }
            }
            documentsList.add(document);
        }
    }

    //Returneaza o lista de document sau arunca exceptie in caz ca nu sunt elemente in catalog
    public List<Document> getAllDocuments() throws EmptyCatalogException{
        if(documentsList.size() == 0)
            throw new EmptyCatalogException("The catalog is empty");
        else return documentsList;
    }

    //Returneaza o lista de documente ce au numele la fel cu cel dat ca parametru sau arunca exceptie
    // daca nu exista niciunul cu numele acela
    public List<Document> getDocumentsByName(String name) throws DocumentNotFoundException {
        List<Document> newDocumentList = new ArrayList<Document>();
        for(Document document:documentsList){
            if(document.getName().equals(name))
                newDocumentList.add(document);
        }
        if(newDocumentList.size() == 0)
            throw new DocumentNotFoundException("Document not found");

        else return newDocumentList;
    }

    //Returneaza un document cu id-ul dat ca parametru, cum id-ul este unic nu pot fi mai multe
    //daca documentul cu acel id nu este gasit se arunca exceptie
    //Pentru filtrare am facut un stream cu elementele din lista si apoi o filtrare pe stream dupa ID, iar findAny
    //e pentru a returna obiectul gasit
    public Document getDocumentById(String id) throws DocumentNotFoundException {

        Document document = documentsList.stream()
                                         .filter(doc ->  id.equals(doc.getId()))
                                         .findAny()
                                         .orElse(null);
        if(document == null)
            throw new DocumentNotFoundException("Document not found");

        else return document;
    }

    //Face acelasi lucru ca metoda getDocumentByID doar ca aceasta cauta dupa un path
    public Document getDocumentByPath(String path) throws DocumentNotFoundException {
        Document document = documentsList.stream()
                .filter(doc ->  path.equals(doc.getId()))
                .findAny()
                .orElse(null);
        if(document == null)
            throw new DocumentNotFoundException("Document not found");

        else return document;
    }

    //Face acelasi lucru ca metoda getDocumentByID doar ca aceasta cauta dupa un nume
    public Document getDocumentByName(String name) throws DocumentNotFoundException {
        Document document = documentsList.stream()
                .filter(doc ->  path.equals(doc.getName()))
                .findAny()
                .orElse(null);
        if(document == null)
            throw new DocumentNotFoundException("Document not found");

        else return document;
    }


    //Am suprascris toString() pentru afisarea elementelor din catalog
    @Override
    public String toString() {
        StringBuilder finalString = new StringBuilder("\t Catalog name: ").append(this.name)
                        .append("\t Catalog path: ").append(this.path).append("\n");

        if(documentsList.size() != 0){
            finalString.append("\t Has documents: \n");
            for(Document document:documentsList){
                finalString.append("\t\t").append("Document ID: ").append(document.getId()).append("  |  ")
                                          .append("Document name: ").append(document.getName()).append("  |  ")
                                          .append("Document path: ").append(document.getPath()).append("  ")
                                          .append("\n");

                try {
                    if(document.getAllTags().size() != 0) {
                        finalString.append("\t\t Has tags: \n");
                        for (Map.Entry<String,Object> entry : document.getAllTags().entrySet())
                            finalString.append("\t\t\t").append(entry.getKey()).append(entry.getValue().toString()).append("\n");
                    }
                } catch (Exception ignored) {}
                finalString.append("\n");
            }
        }
        return finalString.toString();
    }
}
