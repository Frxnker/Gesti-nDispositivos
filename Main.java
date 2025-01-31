package Programación.Trim2.Tema6.PracticaObligatoria;

import java.util.*;
import java.io.*;

/**
 * Clase principal que gestiona el menú y la interacción con el usuario.
 * 
 * @author Frxnker
 */
public class Main {

    private static ArrayList<Dispositivo> listaDispositivos = new ArrayList<>();
    private static Scanner sc = new Scanner(System.in);
    private static int opcion = -1;
    private static final String RUTA_ARCHIVO = "./Lista.dat";


    /**
     * Método principal que gestiona el menú y las opciones del usuario.
     * 
     * @param args Argumentos de línea de comandos (no utilizados).
     */
    public static void main(String[] args) {

        // Cargamos los datos del archivo
        cargarDatos();

        while (opcion != 0) {
            System.out.println("        MENÚ PRINCIPAL       ");
            System.out.println("-----------------------------");
            System.out.println("1. Añadir dispositivos");
            System.out.println("2. Mostrar dispositivos");
            System.out.println("3. Buscar dispositivos");
            System.out.println("4. Borrar dispositivos");
            System.out.println("5. Cambiar estado dispositivo");
            System.out.println("6. Modificar dispositivo");
            System.out.println("0. Salir");

            opcion = sc.nextInt();
            sc.nextLine(); // Limpiar el salto de línea

            switch (opcion) {
                case 1: añadirDispositivo();
                    break;
                case 2: mostrarDispositivos();
                    break;
                case 3: buscarDispositivo();
                    break;
                case 4: borrarDispositivo();
                    break;
                case 5: cambiarEstadoDispositivo();
                    break;
                case 6: modificarDispositivo();
                    break;
                case 0: 
                    System.out.println("Guardando datos y saliendo...");
                    guardarDatos(); // Guardar los datos antes de salir
                    break;
                default: System.out.println("Opción no válida, intente de nuevo.");
            }
        }
        sc.close();
    }

    /**
     * Carga los datos desde el archivo "Lista.dat".
     */
    @SuppressWarnings("unchecked")
    private static void cargarDatos() {
        File archivo = new File(RUTA_ARCHIVO);  // Usamos la ruta especificada en la constante
        if (!archivo.exists()) {
            return;
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(archivo))) {
            listaDispositivos = (ArrayList<Dispositivo>) ois.readObject();
            System.out.println("Datos cargados correctamente.");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error al cargar los datos.");
            e.printStackTrace();
        }
    }

    /**
     * Guarda los datos en el archivo "Lista.dat".
     */
    private static void guardarDatos() {
        File archivo = new File(RUTA_ARCHIVO);
        try {
            if (!archivo.exists()) {
                archivo.createNewFile();  // Si el archivo no existe, lo crea
            }
            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(archivo))) {
                oos.writeObject(listaDispositivos);
                System.out.println("Datos guardados correctamente.");
            }
        } catch (IOException e) {
            System.out.println("Error al guardar los datos.");
            e.printStackTrace();
        }
    }
    

    /**
     * Añade un nuevo dispositivo a la lista.
     */
    private static void añadirDispositivo() {
        System.out.println("Ingrese la marca");
        String marca = sc.nextLine();

        System.out.println("Ingrese el modelo");
        String modelo = sc.nextLine();

        System.out.println("Ingrese el estado (true/false)");
        boolean estado = sc.nextBoolean();
        sc.nextLine();  // Para consumir el salto de línea

        Dispositivo dispositivo = new Dispositivo(marca, modelo, estado);
        listaDispositivos.add(dispositivo);
        System.out.println("Dispositivo añadido correctamente.");
        guardarDatos(); // Guardamos los datos después de añadir el dispositivo
    }

    /**
     * Muestra todos los dispositivos en la lista.
     */
    private static void mostrarDispositivos() {
        if (listaDispositivos.isEmpty()) {
            System.out.println("No hay dispositivos para mostrar.");
            return;
        } else {
            for (Dispositivo d : listaDispositivos) {
                System.out.println(d);
            }
        }
    }

    /**
     * Busca un dispositivo por su ID.
     */
    private static void buscarDispositivo() {
        System.out.println("Ingrese el id del dispositivo a buscar");
        int id = sc.nextInt();
        sc.nextLine();

        for (Dispositivo d : listaDispositivos) {
            if (d.getId() == id) {
                System.out.println(d);
                return;
            }
        }
        System.out.println("No se ha encontrado el dispositivo con el id " + id);
    }

    /**
     * Elimina un dispositivo de la lista mediante su ID.
     */
    private static void borrarDispositivo() {
        System.out.println("Ingrese el id del dispositivo a borrar");
        int id = sc.nextInt();
        sc.nextLine();

        for (Dispositivo d : listaDispositivos) {
            if (d.getId() == id) {
                listaDispositivos.remove(d);
                System.out.println("Dispositivo borrado correctamente.");
                guardarDatos(); // Guardamos los datos después de borrar el dispositivo
                return;
            }
        }
        System.out.println("No se ha encontrado el dispositivo con el id " + id);
    }

    /**
     * Cambia el estado de un dispositivo (de encendido a apagado o viceversa) usando su ID.
     */
    private static void cambiarEstadoDispositivo() {
        System.out.println("Ingrese el id del dispositivo a cambiar el estado");
        int id = sc.nextInt();
        sc.nextLine();

        for (Dispositivo d : listaDispositivos) {
            if (d.getId() == id) {
                d.setEstado(!d.isEstado());
                System.out.println("Estado cambiado correctamente.");
                guardarDatos(); // Guardamos los datos después de cambiar el estado
                return;
            }
        }
        System.out.println("No se ha encontrado el dispositivo con el id " + id);
    }

    /**
     * Modifica los datos de un dispositivo (marca, modelo y estado).
     */
    private static void modificarDispositivo() {
        System.out.println("Ingrese el id del dispositivo a modificar");
        int id = sc.nextInt();
        sc.nextLine();

        for (Dispositivo d : listaDispositivos) {
            if (d.getId() == id) {
                System.out.println("Ingrese la marca");
                String marca = sc.nextLine();
                d.setMarca(marca);

                System.out.println("Ingrese el modelo");
                String modelo = sc.nextLine();
                d.setModelo(modelo);

                System.out.println("Ingrese el estado (true/false)");
                boolean estado = sc.nextBoolean();
                d.setEstado(estado);

                System.out.println("Dispositivo modificado correctamente.");
                guardarDatos(); // Guardamos los datos después de modificar el dispositivo
                return;
            }
        }
        System.out.println("No se ha encontrado el dispositivo con el id " + id);
    }
}
