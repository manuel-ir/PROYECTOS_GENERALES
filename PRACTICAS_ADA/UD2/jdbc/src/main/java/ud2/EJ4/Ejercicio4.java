package ud2.EJ4;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Solución para el Ejercicio 4.
 * Esta clase se conecta a la BD BD_EJERCICIO4 y ejecuta una serie de
 * Procedimientos Almacenados (Stored Procedures) usando CallableStatement.
 * Cada método de esta clase llama a un procedimiento almacenado en el SQL.
 */
public class Ejercicio4 {

    /**
     * Método principal que orquesta la conexión y la llamada a procedimientos.
     */
    public static void main(String[] args) {

        // La URL de conexión apunta a la nueva base de datos del Ejercicio 4
        String url = "jdbc:mysql://localhost:3306/BD_EJERCICIO4";
        String user = "root";
        String password = "1234";

        Connection con = null;

        try {
            con = DriverManager.getConnection(url, user, password);
            System.out.println("Conexión establecida con la BD 'BD_EJERCICIO4'.");

            // Llamamos a cada método que, a su vez, llama a un procedimiento
            consultaA(con, 10, 12);
            consultaB(con, 111);
            consultaC(con);
            consultaD(con);
            consultaE(con, "FONTANERO", 312);
            consultaF(con);
            consultaG(con);
            consultaH(con);
            consultaI(con);
            consultaJ(con);
            consultaK(con);
            consultaL(con, 15);

        } catch (SQLException e) {
            System.err.println("Error fatal de SQL: " + e.getMessage());
            e.printStackTrace();
        } finally {
            // Cerramos la conexión principal al final
            if (con != null) {
                try {
                    con.close();
                    System.out.println("\nConexión cerrada.");
                } catch (SQLException e) {
                    System.err.println("Error al cerrar la conexión: " + e.getMessage());
                }
            }
        }
    }

    /**
     * a) Llama a 'sp_consulta_a' para buscar trabajadores con tarifa entre dos
     * valores.
     * @param con Conexión a la BD
     * @param min Tarifa mínima (parámetro 1)
     * @param max Tarifa máxima (parámetro 2)
     */
    public static void consultaA(Connection con, double min, double max) {
        // La sintaxis JDBC para llamar a un procedimiento es {call nombre_proc(?, ?)}
        String sql = "{call sp_consulta_a(?, ?)}";
        System.out.println("\na. Trabajadores con tarifa entre " + min + " y " + max + ":");

        // Usamos CallableStatement para llamar procedimientos
        // try-with-resources se encarga de cerrarlo
        try (CallableStatement cs = con.prepareCall(sql)) {
            
            // Seteamos los parámetros IN (de entrada)
            cs.setDouble(1, min);
            cs.setDouble(2, max);

            // Ejecutamos la consulta y obtenemos el ResultSet
            try (ResultSet rs = cs.executeQuery()) {
                while (rs.next()) {
                    System.out.println("  Nombre: " + rs.getString("NOMBRE") + " | Tarifa: " + rs.getDouble("TARIFA"));
                }
            }
        } catch (SQLException e) {
            System.err.println("  Error en la consulta A: " + e.getMessage());
        }
    }

    /**
     * b) Llama a 'sp_consulta_b' para los oficios de un edificio.
     * @param con Conexión a la BD
     * @param idEdificio ID del edificio a filtrar
     */
    public static void consultaB(Connection con, int idEdificio) {
        String sql = "{call sp_consulta_b(?)}";
        System.out.println("\nb. Oficios en el edificio " + idEdificio + ":");

        try (CallableStatement cs = con.prepareCall(sql)) {
            cs.setInt(1, idEdificio);

            try (ResultSet rs = cs.executeQuery()) {
                while (rs.next()) {
                    System.out.println("  Oficio: " + rs.getString("OFICIO"));
                }
            }
        } catch (SQLException e) {
            System.err.println("  Error en la consulta B: " + e.getMessage());
        }
    }

    /**
     * c) Llama a 'sp_consulta_c' para ver trabajador y supervisor.
     * @param con Conexión a la BD
     */
    public static void consultaC(Connection con) {
        String sql = "{call sp_consulta_c()}"; // Sin parámetros
        System.out.println("\nc. Trabajador y su supervisor:");

        try (CallableStatement cs = con.prepareCall(sql);
             ResultSet rs = cs.executeQuery()) {
            
            while (rs.next()) {
                System.out.println("  Trabajador: " + rs.getString("Trabajador") + " | Supervisor: " + rs.getString("Supervisor"));
            }
        } catch (SQLException e) {
            System.err.println("  Error en la consulta C: " + e.getMessage());
        }
    }

    /**
     * d) Llama a 'sp_consulta_d' para ver trabajadores en oficinas.
     * @param con Conexión a la BD
     */
    public static void consultaD(Connection con) {
        String sql = "{call sp_consulta_d()}";
        System.out.println("\nd. Trabajadores asignados a 'OFICINA':");

        try (CallableStatement cs = con.prepareCall(sql);
             ResultSet rs = cs.executeQuery()) {
            
            while (rs.next()) {
                System.out.println("  Nombre: " + rs.getString("NOMBRE"));
            }
        } catch (SQLException e) {
            System.err.println("  Error en la consulta D: " + e.getMessage());
        }
    }

    /**
     * e) Llama a 'sp_consulta_e' para total de días de un oficio en un edificio.
     * @param con Conexión a la BD
     * @param oficio Oficio a filtrar
     * @param idEdificio ID del edificio a filtrar
     */
    public static void consultaE(Connection con, String oficio, int idEdificio) {
        String sql = "{call sp_consulta_e(?, ?)}";
        System.out.println("\ne. Total de días de '" + oficio + "' en edificio " + idEdificio + ":");

        try (CallableStatement cs = con.prepareCall(sql)) {
            cs.setString(1, oficio);
            cs.setInt(2, idEdificio);

            try (ResultSet rs = cs.executeQuery()) {
                // SUM() devuelve una sola fila
                if (rs.next()) {
                    System.out.println("  Total Días: " + rs.getInt("Total_Dias"));
                }
            }
        } catch (SQLException e) {
            System.err.println("  Error en la consulta E: " + e.getMessage());
        }
    }

    /**
     * f) Llama a 'sp_consulta_f' para contar los oficios.
     * @param con Conexión a la BD
     */
    public static void consultaF(Connection con) {
        String sql = "{call sp_consulta_f()}";
        System.out.println("\nf. Número total de oficios diferentes:");

        try (CallableStatement cs = con.prepareCall(sql);
             ResultSet rs = cs.executeQuery()) {
            
            // COUNT() devuelve una sola fila
            if (rs.next()) {
                System.out.println("  Total de Oficios: " + rs.getInt("Total_Oficios"));
            }
        } catch (SQLException e) {
            System.err.println("  Error en la consulta F: " + e.getMessage());
        }
    }

    /**
     * g) Llama a 'sp_consulta_g' para la tarifa MAX por supervisor.
     * @param con Conexión a la BD
     */
    public static void consultaG(Connection con) {
        String sql = "{call sp_consulta_g()}";
        System.out.println("\ng. Tarifa más alta por supervisor:");

        try (CallableStatement cs = con.prepareCall(sql);
             ResultSet rs = cs.executeQuery()) {
            
            while (rs.next()) {
                System.out.println("  Supervisor: " + rs.getString("Supervisor") + " | Tarifa Máx: " + rs.getDouble("Tarifa_Max"));
            }
        } catch (SQLException e) {
            System.err.println("  Error en la consulta G: " + e.getMessage());
        }
    }

    /**
     * h) Llama a 'sp_consulta_h' (tarifa MAX, supervisor con >1 trabajador).
     * @param con Conexión a la BD
     */
    public static void consultaH(Connection con) {
        String sql = "{call sp_consulta_h()}";
        System.out.println("\nh. Tarifa más alta por supervisor (que supervisa a > 1):");

        try (CallableStatement cs = con.prepareCall(sql);
             ResultSet rs = cs.executeQuery()) {
            
            while (rs.next()) {
                System.out.println("  Supervisor: " + rs.getString("Supervisor") + " | Tarifa Máx: " + rs.getDouble("Tarifa_Max"));
            }
        } catch (SQLException e) {
            System.err.println("  Error en la consulta H: " + e.getMessage());
        }
    }

    /**
     * i) Llama a 'sp_consulta_i' (tarifa < promedio total).
     * @param con Conexión a la BD
     */
    public static void consultaI(Connection con) {
        String sql = "{call sp_consulta_i()}";
        System.out.println("\ni. Trabajadores con tarifa MENOR al promedio TOTAL:");

        try (CallableStatement cs = con.prepareCall(sql);
             ResultSet rs = cs.executeQuery()) {
            
            while (rs.next()) {
                System.out.println("  Nombre: " + rs.getString("NOMBRE") + " | Tarifa: " + rs.getDouble("TARIFA"));
            }
        } catch (SQLException e) {
            System.err.println("  Error en la consulta I: " + e.getMessage());
        }
    }

    /**
     * j) Llama a 'sp_consulta_j' (tarifa < promedio del oficio).
     * @param con Conexión a la BD
     */
    public static void consultaJ(Connection con) {
        String sql = "{call sp_consulta_j()}";
        System.out.println("\nj. Trabajadores con tarifa MENOR al promedio de su OFICIO:");

        try (CallableStatement cs = con.prepareCall(sql);
             ResultSet rs = cs.executeQuery()) {
            
            while (rs.next()) {
                System.out.println("  Nombre: " + rs.getString("NOMBRE") + " | Oficio: " + rs.getString("OFICIO") + " | Tarifa: " + rs.getDouble("TARIFA"));
            }
        } catch (SQLException e) {
            System.err.println("  Error en la consulta J: " + e.getMessage());
        }
    }

    /**
     * k) Llama a 'sp_consulta_k' (tarifa < promedio del supervisor).
     * @param con Conexión a la BD
     */
    public static void consultaK(Connection con) {
        String sql = "{call sp_consulta_k()}";
        System.out.println("\nk. Trabajadores con tarifa MENOR al promedio de su SUPERVISOR:");

        try (CallableStatement cs = con.prepareCall(sql);
             ResultSet rs = cs.executeQuery()) {
            
            while (rs.next()) {
                System.out.println("  Nombre: " + rs.getString("NOMBRE") + " | Supervisor: " + rs.getInt("ID_SUPV") + " | Tarifa: " + rs.getDouble("TARIFA"));
            }
        } catch (SQLException e) {
            System.err.println("  Error en la consulta K: " + e.getMessage());
        }
    }

    /**
     * l) Llama a 'sp_consulta_l' (supervisores con trabajadores > tarifa).
     * @param con Conexión a la BD
     * @param tarifaMinima Tarifa del trabajador a superar
     */
    public static void consultaL(Connection con, double tarifaMinima) {
        String sql = "{call sp_consulta_l(?)}";
        System.out.println("\nl. Supervisores con trabajadores con tarifa > " + tarifaMinima + ":");

        try (CallableStatement cs = con.prepareCall(sql)) {
            cs.setDouble(1, tarifaMinima);

            try (ResultSet rs = cs.executeQuery()) {
                while (rs.next()) {
                    System.out.println("  Supervisor: " + rs.getString("Supervisor"));
                }
            }
        } catch (SQLException e) {
            System.err.println("  Error en la consulta L: " + e.getMessage());
        }
    }
}