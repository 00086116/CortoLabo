package DAO;


import Conexion.Conexion;
import Interfaces.metodos;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import Modelo.Producto;

public class ProductoDao implements metodos<Producto> {
    private static final Conexion con = Conexion.conectar();
    
    private static final String SQL_INSERT ="INSERT INTO productos(id,nombre,codigo,tipo,cantidad,precio,disponibilidad) VALUES (?,?,?,?,?,?,?)";
    private static final String SQL_UPDATE ="UPDATE productos SET codigo = ? ,cantidad = ?,disponibilidad =? WHERE codigo = ?";
    private static final String SQL_DELETE ="DELETE FROM productos WHERE codigo = ?";
    private static final String SQL_READ = "SELECT * FROM productos WHERE codigo = ?";
    private static final String SQL_READALL = "SELECT * FROM productos";
    
    @Override
    public boolean create(Producto g){
        PreparedStatement ps;
        try{
            ps = con.getCnx().prepareStatement(SQL_INSERT);
            ps.setInt(1, g.getId());
            ps.setString(2, g.getNombre());
            ps.setString(3, g.getCodigo());
            ps.setString(4, g.getTipo());
            ps.setInt(5, g.getStock());
            ps.setInt(6, g.getPrecio());
            ps.setBoolean(7, g.getExistencia());
            if(ps.executeUpdate() > 0){
                return true;
            }
        }
        catch(SQLException ex){
            System.out.println(ex.getMessage());
            Logger.getLogger(ProductoDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally{
            con.cerrarConexion();
        }
        return false;
    }
    
    @Override
    public boolean delete(Object key){
        PreparedStatement ps;
        try{
            ps = con.getCnx().prepareStatement(SQL_DELETE);
            ps.setString(1, key.toString());
            
            if(ps.executeUpdate() > 0){
                return true;
            }
        }
        catch(SQLException ex){
            System.out.println(ex.getMessage());
            Logger.getLogger(ProductoDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally{
            con.cerrarConexion();
        }
        return false;
    }
    
    public boolean update(Producto c){
        PreparedStatement ps;
        try{
            System.out.println(c.getCodigo());
            ps = con.getCnx().prepareStatement(SQL_UPDATE);
            ps.setString(2, c.getNombre());
            ps.setInt(5, c.getStock());
            ps.setBoolean(7,c.getExistencia());
            ps.setString(3, c.getCodigo());
            
            if(ps.executeUpdate() > 0){
                return true;
            }
        }
        catch(SQLException ex){
            System.out.println(ex.getMessage());
            Logger.getLogger(ProductoDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally{
            con.cerrarConexion();
        }
        return false;
    }
    
    @Override
    public Producto read(Object key){
        Producto f = null;
        PreparedStatement ps;
        ResultSet rs;
        try{
            ps = con.getCnx().prepareStatement(SQL_READ);
            ps.setString(1, key.toString());
            
            rs = ps.executeQuery();
            
            while(rs.next()){
                f = new Producto(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getInt(5),rs.getInt(6),rs.getBoolean(7));
            }
            rs.close();
        }
        catch(SQLException ex){
            System.out.println(ex.getMessage());
            Logger.getLogger(ProductoDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally{
            con.cerrarConexion();
        }
        return f;
    }
    
    @Override
    public ArrayList<Producto> readAll(){
        ArrayList<Producto> all = new ArrayList();
        Statement s;
        ResultSet rs;
        try{
            s = con.getCnx().prepareStatement(SQL_READALL);
            rs = s.executeQuery(SQL_READALL);
            while(rs.next()){
                all.add(new Producto(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getInt(5),rs.getInt(6),rs.getBoolean(7)));
            }
            rs.close();
        }
        catch(SQLException ex){
            Logger.getLogger(ProductoDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return all;
    }
    
}