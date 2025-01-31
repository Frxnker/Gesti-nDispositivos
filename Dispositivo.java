package Programación.Trim2.Tema6.PracticaObligatoria;

import java.io.*;
import java.util.ArrayList;

/**
 * Clase que representa un dispositivo genérico. Puede ser utilizado como base
 * para dispositivos específicos como impresoras u ordenadores.
 * 
 * @author Frxnker
 */
public class Dispositivo implements Serializable {

    public static final String ARCHIVO_DATOS = "Lista.dat";  // Ruta del archivo para guardar los dispositivos
    private static int contador = 0;
    private int id;
    private String marca;
    private String modelo;
    private boolean estado;
    private boolean borrado = false;  // Propiedad de borrado

    /**
     * Constructor para crear un dispositivo con marca, modelo y estado.
     * 
     * @param marca  Marca del dispositivo.
     * @param modelo Modelo del dispositivo.
     * @param estado Estado del dispositivo (encendido/apagado).
     */
    public Dispositivo(String marca, String modelo, boolean estado) {
        this.id = contador++;
        this.marca = marca;
        this.modelo = modelo;
        this.estado = estado;
    }

    /**
     * Constructor para crear un dispositivo con un ID específico.
     * 
     * @param id ID único del dispositivo.
     */
    public Dispositivo(int id) {
        this.id = id;
        this.marca = " ";
        this.modelo = " ";
        this.estado = false;
    }

    public int getId() {
        return id;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public boolean isBorrado() {
        return borrado;
    }

    public void setBorrado(boolean borrado) {
        this.borrado = borrado;
    }

    /**
     * Método toString que representa al dispositivo como un String.
     * 
     * @return Representación del dispositivo en forma de cadena.
     */
    @Override
    public String toString() {
        return "Dispositivo{" + "id=" + id + ", marca=" + marca + ", modelo=" + modelo + ", estado=" + estado + '}';
    }

    /**
     * Guarda el dispositivo en el archivo de datos.
     * 
     * @return true si la operación fue exitosa, false en caso contrario.
     */
    public boolean save() {
        ArrayList<Dispositivo> listaDispositivos = loadAll();

        boolean encontrado = false;
        for (int i = 0; i < listaDispositivos.size(); i++) {
            if (listaDispositivos.get(i).getId() == this.id) {
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
     * Carga un dispositivo desde el archivo de datos usando su ID.
     * 
     * @param id El ID del dispositivo.
     * @return El dispositivo encontrado, o null si no existe.
     */
    public static Dispositivo load(int id) {
        ArrayList<Dispositivo> listaDispositivos = loadAll();
        for (Dispositivo d : listaDispositivos) {
            if (d.getId() == id && !d.isBorrado()) {
                return d;
            }
        }
        return null;
    }

    /**
     * Elimina un dispositivo de la lista marcándolo como borrado.
     * 
     * @return true si la operación fue exitosa, false en caso contrario.
     */
    public boolean delete() {
        ArrayList<Dispositivo> listaDispositivos = loadAll();
        for (Dispositivo d : listaDispositivos) {
            if (d.getId() == this.id) {
                d.setBorrado(true);
                return guardarLista(listaDispositivos);
            }
        }
        return false;
    }

    /**
     * Carga todos los dispositivos desde el archivo de datos.
     * 
     * @return Lista de dispositivos.
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
            return new ArrayList<>();
        }
    }

    /**
     * Guarda la lista de dispositivos en el archivo de datos.
     * 
     * @param lista Lista de dispositivos.
     * @return true si la operación fue exitosa, false en caso contrario.
     */
    private static boolean guardarLista(ArrayList<Dispositivo> lista) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ARCHIVO_DATOS))) {
            oos.writeObject(lista);
            return true;
        } catch (IOException e) {
            return false;
        }
    }
}
