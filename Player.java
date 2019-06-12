import java.util.Stack;
import java.util.ArrayList; 
/**
 * @author  Sergio Laiz Lozano
 * @version v0.5
 */
public class Player
{
    private Room currentRoom;
    private Stack movimientos;
    private ArrayList <Item> mochila;
    private int pesoMochila;
    private int pesoMaximoMochila;

    /**
     * Constructor for objects of class Player
     */
    public Player(int pesoMaximo)
    {
        movimientos = new  Stack();
        mochila = new ArrayList();
        pesoMochila = 0;
        currentRoom =  null;
        pesoMaximoMochila = pesoMaximo;
    }

    /**
     * Metodo que devuelve la habitacion en la que estamos
     */
    public Room getRoom(){
        return currentRoom;
    }

    /**
     * Metodo que devuelve la informacion de la habitacion en la que nos encontramos.
     */
    public void preguntarUbicacion(){
        System.out.println(currentRoom.getLongDescription());
        System.out.println();
    }

    /**
     * Metodo que permite ver los objetos que hay en la habitacion en la que nos encontramos.
     */
    public void look() {   
        System.out.println(currentRoom.getItem());
        preguntarUbicacion();
    }

    /**
     * Metodo que permite comer alimentos.
     */
    public void eat() {    
        System.out.println("Acabas de comer y ya no tienes hambre");
    }

    /**
     * Metodo que permite coger objetos.
     * 
     *  @param idItem = nombre del objeto que queremos coger
     */
    public void take (String idItem){
        String cadeneaADevolver ="";
        Item itemTemp = currentRoom.getItemPorId(idItem);
        if(currentRoom.encontrarObjeto(itemTemp)){
            int pesoConObjeto = pesoMochila + itemTemp.getPeso();
            if(itemTemp.getId().equalsIgnoreCase("cabra")){
                Item llave = new Item("llaves","¡por fin las tengo!, maldita cabra", 0,true);
                currentRoom.eliminarItem(idItem);
                mochila.add(llave);  
                cadeneaADevolver = "ya tengo las llaves, puedo ir a la puerta e irme\n";
            }
            else{
                if(pesoConObjeto <= pesoMaximoMochila && itemTemp.getCanBePickedUp() ){
                    mochila.add(itemTemp);
                    //Eliminamos el item de la habitacion
                    currentRoom.eliminarItem(idItem);
                    pesoMochila += itemTemp.getPeso();
                    cadeneaADevolver = "Has cojido " + itemTemp.getId() + "\n";
                }
                else if(!itemTemp.getCanBePickedUp()){
                    cadeneaADevolver = "No se puede coger este objeto\n";
                }
                else {
                    cadeneaADevolver = "No puedes llevar mas\n";
                }
            }
        }
        else{
            cadeneaADevolver = "Losiento, ese objeto no existe\n";
        }
        System.out.println(cadeneaADevolver);
        preguntarUbicacion();
    }

    /**
     * Metodo que permite beber agua o cerveza, agua = +200g mochila | cerveza = iguala el peso maximo al peso de los objetos de tu mochila.
     * 
     *  @param idItem = nombre de la bebida
     */
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
     * Metodo que permite almacenar la habitacion
     */
    public void setHabitacion(Room room){
        currentRoom = room;
    }

    /**
     * Metodo que permite mostrar por pantalla el contenido de nuestra mochila
     */
    public void verMochila(){ 
        if(!mochila.isEmpty()){
            String cadenaADevolver = "Contenido mochila : \n";
            for(Item itemTem : mochila){
                cadenaADevolver += " - "+ itemTem.getId() +":" + itemTem.getDescripcion() + "\n";
            }
            System.out.println(cadenaADevolver + "Espacio utilizado :" + pesoMochila + "g.\n");
        }
        else{
            System.out.println("La mochila esta vacia\n");
        }
        System.out.println("Espacio disponible "+ (pesoMaximoMochila - pesoMochila)+ "g.\n");
        preguntarUbicacion();
    }

    /**
     * Metodo que permite soltar objetos.
     */
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
            movimientos.push(habitacionActual);
            //se almacena la direccion que se sigue.
            currentRoom = nextRoom;
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