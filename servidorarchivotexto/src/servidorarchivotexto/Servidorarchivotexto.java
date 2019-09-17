/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servidorarchivotexto;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 *
 * @author Fer
 */
public class Servidorarchivotexto {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        ClaseServidor datosServidor = new ClaseServidor();
        if (args.length > 0) {
            if (datosServidor.validarPuerto(args[0]) != 0) {
                datosServidor.crearServidor(datosServidor.getPuerto());
                if (datosServidor.getSocket().isConnected()) {
                    System.out.println("Servidor creado exitosamente, el puerto es: " + datosServidor.getPuerto());
                    datosServidor.crearLector(datosServidor.getSocket());
                    datosServidor.crearEscritor(datosServidor.getSocket());
                    while (true) {
                        try {
                            if (datosServidor.getSocket().isClosed()) {
                                datosServidor.getServidorSocket().close();
                                datosServidor.crearServidor(datosServidor.getPuerto());
                                datosServidor.crearLector(datosServidor.getSocket());
                                datosServidor.crearEscritor(datosServidor.getSocket());
                            }
                            datosServidor.setDatosEntrada(datosServidor.lector.readLine());

                            if (datosServidor.getDatosEntrada().equalsIgnoreCase("confirmado")) {
                                try {
                                    RandomAccessFile archivo = new RandomAccessFile(datosServidor.getArchivo().getAbsolutePath(), "r");
                                    String str;
                                    while ((str = archivo.readLine()) != null) {

                                        datosServidor.getEscritor().println(str);
                                    }
                                    System.out.println("Archivo enviado");
                                    archivo.close();
                                    datosServidor.getSocket().close();
                                } catch (IOException e) {
                                    System.out.println("Error al enviar texto de archivo");
                                }
                            } else {
                                if (datosServidor.getDatosEntrada().contains("C:") || datosServidor.getDatosEntrada().contains("c:")) {
                                    System.out.println("El cliente dice: " + datosServidor.getDatosEntrada());
                                    datosServidor.setArchivo(new File(datosServidor.getDatosEntrada()));
                                    if (datosServidor.getArchivo().isFile()) {
                                        datosServidor.setDatoSalida(datosServidor.getArchivo().getName());
                                        datosServidor.getEscritor().println(datosServidor.getDatoSalida());
                                    } else {
                                        datosServidor.getEscritor().println("no es un archivo valido");
                                    }
                                } else {
                                    System.out.println("Introduce una ruta valida");
                                    datosServidor.getEscritor().println("no es un archivo valido");
                                }
                            }
                        } catch (IOException e) {
                            System.out.println("Error en la conexion con el cliente");
                            datosServidor.cerrarLector();
                        }
                    }
                }
            }
        } else {
            System.out.println("Introduce numero de puerto antes de continuar");
        }
    }

}
