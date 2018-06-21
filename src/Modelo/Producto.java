
package Modelo;

public class Producto {

    private int id;
    private String codigo;
    private int precio;
    private String nombre;
    private int cantidad;
    private boolean existencia;
    private String tipo;

    public Producto() {

    }

    public Producto(int id, String nombre ,String codigo,String tipo, int stock, int precio, boolean existencia) {
        this.id = id;
        this.codigo = codigo;
        this.precio = precio;
        this.nombre = nombre;
        this.cantidad = stock;
        this.existencia = existencia;
        this.tipo=tipo;

    }

    public Producto(String codigo, String marca, int stock, boolean existencia) {
        this.codigo = codigo;
        this.nombre = marca;
        this.cantidad = stock;
        this.existencia = existencia;
    }

    public Producto(String marca, int stock, boolean existencia) {
        this.nombre = marca;
        this.cantidad = stock;
        this.existencia = existencia;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public int getStock() {
        return cantidad;
    }

    public boolean getExistencia() {
        return existencia;
    }

    public void setExistencia(boolean b) {
        existencia = b;
    }
    
    public int getPrecio() {
        return precio;
    }
    public String getTipo() {
        return tipo;
    }

    public int getId() {
        return id;
    }
}