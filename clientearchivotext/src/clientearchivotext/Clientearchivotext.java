/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientearchivotext;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 *
 * @author Fer
 */
public class Clientearchivotext {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        ClaseCliente datosCliente = new ClaseCliente();

        if (args.length > 0) {
            if (datosCliente.validarPuerto(args[1]) != 0) {
                datosCliente.crearCliente(args[0], datosCliente.getPuerto());
                System.out.println("Cliente creado exitosamente, conectado a : " + args[0] + ":" + datosCliente.getPuerto());
                if (datosCliente.getSocket().isConnected()) {
                    datosCliente.crearLector(datosCliente.getSocket());
                    datosCliente.crearEscritor(datosCliente.getSocket());
                    while (true) {
                        if (datosCliente.getSocket().isClosed()) {
                            datosCliente.crearLector(datosCliente.getSocket());
                            datosCliente.crearEscritor(datosCliente.getSocket());
                        }
                        if (datosCliente.getScanner().nextLine().equalsIgnoreCase("archivo")) {
                            System.out.println("Escribe la ruta del archivo a descargar");
                            datosCliente.setDatoSalida(datosCliente.getScanner().nextLine());
                            datosCliente.getEscritor().println(datosCliente.getDatoSalida());
                            try {
                                String datoTexto = datosCliente.getLector().readLine();
                                if (datoTexto.equalsIgnoreCase("no es un archivo valido")) {
                                    System.out.println("El archivo no existe, escribe un ruta valida");
                                } else {
                                    datosCliente.setDatosEntrada(datoTexto);
                                    System.out.println("El archivo: " + datosCliente.getDatosEntrada() + " existe");
                                    System.out.println("Elige un nombre de archivo");
                                    String nombre;
                                    do {
                                        nombre = datosCliente.getScanner().nextLine();
                                        if (nombre.contains(".")) {
                                            System.out.println("Escribe un nombre sin extension '.' ");
                                        } else {
                                            datosCliente.setArchivo(new File(datosCliente.getRutaGuardado() + nombre + ".txt"));
                                            datosCliente.getEscritor().println("confirmado");
                                            BufferedWriter writer = new BufferedWriter(new FileWriter(datosCliente.getArchivo()));
                                            String datos;
                                            while ((datos = datosCliente.getLector().readLine()) != null) {
                                                writer.write(datos);
                                                writer.newLine();
                                                writer.flush();
                                            }
                                            writer.close();
                                            datosCliente.getSocket().close();
                                            System.out.println("Archivo descargado");
                                            System.exit(1);

                                        }
                                    } while (nombre.contains("."));
                                }

                            } catch (IOException e) {
                                System.out.println("Error de conexión con el servidor");
                                datosCliente.cerrarLector();
                                System.exit(1);
                            }

                        } else {
                            System.out.println("Utiiza la palabra 'archivo' para descargar");
                        }

                    }
                }
            }
        } else {
            System.out.println("Introduce la direccion y el puerto antes de continuar");
        }
    }

}
