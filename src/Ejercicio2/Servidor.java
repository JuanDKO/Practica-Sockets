package Ejercicio2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class Servidor {

    private static ArrayList<Integer> numeros = new ArrayList<>();

    public static void main(String[] args) {
        System.out.println("Ejercicio1.Servidor arrancado y esperando...");

        // Bloque try-with-resources para garantizar el cierre de puertos y flujos
        try (
                // 1. Abrimos el puerto de escucha del servidor
                ServerSocket server = new ServerSocket(1234);

                // 2. accept() congela el hilo hasta que llega la conexión de un Ejercicio1.Cliente.
                // Devuelve un 'Socket' listo para interactuar con ese equipo.
                Socket cliente = server.accept();

                // 3. Flujo de ENTRADA para obtener los mensajes del cliente
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(cliente.getInputStream()));

                // 4. Flujo de SALIDA para enviarle repuestas de texto (con autoflush true)
                PrintWriter out = new PrintWriter(
                        cliente.getOutputStream(), true);) {
            String operacion;

            // Bucle principal: Permanece leyendo y contestando hasta que el cliente envía
            // "EXIT" o se desconecta (llega null)
            while ((operacion = in.readLine()) != null && !operacion.equals("EXIT")) {
                String cadena = null;

                switch (operacion) {
                    case "AGREGAR":
                        out.println("Pasame el numero:");
                        String resp = in.readLine();
                        numeros.add(Integer.parseInt(resp));
                        out.println("numero" + resp + "añadido");
                        break;
                    case "MOSTRAR":
                        out.println(numeros.toString());
                        break;
                    case "MEDIA":
                        out.println(media());
                        break;
                    case "MAX":
                        out.println(max());
                        break;
                    case "DEL":
                        numeros.clear();
                        break;
                    default:
                        out.println("Operación no permitida");
                }

            }

            // Una vez se sale del while (por EXIT), finaliza la sesión amablemente
            out.println("ADIOS");

        } catch (IOException e) {
            // Normalmente salta si el puerto está ocupado (BindException)
            // o si el cliente cierra abruptamente la conexión (SocketException)
            System.err.println("Error de conexión");
        } catch (ArithmeticException a) {
            System.err.println("División sobre 0" + a.getMessage());
        }

    }


    public static Double media() {
        Double media = 0.0;
        for(int i:numeros) {
            media += i;
        }
        return media / numeros.size();
    }

    public static Integer max() {
        int maximo = 0;
        for (int i:numeros) {
            maximo+= i;
        }
        //maximo = Collections.max(numeros); Otra forma de hacerlo
        return maximo;
    }


}
