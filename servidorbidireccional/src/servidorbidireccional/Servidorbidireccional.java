/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servidorbidireccional;

import java.io.IOException;

/**
 *
 * @author Fer
 */
public class Servidorbidireccional {

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
                            System.out.println("El cliente dice: " + datosServidor.getDatosEntrada());
                            datosServidor.setDatoSalida(datosServidor.getScanner().nextLine());
                            datosServidor.getEscritor().println(datosServidor.getDatoSalida());
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
