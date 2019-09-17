/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servidorarchivos;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 *
 * @author Fer
 */
public class Servidorarchivos {

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
                                
                            }else{
                              datosServidor.setDatosEntrada(datosServidor.lector.readLine());
                              System.out.println(datosServidor.getDatosEntrada());
                            if(datosServidor.getDatosEntrada() != null){  
                            if (datosServidor.getDatosEntrada().equalsIgnoreCase("confirmado")) {
                                try {
                                    FileInputStream archivoSalida = new FileInputStream(datosServidor.getArchivo().getAbsolutePath());
                                    BufferedInputStream lectorArchivo = new BufferedInputStream(archivoSalida);
                                    byte[] contenido = new byte[1024];
                                    int datos;
                                
                                    while ( (datos = lectorArchivo.read(contenido)) != -1) {
                                        
                                        datosServidor.getSocket().getOutputStream().write(contenido, 0, contenido.length);
                                        
                                    }
                                    System.out.println("Archivo enviado");
                                    archivoSalida.close();
                                    datosServidor.getSocket().close();
                                } catch (IOException e) {
                                    System.out.println("Error en el envio de datos");
                                }
                            }
                            
                                if(datosServidor.getDatosEntrada() != null){  
                                if (datosServidor.getDatosEntrada().contains("C:") || datosServidor.getDatosEntrada().contains("c:")) {
                                    System.out.println("El cliente dice: " + datosServidor.getDatosEntrada());
                                    datosServidor.setArchivo(new File(datosServidor.getDatosEntrada()));
                                    if (datosServidor.getArchivo().isFile()) {
                                        datosServidor.setDatoSalida(Long.toString(datosServidor.getArchivo().length()));
                                        datosServidor.getEscritor().println(datosServidor.getDatoSalida());
                                        datosServidor.setDatoSalida(datosServidor.getArchivo().getName());
                                        datosServidor.getEscritor().println(datosServidor.getDatoSalida());
                                    } else {
                                        datosServidor.getEscritor().println("no es un archivo valido");
                                    }
                                }
                                } 
                                
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
