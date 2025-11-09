package ud2.EJ5;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;

/**
 * Solución para el Ejercicio 5.
 * Esta clase se conecta a la BD EMPRESA y ejecuta una serie de
 * transacciones (A-E), asegurando que se completen todas las operaciones
 * o ninguna (atomicidad).
 *
 * Una TRANSACCIÓN es un grupo de operaciones que se tratan como una unidad.
 * O todas tienen éxito (COMMIT), o si una falla, ninguna lo hace (ROLLBACK).
 */
public class Ejercicio5 {

    /**
     * Método principal que orquesta la conexión y la ejecución de transacciones.
     */
    public static void main(String[] args) {

        String url = "jdbc:mysql://localhost:3306/EMPRESA";
        String user = "root";
        String password = "1234";

        Connection con = null;

        try {
            con = DriverManager.getConnection(url, user, password);
            System.out.println("Conexión establecida con la BD 'EMPRESA'.");
            System.out.println("Ejecutando transacciones...");

            // A. Ascender a un director en funciones (ID 150)
            // (El director 150 está en funciones en el depto 120)
            transaccionA(con, 150);

            // B. Cambiar nombre de centro (10) y depto (121)
            transaccionB(con, 10, "SEDE CENTRAL NUEVA", 121, "RECURSOS HUMANOS");

            // C. Eliminar un empleado (ID 110) y bajar presupuesto
            // (ID 110, 'PONS, CESAR' no es director ni supervisor, se puede borrar)
            transaccionC(con, 110);
            
            // D. Aumentar presupuesto depto 130 y contratar 4 empleados
            transaccionD(con, 130);

            // E. Aumentar hijos y ascender a director (ID 180)
            // (ID 180 es director en 'F' del depto 111)
            transaccionE(con, 180, 3); // Le ponemos 3 hijos

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

    // TRANSACCIÓN A
    /**
     * A. Cambiar un director de en funciones a en propiedad,
     * se le debe subir el sueldo un 20%.
     * (2 operaciones: UPDATE EMPLEADOS y UPDATE DEPARTAMENTOS)
     * @param con Conexión a la BD
     * @param idDirector Código del empleado director
     */
    public static void transaccionA(Connection con, int idDirector) {
        String sqlUpdateEmp = "UPDATE EMPLEADOS SET Salario = Salario * 1.20 WHERE Cod = ?";
        String sqlUpdateDepto = "UPDATE DEPARTAMENTOS SET Tipo_dir = 'P' WHERE Director = ? AND Tipo_dir = 'F'";

        System.out.println("\nIniciando Transacción A (Ascender Director " + idDirector + ")...");
        
        // Declaramos los PS fuera del try para que sean visibles en el finally
        PreparedStatement psEmp = null;
        PreparedStatement psDepto = null;

        try {
            // 1. Iniciar la transacción:
            // Desactivamos el auto-commit. Nada se guardará hasta que lo digamos.
            con.setAutoCommit(false);

            // 2. Operación 1: Subir sueldo al empleado
            psEmp = con.prepareStatement(sqlUpdateEmp);
            psEmp.setInt(1, idDirector);
            int filasEmp = psEmp.executeUpdate(); // Contamos filas afectadas

            // 3. Operación 2: Cambiar tipo de director a 'P'
            psDepto = con.prepareStatement(sqlUpdateDepto);
            psDepto.setInt(1, idDirector);
            int filasDepto = psDepto.executeUpdate(); // Contamos filas afectadas

            // 4. Comprobación: Ambas operaciones deben afectar al menos a 1 fila
            if (filasEmp > 0 && filasDepto > 0) {
                // 5. Si todo fue bien, confirmar los cambios permanentemente
                con.commit();
                System.out.println("  ÉXITO: Transacción A completada.");
            } else {
                // 6. Si algo no se actualizó (ej. el director no existía o ya era 'P')
                con.rollback(); // Deshacer los cambios
                System.out.println("  FALLO: No se encontró al director en funciones. Transacción A revertida.");
            }

        } catch (SQLException e) {
            // 7. Si hay un error de SQL (ej. tabla no existe), revertir
            System.err.println("  Error en Transacción A: " + e.getMessage());
            try {
                if (con != null) con.rollback(); // Intentamos deshacer
            } catch (SQLException e_rb) {
                System.err.println("  Error al hacer rollback: " + e_rb.getMessage());
            }
        } finally {
            // 8. Limpiar recursos y devolver la conexión al modo normal
            try {
                if (psEmp != null) psEmp.close();
                if (psDepto != null) psDepto.close();
                // Pase lo que pase, volvemos a poner el AutoCommit en true
                con.setAutoCommit(true); 
            } catch (SQLException e_close) {
                System.err.println("  Error al cerrar recursos: " + e_close.getMessage());
            }
        }
    }

    // TRANSACCIÓN B
    /**
     * B. Cambiar el nombre de un centro y de todos los departamentos
     * que se encuentran en ese centro.
     * (Interpretamos que es 1 centro y 1 depto específico de ese centro)
     * @param con Conexión a la BD
     * @param idCentro ID del centro
     * @param nuevoNombreCentro Nuevo nombre para el centro
     * @param idDepto ID del depto
     * @param nuevoNombreDepto Nuevo nombre para el depto
     */
    public static void transaccionB(Connection con, int idCentro, String nuevoNombreCentro, int idDepto, String nuevoNombreDepto) {
        String sqlCentro = "UPDATE CENTROS SET Nombre = ? WHERE Numero = ?";
        // El WHERE aquí es clave: se asegura que el depto PERTENECE a ese centro
        String sqlDepto = "UPDATE DEPARTAMENTOS SET Nombre = ? WHERE Numero = ? AND Centro = ?";

        System.out.println("\nIniciando Transacción B (Renombrar Centro " + idCentro + " y Depto " + idDepto + ")...");
        PreparedStatement psCentro = null;
        PreparedStatement psDepto = null;

        try {
            con.setAutoCommit(false); // Iniciar transacción

            // Op 1: Cambiar nombre Centro
            psCentro = con.prepareStatement(sqlCentro);
            psCentro.setString(1, nuevoNombreCentro);
            psCentro.setInt(2, idCentro);
            int filasCentro = psCentro.executeUpdate();
            
            // Op 2: Cambiar nombre Depto
            psDepto = con.prepareStatement(sqlDepto);
            psDepto.setString(1, nuevoNombreDepto);
            psDepto.setInt(2, idDepto);
            psDepto.setInt(3, idCentro); // Asegura que el depto es de ese centro
            int filasDepto = psDepto.executeUpdate();

            // Ambas deben tener éxito
            if (filasCentro > 0 && filasDepto > 0) {
                con.commit(); // Confirmar
                System.out.println("  ÉXITO: Transacción B completada.");
            } else {
                con.rollback(); // Deshacer
                System.out.println("  FALLO: No se encontró el centro o el depto no pertenecía a él. Transacción B revertida.");
            }

        } catch (SQLException e) {
            System.err.println("  Error en Transacción B: " + e.getMessage());
            try {
                if (con != null) con.rollback();
            } catch (SQLException e_rb) {
                System.err.println("  Error al hacer rollback: " + e_rb.getMessage());
            }
        } finally {
            try {
                if (psCentro != null) psCentro.close();
                if (psDepto != null) psDepto.close();
                con.setAutoCommit(true); // Resetear
            } catch (SQLException e_close) {
                System.err.println("  Error al cerrar recursos: " + e_close.getMessage());
            }
        }
    }

    // TRANSACCIÓN C
    /**
     * C. Eliminar un empleado y disminuir el presupuesto del
     * departamento donde trabajaba un 5%.
     * @param con Conexión a la BD
     * @param idEmpleado ID del empleado a eliminar
     */
    public static void transaccionC(Connection con, int idEmpleado) {
        System.out.println("\nIniciando Transacción C (Eliminar Empleado " + idEmpleado + ")...");
        
        String sqlSelectDepto = "SELECT Departamento FROM EMPLEADOS WHERE Cod = ?";
        String sqlDeleteEmp = "DELETE FROM EMPLEADOS WHERE Cod = ?";
        String sqlUpdateDepto = "UPDATE DEPARTAMENTOS SET Presupuesto = Presupuesto * 0.95 WHERE Numero = ?";
        
        PreparedStatement psSelect = null;
        PreparedStatement psDelete = null;
        PreparedStatement psUpdate = null;
        int deptoId = -1; // Para guardar el ID del depto del empleado

        try {
            // 1. Averiguar el depto del empleado ANTES de la transacción
            // Si lo hiciéramos dentro, y el DELETE fallara, no podríamos
            // obtener este ID para el rollback.
            psSelect = con.prepareStatement(sqlSelectDepto);
            psSelect.setInt(1, idEmpleado);
            try (ResultSet rs = psSelect.executeQuery()) {
                if (rs.next()) {
                    deptoId = rs.getInt("Departamento");
                }
            }

            if (deptoId == -1) {
                System.out.println("  FALLO: No se encontró al empleado. Transacción C cancelada.");
                return; // Salimos del método, no hay nada que hacer
            }

            // 2. Iniciar la transacción
            con.setAutoCommit(false);

            // 3. Operación 1: Eliminar empleado
            // (Esto fallará si es Director o Supervisor, lo cual es correcto
            // y será capturado por el catch)
            psDelete = con.prepareStatement(sqlDeleteEmp);
            psDelete.setInt(1, idEmpleado);
            int filasDelete = psDelete.executeUpdate();
            
            // 4. Operación 2: Actualizar presupuesto
            psUpdate = con.prepareStatement(sqlUpdateDepto);
            psUpdate.setInt(1, deptoId); // Usamos el ID que guardamos
            int filasUpdate = psUpdate.executeUpdate();

            // Ambas deben tener éxito
            if (filasDelete > 0 && filasUpdate > 0) {
                con.commit(); // Confirmar
                System.out.println("  ÉXITO: Transacción C completada.");
            } else {
                con.rollback(); // Deshacer
                System.out.println("  FALLO: No se pudo eliminar o actualizar. Transacción C revertida.");
            }

        } catch (SQLException e) {
            // Si el empleado es Director o Supervisor, saltará un error de FK aquí
            System.err.println("  Error en Transacción C (Quizás el empleado es Director/Supervisor?): " + e.getMessage());
            try {
                if (con != null) con.rollback();
            } catch (SQLException e_rb) {
                System.err.println("  Error al hacer rollback: " + e_rb.getMessage());
            }
        } finally {
            try {
                if (psSelect != null) psSelect.close();
                if (psDelete != null) psDelete.close();
                if (psUpdate != null) psUpdate.close();
                con.setAutoCommit(true); // Resetear
            } catch (SQLException e_close) {
                System.err.println("  Error al cerrar recursos: " + e_close.getMessage());
            }
        }
    }
    
    // TRANSACCIÓN D
    /**
     * D. Aumentar el presupuesto de departamento un 20% y
     * contratar a 4 nuevos empleados.
     * @param con Conexión a la BD
     * @param idDepto Depto que recibe el aumento y los empleados
     */
    public static void transaccionD(Connection con, int idDepto) {
        System.out.println("\nIniciando Transacción D (Contratar 4 empleados en Depto " + idDepto + ")...");
        
        String sqlUpdateDepto = "UPDATE DEPARTAMENTOS SET Presupuesto = Presupuesto * 1.20 WHERE Numero = ?";
        String sqlInsertEmp = "INSERT INTO EMPLEADOS (Cod, Departamento, Telefono, Fecha_nacimiento, Fecha_ingreso, Salario, Nombre) VALUES (?, ?, ?, ?, ?, ?, ?)";
        
        PreparedStatement psUpdate = null;
        PreparedStatement psInsert = null;

        try {
            con.setAutoCommit(false); // Iniciar transacción
            
            // Op 1: Aumentar presupuesto
            psUpdate = con.prepareStatement(sqlUpdateDepto);
            psUpdate.setInt(1, idDepto);
            psUpdate.executeUpdate();
            
            // Op 2: Insertar 4 empleados
            // Reutilizamos el mismo PreparedStatement
            psInsert = con.prepareStatement(sqlInsertEmp);
            
            // Empleado 1
            psInsert.setInt(1, 901); // Nuevo ID
            psInsert.setInt(2, idDepto);
            psInsert.setInt(3, 901);
            psInsert.setObject(4, LocalDate.of(1990, 1, 1));
            psInsert.setObject(5, LocalDate.now());
            psInsert.setDouble(6, 1200);
            psInsert.setString(7, "NUEVO, EMPLEADO 1");
            psInsert.executeUpdate(); // Ejecuta el 1ro
            
            // Empleado 2
            psInsert.setInt(1, 902); // Nuevo ID
            psInsert.setInt(2, idDepto);
            psInsert.setInt(3, 902);
            psInsert.setObject(4, LocalDate.of(1991, 1, 1));
            psInsert.setObject(5, LocalDate.now());
            psInsert.setDouble(6, 1200);
            psInsert.setString(7, "NUEVO, EMPLEADO 2");
            psInsert.executeUpdate(); // Ejecuta el 2do
            
            // Empleado 3
            psInsert.setInt(1, 903); // Nuevo ID
            psInsert.setInt(2, idDepto);
            psInsert.setInt(3, 903);
            psInsert.setObject(4, LocalDate.of(1992, 1, 1));
            psInsert.setObject(5, LocalDate.now());
            psInsert.setDouble(6, 1200);
            psInsert.setString(7, "NUEVO, EMPLEADO 3");
            psInsert.executeUpdate(); // Ejecuta el 3ro
            
            // Empleado 4
            psInsert.setInt(1, 904); // Nuevo ID
            psInsert.setInt(2, idDepto);
            psInsert.setInt(3, 904);
            psInsert.setObject(4, LocalDate.of(1993, 1, 1));
            psInsert.setObject(5, LocalDate.now());
            psInsert.setDouble(6, 1200);
            psInsert.setString(7, "NUEVO, EMPLEADO 4");
            psInsert.executeUpdate(); // Ejecuta el 4to

            // Si llegamos aquí sin que salte una excepción, confirmamos
            con.commit();
            System.out.println("  ÉXITO: Transacción D completada.");

        } catch (SQLException e) {
            // Si un 'Cod' ya existía, saltará un error de Primary Key
            System.err.println("  Error en Transacción D (Quizás un 'Cod' ya existía?): " + e.getMessage());
            try {
                if (con != null) con.rollback(); // Deshacer todo
            } catch (SQLException e_rb) {
                System.err.println("  Error al hacer rollback: " + e_rb.getMessage());
            }
        } finally {
            try {
                if (psUpdate != null) psUpdate.close();
                if (psInsert != null) psInsert.close();
                con.setAutoCommit(true); // Resetear
            } catch (SQLException e_close) {
                System.err.println("  Error al cerrar recursos: " + e_close.getMessage());
            }
        }
    }
    
    // TRANSACCIÓN E
    /**
     * E. Aumentar el número de hijos de un empleado, que es director
     * del departamento en funciones, hace que pase a ser director en propiedad.
     * @param con Conexión a la BD
     * @param idDirector ID del empleado/director
     * @param nuevoNumHijos El nuevo número de hijos
     */
    public static void transaccionE(Connection con, int idDirector, int nuevoNumHijos) {
        System.out.println("\nIniciando Transacción E (Aumentar hijos y ascender a " + idDirector + ")...");
        
        // Es la misma lógica que la Transacción A, solo cambia la primera SQL
        String sqlUpdateEmp = "UPDATE EMPLEADOS SET Num_hijos = ? WHERE Cod = ?";
        String sqlUpdateDepto = "UPDATE DEPARTAMENTOS SET Tipo_dir = 'P' WHERE Director = ? AND Tipo_dir = 'F'";
        
        PreparedStatement psEmp = null;
        PreparedStatement psDepto = null;

        try {
            con.setAutoCommit(false); // Iniciar transacción
            
            // Op 1: Aumentar hijos
            psEmp = con.prepareStatement(sqlUpdateEmp);
            psEmp.setInt(1, nuevoNumHijos);
            psEmp.setInt(2, idDirector);
            int filasEmp = psEmp.executeUpdate();
            
            // Op 2: Ascender a 'P'
            psDepto = con.prepareStatement(sqlUpdateDepto);
            psDepto.setInt(1, idDirector);
            int filasDepto = psDepto.executeUpdate();

            // Ambas operaciones deben tener éxito
            if (filasEmp > 0 && filasDepto > 0) {
                con.commit(); // Confirmar
                System.out.println("  ÉXITO: Transacción E completada.");
            } else {
                con.rollback(); // Deshacer
                System.out.println("  FALLO: No se encontró al director o no estaba en 'F'. Transacción E revertida.");
            }
            
        } catch (SQLException e) {
            System.err.println("  Error en Transacción E: " + e.getMessage());
            try {
                if (con != null) con.rollback();
            } catch (SQLException e_rb) {
                System.err.println("  Error al hacer rollback: " + e_rb.getMessage());
            }
        } finally {
            try {
                if (psEmp != null) psEmp.close();
                if (psDepto != null) psDepto.close();
                con.setAutoCommit(true); // Resetear
            } catch (SQLException e_close) {
                System.err.println("  Error al cerrar recursos: " + e_close.getMessage());
            }
        }
    }
}