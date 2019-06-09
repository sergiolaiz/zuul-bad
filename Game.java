import java.util.Stack;
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

    private static final String NORTE = "norte";
    private static final String SUR = "sur";
    private static final String ESTE = "este";
    private static final String OESTE = "oeste";
    private static final String NOROESTE = "noroeste";
    private static final String NORESTE = "noreste";
    private static final String SURESTE = "sureste";
    private static final String SUROESTE = "suroeste";

    private Player jugador;
    /**
     * Create the game and initialise its internal map.
     */
    public Game() 
    {
        createRooms();
        parser = new Parser();
        jugador = new Player("Pedro");
    }

    /**
     * Create all the rooms and link their exits together.
     */
    private Room createRooms()
    {
        Room entrada,salon, cocina,comedor,aseo,habitacion,jadrinTrasero,casetaAnimales;

        // create the rooms
        entrada = new Room(" frente a la puerta de salida en la entrada de tu casa.");
        entrada.addItem("cartera","Casi se me olvida",200, true);
        entrada.addItem("movil","Sin movil no puedo vivir",200, true);
        entrada.addItem("boli","suelta",20,false);
        salon = new Room("en el salon");
        cocina = new Room("en la cocina");
        cocina.addItem("cerveza","puedes beberla",0,false);
        comedor = new Room("en el comedor");
        aseo = new Room("en el aseo");
        habitacion = new Room("en la habitacion");
        jadrinTrasero = new Room("en el jardin trasero");
        casetaAnimales = new Room("en la caseta de los animales");

        // initialise room exits
        entrada.setExit(NORTE,salon);
        entrada.setExit(NORESTE,cocina);
        entrada.setExit(ESTE,comedor);
        entrada.setExit(SURESTE,aseo);
        entrada.setExit(SUR,habitacion);

        salon.setExit(SUR,entrada);

        habitacion.setExit(NORTE,entrada);
        habitacion.setExit(ESTE,aseo);

        cocina.setExit(SUROESTE,entrada);
        cocina.setExit(SUR,comedor);

        comedor.setExit(NORTE,cocina);
        comedor.setExit(SUR,aseo);
        comedor.setExit(ESTE,jadrinTrasero);        
        comedor.setExit(OESTE,entrada);

        aseo.setExit(OESTE,habitacion);
        aseo.setExit(NORTE,comedor);
        aseo.setExit(NOROESTE,entrada);

        jadrinTrasero.setExit(ESTE,casetaAnimales);
        jadrinTrasero.setExit(OESTE,comedor);

        casetaAnimales.setExit(OESTE,jadrinTrasero);

        return entrada;  // start game outside
    }

    /**
     *  Main play routine.  Loops until end of play.
     */
    public void play() 
    {            
        jugador.setHabitacion(createRooms());
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
        jugador.preguntarUbicacion();
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
            jugador.goRoom(command);
        }
        else if (commandWord.equals("look")) {  
            jugador.look();
        }
        else if (commandWord.equals("eat")) {   
            jugador.eat();
        }
        else if (commandWord.equals("quit")) {
            wantToQuit = quit(command);
        }
        else if (commandWord.equals("back")) {
            jugador.back();
            currentRoom =  jugador.getRoom();
        }
        else if (commandWord.equals("take")) {
            jugador.take(command.getSecondWord());
        }
        else if (commandWord.equals("mochila")) {
            jugador.verMochila();
        }
        else if (commandWord.equals("drop")) {
            jugador.dropElemento(command.getSecondWord());
        }

        return wantToQuit;
    }

    /**
     * Print out some help information.
     * Here we print some stupid, cryptic message and a list of the 
     * command words.
     */
    private void printHelp() 
    {
        System.out.println("Has perdido las llaves del coche y necesitas salir de casa");
        System.out.println("antes de llegar tarde al trabajo.");
        System.out.println("Menunda manera de empezar el dia ¿Verdad?.\n");
        System.out.println("Que comando quieres escribir:");
        parser.showCommands();
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

}
