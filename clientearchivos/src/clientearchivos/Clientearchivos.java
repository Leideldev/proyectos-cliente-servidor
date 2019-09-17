/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientearchivos;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;

/**
 *
 * @author Fer
 */
public class Clientearchivos {

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
                        System.out.println("Escribe la palabra archivo para descargar");
                        if (datosCliente.getScanner().nextLine().equalsIgnoreCase("archivo")) {
                            System.out.println("Escribe la ruta del archivo a descargar");
                            datosCliente.setDatoSalida(datosCliente.getScanner().nextLine());
                            datosCliente.getEscritor().println(datosCliente.getDatoSalida());
                            try {
                                String datoTexto = datosCliente.getLector().readLine();
                                if (datoTexto.equalsIgnoreCase("no es un archivo valido")) {
                                    System.out.println("El archivo no existe, escribe un ruta valida");
                                } else {
                                    String datoNombre = datosCliente.getLector().readLine();
                                    datosCliente.setDatosEntrada(datoTexto);
                                    System.out.println("Longitud del archivo: " + Integer.parseInt(datosCliente.getDatosEntrada()));
                                    System.out.println("Nombre del archivo: " + datoNombre);
                                    System.out.println("Elige un nombre de archivo");
                                    String nombre;
                                    do {
                                        nombre = datosCliente.getScanner().nextLine();
                                        if (nombre.contains(".")) {
                                            System.out.println("Escribe un nombre sin extension '.' ");
                                        } else {
                                            datosCliente.setArchivo(new File(datosCliente.getRutaGuardado() + nombre + datoNombre.substring(datoNombre.lastIndexOf("."))));
                                            datosCliente.getEscritor().println("confirmado");
                                            BufferedOutputStream escritorArchivo=new BufferedOutputStream(new FileOutputStream(datosCliente.getArchivo()));          
                                            byte[] contenido = new byte[1024];
                                            int datos;
                                            Long contador = 0L;
                                            while ((datos = datosCliente.getSocket().getInputStream().read(contenido)) != 1) {
                                                  if((Long.parseLong(datosCliente.getDatosEntrada()) - contador) <= 1024){
                                                       System.out.print( "\r"+ "100%");
                                                    escritorArchivo.write(contenido, 0, (int) ( (int) Long.parseLong(datosCliente.getDatosEntrada()) - contador) );
                                                    break;
                                                  }
                                                  escritorArchivo.write(contenido, 0, contenido.length);
                                                  contador += 1024;
                                                  System.out.print( "\r"+(contador*100)/(Long.parseLong(datosCliente.getDatosEntrada())) + "%");
                                            }
                                            escritorArchivo.close();
                                            datosCliente.getSocket().close();
                                            System.out.println();
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
