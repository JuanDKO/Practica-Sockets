import java.io.*;
import java.net.*;

public class Servidor {
    public static void main(String[] args) {
        System.out.println("Servidor arrancado y esperando...");

        // Bloque try-with-resources para garantizar el cierre de puertos y flujos
        try (
                // 1. Abrimos el puerto de escucha del servidor
                ServerSocket server = new ServerSocket(1234);

                // 2. accept() congela el hilo hasta que llega la conexión de un Cliente.
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
                    case "CIFRAR":
                    case "DESCIFRAR":
                        // Solicitamos al cliente la cadena a encriptar/desencriptar
                        out.println("Pasame la cadena");
                        // Leemos la entrada del cliente por el socket bloqueantemente
                        cadena = in.readLine();
                        break;
                    default:
                        out.println("Operación no permitida");
                }

                // Llamamos a nuestro método lógico y mandamos el resultado procesado al cliente
                out.println(cifrar_descifrar(cadena, operacion, 3));
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

    public static String cifrar_descifrar(String cadena, String operacion, int desplazamiento) {
        int rango = ('z' - 'a') + 1;

        String resultado = "";
        char c;
        switch (operacion) {
            case "CIFRAR":

                for (int i = 0; i < cadena.length(); i++) {
                    c = cadena.charAt(i);

                    if (c >= 'a' && c <= 'z') {
                        resultado += (char) (((c - 'a') + desplazamiento) % rango + 'a');
                    } else if (c >= 'A' && c <= 'Z') {
                        resultado += (char) (((c - 'A') + desplazamiento) % rango + 'A');
                    } else {
                        resultado += c;
                    }
                }
                break;
            case "DESCIFRAR":
                for (int i = 0; i < cadena.length(); i++) {
                    c = cadena.charAt(i);

                    if (c >= 'a' && c <= 'z') {
                        resultado += (char) (((c - 'a') - desplazamiento + rango) % rango + 'a');
                    } else if (c >= 'A' && c <= 'Z') {
                        resultado += (char) (((c - 'A') - desplazamiento + rango) % rango + 'A');
                    } else {
                        resultado += c;
                    }
                }
                break;
        }
        return resultado;
    }
}
