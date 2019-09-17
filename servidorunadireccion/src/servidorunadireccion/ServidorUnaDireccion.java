/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servidorunadireccion;

import java.net.*;
import java.io.*;
import java.nio.file.Files;
import java.util.Scanner;

/**
 *
 * @author ITLM
 */
public class ServidorUnaDireccion {

    public static void main(String[] args) {
        ServerSocket servidorSocket = null;
        Socket socket = null;
        String entrada;
        String salida;
        File archivo = null;
        byte[] contenido = null;
        Scanner escrito = new Scanner(System.in);

        ClaseServidor datosServidor = new ClaseServidor();
        if (args.length > 0) {
            if (datosServidor.validarPuerto(args[0]) != 0) {
                datosServidor.crearServidor(datosServidor.getPuerto());
                System.out.println("Servidor creado exitosamente, el puerto es: " + datosServidor.getPuerto());
                if (datosServidor.getSocket().isConnected()) {
                    datosServidor.crearLector(datosServidor.getSocket());
                    while (true) {
                        try {
                            if (datosServidor.getSocket().isClosed()) {
                                datosServidor.getServidorSocket().close();
                                datosServidor.crearServidor(datosServidor.getPuerto());
                                datosServidor.crearLector(datosServidor.getSocket());
                            }
                            datosServidor.setDatosEntrada(datosServidor.lector.readLine());
                            System.out.println("El cliente dice: " + datosServidor.getDatosEntrada());
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

        /*
        try{
          BufferedReader lector = new BufferedReader(
        new InputStreamReader(
           socket.getInputStream()
       )                
      );
          
         PrintWriter escritor = new PrintWriter(socket.getOutputStream(),true);       
      
      do{
           entrada = lector.readLine();
          
          if(entrada.contains("C:")){
              System.out.println(entrada);
               archivo = new File(entrada);  
               System.out.println(archivo.isFile());
              contenido = Files.readAllBytes(archivo.toPath());
              OutputStream out = socket.getOutputStream() ;
       escritor.println(contenido.length);
       System.out.println(contenido.length);
              lector.readLine();
         out.write(contenido, 0, contenido.length);
         out.flush();
      
          }
          entrada = lector.readLine();
          System.out.println("Cliente dice: " + entrada);
          if(entrada.equalsIgnoreCase("fin")){
              System.out.println("Me fuí");
              socket.close();
              servidorSocket.close();
              System.exit(0);
          }
         salida = escrito.nextLine();
          escritor.println(salida);
       }while (!entrada.equalsIgnoreCase("fin"));
       
        }catch(IOException e){
            System.out.print(e);
            System.exit(1);
        }
         */
    }

}
