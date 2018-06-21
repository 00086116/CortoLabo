package DAO;

import Conexion.Conexion;
import Interfaces.Metodos;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import Modelo.Producto;


public class ProductoDao implements Metodos<Producto>{
    private static final String SQL_INSERT = "INSERT INTO productos (codigo,precio,nombre,cantidad, tipo) VALUES (?,?,?,?,?)";
    private static final String SQL_UPDATE = "UPDATE productos SET tipo = ?, nombre = ?, cantidad = ?, disponibilidad=?  WHERE codigo = ?";
    private static final String SQL_DELETE = "DELETE FROM productos WHERE codigo = ?";
    private static final String SQL_READ = "SELECT * FROM productos";
    private static final String SQL_READALL = "SELECT * FROM productos";
    private static final Conexion con = Conexion.conectar();

    @Override
    public boolean create(Producto g) {
        PreparedStatement ps;
        try {
            ps = con.getCnx().prepareStatement(SQL_INSERT);
            ps.setInt(1, g.getCodigo());
            ps.setString(2, g.getPrecio());
            ps.setString(5, g.getNombre());
            ps.setString(6, g.getCantidad());
            ps.setString(3, g.getTipo());
            ps.setBoolean(4, true);
            if (ps.executeUpdate() > 0){
                return true;
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            Logger.getLogger(ProductoDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            con.cerrarConexion();
        }
        return false;
    }

    @Override
    public boolean delete(Object key) {
        PreparedStatement ps;
        try {
            ps = con.getCnx().prepareStatement(SQL_DELETE);
            ps.setString(1, key.toString());
            if (ps.executeUpdate() > 0) {
                return true;
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            Logger.getLogger(ProductoDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            con.cerrarConexion();
        }
        return false;
    }

    @Override
    public boolean update(Producto c) {
        PreparedStatement ps;
        try {
            System.out.println(c.getCodigo());
            ps = con.getCnx().prepareStatement(SQL_UPDATE);
            ps.setInt(1, c.getPrecio());
            ps.setString(2, c.getNombre());
            ps.setBoolean(3, c.getDisponibilidad());
            ps.setString(4, c.getNombre());
            ps.setInt(5, c.getCantidad());
            if (ps.executeUpdate() > 0) {
                return true;
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            Logger.getLogger(ProductoDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            con.cerrarConexion();
        }
        return false;
    }

    @Override
    public Producto read(Object key) {
        Producto f = null;
        PreparedStatement ps;
        ResultSet rs;
        try {
            ps = con.getCnx().prepareStatement(SQL_READ);
            ps.setString(1, key.toString());
            
            rs = ps.executeQuery();
            
            while(rs.next()) {
                f = new Producto (rs.getString(1), rs.getString(2),rs.getString(3), rs.getInt(4),rs.getString(5) , rs.getBoolean(6));
            }
            rs.close();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            Logger.getLogger(ProductoDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            con.cerrarConexion();
        }
        return f;
    }

    @Override
    public ArrayList<Producto> readAll() {
        ArrayList<Producto> all = new ArrayList();
        Statement s;
        ResultSet rs;
        try {
            s = con.getCnx().prepareStatement(SQL_READALL);
            rs = s.executeQuery(SQL_READALL);
            while(rs.next()){
                all.add(new Producto(rs.getString(1), rs.getString(2),rs.getString(3), rs.getInt(4),rs.getString(5) , rs.getBoolean(6)));
            }
            rs.close();
        } catch (SQLException ex) {
           Logger.getLogger(ProductoDao.class.getName()).log(Level.SEVERE, null, ex); 
        } finally {
            con.cerrarConexion();
        }
        return all;
    }
    
}