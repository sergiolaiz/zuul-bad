/**
 * @author  Sergio Laiz Lozano
 * @version v0.5
 */
public class Item
{
    // instance variables - replace the example below with your own
    private String descripcion;
    private int pesoGramos;
    private String id;
    //Atributo que permite definir si un objeto se puede coger o no
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
    
    /**
     * Metodo que devuleve si un item se puede coger o no
     */
    public boolean getCanBePickedUp(){
        return canBePickedUp;
    }
    
    /**
     * Metodo que nos devuelve el peso del item
     */
    public int getPeso(){
        return pesoGramos;
    }
    
    /**
     * Metodo que devuelve la descripcion del item
     */  
    public String getDescripcion(){
        return descripcion;
    }
    
    /**
     * Metodo que devuelve el id del item
     * 
     *  @param id = nombre del item
     */
    public String getId(){
        return id;
    }
    
    /**
     * Metodo toString que nos permite definir como se mostrara el item
     */
    public String toString(){
        String peso = (pesoGramos != 0)? ", peso " + Integer.toString(pesoGramos)+ "g." : "." ;
        return id + ": " + descripcion +  peso ;
    }
}