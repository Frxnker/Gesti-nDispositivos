package Programación.Trim2.Tema6.PracticaObligatoria;

import java.io.*;
import java.util.ArrayList;

/**
 * Representa una impresora, que es un tipo de dispositivo. Hereda de la clase {@link Dispositivo}.
 * Esta clase permite almacenar y cargar objetos de tipo {@link Impresora} en un archivo mediante serialización.
 * Contiene atributos específicos de una impresora, como el tipo, la capacidad de imprimir a color y si tiene función de escáner.
 * 
 * <p>Los objetos de esta clase pueden ser guardados y cargados en un archivo mediante los métodos {@link #save()} y {@link #load(int)}.</p>
 */
public class Impresora extends Dispositivo {

    /**
     * El archivo donde se almacenan los dispositivos.
     */
    private static final String ARCHIVO_DATOS = "dispositivos.dat";  // Definimos la constante de archivo

    private int tipo;
    private boolean color;
    private boolean scanner;

    /**
     * Constructor de la clase {@link Impresora}.
     * 
     * @param tipo El tipo de impresora (por ejemplo, 0 para inyección de tinta, 1 para láser).
     * @param color {@code true} si la impresora puede imprimir a color, {@code false} si solo en blanco y negro.
     * @param scanner {@code true} si la impresora tiene función de escáner, {@code false} si no.
     * @param marca La marca del dispositivo.
     * @param modelo El modelo del dispositivo.
     * @param estado El estado del dispositivo (activo o inactivo).
     */
    public Impresora(int tipo, boolean color, boolean scanner, String marca, String modelo, boolean estado) {
        super(marca, modelo, estado);
        this.tipo = tipo;
        this.color = color;
        this.scanner = scanner;
    }

    /**
     * Constructor por defecto para la clase {@link Impresora}.
     * Inicializa los atributos con valores predeterminados.
     */
    public Impresora() {
        super("Desconocido", "Desconocido", false);  // Constructor por defecto
        this.tipo = 0;
        this.color = false;
        this.scanner = false;
    }

    /**
     * Obtiene el tipo de la impresora.
     * 
     * @return El tipo de impresora (por ejemplo, 0 para inyección de tinta, 1 para láser).
     */
    public int getTipo() {
        return tipo;
    }

    /**
     * Establece el tipo de la impresora.
     * 
     * @param tipo El tipo de impresora (por ejemplo, 0 para inyección de tinta, 1 para láser).
     */
    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    /**
     * Verifica si la impresora puede imprimir a color.
     * 
     * @return {@code true} si la impresora puede imprimir a color, {@code false} en caso contrario.
     */
    public boolean isColor() {
        return color;
    }

    /**
     * Establece si la impresora puede imprimir a color.
     * 
     * @param color {@code true} si la impresora puede imprimir a color, {@code false} si solo imprime en blanco y negro.
     */
    public void setColor(boolean color) {
        this.color = color;
    }

    /**
     * Verifica si la impresora tiene función de escáner.
     * 
     * @return {@code true} si la impresora tiene función de escáner, {@code false} en caso contrario.
     */
    public boolean isScanner() {
        return scanner;
    }

    /**
     * Establece si la impresora tiene función de escáner.
     * 
     * @param scanner {@code true} si la impresora tiene función de escáner, {@code false} si no.
     */
    public void setScanner(boolean scanner) {
        this.scanner = scanner;
    }

    /**
     * Devuelve una representación en cadena del objeto {@link Impresora}.
     * 
     * @return Una cadena con los atributos de la impresora.
     */
    @Override
    public String toString() {
        return super.toString() + ", Impresora{" + "tipo=" + tipo + ", color=" + color + ", scanner=" + scanner + '}';
    }

    /**
     * Guarda el objeto {@link Impresora} en un archivo. Si ya existe un objeto con el mismo ID, lo actualiza.
     * Si no existe, lo agrega a la lista de dispositivos.
     * 
     * @return {@code true} si el objeto se guardó correctamente, {@code false} en caso contrario.
     */
    @Override
    public boolean save() {
        ArrayList<Dispositivo> listaDispositivos = loadAll();

        boolean encontrado = false;
        for (int i = 0; i < listaDispositivos.size(); i++) {
            if (listaDispositivos.get(i).getId() == this.getId()) {
                listaDispositivos.set(i, this);
                encontrado = true;
                break;
            }
        }

        if (!encontrado) {
            listaDispositivos.add(this);
        }

        return guardarLista(listaDispositivos);
    }

    /**
     * Carga un objeto {@link Impresora} desde el archivo de dispositivos usando su ID.
     * 
     * @param id El ID de la impresora que se desea cargar.
     * @return El objeto {@link Impresora} si se encuentra en el archivo, {@code null} en caso contrario.
     */
    public static Impresora load(int id) {
        ArrayList<Dispositivo> listaDispositivos = loadAll();
        for (Dispositivo d : listaDispositivos) {
            if (d.getId() == id && !d.isBorrado()) {  // Verifica que no esté borrado
                if (d instanceof Impresora) {
                    return (Impresora) d;
                }
            }
        }
        return null;
    }

    /**
     * Carga todos los dispositivos desde el archivo.
     * 
     * @return Una lista de todos los dispositivos cargados desde el archivo. Si el archivo no existe o hay un error, devuelve una lista vacía.
     */
    public static ArrayList<Dispositivo> loadAll() {
        File archivo = new File(ARCHIVO_DATOS);
        if (!archivo.exists()) {
            return new ArrayList<>();
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(ARCHIVO_DATOS))) {
            @SuppressWarnings("unchecked")
            ArrayList<Dispositivo> dispositivos = (ArrayList<Dispositivo>) ois.readObject();
            return dispositivos;
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace(); // Log error for debugging
            return new ArrayList<>();
        }
    }

    /**
     * Guarda la lista de dispositivos en el archivo.
     * 
     * @param lista La lista de dispositivos a guardar.
     * @return {@code true} si la lista se guarda correctamente, {@code false} en caso contrario.
     */
    private static boolean guardarLista(ArrayList<Dispositivo> lista) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ARCHIVO_DATOS))) {
            oos.writeObject(lista);
            return true;
        } catch (IOException e) {
            e.printStackTrace(); // Log error for debugging
            return false;
        }
    }
}
