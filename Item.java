
/**
 * Write a description of class Item here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Item
{
    // instance variables - replace the example below with your own
    private String nombre;
    private String descripcion;

    /**
     * Constructor for objects of class Item
     */
    public Item(String nombre,String descripcion)
    {
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    public String toString(){
        return nombre + " :" +descripcion;
    }
}
