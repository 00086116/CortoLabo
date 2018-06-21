package Vista;

import DAO.ProductoDao;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import modelo.Producto;

public class Consulta extends JFrame {

    public JLabel lblCodigo, lblPrecio, lblNombre, Disponibilidad,lblCantidad,lblTipo;

    public JTextField codigo,precio,nombre ,disponibilidad, stock,cantidad;
    public JComboBox tipo;

    ButtonGroup existencia = new ButtonGroup();
    public JRadioButton no;
    public JRadioButton si;
    public JTable resultados;

    public JPanel table;

    public JButton buscar, eliminar, insertar, limpiar, actualizar;

    private static final int ANCHOC = 130, ALTOC = 30;

    DefaultTableModel tm;

    public Consulta() {
        super("Inventario");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        agregarLabels();
        formulario();
        llenarTabla();
        Container container = getContentPane();
        container.add(lblCodigo);
        container.add(lblPrecio);
        container.add(lblNombre);
        container.add(Disponibilidad);
        container.add(lblCantidad);
        container.add(lblTipo);
        
        container.add(codigo);
        container.add(precio);
        container.add(nombre);
        container.add(cantidad);
        container.add(disponibilidad);
        container.add(tipo);
        container.add(stock);
        
        container.add(si);
        container.add(no);
        container.add(buscar);
        container.add(insertar);
        container.add(actualizar);
        container.add(eliminar);
        container.add(limpiar);
        container.add(table);
        setSize(800, 800);
        eventos();

    }

    public final void agregarLabels() {
        lblCodigo = new JLabel("Codigo");
        lblPrecio = new JLabel("Precio");
        lblNombre = new JLabel("Stock");
        lblCantidad = new JLabel("Cantidad");
        lblTipo = new JLabel("Tipo");
        Disponibilidad = new JLabel("Disponibilidad");
        lblCodigo.setBounds(10, 10, ANCHOC, ALTOC);
        lblPrecio.setBounds(10, 60, ANCHOC, ALTOC);
        lblNombre.setBounds(10, 100, ANCHOC, ALTOC);
        lblCantidad.setBounds(10, 140, ANCHOC, ALTOC);
        lblTipo.setBounds(10, 180, ANCHOC, ALTOC);
        Disponibilidad.setBounds(10, 220, ANCHOC, ALTOC);

    }
    
    public final void formulario(){
        cantidad=new JTextField();
        codigo = new JTextField();
        precio = new JTextField();
        nombre = new JTextField();
        disponibilidad = new JTextField();
        tipo = new JComboBox();
        stock = new JTextField();
        si = new JRadioButton("si",true);
        no = new JRadioButton("no");
        resultados = new JTable();
        buscar = new JButton("Buscar");
        insertar = new JButton("Insertar");
        eliminar = new JButton("Eliminar");
        actualizar = new JButton("Actualizar");
        limpiar = new JButton("Limpiar");
        
        table = new JPanel();
        
        tipo.addItem("Fruta");
        tipo.addItem("Verdura");
        tipo.addItem("Bebida");
        tipo.addItem("Dulce");
        
        existencia = new ButtonGroup();
        existencia.add(si);
        existencia.add(no);
        
        codigo.setBounds(140, 10, ANCHOC, ALTOC);
        precio.setBounds(140, 60, ANCHOC, ALTOC);
        nombre.setBounds(140, 100, ANCHOC, ALTOC);
        cantidad.setBounds(140, 140, ANCHOC, ALTOC);
        tipo.setBounds(140, 180, ANCHOC, ALTOC);
        stock.setBounds(140, 220, ANCHOC, ALTOC);
        si.setBounds(140, 260, ANCHOC, ALTOC);
        no.setBounds(210, 260, ANCHOC, ALTOC);
        
        buscar.setBounds(300, 10, ANCHOC, ALTOC);
        insertar.setBounds(10, 300, ANCHOC, ALTOC);
        actualizar.setBounds(150, 300, ANCHOC, ALTOC);
        eliminar.setBounds(300, 300, ANCHOC, ALTOC);
        limpiar.setBounds(450, 300, ANCHOC, ALTOC);
        resultados= new JTable();
        table.setBounds(10, 340, 500, 200);
        table.add(new JScrollPane(resultados));
        
    }
    
    public void llenarTabla(){
        tm = new DefaultTableModel(){
            public Class<?> getColumnClass(int column){
                switch(column){
                    case 0:
                        return String.class;
                    case 1:
                        return String.class;
                    case 2:
                        return String.class;
                    case 3:
                        return Boolean.class;
                    case 4:
                        return String.class;
                    case 5:
                        return String.class;
                    default:
                        return Boolean.class;
                }
            }
        };
        tm.addColumn("Codigo");
        tm.addColumn("Nombre");
        tm.addColumn("Tipo");
        tm.addColumn("Disponibilidad");
        tm.addColumn("Precio");
        tm.addColumn("Cantidad");
        
        ProductoDao fd = new ProductoDao();
        ArrayList<Producto> filtros = fd.readAll();
        
        for(Producto fi:filtros){
            tm.addRow(new Object[]{fi.getCodigo(),fi.getNombre(),fi.getTipo(),fi.getExistencia(),fi.getPrecio(),fi.getStock()});
        }
        
        resultados.setModel(tm);
    }
    public void eventos(){
        insertar.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                ProductoDao fd = new ProductoDao();
                Producto f = new Producto(codigo.getText(),tipo.getSelectedItem().toString(),
                        Integer.parseInt(stock.getText()),true);
                if(no.isSelected()){
                    f.setExistencia(false);
                }
                if(fd.create(f)){
                    JOptionPane.showMessageDialog(null, "Filtro regiustrado con exito");
                    limpiarCampos();
                    llenarTabla();
                }
                else{
                    JOptionPane.showMessageDialog(null, "Ocurrio un problema al momento de crear el filtro");
                }
            }
            
        });
        actualizar.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                ProductoDao fd = new ProductoDao();
                Producto f = new Producto(codigo.getText(),tipo.getSelectedItem().toString(),
                        Integer.parseInt(stock.getText()),true);
                if(no.isSelected()){
                    f.setExistencia(false);
                }
                if(fd.update(f)){
                    JOptionPane.showMessageDialog(null, "Filtro modificado con exito");
                    limpiarCampos();
                    llenarTabla();
                }
                else{
                    JOptionPane.showMessageDialog(null, "Ocurrio un problema al momento de modificar el filtro");
                }
            }
        });
        eliminar.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                ProductoDao fd = new ProductoDao();
                
                if(fd.delete(codigo.getText())){
                    JOptionPane.showMessageDialog(null, "Producto eliminado con exito");
                }
                else{
                    JOptionPane.showMessageDialog(null, "Ocurrio un problema al momento de eliminar el producto");
                }
            }
        });
        buscar.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                ProductoDao fd = new ProductoDao();
                Producto f = fd.read(codigo.getText());
                if(f == null ){
                    JOptionPane.showMessageDialog(null, "El producto buscado no se ha encontrado");
                }
                else{
                    codigo.setText(f.getCodigo());
                    tipo.setSelectedItem(f.getNombre());
                    stock.setText(Integer.toString(f.getStock()));
                    
                    if(f.getExistencia()){
                        si.setSelected(true);
                    }
                    else{
                        no.setSelected(true);
                    }
                }
            }
        });
        limpiar.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                limpiarCampos();
            }
        });
    }
    public void limpiarCampos(){
        codigo.setText("");
        tipo.setSelectedItem("Fruta");
        stock.setText("");
    }
    public static void main(String[] args){
        java.awt.EventQueue.invokeLater(new Runnable(){
            @Override
            public void run(){
                new Consulta().setVisible(true);
            }
        });
    }
}