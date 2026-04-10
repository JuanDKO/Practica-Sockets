package Ejercicio2;


import java.io.*;
import java.net.*;

public class Cliente {
    public static void main(String[] args) {
        System.out.println("Ejercicio1.Cliente conectado");

        // Utilizamos try-with-resources para asegurar que todos los flujos de red y
        // teclado
        // se cierren automáticamente al terminar, evitando fugas de memoria o puertos
        // bloqueados.
        try (
                // 1. Establecer la conexión con el servidor (IP localhost, puerto 1234)
                Socket socket = new Socket("localhost", 1234);

                // 2. Preparar el flujo de SALIDA (hacia el servidor)
                // Usamos PrintWriter con autoflush (true) para mandar el texto fácilmente
                PrintWriter out = new PrintWriter(
                        socket.getOutputStream(), true);

                // 3. Preparar el flujo de ENTRADA (desde el servidor)
                // Lee el flujo de red entrante y lo convierte a texto
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(socket.getInputStream()));

                // 4. Preparar la lectura desde el TECLADO local
                BufferedReader teclado = new BufferedReader(
                        new InputStreamReader(System.in));) {

            String opcion;
            System.out.println("Introduce operación (AGREGAR / MOSTRAR / MEDIA / MAX/ DEL):");
            while ((opcion = teclado.readLine()) != null && !opcion.equals("EXIT")) {
                switch (opcion) {
                    case "AGREGAR":
                        out.println(opcion);
                        System.out.println(in.readLine());
                        out.println(teclado.readLine());

                        System.out.println(in.readLine());
                        break;
                    case "MOSTRAR":
                    case "MEDIA":
                    case "MAX":
                    case "DEL":
                            out.println(opcion);
                        System.out.println("RESPUESTA:" );
                        break;

                    default:
                        out.println(opcion);
                        System.out.println("Ejercicio1.Servidor: " + in.readLine());
                        break;
                }

                System.out.println("Introduce operación (CIFRAR / DESCIFRAR / EXIT):");
            }

            // Enviamos el EXIT al servidor para que cierre correctamente
            out.println("EXIT");
            System.out.println("Ejercicio1.Servidor: " + in.readLine());

        } catch (IOException e) {
            // Capturamos cualquier error en la red (ej. si el servidor está apagado)
            System.err.println("Error en conexión");
        } catch (ArithmeticException a) {
            System.err.println("División sobre 0" + a.getMessage());
        }
    }
}
