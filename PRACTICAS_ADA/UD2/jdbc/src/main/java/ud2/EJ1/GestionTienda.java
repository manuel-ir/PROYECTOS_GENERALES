package ud2.EJ1;

// Importaciones necesarias de java.sql
import java.sql.Connection;
import java.sql.DriverManager; // <-- Necesario para conectar
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class GestionTienda {

    public static void main(String[] args) {
        
        // Usamos try-with-resources para el Scanner
        try (Scanner sc = new Scanner(System.in)) {

            // --- LÓGICA DE CONEXIÓN INTEGRADA ---
            // 1. Definimos los datos de conexión (apuntando a "tienda")
            String url = "jdbc:mysql://localhost:3306/tienda";
            String user = "root";
            String password = "1234";
            
            // 2. Declaramos la Conexión fuera del try para que sea visible en el finally
            Connection con = null;
            // ------------------------------------

            try {
                // 3. ESTABLECER CONEXIÓN
                con = DriverManager.getConnection(url, user, password);
                System.out.println("Conexión establecida con la BD 'tienda'.");

                // Pasamos el objeto 'con' (java.sql.Connection) a los métodos
                insertarDatosIniciales(con);

                int opcion = 0;
                while (opcion != 5) {
                    System.out.println("\n--- MENÚ GESTIÓN TIENDA ---");
                    System.out.println("1. Insertar nuevo cliente (Seguro)");
                    System.out.println("2. Actualizar cliente existente (Seguro)");
                    System.out.println("3. Borrar cliente (Seguro)");
                    System.out.println("4. Consultar todos los clientes");
                    System.out.println("5. Salir");
                    System.out.print("Elige una opción: ");

                    try {
                        opcion = Integer.parseInt(sc.nextLine());
                    } catch (NumberFormatException e) {
                        opcion = 0;
                    }

                    // Pasamos 'con' a todos los métodos que lo necesitan
                    switch (opcion) {
                        case 1:
                            insertarCliente(con, sc);
                            break;
                        case 2:
                            actualizarCliente(con, sc);
                            break;
                        case 3:
                            borrarCliente(con, sc);
                            break;
                        case 4:
                            consultarClientes(con);
                            break;
                        case 5:
                            System.out.println("Saliendo del programa...");
                            break;
                        default:
                            System.out.println("Opción no válida. Inténtalo de nuevo.");
                    }
                }

            } catch (SQLException e) {
                System.err.println("Error fatal de SQL: " + e.getMessage());
                e.printStackTrace();
            } finally {
                // 4. CERRAR CONEXIÓN (siempre)
                // Este finally es necesario para cerrar la conexión principal
                if (con != null) {
                    try {
                        con.close(); // <-- Usamos el .close() estándar
                        System.out.println("Conexión cerrada");
                    } catch (SQLException e) {
                        System.err.println("Error al cerrar la conexión: " + e.getMessage());
                    }
                }
            }
        } 
    }

    /**
     * Inserta los 5 registros iniciales.
     * AHORA RECIBE: Connection con (el objeto de java.sql)
     */
    private static void insertarDatosIniciales(Connection con) {
        System.out.println("Insertando datos iniciales (si no existen)...");
        String[] inserts = {
            "INSERT INTO clientes (password, usuario, `direccion`, `telefono`) VALUES ('333', 'Juan', 'Paseo de Roma 34', '333444555')",
            "INSERT INTO clientes (password, usuario, `direccion`, `telefono`) VALUES ('222', 'lola', 'Av. Alemania', '222333444')",
            "INSERT INTO clientes (password, usuario, `direccion`, `telefono`) VALUES ('555', 'María', 'Avda. París, 7', '555666777')",
            "INSERT INTO clientes (password, usuario, `direccion`, `telefono`) VALUES ('444', 'Pedro', 'Plaza de la Constitución', '444555666')",
            "INSERT INTO clientes (password, usuario, `direccion`, `telefono`) VALUES ('111', 'pepe', 'Av. París', '111222333')"
        };

        // Re-implementamos la lógica de DML aquí
        for (String sql : inserts) {
            // Usamos try-with-resources para el Statement
            try (Statement stmt = con.createStatement()) {
                stmt.executeUpdate(sql);
            } catch (SQLException e) {
                // Esto es normal si los datos ya existen o la tabla no existe
                System.out.println("Aviso: No se pudo ejecutar insert. Causa: " + e.getMessage());
            }
        }
    }

    /**
     * MÉTODO 1: Insertar (Seguro)
     * AHORA RECIBE: Connection con
     */
    public static void insertarCliente(Connection con, Scanner sc) {
        System.out.println("\n--- Insertar Nuevo Cliente (Seguro) ---");
        System.out.print("Usuario: ");
        String usuario = sc.nextLine();
        System.out.print("Password: ");
        String password = sc.nextLine();
        System.out.print("Dirección: ");
        String direccion = sc.nextLine();
        System.out.print("Teléfono: ");
        String telefono = sc.nextLine();
        
        String sql = "INSERT INTO clientes (usuario, password, `dirección`, `teléfono`) VALUES (?, ?, ?, ?)";

        // Ya no usamos con.getCon(), solo 'con'
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, usuario);
            ps.setString(2, password);
            ps.setString(3, direccion);
            ps.setString(4, telefono);

            int n = ps.executeUpdate();
            if (n > 0) {
                System.out.println("Se ha realizado la operación DML correctamente");
            }
        } catch (SQLException e) {
            System.err.println("Error al insertar cliente: " + e.getMessage());
        }
    }

    /**
     * MÉTODO 2: Actualizar (Seguro)
     * AHORA RECIBE: Connection con
     */
    public static void actualizarCliente(Connection con, Scanner sc) {
        System.out.println("\n--- Actualizar Cliente (Seguro) ---");
        System.out.print("Usuario del cliente a actualizar: ");
        String usuario = sc.nextLine();
        System.out.print("Nuevo Password: ");
        String password = sc.nextLine();
        System.out.print("Nueva Dirección: ");
        String direccion = sc.nextLine();
        System.out.print("Nuevo Teléfono: ");
        String telefono = sc.nextLine();

        String sql = "UPDATE clientes SET password = ?, `dirección` = ?, `teléfono` = ? WHERE usuario = ?";

        // Ya no usamos con.getCon(), solo 'con'
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, password);
            ps.setString(2, direccion);
            ps.setString(3, telefono);
            ps.setString(4, usuario); 

            int n = ps.executeUpdate();
            if (n > 0) {
                System.out.println("Se ha realizado la operación DML correctamente");
            } else {
                System.out.println("No se encontró ningún cliente con ese usuario.");
            }
        } catch (SQLException e) {
            System.err.println("Error al actualizar cliente: " + e.getMessage());
        }
    }

    /**
     * MÉTODO 3: Borrar (Seguro)
     * AHORA RECIBE: Connection con
     */
    public static void borrarCliente(Connection con, Scanner sc) {
        System.out.println("\n--- Borrar Cliente (Seguro) ---");
        System.out.print("Usuario del cliente a borrar: ");
        String usuario = sc.nextLine();

        String sql = "DELETE FROM clientes WHERE usuario = ?";

        // Ya no usamos con.getCon(), solo 'con'
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, usuario);

            int n = ps.executeUpdate();
            if (n > 0) {
                System.out.println("Se ha realizado la operación DML correctamente");
            } else {
                System.out.println("No se encontró ningún cliente con ese usuario.");
            }
        } catch (SQLException e) {
            System.err.println("Error al borrar cliente: " + e.getMessage());
        }
    }

    /**
     * MÉTODO 4: Consultar
     * AHORA RECIBE: Connection con
     */
    public static void consultarClientes(Connection con) {
        String sql = "SELECT * FROM clientes";

        // Ya no usamos con.getCon(), solo 'con'
        try (Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            System.out.println("\n--- Lista de Clientes ---");
            System.out.printf("%-10s | %-10s | %-25s | %-15s%n", "USUARIO", "PASSWORD", "DIRECCIÓN", "TELÉFONO");
            System.out.println("--------------------------------------------------------------------------");

            while (rs.next()) {
                String usuario = rs.getString("usuario");
                String pass = rs.getString("password");
                String dir = rs.getString("dirección");
                String tel = rs.getString("teléfono");
                System.out.printf("%-10s | %-10s | %-25s | %-15s%n", usuario, pass, dir, tel);
            }
        } catch (SQLException e) {
            System.err.println("Error al consultar clientes: " + e.getMessage());
        }
    }
}