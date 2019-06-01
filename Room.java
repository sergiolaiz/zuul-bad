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
    public String description;
    private HashMap<String, Room> salidas;

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
    }

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
        String cadenaADevolver = "Salidas: ";
        if(salidas.get("north") != null){
            cadenaADevolver += "north ";
        }
        if(salidas.get("south") != null){
            cadenaADevolver += "south ";
        }
        if(salidas.get("east") != null){
            cadenaADevolver += "east ";
        }
        if(salidas.get("west") != null){
            cadenaADevolver += "west ";
        }
        if(salidas.get("southEast") != null){
            cadenaADevolver += "southEast ";
        }
        if(salidas.get("northEast") != null){
            cadenaADevolver += "northEast ";
        }

        return cadenaADevolver;
    }
}
