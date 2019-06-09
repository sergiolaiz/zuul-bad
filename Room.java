import java.util.*;

/**
 * Class Room - a room in an adventure game.
 *
 * This class is part of the "World of Zuul" application. 
 * "World of Zuul" is a very simple, text based adventure game.  
 *
 * A "Room" represents one location in the scenery of the game.  It is 
 * connected to other rooms via exits.  The exits are labelled north, 
 * east, south, west.  For each direction, the room stores a reference
 * to the neighboring room, or null if there is no exit in that direction.
 * 
 * @author  Michael Kölling and David J. Barnes
 * @version 2011.07.31
 */
public class Room 
{
    private String description;
    private HashMap<String, Room> salidas;
    private ArrayList<Item> objetos;
    private static final String NORTE = "norte";
    private static final String SUR = "sur";
    private static final String ESTE = "este";
    private static final String OESTE = "oeste";
    private static final String NOROESTE = "noroeste";
    private static final String NORESTE = "noreste";
    private static final String SURESTE = "sureste";
    private static final String SUROESTE = "suroeste";

    private int siguienteId;
    /**
     * Create a room described "description". Initially, it has
     * no exits. "description" is something like "a kitchen" or
     * "an open court yard".
     * @param description The room's description.
     */
    public Room(String description) 
    {
        this.description = description;
        salidas =  new HashMap<>();
        objetos = new ArrayList<Item>();
        siguienteId = 1;
    }

    /**
     * Define una salida para la habitacion.
     * @param direction El nombre de la direccion de la salida
     * @param neighbor La habitacion a la que se llega usando esa salida
     */
    public void setExit(String direction, Room neighbor){
        salidas.put(direction,neighbor);
    }

    /**
     * @return The description of the room.
     */
    public String getDescription()
    {
        return description;
    }

    public Room getExit(String direccion){
        Room habitacionDondeIr =  null;        
        if(direccion.equalsIgnoreCase(NORTE)){
            habitacionDondeIr = salidas.get(NORTE);
        }
        if(direccion.equalsIgnoreCase(SUR)){
            habitacionDondeIr = salidas.get(SUR);
        }
        if(direccion.equalsIgnoreCase(ESTE)){
            habitacionDondeIr = salidas.get(ESTE);
        }
        if(direccion.equalsIgnoreCase(OESTE)){
            habitacionDondeIr = salidas.get(OESTE);
        }
        if(direccion.equalsIgnoreCase(SURESTE)){
            habitacionDondeIr = salidas.get(SURESTE);
        }
        if(direccion.equalsIgnoreCase(NORESTE)){
            habitacionDondeIr = salidas.get(NORESTE);
        }
        if(direccion.equalsIgnoreCase(SUROESTE)){
            habitacionDondeIr = salidas.get(SUROESTE);
        }
        if(direccion.equalsIgnoreCase(NOROESTE)){
            habitacionDondeIr = salidas.get(NOROESTE);
        }
        return habitacionDondeIr;
    }

    public String getExitString(){
        Set<String> posiblesDirecciones = salidas.keySet();
        String descripcionSalidas = "Posibles salidas: ";
        for(String salida : posiblesDirecciones){
            descripcionSalidas += salida + " ";
        }
        return descripcionSalidas;
    }

    /**
     * Devuelve un texto con la descripcion larga de la habitacion del tipo:
     *     You are in the 'name of room'
     *     Exits: north west southwest
     * @return Una descripcion de la habitacion incluyendo sus salidas
     */
    public String getLongDescription(){
        return "Estas en " + description + "\n" +  getExitString();
    }

    public void addItem(String id,String descripcion,int pesoGramos,boolean canBePickedUp ){
        Item itemNuevo = new Item(id,descripcion,pesoGramos,canBePickedUp);
        objetos.add(itemNuevo);
        siguienteId ++;
    }

    public Item getItemPorId(String id){
        Item itemBuscado = null;
        if(!objetos.isEmpty()){
            boolean buscando = true;
            int cont = 0;
            while (cont < objetos.size() && buscando ){
                if(objetos.get(cont).getId().equalsIgnoreCase(id)){
                    itemBuscado = objetos.get(cont);
                    buscando =  false;
                }
                cont ++;
            }
        }
        return itemBuscado;
    }
    
    public void soltarObjeto(Item item){
        objetos.add(item);
    }

    public void eliminarItem(String id){
        if(!objetos.isEmpty()){
            boolean buscando = true;
            int cont = 0;
            while (cont < objetos.size() && buscando ){
                if(objetos.get(cont).getId().equalsIgnoreCase(id)){
                    objetos.remove(cont);
                    buscando =  false;
                }
                cont ++;
            }
        }
    }

    public String getItem(){
        String cadenaObjetos = "Losiento , no hay objetos\n";
        if(!objetos.isEmpty()){
            cadenaObjetos =  "";
            for(Item itemTemp : objetos){
                cadenaObjetos += itemTemp.toString() + "\n";
            }
        }
        return cadenaObjetos;
    }
}