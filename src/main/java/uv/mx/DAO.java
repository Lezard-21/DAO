package uv.mx;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DAO {
    public static List<Usuario> getAllUsuarios() {
        ResultSet rs = null;
        List<Usuario> resultado = new ArrayList<>();
        PreparedStatement preparedStatement = null;
        Connection conn = Conexion.getConnection();
        String query = "SELECT * from users";

        try {
            preparedStatement = conn.prepareStatement(query);
            rs = preparedStatement.executeQuery();
            while (rs.next()) {
                Usuario p = new Usuario(rs.getString("id"), rs.getString("nombre"), rs.getString("pass"));
                resultado.add(p);
            }
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            try {
                rs.close();
                conn.close();
                preparedStatement.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
        return resultado;
    }

    public static String createUsuario(Usuario p) {
        PreparedStatement preparedStatement = null;
        Connection conn = Conexion.getConnection();
        String msj = "";
        try {
            String query = "INSERT INTO users (id, nombre, pass) values (?,?,?)";
            preparedStatement = conn.prepareStatement(query);
            preparedStatement.setString(1, p.getId());
            preparedStatement.setString(2, p.getNombre());
            preparedStatement.setString(3, p.getPassword());
            if (preparedStatement.executeUpdate() > 0)
                msj = "product added";
            else
                msj = "product not added";

        } catch (Exception e) {
            System.out.println(e);
        } finally {
            try {
                conn.close();
                preparedStatement.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
        return msj;
    }

    public static Usuario modifyUsuario(Usuario u){
        PreparedStatement stm = null;
        Connection conn = null;
        Usuario updatedUser = null;
    
        conn = Conexion.getConnection();
    
        try {
            String sql = "UPDATE users SET nombre = ?, pass = ? WHERE id = ?";
            stm = conn.prepareStatement(sql);
            stm.setString(1, u.getNombre());
            stm.setString(2, u.getPassword());
            stm.setString(3, u.getId());
            int rowsUpdated = stm.executeUpdate();
    
            if (rowsUpdated > 0) {
                updatedUser = GetUsuariosFromId(u.getId());
            }
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            if (stm != null) {
                try {
                    stm.close();
                } catch (Exception e) {
                    System.out.println(e);
                }
                stm = null;
            }
            try {
                conn.close();
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    
        return updatedUser;
    }

    public static Usuario deleteUsuario(String id){
        Usuario res = null;
        Connection conn = Conexion.getConnection();
        PreparedStatement preparedStatement = null;
        String query = "DELETE FROM users WHERE id = ?";
        try {
            res = DAO.GetUsuariosFromId(id);
            System.out.println(DAO.GetUsuariosFromId(id));
            if (res!=null) {
                preparedStatement = conn.prepareStatement(query);
                preparedStatement.setString(1, id);  
                preparedStatement.executeUpdate();
                conn.close();
                preparedStatement.close();
            }else{
                System.out.println(id);
                System.out.println("El id que ingreso no es valido");
            }
            // String queryRes = "SELECT * from users WHERE id= ?";
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } 
        return res;
    }

     public static Usuario GetUsuariosFromId(String id) {
        ResultSet rs = null;
        Usuario resultado = null;
        PreparedStatement preparedStatement = null;
        Connection conn = Conexion.getConnection();
        String query = "SELECT * from users where id=?";

        try {
            preparedStatement = conn.prepareStatement(query);
            preparedStatement.setString(1, id); 
            rs = preparedStatement.executeQuery();
            while (rs.next()) {
                resultado = new Usuario(rs.getString("id"), rs.getString("nombre"), rs.getString("pass"));
            }
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            try {
                rs.close();
                conn.close();
                preparedStatement.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
        return resultado;
    }
}
