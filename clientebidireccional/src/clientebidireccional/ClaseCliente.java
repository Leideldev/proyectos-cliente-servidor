/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientebidireccional;
import java.net.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;
/**
 *
 * @author Fer
 */


public class ClaseCliente {
   
        Socket socket = null; 
        int puerto;
        BufferedReader lector;
        PrintWriter escritor;
        String datosEntrada;
        String datoSalida = null;
        Scanner scanner = new Scanner(System.in);

    public BufferedReader getLector() {
        return lector;
    }

    public void setLector(BufferedReader lector) {
        this.lector = lector;
    }

    public PrintWriter getEscritor() {
        return escritor;
    }

    public void setEscritor(PrintWriter escritor) {
        this.escritor = escritor;
    }

        
        
    public int getPuerto() {
        return puerto;
    }

    public void setPuerto(int puerto) {
        this.puerto = puerto;
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }


    public String getDatosEntrada() {
        return datosEntrada;
    }

    public void setDatosEntrada(String datosEntrada) {
        this.datosEntrada = datosEntrada;
    }

    public String getDatoSalida() {
        return datoSalida;
    }

    public void setDatoSalida(String datoSalida) {
        this.datoSalida = datoSalida;
    }


    public Scanner getScanner() {
        return scanner;
    }

    public void setScanner(Scanner scanner) {
        this.scanner = scanner;
    }       
        
    public void crearCliente(String direccion, int puerto){
        try{
          setSocket(new Socket(direccion,puerto));
        }catch(IOException e){
            System.out.println("No se pudo conectar al servidor, verificar la dirección o puerto");
            System.exit(1);
        }  
   }
    
   public void crearLector(Socket socket){
       try{
         setLector(new BufferedReader(new InputStreamReader(getSocket().getInputStream())));  
       }catch(IOException e){
           System.out.println("No se pudo crear el lector");
       }
   }
   
    public void crearEscritor(Socket socket){
       try{
         setEscritor(new PrintWriter(getSocket().getOutputStream(),true));  
       }catch(IOException e){
           System.out.println("No se pudo crear el escritor");
       }
   }
    
    public void cerrarLector() {
        try {
            getLector().close();
        } catch (IOException e) {
            System.out.println("Error al cerrar el lector");
        }
    }
    
    public int validarPuerto(String argumento){
       try{
           if(Integer.parseInt(argumento) <= 65536 && Integer.parseInt(argumento) >= 2500){
              setPuerto(Integer.parseInt(argumento));
          return Integer.parseInt(argumento);   
           }       
       }catch(NumberFormatException e){
           System.out.println("El argumento puerto no es númerico o es muy grande");  
            return 0;
       }
       System.out.println("Elige un puerto dentro del rango de 2500 a 65536 ");  
       return 0;
   }



        
}


