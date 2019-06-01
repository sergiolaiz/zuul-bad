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
        if(direccion.equalsIgnoreCase("north")){
            habitacionDondeIr = salidas.get("north");
        }
        if(direccion.equalsIgnoreCase("south")){
            habitacionDondeIr = salidas.get("south");
        }
        if(direccion.equalsIgnoreCase("east")){
            habitacionDondeIr = salidas.get("east");
        }
        if(direccion.equalsIgnoreCase("west")){
            habitacionDondeIr = salidas.get("west");
        }
        if(direccion.equalsIgnoreCase("southEast")){
            habitacionDondeIr = salidas.get("southEast");
        }
        if(direccion.equalsIgnoreCase("northEast")){
            habitacionDondeIr = salidas.get("northEast");
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
    
    public void addItem(Item newItem){
        objetos.add(newItem);
    }
    
    public String getItem(){
        String cadenaObjetos = "";
        for(int cont = 0; cont < objetos.size(); cont ++){
            cadenaObjetos += (cont + 1) +": "+objetos.get(cont).toString() + "\n";
        }
        return cadenaObjetos;
    }
}
