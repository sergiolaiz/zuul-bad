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
        jugador = new Player();
    }

    /**
     * Create all the rooms and link their exits together.
     */
    private Room createRooms()
    {
        Room entrada,salon, cocina,comedor,aseo,habitacion,jadrinTrasero,casetaAnimales;

        // create the rooms
        entrada = new Room(" frente a la puerta de salida en la entrada de tu casa.");
        salon = new Room("en el salon");
        cocina = new Room("en la cocina");
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

        //Creamos objetos en las habitaciones
        entrada.addItem("cartera","Casi se me olvida",200, true);
        entrada.addItem("movil","Sin movil no puedo vivir",200, true);
        entrada.addItem("zapatero","el zapatero del ikea",0,false);
        entrada.addItem("perchero","no hay cazadoras",0,false);
        entrada.addItem("paraguas","no creo que llueva",0,false);

        salon.addItem("boli","realmente no lo necesito",5,true);
        salon.addItem("papeles","menudo desorden",20,true);
        salon.addItem("televisor","mi nuevo televisor de 49\"",0,false);
        salon.addItem("sofa","deberia limpiarlo, tiene pelos de animal",0,false);
        salon.addItem("lampara","la lampara led del ikea",0,false);
        salon.addItem("chimena","da calor cuando esta encendida, cuando no, no",0,false);

        cocina.addItem("cerveza","Esto no me tiene que sentar bien ahora",200,false);
        cocina.addItem("agua","Igual me viene bien refrescarme",200,false);
        cocina.addItem("plato","un plato limpio",100,true);
        cocina.addItem("cuchara","no le veo utilidad en este momento",20,true);
        cocina.addItem("cafetera","ya me tome un buen cafe",0,false);
        cocina.addItem("nevera","esta medio vacia, hay que comprar",0,false);
        cocina.addItem("vitroceramica","perfecta para hacerte unas papas",0,false);

        comedor.addItem("mesa","se nota que no me gusta limpiar",0,false);
        comedor.addItem("sillas","menudo desorden de sillas",0,false);
        comedor.addItem("chicles","son de fresa",10,true);
        comedor.addItem("jarron","es el jarron que me regalo la abuela",500,true);

        aseo.addItem("cepillo","ya me he limpiado los dientes",20,true);
        aseo.addItem("espejo","ya me he visto la cara de dormido",20,false);
        aseo.addItem("gel","para lavarse las manos",100,true);
        aseo.addItem("toalla","para secarme las manos",200,true);

        jadrinTrasero.addItem("rosas","aun estan floreciendo",0,false);
        jadrinTrasero.addItem("tomate","ya empiza a salir algun tomate",150,true);
        jadrinTrasero.addItem("lechuga","casi estan listas para comer",0,false);

        casetaAnimales.addItem("huevos","siempre encuentro alguno por el suelo",60,true);
        casetaAnimales.addItem("trigo","deberia comprar, se esta acabando",0,false);

        habitacion.addItem("cama","mi bonita cama de 1,50",0,false);
        habitacion.addItem("ordenador","es un msi, y solo sirve para calentarse.",0,false);
        habitacion.addItem("armario","lleno de ropa que casi ni uso", 0, false);
        habitacion.addItem("estanteria","llena de juegos", 0,false);
        habitacion.addItem("escritorio","algun dia tendre que ordenarlo", 0, false);
        habitacion.addItem("silla","mi silla comoda del ikea", 0, false);
        habitacion.addItem("catalogo","nuevo catalogo de IKEA ¡Como me gusta!",200, true);
        
        

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
        if (commandWord.equals("ayuda")) {
            printHelp();
        }
        else if (commandWord.equals("ir")) {
            jugador.goRoom(command);
        }
        else if (commandWord.equals("mirar")) {  
            jugador.look();
        }
        else if (commandWord.equals("comer")) {   
            jugador.eat();
        }
        else if (commandWord.equals("salir")) {
            wantToQuit = quit(command);
        }
        else if (commandWord.equals("atras")) {
            jugador.back();
        }
        else if (commandWord.equals("coger")) {
            jugador.take(command.getSecondWord());
        }
        else if (commandWord.equals("mochila")) {
            jugador.verMochila();
        }
        else if (commandWord.equals("soltar")) {
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
