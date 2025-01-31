package Programación.Trim2.Tema6.PracticaObligatoria;

import java.io.*;
import java.util.ArrayList;

/**
 * Representa un ordenador, que es un tipo de dispositivo. Hereda de la clase {@link Dispositivo}.
 * Esta clase permite almacenar y cargar objetos de tipo {@link Ordenador} en un archivo mediante serialización.
 * Contiene atributos específicos de un ordenador como la memoria RAM, procesador, tamaño y tipo de disco.
 * 
 * <p>Los objetos de esta clase pueden ser guardados y cargados en un archivo mediante los métodos {@link #save()} y {@link #load(int)}.</p>
 */
public class Ordenador extends Dispositivo {

    /**
     * El archivo donde se almacenan los dispositivos.
     */
    private static final String ARCHIVO_DATOS = "dispositivos.dat";  // Definimos la constante de archivo

    private int ram;
    private String procesador;
    private int tamDisco;
    private int tipoDisco;

    /**
     * Constructor de la clase {@link Ordenador}.
     * 
     * @param ram La cantidad de memoria RAM en GB.
     * @param procesador El nombre del procesador.
     * @param tamDisco El tamaño del disco en GB.
     * @param tipoDisco El tipo de disco (0 para HDD, 1 para SSD).
     * @param marca La marca del dispositivo.
     * @param modelo El modelo del dispositivo.
     * @param estado El estado del dispositivo (activo o inactivo).
     */
    public Ordenador(int ram, String procesador, int tamDisco, int tipoDisco, String marca, String modelo, boolean estado) {
        super(marca, modelo, estado);
        this.ram = ram;
        this.procesador = procesador;
        this.tamDisco = tamDisco;
        this.tipoDisco = tipoDisco;
    }

    /**
     * Constructor por defecto para la clase {@link Ordenador}.
     * Inicializa los atributos con valores predeterminados.
     */
    public Ordenador() {
        super("Desconocido", "Desconocido", false);  // Constructor por defecto
        this.ram = 0;
        this.procesador = "Desconocido";
        this.tamDisco = 0;
        this.tipoDisco = 0;
    }

    /**
     * Obtiene la cantidad de memoria RAM del ordenador.
     * 
     * @return La cantidad de RAM en GB.
     */
    public int getRam() {
        return ram;
    }

    /**
     * Establece la cantidad de memoria RAM del ordenador.
     * 
     * @param ram La cantidad de RAM en GB.
     */
    public void setRam(int ram) {
        this.ram = ram;
    }

    /**
     * Obtiene el nombre del procesador del ordenador.
     * 
     * @return El nombre del procesador.
     */
    public String getProcesador() {
        return procesador;
    }

    /**
     * Establece el nombre del procesador del ordenador.
     * 
     * @param procesador El nombre del procesador.
     */
    public void setProcesador(String procesador) {
        this.procesador = procesador;
    }

    /**
     * Obtiene el tamaño del disco del ordenador.
     * 
     * @return El tamaño del disco en GB.
     */
    public int getTamDisco() {
        return tamDisco;
    }

    /**
     * Establece el tamaño del disco del ordenador.
     * 
     * @param tamDisco El tamaño del disco en GB.
     */
    public void setTamDisco(int tamDisco) {
        this.tamDisco = tamDisco;
    }

    /**
     * Obtiene el tipo de disco del ordenador.
     * 
     * @return El tipo de disco (0 para HDD, 1 para SSD).
     */
    public int getTipoDisco() {
        return tipoDisco;
    }

    /**
     * Establece el tipo de disco del ordenador.
     * 
     * @param tipoDisco El tipo de disco (0 para HDD, 1 para SSD).
     */
    public void setTipoDisco(int tipoDisco) {
        this.tipoDisco = tipoDisco;
    }

    /**
     * Devuelve una representación en cadena del objeto {@link Ordenador}.
     * 
     * @return Una cadena con los atributos del ordenador.
     */
    @Override
    public String toString() {
        return super.toString() + ", Ordenador{" + "ram=" + ram + ", procesador=" + procesador + ", tamDisco=" + tamDisco + ", tipoDisco=" + tipoDisco + '}';
    }

    /**
     * Guarda el objeto {@link Ordenador} en un archivo. Si ya existe un objeto con el mismo ID, lo actualiza.
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
     * Carga un objeto {@link Ordenador} desde el archivo de dispositivos usando su ID.
     * 
     * @param id El ID del ordenador que se desea cargar.
     * @return El objeto {@link Ordenador} si se encuentra en el archivo, {@code null} en caso contrario.
     */
    public static Ordenador load(int id) {
        ArrayList<Dispositivo> listaDispositivos = loadAll();
        for (Dispositivo d : listaDispositivos) {
            if (d.getId() == id && !d.isBorrado()) {
                if (d instanceof Ordenador) {
                    return (Ordenador) d;
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
            ArrayList<Dispositivo> listaDispositivos = (ArrayList<Dispositivo>) ois.readObject();
            return listaDispositivos;
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
