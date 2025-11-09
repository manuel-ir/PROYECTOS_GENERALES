package ud2.EJ2; 

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Solución para el Ejercicio 2.
 * Esta clase se conecta a la BD EMPRESA y ejecuta una serie de consultas
 * (A-L) una tras otra, imprimiendo los resultados en consola.
 * Usa Statement para consultas estáticas y PreparedStatement para las que
 * tienen parámetros (aunque sean fijos, como buena práctica).
 */
public class Ejercicio2 {

    /**
     * Método principal que orquesta la conexión y la ejecución de consultas.
     * @param args Argumentos de línea de comandos (no se usan)
     */
    public static void main(String[] args) {

        // Datos de conexión a la BD EMPRESA
        String url = "jdbc:mysql://localhost:3306/EMPRESA";
        String user = "root";
        String password = "1234";

        // Declaramos la Conexión fuera del try para que sea visible en el finally
        Connection con = null;

        try {
            // 1. Establecemos la conexión una sola vez
            con = DriverManager.getConnection(url, user, password);
            System.out.println("Conexión establecida con la BD 'EMPRESA'.");

            // 2. Ejecutamos todas las consultas en orden,
            //    pasando la conexión a cada método.
            consultaA(con);
            consultaB(con);
            consultaC(con);
            consultaD(con);
            consultaE(con);
            consultaF(con);
            consultaG(con);
            consultaH(con);
            consultaI(con);
            consultaJ(con);
            consultaK(con);
            consultaL(con);

        } catch (SQLException e) {
            // Captura cualquier error de SQL que ocurra (ej. BD no disponible)
            System.err.println("Error fatal de SQL: " + e.getMessage());
            e.printStackTrace();
        } finally {
            // 3. Este bloque finally es crucial.
            // Se ejecuta SIEMPRE (con o sin error) para cerrar la conexión.
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

    // CONSULTA "A"
    /**
     * A. Hallar la comisión, el nombre y el salario de los empleados con más de
     * tres hijos, ordenados por comisión y, dentro de comisión, alfabéticamente.
     * @param con La conexión activa a la base de datos.
     */
    public static void consultaA(Connection con) {
        // Consulta SQL estática (sin variables), con WHERE y ORDER BY
        String sql = "SELECT Comision, Nombre, Salario FROM EMPLEADOS " +
                     "WHERE Num_hijos > 3 " +
                     "ORDER BY Comision, Nombre";

        System.out.println("\nA. Empleados con más de 3 hijos");
        
        // Usamos Statement porque la consulta es estática (sin parámetros '?')
        // Usamos try-with-resources: 'stmt' y 'rs' se cierran solos al final.
        try (Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            // El bucle while(rs.next()) mueve el cursor fila por fila.
            // Devuelve false cuando no hay más filas.
            while (rs.next()) {
                // Leemos la comisión como un String para poder comprobar si es NULL
                String comisionStr = rs.getString("Comision");
                String nombre = rs.getString("Nombre");
                double salario = rs.getDouble("Salario");

                // (comisionStr == null ? "N/A" : comisionStr) es un 'if' corto (operador ternario)
                // Si es null, imprime "N/A", si no, imprime el valor.
                System.out.println("  Nombre: " + nombre + " | Salario: " + salario + " | Comisión: " + (comisionStr == null ? "N/A" : comisionStr));
            }
        } catch (SQLException e) {
            System.err.println("  Error en la consulta A: " + e.getMessage());
        }
    }

    // CONSULTA "B"
    /**
     * B. Obtener los nombres de los departamentos que no dependen de otros.
     * @param con La conexión activa a la base de datos.
     */
    public static void consultaB(Connection con) {
        // La condición es que el campo Depto_jefe (clave foránea a sí misma) sea NULL
        String sql = "SELECT Nombre FROM DEPARTAMENTOS WHERE Depto_jefe IS NULL";

        System.out.println("\nB. Departamentos que no dependen de otros (Jefes Supremos)");
        try (Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                System.out.println("  Nombre: " + rs.getString("Nombre"));
            }
        } catch (SQLException e) {
            System.err.println("  Error en la consulta B: " + e.getMessage());
        }
    }

    // CONSULTA "C"
    /**
     * C. Obtener, por orden alfabético, los nombres y los salarios de los
     * empleados cuyo salario esté comprendido entre 1250 y 1300 euros.
     * @param con La conexión activa a la base de datos.
     */
    public static void consultaC(Connection con) {
        // Usamos PreparedStatement porque la consulta tiene parámetros (BETWEEN ? AND ?)
        String sql = "SELECT Nombre, Salario FROM EMPLEADOS " +
                     "WHERE Salario BETWEEN ? AND ? " +
                     "ORDER BY Nombre";

        System.out.println("\nC. Empleados con salario entre 1250 y 1300");
        
        // try-with-resources para el PreparedStatement (se cierra solo)
        try (PreparedStatement ps = con.prepareStatement(sql)) {

            // Asignamos valores a los '?' (1 es el primero, 2 el segundo)
            ps.setDouble(1, 1250.0);
            ps.setDouble(2, 1300.0);

            // Ejecutamos la consulta y obtenemos el ResultSet (dentro de otro try)
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    System.out.println("  Nombre: " + rs.getString("Nombre") + " | Salario: " + rs.getDouble("Salario"));
                }
            }
        } catch (SQLException e) {
            System.err.println("  Error en la consulta C: " + e.getMessage());
        }
    }

    // CONSULTA "D"
    /**
     * D. Datos de los empleados que cumplen la condición anterior (salario
     * 1250-1300) o tienen al menos un hijo.
     * @param con La conexión activa a la base de datos.
     */
    public static void consultaD(Connection con) {
        // Usamos PreparedStatement por los 3 parámetros
        String sql = "SELECT Cod, Nombre, Salario, Num_hijos FROM EMPLEADOS " +
                     "WHERE (Salario BETWEEN ? AND ?) OR (Num_hijos >= ?)";

        System.out.println("\nD. Empleados (salario 1250-1300) O (con hijos)");
        try (PreparedStatement ps = con.prepareStatement(sql)) {

            // Asignamos valores a los tres '?' en orden
            ps.setDouble(1, 1250.0);
            ps.setDouble(2, 1300.0);
            ps.setInt(3, 1); // "al menos un hijo"

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    System.out.println("  Cod: " + rs.getInt("Cod") +
                                       " | Nombre: " + rs.getString("Nombre") +
                                       " | Salario: " + rs.getDouble("Salario") +
                                       " | Hijos: " + rs.getInt("Num_hijos"));
                }
            }
        } catch (SQLException e) {
            System.err.println("  Error en la consulta D: " + e.getMessage());
        }
    }

    // CONSULTA "E"
    /**
     * E. Obtener, por orden alfabético, los nombres de los departamentos que no
     * contengan la palabra 'Dirección' ni 'Sector'.
     * @param con La conexión activa a la base de datos.
     */
    public static void consultaE(Connection con) {
        // Usamos NOT LIKE con el comodín '%' para buscar subcadenas
        String sql = "SELECT Nombre FROM DEPARTAMENTOS " +
                     "WHERE Nombre NOT LIKE '%Dirección%' AND Nombre NOT LIKE '%Sector%' " +
                     "ORDER BY Nombre";

        System.out.println("\nE. Deptos sin 'Dirección' ni 'Sector'");
        try (Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                System.out.println("  Nombre: " + rs.getString("Nombre"));
            }
        } catch (SQLException e) {
            System.err.println("  Error en la consulta E: " + e.getMessage());
        }
    }

    // CONSULTA "F"
    /**
     * F. Deptos que, o bien tienen directores en 'F' y presupuesto <= 5, o bien
     * no dependen de ningún otro.
     * @param con La conexión activa a la base de datos.
     */
    public static void consultaF(Connection con) {
        // Consulta con lógica 'OR' y 'AND' agrupada por paréntesis
        String sql = "SELECT Nombre, Tipo_dir, Presupuesto, Depto_jefe FROM DEPARTAMENTOS " +
                     "WHERE (Tipo_dir = ? AND Presupuesto <= ?) OR (Depto_jefe IS NULL) " +
                     "ORDER BY Nombre";

        System.out.println("\nF. Deptos (Dir='F' y Pres<=5) O (Sin Jefe)");
        try (PreparedStatement ps = con.prepareStatement(sql)) {

            // Asignamos los parámetros para la primera condición
            ps.setString(1, "F");
            ps.setInt(2, 5); // Presupuesto está en miles (5 = 5000)

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    // Usamos getString() para Depto_jefe para poder manejar el NULL
                    String deptoJefeStr = rs.getString("Depto_jefe");
                    
                    if (deptoJefeStr == null) {
                        deptoJefeStr = "N/A"; // Más claro que imprimir "null"
                    }

                    System.out.println("  Nombre: " + rs.getString("Nombre") +
                                       " | Tipo Dir: " + rs.getString("Tipo_dir") +
                                       " | Presupuesto: " + rs.getDouble("Presupuesto") +
                                       " | Jefe: " + deptoJefeStr);
                }
            }
        } catch (SQLException e) {
            System.err.println("  Error en la consulta F: " + e.getMessage());
        }
    }

    // CONSULTA "G"
    /**
     * G. Hallar, por orden de número de empleado, el nombre y el salario total
     * (salario más comisión) de los empleados cuyo salario total supera los 1300
     * euros.
     * @param con La conexión activa a la base de datos.
     */
    public static void consultaG(Connection con) {
        // Traemos los datos en crudo. El cálculo y filtro se harán en Java
        // para manejar los NULL de forma simple (como pediste).
        String sql = "SELECT Cod, Nombre, Salario, Comision FROM EMPLEADOS ORDER BY Cod";

        System.out.println("\nG. Empleados con Salario Total > 1300 (calculado en Java)");
        
        try (Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                int cod = rs.getInt("Cod");
                String nombre = rs.getString("Nombre");
                double salario = rs.getDouble("Salario");
                
                // Leemos la comisión como String para comprobar si es null
                String comisionStr = rs.getString("Comision");
                
                double comision = 0.0;
                // Si el String no es null, lo convertimos a double
                if (comisionStr != null) {
                    comision = Double.parseDouble(comisionStr);
                }
                
                // Ahora la suma es segura. Si era null, sumará 0.0
                double salarioTotal = salario + comision;

                // Hacemos el filtro (el "WHERE") en Java
                if (salarioTotal > 1300) {
                    System.out.println("  Cod: " + cod + " | Nombre: " + nombre + " | Salario Total: " + salarioTotal);
                }
            }
        } catch (SQLException e) {
            System.err.println("  Error en la consulta G: " + e.getMessage());
        }
    }

    // CONSULTA "H"
    /**
     * H. Hallar el número de empleados de toda la empresa.
     * @param con La conexión activa a la base de datos.
     */
    public static void consultaH(Connection con) {
        // COUNT(*) es una función de agregación de SQL
        String sql = "SELECT COUNT(*) FROM EMPLEADOS";

        System.out.println("\nH. Número total de empleados");
        try (Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            // Una función de agregación sin GROUP BY siempre devuelve UNA fila.
            // Por eso usamos 'if' en vez de 'while'.
            if (rs.next()) {
                // Obtenemos el resultado por el índice de la columna (1)
                int totalEmpleados = rs.getInt(1); 
                System.out.println("  Número total de empleados: " + totalEmpleados);
            }
        } catch (SQLException e) {
            System.err.println("  Error en la consulta H: " + e.getMessage());
        }
    }

    // CONSULTA "I"
    /**
     * I. Hallar cuántos departamentos existen y el presupuesto anual medio.
     * @param con La conexión activa a la base de datos.
     */
    public static void consultaI(Connection con) {
        // Múltiples funciones de agregación
        String sql = "SELECT COUNT(*), AVG(Presupuesto) FROM DEPARTAMENTOS";

        System.out.println("\nI. Resumen de Departamentos");
        try (Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            // Igual que H, devuelve UNA fila
            if (rs.next()) {
                int totalDeptos = rs.getInt(1); // Columna 1
                double presupuestoMedio = rs.getDouble(2); // Columna 2
                System.out.println("  Número de departamentos: " + totalDeptos);
                System.out.println("  Presupuesto medio: " + presupuestoMedio + " miles de euros");
            }
        } catch (SQLException e) {
            System.err.println("  Error en la consulta I: " + e.getMessage());
        }
    }

    // CONSULTA "J"
    /**
     * J. Hallar el número de empleados y de extensiones telefónicas distintas del
     * departamento 112.
     * @param con La conexión activa a la base de datos.
     */
    public static void consultaJ(Connection con) {
        // Usamos COUNT(DISTINCT ...) para contar valores únicos
        String sql = "SELECT COUNT(*), COUNT(DISTINCT Telefono) FROM EMPLEADOS " +
                     "WHERE Departamento = ?";

        System.out.println("\nJ. Resumen Depto 112");
        try (PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, 112); // Parámetro para el WHERE

            try (ResultSet rs = ps.executeQuery()) {
                // De nuevo, UNA fila de resultado
                if (rs.next()) {
                    int totalEmpleados = rs.getInt(1);
                    int telefonosDistintos = rs.getInt(2);
                    System.out.println("  Empleados en Depto 112: " + totalEmpleados);
                    System.out.println("  Teléfonos distintos en Depto 112: " + telefonosDistintos);
                }
            }
        } catch (SQLException e) {
            System.err.println("  Error en la consulta J: " + e.getMessage());
        }
    }

    // CONSULTA "K"
    /**
     * K. Códigos de los departamentos que no hacen de departamento jefe.
     * @param con La conexión activa a la base de datos.
     */
    public static void consultaK(Connection con) {
        // Usamos una subconsulta con NOT IN.
        // Un depto no es jefe si su 'Numero' NO ESTÁ en la lista de 'Depto_jefe'
        String sql = "SELECT Numero, Nombre FROM DEPARTAMENTOS " +
                     "WHERE Numero NOT IN ( " +
                     "  SELECT DISTINCT Depto_jefe FROM DEPARTAMENTOS WHERE Depto_jefe IS NOT NULL " +
                     ")";

        System.out.println("\nK. Departamentos que NO son jefes");
        try (Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                System.out.println("  Cod: " + rs.getInt("Numero") + " | Nombre: " + rs.getString("Nombre"));
            }
        } catch (SQLException e) {
            System.err.println("  Error en la consulta K: " + e.getMessage());
        }
    }

    // CONSULTA "L"
    /**
     * L. Ídem pero que sí hacen de departamento jefe de algún otro departamento.
     * @param con La conexión activa a la base de datos.
     */
    public static void consultaL(Connection con) {
        // La forma más simple es un JOIN de la tabla consigo misma.
        // Unimos DEPARTAMENTOS (d) con DEPARTAMENTOS (d_hijos)
        // donde el Numero de 'd' sea igual al Depto_jefe de 'd_hijos'.
        String sql = "SELECT DISTINCT d.Numero, d.Nombre FROM DEPARTAMENTOS d " +
                     "JOIN DEPARTAMENTOS d_hijos ON d.Numero = d_hijos.Depto_jefe " +
                     "ORDER BY d.Numero";

        System.out.println("\nL. Departamentos que SÍ son jefes");
        try (Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                System.out.println("  Cod: " + rs.getInt("Numero") + " | Nombre: " + rs.getString("Nombre"));
            }
        } catch (SQLException e) {
            System.err.println("  Error en la consulta L: " + e.getMessage());
        }
    }
}