package Main;

import java.awt.*;
import java.io.*;
import java.net.URI;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Clasa CatalogController:
    *Aceasta clasa se ocupa cu descarcarea si incarcarea obiectelor plus partea de viewDocument
 *
 * @author avram
 */

public class CatalogController {

    private CatalogController(){}

    /**
     * Mai intai folosim FileOutputStream petru a scrie intr-un fisier dat ca parametru
     * apoi folosim ObjectOutputStream pentru a putea scrie obiecte
     * iar cand se apeleaza metoda .writeObject() obiectul este serializat (converitit la byteStream) incarcat in fisier
     *
     * @param catalog
     * @throws IOException
     */
    public static void save(Catalog catalog)
            throws IOException {
        try (var objectOut = new ObjectOutputStream(
                new FileOutputStream(catalog.getPath()))) {
            objectOut.writeObject(catalog);
        }
    }

    /**
     * Mai intai folosim FileInputStream petru a citi dintr-un fisier dat ca parametru
     * apoi folosim ObjectInputStream pentru a putea citi obiecte
     * iar cand se apeleaza metoda .readObject() obiectul este deserializat si pus in instanta obj a lui Object
     * la final se casteaza la tipul de data returnat (Catalog)
     *
     * @param path
     * @return Catalog
     * @throws IOException
     */
     public static Catalog load(String path)
            throws IOException {
        try {
            FileInputStream fileIn = new FileInputStream(path);
            ObjectInputStream objectIn = new ObjectInputStream(fileIn);

            Object obj = objectIn.readObject();
            objectIn.close();

            return (Catalog) obj;

        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }

    }

    /**
     * Clasa Desktop permite unei aplicatii Java sa ruleze alte aplicatii asociate de pe computer local
     * pentru a face asta are nevoie de un URI(identificator pentru resurse) in care am incarcat path-ul documentului
     * apoi folosind metoda .browse(); deschid fisierul sau pornesc o aplicatie asociata la URI-ul respecitv
     *
     * Daca fisierul nu este gasit se arunca o exceptie pentru ca ruta("path-ul") nu este corecta apoi este prinsa si
     * se afiseaza mesajjul specific
     * @param doc
     */
    public static void view(Document doc)
            throws IOException {

        Desktop desktop = Desktop.getDesktop();
        try
        {
            URI uri = new URI( doc.getPath() );
            Desktop dt = Desktop.getDesktop();
            dt.browse(uri);
        }
        catch(Exception ex){ex.printStackTrace();}
    }

}
