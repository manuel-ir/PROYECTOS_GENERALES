package ud2;

 import java.sql.Connection;
 import java.sql.DriverManager;
 import java.sql.PreparedStatement;
 import java.sql.SQLException;

 import java.io.BufferedReader;
 import java.io.InputStreamReader;
 import java.io.IOException;
 
public class InsercionDetallada {

    public static void main(String[] args) {

        // --- 1. DEFINICIÓN DE DATOS DE CONEXIÓN ---
        // Definimos la URL de conexión.
        // Formato: jdbc:mysql://[servidor]:[puerto]/[base_de_datos]
        String url = "jdbc:mysql://localhost:3306/empresa";
        String user = "root";
        String password = "1234";

        // --- 2. INICIALIZACIÓN DE RECURSOS ---
        // Declaramos los objetos de Conexión y PreparedStatement fuera del
        // bloque try. Los inicializamos a 'null' para que podamos
        // comprobar en el bloque 'finally' si se llegaron a crear
        // antes de intentar cerrarlos.
        Connection conexion = null;
        PreparedStatement ps = null;

        // Usamos un 'try-with-resources' para el BufferedReader.
        // Esto asegura que 'br' se cerrará automáticamente al
        // finalizar el bloque try, incluso si ocurre un error.
        try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {

            // --- 3. OBTENER DATOS DEL USUARIO ---
            // Solicitamos al usuario que ingrese el nombre del departamento.
            System.out.println("INTRODUCE EL NOMBRE DEL NUEVO DEPARTAMENTO:");
            // Leemos la línea completa que el usuario escriba en la consola.
            String nombreDepartamento = br.readLine();

            // --- 4. ESTABLECER CONEXIÓN ---
            // Usamos el DriverManager para obtener un objeto Connection.
            // Le pasamos la URL, el usuario y la contraseña.
            // Esta línea es la que realmente "abre" la conexión con la BD.
            System.out.println("Conectando a la base de datos...");
            conexion = DriverManager.getConnection(url, user, password);
            System.out.println("Conexión establecida.");

            // --- 5. PREPARAR LA CONSULTA (MODO SEGURO) ---
            // Esta es la plantilla SQL.
            // Usamos un '?' como marcador de posición (placeholder).
            // Esto evita la Inyección SQL, ya que la BD "sabe" que
            // lo que venga después es un dato, no parte del comando SQL.
            String sql = "INSERT INTO departamentos (nombre) VALUES (?)";

            // Creamos un objeto PreparedStatement.
            // La consulta SQL se pre-compila en la base de datos.
            // Esto es más eficiente si ejecutamos la misma consulta muchas veces.
            ps = conexion.prepareStatement(sql);

            // --- 6. ASIGNAR PARÁMETROS ---
            // Asignamos el valor de nuestra variable 'nombreDepartamento'
            // al primer '?' que encontramos en la plantilla SQL.
            // ¡Importante! Los índices en JDBC para parámetros empiezan en 1, no en 0.
            ps.setString(1, nombreDepartamento);

            // --- 7. EJECUTAR LA CONSULTA ---
            // Usamos executeUpdate() porque esta consulta modifica datos (es un
            // INSERT, UPDATE o DELETE).
            // No le pasamos la SQL de nuevo, porque ya la tiene el PreparedStatement.
            // El método devuelve el número de filas que fueron afectadas.
            int filasAfectadas = ps.executeUpdate();

            // --- 8. VERIFICAR EL RESULTADO ---
            // Si filasAfectadas es mayor que 0, significa que la inserción
            // tuvo éxito (en este caso, debería ser 1).
            if (filasAfectadas > 0) {
                System.out.println("DEPARTAMENTO '" + nombreDepartamento + "' INSERTADO CORRECTAMENTE");
            } else {
                // Esto es raro en un INSERT, pero podría pasar.
                System.out.println("NO SE PUDO INSERTAR EL DEPARTAMENTO");
            }

        } catch (SQLException e) {
            // --- 9. MANEJO DE EXCEPCIONES SQL ---
            // Capturamos cualquier error que ocurra durante la conexión,
            // preparación o ejecución de la consulta SQL.
            System.out.println("ERROR AL CONECTAR O EJECUTAR LA CONSULTA");
            e.printStackTrace(); // Imprime la traza completa del error

        } catch (IOException e) {
            // --- 10. MANEJO DE EXCEPCIONES DE ENTRADA/SALIDA ---
            // Capturamos errores relacionados con la lectura de la consola
            // (por ejemplo, si se corta la entrada).
            System.out.println("ERROR AL LEER LA ENTRADA DEL USUARIO");
            e.printStackTrace();

        } finally {
            // --- 11. CIERRE DE RECURSOS ---
            // Este bloque se ejecuta SIEMPRE, tanto si el 'try' tuvo éxito
            // como si saltó un 'catch' por un error.
            // Es VITAL para liberar los recursos de la BD y evitar "fugas".
            System.out.println("Cerrando recursos...");
            try {
                // Cerramos primero el PreparedStatement.
                // Comprobamos si es 'null' por si la conexión falló
                // antes de que 'ps' fuera creado.
                if (ps != null) {
                    ps.close();
                }
                // Finalmente, cerramos la Conexión.
                // También comprobamos si es 'null'.
                if (conexion != null) {
                    conexion.close();
                }
                System.out.println("Recursos cerrados.");
            } catch (SQLException e) {
                // Si ocurre un error al CERRAR los recursos, lo capturamos.
                System.out.println("ERROR AL CERRAR LOS RECURSOS DE LA BD");
                e.printStackTrace();
            }
        }
    }
}