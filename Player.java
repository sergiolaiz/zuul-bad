import java.util.Stack;
import java.util.ArrayList; 
/**
 * Write a description of class Player here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Player
{
    private Room currentRoom;
    private Stack movimientos;
    private int pesoMaximoMochila;
    private ArrayList <Item> mochila;
    private int pesoMochila;
    private String nombreJugador;

    /**
     * Constructor for objects of class Player
     */
    public Player(int pesoMaximoMochila)
    {
        this.pesoMaximoMochila = pesoMaximoMochila;
        movimientos = new  Stack();
        mochila = new ArrayList();
        pesoMochila = 0;
        currentRoom =  null;
    }

    public Room getRoom(){
        return currentRoom;
    }

    public void preguntarUbicacion(){
        System.out.println(currentRoom.getLongDescription());
        System.out.println();
    }

    public void look() {   
        System.out.println(currentRoom.getItem());
        preguntarUbicacion();
    }

    public void setHabitacion(Room habitacion){
        currentRoom =  habitacion;
    }

    public void eat() {    
        System.out.println("Acabas de comer y ya no tienes hambre");
    }

    public void take (String idItem){
        String cadeneaADevolver ="";
        String espacioDisponible = "";
        Item itemTemp = currentRoom.getItemPorId(idItem);
        int pesoConObjeto = pesoMochila + itemTemp.getPeso();
        if(pesoConObjeto <= pesoMaximoMochila && itemTemp.getCanBePickedUp() ){
            mochila.add(itemTemp);
            //Eliminamos el item de la habitacion
            currentRoom.eliminarItem(idItem);
            espacioDisponible = String.valueOf(pesoMaximoMochila - pesoConObjeto) + "g.";
            pesoMochila += itemTemp.getPeso();
            cadeneaADevolver = "Has cogido " + itemTemp.getId() +
            ". Espacio disponible "+ espacioDisponible +"\n";
        }
        else if(!itemTemp.getCanBePickedUp()){
            cadeneaADevolver = "No se puede coger este objeto\n";
        }
        else {
            cadeneaADevolver = "No puedes llevar mas\n";
        }
        System.out.println(cadeneaADevolver);
        preguntarUbicacion();
    }

    public void guardarHabitacion(Room habitacion ){
        movimientos.push(habitacion);
    }

    public void verMochila(){ 
        if(!mochila.isEmpty()){
            String cadenaADevolver = "Contenido mochila : \n";
            for(Item itemTem : mochila){
                cadenaADevolver += itemTem.getId() +":" + itemTem.getDescripcion() + "\n";
            }
            System.out.println(cadenaADevolver + "Espacio disponible :" +(pesoMaximoMochila - pesoMochila ) + "\n");
        }
        else{
            System.out.println("La mochila esta vacia\n");
        }
        preguntarUbicacion();
    }

    public void dropElemento(String id){
        if(!mochila.isEmpty()){
            boolean buscando = true;
            int cont = 0;
            while (cont < mochila.size() && buscando ){
                if(mochila.get(cont).getId().equalsIgnoreCase(id) ){
                    Item itemTemp = mochila.get(cont);
                    currentRoom.soltarObjeto(itemTemp);
                    System.out.println("Has soltado " + itemTemp.getId() + "\n");
                    pesoMochila -= itemTemp.getPeso();
                    mochila.remove(cont);

                    buscando =  false;
                }
                cont ++;
            }
        }
        preguntarUbicacion();
    }

    public void drink (String idItem){
        String cadeneaADevolver ="";
        Item itemTemp = currentRoom.getItemPorId(idItem);
        if(currentRoom.encontrarObjeto(itemTemp) && idItem != null){
            if(itemTemp.getId().equalsIgnoreCase("agua") || itemTemp.getId().equalsIgnoreCase("cerveza")){
                if(itemTemp.getId().equalsIgnoreCase("agua")){
                    pesoMaximoMochila += itemTemp.getPeso();
                    cadeneaADevolver += "Creo que me ha sentado bien, me siento con mas fuerza\n";
                    currentRoom.eliminarItem(idItem);
                }

                else{
                    pesoMaximoMochila = pesoMochila;
                    cadeneaADevolver += "Creo que me ha sentado mal tomar esto.\n";
                    currentRoom.eliminarItem(idItem);
                }
            }
        }
        else{
            cadeneaADevolver += "Losiento, esto no se puede beber\n";
        }
        System.out.println(cadeneaADevolver);
        preguntarUbicacion();    
    }

    /** 
     * Try to go in one direction. If there is an exit, enter
     * the new room, otherwise print an error message.
     */
    public void goRoom(Command command) 
    {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("¿Donde quieres ir?");
            return;
        }

        String direction = command.getSecondWord();

        // Try to leave current room.
        Room nextRoom = currentRoom.getExit(direction);

        if (nextRoom == null) {
            System.out.println("¡No hay niguna puerta!");
        }
        else {
            //en caso de que la salida exista, la almacenamos en nuestros movimientos para poder
            // volver atras en caso de que lo necesitemos.
            Room habitacionActual = currentRoom;
            guardarHabitacion(habitacionActual);
            //se almacena la direccion que se sigue.
            currentRoom = nextRoom;
            setHabitacion(nextRoom);
            preguntarUbicacion();
        }
    }

    public void back(){
        //comprobamos que nuestra lista de movimientos no este vacia para saber si ya regresamos al
        //inicio
        if(!movimientos.empty()){
            //creamos un String con la direccion contraria a la anterior.
            currentRoom = (Room) movimientos.pop();
            preguntarUbicacion();

        }
        else{
            System.out.println("¡Ya volviste donde empezaste!");
            preguntarUbicacion();
        }

    }
}