package Main;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Clasa Document
    * Fiecarui Document ii este asociat un id, un nume, un path si un enum care specifica tipul path-ului documentului(URL sau FILE)
        *Sunt tratate exceptiile in care:
            *Se incearca sa se obtina o valoare a tag-ului cu o cheie gresita de aceea
            *este adaugata exceptia KeyDoesNotExist(creata de mine) la signatura metodei .getTagValue()

            *Daca se incearca sa se obtina map-ul de tag-uri dar map-ul este gol de aceea
            *este adaugata exceptia TagMapIsEmptyException(creata de mine) la signatura metodei .getAllTags()
 *
    * Trebuie sa implementeze interfata Serializable pentru a putea fi serializat obiectul
 * @author avram
 */

public class Document implements Serializable {

    private String id = "";
    private String name = "";
    private String path = "";
    private DocumentPathType type;

    private Map<String, Object> tagMap = new HashMap<>();

    Document(String id, String name, String path, DocumentPathType type){
        this.id = id;
        this.name = name;
        this.path = path;
        this.type = type;
    }

    //Getter-re si Setter-re pentru atribute
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
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
    public DocumentPathType getType() {
        return type;
    }
    public void setType(DocumentPathType type) {
        this.type = type;
    }

    //Se adauga un tag cu cheia key si valoarea value de tip Object la map
    public void addTag(String key, Object value){
        tagMap.put(key, value);
    }

    //Returneaza Map-ul sau arunca o exceptie in caz ca Map-ul e gol
    public Map<String, Object> getAllTags() throws TagMapIsEmptyException{
        if(tagMap.size() == 0) throw new TagMapIsEmptyException("Document " + name + " does not have tags");
        return tagMap;
    }

    //Se returneaza valoarea unei key trimise ca parametru sau arunca o exceptie in caz ca nu  exista cheia in map
    public Object getTagValue(String key) throws KeyDoesNotExist {
        for (String localKey : tagMap.keySet()) {
            if (localKey.equals(key)) return tagMap.get(key);
        }
        throw new KeyDoesNotExist("Key: " + key + " does not exist");
    }

}
