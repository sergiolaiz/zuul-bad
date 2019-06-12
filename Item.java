/**
 * Write a description of class Item here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Item
{
    // instance variables - replace the example below with your own
    private String descripcion;
    private int pesoGramos;
    private String id;
    private boolean canBePickedUp ; 
    /**
     * Constructor for objects of class Item
     */
    public Item(String id,String descripcion, int pesoGramos, boolean canBePickedUp )
    {
        this.pesoGramos = pesoGramos;
        this.descripcion = descripcion;
        this.id = id;
        this.canBePickedUp  = canBePickedUp ;
    }
    
    
    public boolean getCanBePickedUp(){
        return canBePickedUp;
    }
    
    public int getPeso(){
        return pesoGramos;
    }
    
    public String getDescripcion(){
        return descripcion;
    }
    
    public String getId(){
        return id;
    }

    public String toString(){
        String peso = (pesoGramos != 0)? ", peso " + Integer.toString(pesoGramos)+ "g." : "." ;
        return id + ": " + descripcion +  peso ;
    }
}