/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientebidireccional;

import java.io.IOException;

/**
 *
 * @author Fer
 */
public class Clientebidireccional {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        ClaseCliente datosCliente = new ClaseCliente();
        
        if(args.length > 0 ){
         if(datosCliente.validarPuerto(args[1]) != 0 ){            
            datosCliente.crearCliente(args[0],datosCliente.getPuerto());    
            System.out.println("Cliente creado exitosamente, conectado a : " + args[0] + ":"+ datosCliente.getPuerto());
            if(datosCliente.getSocket().isConnected()){ 
              datosCliente.crearLector(datosCliente.getSocket());
              datosCliente.crearEscritor(datosCliente.getSocket());
              while(true){
                  if(datosCliente.getSocket().isClosed()){
                     datosCliente.crearLector(datosCliente.getSocket());
              datosCliente.crearEscritor(datosCliente.getSocket());
                  }
                  datosCliente.setDatoSalida(datosCliente.getScanner().nextLine());
                  datosCliente.getEscritor().println(datosCliente.getDatoSalida());
                  try{
                       datosCliente.setDatosEntrada(datosCliente.getLector().readLine());
                       System.out.println("El servidor dice: " + datosCliente.getDatosEntrada());
                  }catch(IOException e){
                      System.out.println("Error de conexión con el servidor");
                      datosCliente.cerrarLector();
                      System.exit(1);
                  }
                
              }
            }
        }   
        }else{
            System.out.println("Introduce la direccion y el puerto antes de continuar");
        }
    }
    
}
