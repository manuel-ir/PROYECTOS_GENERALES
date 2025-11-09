package ud2.EJ3;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate; // Para la consulta XI

/**
 * Solución para el Ejercicio 3.
 * Esta clase se conecta a la BD EMPRESA y ejecuta una serie de consultas
 * preparadas (con parámetros), probando cada una con distintos valores.
 * El foco aquí es usar PreparedStatement.
 */
public class Ejercicio3 {

    /**
     * Método principal que orquesta la conexión y la ejecución de consultas.
     */
    public static void main(String[] args) {

        String url = "jdbc:mysql://localhost:3306/EMPRESA";
        String user = "root";
        String password = "1234";

        Connection con = null;

        try {
            con = DriverManager.getConnection(url, user, password);
            System.out.println("Conexión establecida con la BD 'EMPRESA'.");

            // I. Empleados por N hijos (1, 2, 3, 4, 5)
            System.out.println("\nI. Empleados por número de hijos:");
            // Usamos un bucle for para probar los 5 valores
            for (int i = 1; i <= 5; i++) {
                consultaI(con, i);
            }

            // II. Salario entre dos extremos
            System.out.println("\nII. Empleados por rango salarial:");
            consultaII(con, 1200, 1300);
            consultaII(con, 1400, 1500); // Probamos con otros valores

            // III. Departamentos que contengan una palabra
            System.out.println("\nIII. Departamentos que contienen una palabra:");
            consultaIII(con, "PERSONAL");
            consultaIII(con, "DATOS"); // Probamos con otra palabra
            
            // IV. Centro por su número
            System.out.println("\nIV. Información de un centro por número:");
            consultaIV(con, 10);
            consultaIV(con, 30); // Probamos con uno que no existe
            
            // V. Empleado por su nombre
            System.out.println("\nV. Información de un empleado por nombre:");
            consultaV(con, "LOPEZ, ANTONIO");
            
            // VI. Empleados que cumplen años en un mes
            System.out.println("\nVI. Empleados que cumplen años en un mes:");
            consultaVI(con, 1);  // Enero
            consultaVI(con, 11); // Noviembre
            
            // VII. Salario total > X
            System.out.println("\nVII. Empleados con salario total superior a un límite:");
            consultaVII(con, 1400);
            consultaVII(con, 1500); // Probamos con otro valor

            // VIII. Empleados y teléfonos por CADA departamento
            consultaVIII(con);
            
            // IX. Empleados que cobran > X en CADA departamento
            consultaIX(con, 1300);
            
            // X. Empleados con > X años al entrar
            System.out.println("\nX. Empleados con cierta edad al ingresar:");
            consultaX(con, 20);
            consultaX(con, 30);
            consultaX(con, 40);
            
            // XI. Antigüedad y Salario
            System.out.println("\nXI. Empleados por antigüedad y salario:");
            // > 3 años Y Salario > 1300
            consultaXI(con, 3, 1300, ">"); 
            // > 5 años Y Salario < 1500
            consultaXI(con, 5, 1500, "<"); 

        } catch (SQLException e) {
            System.err.println("Error fatal de SQL: " + e.getMessage());
            e.printStackTrace();
        } finally {
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

    // CONSULTA I
    /**
     * I. Hallar la comisión, el nombre y el salario de los empleados con más de
     * (parámetro) hijos.
     * @param con Conexión a la BD
     * @param numHijos El número de hijos para el filtro (WHERE Num_hijos > ?)
     */
    public static void consultaI(Connection con, int numHijos) {
        // La consulta es preparada, usa '?'
        String sql = "SELECT Comision, Nombre, Salario FROM EMPLEADOS " +
                     "WHERE Num_hijos > ? " +
                     "ORDER BY Comision, Nombre";

        System.out.println("Empleados con más de " + numHijos + " hijos:");
        
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            
            // Asignamos el valor del parámetro al primer '?'
            ps.setInt(1, numHijos); 

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    String comisionStr = rs.getString("Comision");
                    String nombre = rs.getString("Nombre");
                    double salario = rs.getDouble("Salario");

                    System.out.println("  Nombre: " + nombre + " | Salario: " + salario + " | Comisión: " + (comisionStr == null ? "N/A" : comisionStr));
                }
            }
        } catch (SQLException e) {
            System.err.println("  Error en la consulta I: " + e.getMessage());
        }
    }

    // CONSULTA II
    /**
     * II. Obtener, por orden alfabético, los nombres y los salarios de los
     * empleados cuyo salario esté comprendido entre dos extremos.
     * @param con Conexión a la BD
     * @param min Salario mínimo (parámetro 1)
     * @param max Salario máximo (parámetro 2)
     */
    public static void consultaII(Connection con, double min, double max) {
        String sql = "SELECT Nombre, Salario FROM EMPLEADOS " +
                     "WHERE Salario BETWEEN ? AND ? " +
                     "ORDER BY Nombre";

        System.out.println("Empleados con salario entre " + min + " y " + max + ":");
        try (PreparedStatement ps = con.prepareStatement(sql)) {

            // Asignamos los parámetros
            ps.setDouble(1, min);
            ps.setDouble(2, max);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    System.out.println("  Nombre: " + rs.getString("Nombre") + " | Salario: " + rs.getDouble("Salario"));
                }
            }
        } catch (SQLException e) {
            System.err.println("  Error en la consulta II: " + e.getMessage());
        }
    }

    // CONSULTA III
    /**
     * III. Obtener, por orden alfabético, los nombres de los departamentos que
     * contengan la palabra determinada.
     * @param con Conexión a la BD
     * @param palabra Palabra a buscar (ej: "PERSONAL")
     */
    public static void consultaIII(Connection con, String palabra) {
        String sql = "SELECT Nombre FROM DEPARTAMENTOS " +
                     "WHERE Nombre LIKE ? " +
                     "ORDER BY Nombre";

        System.out.println("Departamentos que contienen '" + palabra + "':");
        try (PreparedStatement ps = con.prepareStatement(sql)) {

            // Ponemos los '%' para que LIKE busque la palabra en cualquier parte
            ps.setString(1, "%" + palabra + "%"); 

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    System.out.println("  Nombre: " + rs.getString("Nombre"));
                }
            }
        } catch (SQLException e) {
            System.err.println("  Error en la consulta III: " + e.getMessage());
        }
    }

    // CONSULTA IV
    /**
     * IV. Obtener toda la información de un centro a partir de su número.
     * @param con Conexión a la BD
     * @param numeroCentro ID del centro a buscar
     */
    public static void consultaIV(Connection con, int numeroCentro) {
        String sql = "SELECT * FROM CENTROS WHERE Numero = ?";

        System.out.println("Información del centro " + numeroCentro + ":");
        try (PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, numeroCentro);

            try (ResultSet rs = ps.executeQuery()) {
                // Usamos 'if' porque esperamos 0 o 1 resultado (es Clave Primaria)
                if (rs.next()) {
                    System.out.println("  Número: " + rs.getInt("Numero") +
                                       " | Nombre: " + rs.getString("Nombre") +
                                       " | Dirección: " + rs.getString("Direccion"));
                } else {
                    System.out.println("  No se encontró el centro " + numeroCentro);
                }
            }
        } catch (SQLException e) {
            System.err.println("  Error en la consulta IV: " + e.getMessage());
        }
    }

    // CONSULTA V
    /**
     * V. Obtener toda la información de un empleado a partir de su nombre.
     * @param con Conexión a la BD
     * @param nombreEmpleado Nombre exacto a buscar
     */
    public static void consultaV(Connection con, String nombreEmpleado) {
        String sql = "SELECT * FROM EMPLEADOS WHERE Nombre = ?";

        System.out.println("Información del empleado '" + nombreEmpleado + "':");
        try (PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, nombreEmpleado);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    // Leemos todos los campos
                    int cod = rs.getInt("Cod");
                    String nombre = rs.getString("Nombre");
                    int depto = rs.getInt("Departamento");
                    int telefono = rs.getInt("Telefono");
                    java.sql.Date fechaNac = rs.getDate("Fecha_nacimiento");
                    java.sql.Date fechaIng = rs.getDate("Fecha_ingreso");
                    double salario = rs.getDouble("Salario");
                    String comisionStr = rs.getString("Comision");
                    int numHijos = rs.getInt("Num_hijos");
                    
                    // Imprimimos la información
                    System.out.println("  Información completa:");
                    System.out.println("    Código: " + cod);
                    System.out.println("    Nombre: " + nombre);
                    System.out.println("    Departamento: " + depto);
                    System.out.println("    Teléfono: " + telefono);
                    System.out.println("    Salario: " + salario);
                    System.out.println("    Comisión: " + (comisionStr == null ? "N/A" : comisionStr));
                    System.out.println("    Nº Hijos: " + numHijos);
                    System.out.println("    Fecha Nacimiento: " + fechaNac);
                    System.out.println("    Fecha Ingreso: " + fechaIng);
                } else {
                    System.out.println("  No se encontró al empleado '" + nombreEmpleado + "'");
                }
            }
        } catch (SQLException e) {
            System.err.println("  Error en la consulta V: " + e.getMessage());
        }
    }

    // CONSULTA VI
    /**
     * VI. Obtener el nombre de todos los empleados que cumplen años en un mes.
     * @param con Conexión a la BD
     * @param mes Número del mes (1=Enero, 12=Diciembre)
     */
    public static void consultaVI(Connection con, int mes) {
        // MONTH() es una función de MySQL que extrae el mes de una fecha
        String sql = "SELECT Nombre, Fecha_nacimiento FROM EMPLEADOS " +
                     "WHERE MONTH(Fecha_nacimiento) = ?";

        System.out.println("Empleados que cumplen años en el mes " + mes + ":");
        try (PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, mes);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    System.out.println("  Nombre: " + rs.getString("Nombre") + " | Fecha Nac: " + rs.getDate("Fecha_nacimiento"));
                }
            }
        } catch (SQLException e) {
            System.err.println("  Error en la consulta VI: " + e.getMessage());
        }
    }
    
    // CONSULTA VII
    /**
     * VII. Hallar el nombre y el salario total de los empleados 
     * cuyo salario total supera un determinado valor.
     * @param con Conexión a la BD
     * @param salarioLimite El salario total a superar
     */
    public static void consultaVII(Connection con, double salarioLimite) {
        // La consulta trae los datos en crudo. El filtro se hace en Java.
        String sql = "SELECT Cod, Nombre, Salario, Comision FROM EMPLEADOS ORDER BY Cod";

        System.out.println("Empleados con Salario Total > " + salarioLimite + " (calculado en Java):");
        
        // Usamos Statement porque la consulta SQL es estática (sin '?')
        try (Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                int cod = rs.getInt("Cod");
                String nombre = rs.getString("Nombre");
                double salario = rs.getDouble("Salario");
                String comisionStr = rs.getString("Comision");
                
                double comision = 0.0;
                if (comisionStr != null) {
                    comision = Double.parseDouble(comisionStr);
                }
                
                double salarioTotal = salario + comision;

                // Hacemos el filtro (el "WHERE") en Java
                if (salarioTotal > salarioLimite) {
                    System.out.println("  Cod: " + cod + " | Nombre: " + nombre + " | Salario Total: " + salarioTotal);
                }
            }
        } catch (SQLException e) {
            System.err.println("  Error en la consulta VII: " + e.getMessage());
        }
    }

    // CONSULTA VIII
    /**
     * VIII. Hallar el número de empleados y de extensiones telefónicas distintas 
     * de cada departamento.
     * @param con Conexión a la BD
     */
    public static void consultaVIII(Connection con) {
        // Usamos GROUP BY para agrupar por departamento
        String sql = "SELECT Departamento, COUNT(*), COUNT(DISTINCT Telefono) " +
                     "FROM EMPLEADOS " +
                     "GROUP BY Departamento " +
                     "ORDER BY Departamento";

        System.out.println("\nVIII. Empleados y teléfonos por departamento:");
        
        try (Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                // Leemos el depto como String para manejar si es NULL
                String deptoStr = rs.getString(1);
                if (deptoStr == null) {
                    deptoStr = "N/A";
                }
                
                int numEmpleados = rs.getInt(2);
                int numTelefonos = rs.getInt(3);
                
                System.out.println("  Depto: " + deptoStr + " | Empleados: " + numEmpleados + " | Teléfonos Distintos: " + numTelefonos);
            }
        } catch (SQLException e) {
            System.err.println("  Error en la consulta VIII: " + e.getMessage());
        }
    }
    
    // CONSULTA IX
    /**
     * IX. Obtener el número de empleados y el nombre de los que cobran más 
     * de un determinado valor en cada departamento.
     * @param con Conexión a la BD
     * @param salarioLimite Salario a superar
     */
    public static void consultaIX(Connection con, double salarioLimite) {
        // La consulta es ambigua, así que hacemos 2 consultas separadas.
        
        // 1. Número total de empleados por departamento (unión izquierda)
        String sqlNumEmpleados = "SELECT d.Nombre, COUNT(e.Cod) " +
                                 "FROM DEPARTAMENTOS d " +
                                 "LEFT JOIN EMPLEADOS e ON d.Numero = e.Departamento " +
                                 "GROUP BY d.Nombre " +
                                 "ORDER BY d.Nombre";

        // 2. Nombres de empleados que superan el límite
        String sqlNombres = "SELECT e.Nombre, e.Salario, d.Nombre AS DeptoNombre " +
                            "FROM EMPLEADOS e " +
                            "JOIN DEPARTAMENTOS d ON e.Departamento = d.Numero " +
                            "WHERE e.Salario > ? " +
                            "ORDER BY d.Nombre, e.Nombre";

        System.out.println("\nIX. Empleados por Depto y Empleados que cobran > " + salarioLimite);

        System.out.println("Número de empleados por departamento:");
        try (Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(sqlNumEmpleados)) {
            
            while (rs.next()) {
                System.out.println("  Depto: " + rs.getString(1) + " | Total Empleados: " + rs.getInt(2));
            }

        } catch (SQLException e) {
            System.err.println("  Error en la consulta IX (Parte 1): " + e.getMessage());
        }

        System.out.println("\nEmpleados que cobran > " + salarioLimite + ":");
        try (PreparedStatement ps = con.prepareStatement(sqlNombres)) {
            
            ps.setDouble(1, salarioLimite); // Asignamos el parámetro

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    System.out.println("  Departamento: " + rs.getString("DeptoNombre") +
                                       " | Nombre: " + rs.getString("Nombre") +
                                       " | Salario: " + rs.getDouble("Salario"));
                }
            }

        } catch (SQLException e) {
            System.err.println("  Error en la consulta IX (Parte 2): " + e.getMessage());
        }
    }
    
    // CONSULTA X
    /**
     * X. Obtener el nombre... de los empleados que tenían más de X años 
     * (20, 30, 40) cuando entraron.
     * @param con Conexión a la BD
     * @param edad Edad que debían tener al ingresar
     */
    public static void consultaX(Connection con, int edad) {
        // TIMESTAMPDIFF(YEAR, fecha1, fecha2) es una función de MySQL
        // que calcula la diferencia en años entre dos fechas.
        String sql = "SELECT Nombre, Salario, Fecha_nacimiento, Fecha_ingreso " +
                     "FROM EMPLEADOS " +
                     "WHERE TIMESTAMPDIFF(YEAR, Fecha_nacimiento, Fecha_ingreso) > ?";

        System.out.println("Empleados con más de " + edad + " años al ingresar:");
        try (PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, edad);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    System.out.println("  Nombre: " + rs.getString("Nombre") +
                                       " | Salario: " + rs.getDouble("Salario") +
                                       " | F. Nac: " + rs.getDate("Fecha_nacimiento") +
                                       " | F. Ingreso: " + rs.getDate("Fecha_ingreso"));
                }
            }
        } catch (SQLException e) {
            System.err.println("  Error en la consulta X: " + e.getMessage());
        }
    }
    
    // CONSULTA XI
    /**
     * XI. Hallar el nombre, código... de los empleados que lleven más de X años 
     * en la empresa y su sueldo sea MAYOR/MENOR que Y.
     * @param con Conexión a la BD
     * @param anios Años de antigüedad
     * @param sueldo Salario a comparar
     * @param comparadorSueldo String (">" o "<") para la comparación
     */
    public static void consultaXI(Connection con, int anios, double sueldo, String comparadorSueldo) {
        
        // Validación del comparador para evitar Inyección SQL
        if (!comparadorSueldo.equals(">") && !comparadorSueldo.equals("<")) {
            System.err.println("Error en Consulta XI: Comparador no válido. Use '>' o '<'.");
            return;
        }

        // 1. Calcular la fecha límite en Java (sin usar CURDATE() de SQL)
        // "Más de X años" = ingresó ANTES de (hoy - X años)
        LocalDate fechaLimite = LocalDate.now().minusYears(anios);

        // 2. Concatenamos el comparador validado. No se puede usar '?' para < o >.
        String sql = "SELECT Nombre, Cod, Salario, Fecha_ingreso " +
                     "FROM EMPLEADOS " +
                     "WHERE Fecha_ingreso < ? " + // Parámetro 1
                     "AND Salario " + comparadorSueldo + " ?"; // Parámetro 2

        System.out.println("Empleados con > " + anios + " años de antigüedad (contratados antes de " + fechaLimite + ") Y Salario " + comparadorSueldo + " " + sueldo + ":");
        
        try (PreparedStatement ps = con.prepareStatement(sql)) {

            // 3. Seteamos los dos '?'
            // ps.setObject puede pasar objetos de fecha modernos de Java
            ps.setObject(1, fechaLimite); 
            ps.setDouble(2, sueldo);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    System.out.println("  Cod: " + rs.getInt("Cod") +
                                       " | Nombre: " + rs.getString("Nombre") +
                                       " | Salario: " + rs.getDouble("Salario") +
                                       " | F. Ingreso: " + rs.getDate("Fecha_ingreso"));
                }
            }
        } catch (SQLException e) {
            System.err.println("  Error en la consulta XI: " + e.getMessage());
        }
    }
}