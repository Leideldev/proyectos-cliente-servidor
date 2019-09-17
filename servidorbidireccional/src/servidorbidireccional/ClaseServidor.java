/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servidorbidireccional;

import java.net.*;
import java.io.*;
import java.nio.file.Files;
import java.util.Scanner;

/**
 *
 * @author Fer
 */
public class ClaseServidor {

    ServerSocket servidorSocket = null;
    Socket socket = null;
    String datoSalida;
    BufferedReader lector;
    PrintWriter escritor;
    String datosEntrada;
    int puerto;
    Scanner scanner = new Scanner(System.in);

    public String getDatoSalida() {
        return datoSalida;
    }

    public void setDatoSalida(String datoSalida) {
        this.datoSalida = datoSalida;
    }

    
    
    public PrintWriter getEscritor() {
        return escritor;
    }

    public void setEscritor(PrintWriter escritor) {
        this.escritor = escritor;
    }

    public BufferedReader getLector() {
        return lector;
    }

    public void setLector(BufferedReader lector) {
        this.lector = lector;
    }

    public String getDatosEntrada() {
        return datosEntrada;
    }

    public void setDatosEntrada(String datosEntrada) {
        this.datosEntrada = datosEntrada;
    }

    public ServerSocket getServidorSocket() {
        return servidorSocket;
    }

    public void setServidorSocket(ServerSocket servidorSocket) {
        this.servidorSocket = servidorSocket;
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public int getPuerto() {
        return puerto;
    }

    public void setPuerto(int puerto) {
        this.puerto = puerto;
    }

    public Scanner getScanner() {
        return scanner;
    }

    public void setScanner(Scanner escrito) {
        this.scanner = scanner;
    }

    ClaseServidor() {

    }

    ClaseServidor(int puerto) {
        this.puerto = puerto;
    }

    public void crearServidor(int puerto) {
        try {
            setServidorSocket(new ServerSocket(puerto));
            setSocket(getServidorSocket().accept());
        } catch (IOException e) {
            System.out.print("No se pudo establecer el servidor");
            System.exit(1);
        }
    }

    public void cerrarLector() {
        try {
            getLector().close();
        } catch (IOException e) {
            System.out.println("Error al cerrar el lector");
        }
    }

    public void crearLector(Socket socket) {
        try {
            setLector(new BufferedReader(new InputStreamReader(getSocket().getInputStream())));
        } catch (IOException e) {
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

    public int validarPuerto(String argumento) {
        try {
            if (Integer.parseInt(argumento) <= 65536 && Integer.parseInt(argumento) >= 2500) {
                setPuerto(Integer.parseInt(argumento));
                return Integer.parseInt(argumento);
            }
        } catch (NumberFormatException e) {
            System.out.println("El argumento puerto no es númerico o es muy grande");
            return 0;
        }
        System.out.println("Elige un puerto dentro del rango de 2500 a 65536 ");
        return 0;
    }

}
