
/**
 *  This class is the main class of the "World of Zuul" application. 
 *  "World of Zuul" is a very simple, text based adventure game.  Users 
 *  can walk around some scenery. That's all. It should really be extended 
 *  to make it more interesting!
 * 
 *  To play this game, create an instance of this class and call the "play"
 *  method.
 * 
 *  This main class creates and initialises all the others: it creates all
 *  rooms, creates the parser and starts the game.  It also evaluates and
 *  executes the commands that the parser returns.
 * 
 * @author  Michael KÃ¶lling and David J. Barnes
 * @version 2011.07.31
 */

public class Game 
{
    private Parser parser;
    private Room currentRoom;

    /**
     * Create the game and initialise its internal map.
     */
    public Game() 
    {
        createRooms();
        parser = new Parser();
    }

    /**
     * Create all the rooms and link their exits together.
     */
    private void createRooms()
    {
        Room entrada,salon, cocina,comedor,aseo,habitacion,jadrinTrasero,casetaAnimales;

        // create the rooms
        entrada = new Room("Estas frente a la puerta de salida en la entrada de tu casa.");
        salon = new Room("en el salon");
        cocina = new Room("en la cocina");
        comedor = new Room("en el comedor");
        aseo = new Room("en el aseo");
        habitacion = new Room("en la habitacion");
        jadrinTrasero = new Room("en el jardin trasero");
        casetaAnimales = new Room("en la caseta de los animales");

        // initialise room exits
        entrada.setExit("north",salon);
        entrada.setExit("northEast",cocina);
        entrada.setExit("east",comedor);
        entrada.setExit("southEast",aseo);
        entrada.setExit("northEast",habitacion);

        salon.setExit("east",entrada);

        habitacion.setExit("north",entrada);
        habitacion.setExit("east",aseo);

        cocina.setExit("east",comedor);

        comedor.setExit("north",cocina);
        comedor.setExit("south",aseo);
        comedor.setExit("east",jadrinTrasero);        
        comedor.setExit("west",entrada);

        jadrinTrasero.setExit("east",casetaAnimales);
        jadrinTrasero.setExit("west",comedor);

        casetaAnimales.setExit("west",jadrinTrasero);

        currentRoom = entrada;  // start game outside
    }

    /**
     *  Main play routine.  Loops until end of play.
     */
    public void play() 
    {            
        printWelcome();

        // Enter the main command loop.  Here we repeatedly read commands and
        // execute them until the game is over.

        boolean finished = false;
        while (! finished) {
            Command command = parser.getCommand();
            finished = processCommand(command);
        }
        System.out.println("Gracias por haber jugado. ¡ADIOS!");
    }

    /**
     * Print out the opening message for the player.
     */
    private void printWelcome()
    {
        System.out.println();
        System.out.println("Bienvenido a mi horrible mañana antes de ir a trabajar");
        System.out.println("Este es el juego que toda persona desa tener que jugar cada mañana");
        System.out.println("o no... veamos si te es divertido.\n");
        System.out.println("Escribe 'help' en caso de que necesites ayuda.");
        preguntarUbicacion();
        System.out.println();
    }

    /**
     * Given a command, process (that is: execute) the command.
     * @param command The command to be processed.
     * @return true If the command ends the game, false otherwise.
     */
    private boolean processCommand(Command command) 
    {
        boolean wantToQuit = false;

        if(command.isUnknown()) {
            System.out.println("Losiento, no te he entendido....");
            return false;
        }

        String commandWord = command.getCommandWord();
        if (commandWord.equals("help")) {
            printHelp();
        }
        else if (commandWord.equals("go")) {
            goRoom(command);
        }
        else if (commandWord.equals("look")) {	
            look();
        }
        else if (commandWord.equals("eat")) {	
            eat();
        }
        else if (commandWord.equals("quit")) {
            wantToQuit = quit(command);
        }

        return wantToQuit;
    }

    // implementations of user commands:

    /**
     * Print out some help information.
     * Here we print some stupid, cryptic message and a list of the 
     * command words.
     */
    private void printHelp() 
    {
        System.out.println("Has perdido las llaves del coche y necesitas salir de casa");
        System.out.println("antes de llegar tarde al trabajo.");
        System.out.println("Menunda manera de empezar el dia ¿Verdad?.");
        System.out.println("Que comando quieres escribir:");
        System.out.println("   go quit help look eat");
    }

    /** 
     * Try to go in one direction. If there is an exit, enter
     * the new room, otherwise print an error message.
     */
    private void goRoom(Command command) 
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
            currentRoom = nextRoom;
            preguntarUbicacion();
        }
    }

    /** 
     * "Quit" was entered. Check the rest of the command to see
     * whether we really quit the game.
     * @return true, if this command quits the game, false otherwise.
     */
    private boolean quit(Command command) 
    {
        if(command.hasSecondWord()) {
            System.out.println("Quit what?");
            return false;
        }
        else {
            return true;  // signal that we want to quit
        }
    }

    private void preguntarUbicacion(){
        System.out.println(currentRoom.getLongDescription());
        System.out.println();
    }

    private void look() {	
        System.out.println(currentRoom.getLongDescription());
    }
    
    private void eat() {	
        System.out.println("Acabas de comer y ya no tienes hambre");
    }
}
