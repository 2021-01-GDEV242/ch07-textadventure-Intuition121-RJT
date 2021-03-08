import java.util.ArrayList;
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
 * @author  Michael Kölling and David J. Barnes
 * @version 2016.02.29
 */

public class Game 
{
    private Parser parser;
    private Room currentRoom;
    
    //stores the previous room
    private Room previousRoom;
    private ArrayList<Items> item;
        
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
        Room outside, theater, pub, lab, office, kitchen, dining_hall,bathroom ;
        Room game_room,lounge,gym,coffee_shop,field,parking_lot;
      
       
        
        // create new rooms
        outside = new Room("Outside the main entrance of the Uni");
        theater = new Room("in a lecture theater");
        pub  = new Room("on campus pub");
        lab  = new Room("science lab");
        office = new Room("in the computing admin office");
        kitchen = new Room("where the food is cooked");
        dining_hall = new Room("where students have their meals");
        bathroom = new Room("where you go");
        game_room = new Room("the fun lounge");
        lounge = new Room("the boring lounge");
        gym = new Room("where buff guys and thick girls work out");
        coffee_shop = new Room("on campus shop near the entrance");
        field = new Room("field often used for recreation");
        parking_lot = new Room("car parking");
        
        //create items for each room
        ;
        
        Items outside_Item[]= {new Items("water fountain",100)};
        Items theater_Item[]= {new Items("popcorn", 1)};
        Items pub_Item[]={new Items("beer",6)};
        Items lab_Item[]={new Items("tongs",1)};
        Items office_Item[]={new Items("pen",2)};
        Items kitchen_Item[]={new Items("pot",8)};
        Items dining_hall_Item[]={new Items("tray",5)};
        Items bathroom_Item[]={new Items("paper",40000)};
        Items game_room_Item[]={new Items("pool ball",500)};
        Items lounge_Item[]={new Items("newspaper",6)};
        Items gym_Item[]={new Items("basketball",5)};
        Items coffee_shop_Item[]={new Items("coffee",6)};
        Items field_Item[]={new Items("soccer ball" ,6)};
        Items  parking_lot_Item[] ={new Items("Honda Civic:"+"Its your car...",6)};
        
        
        // create the rooms with the items that are in them.
        outside = addItemsToRoom(outside,outside_Item);
        theater = addItemsToRoom(theater,theater_Item);
        pub  = addItemsToRoom(pub, pub_Item);
        lab  = addItemsToRoom(lab, lab_Item);
        office = addItemsToRoom(office,office_Item);
        kitchen = addItemsToRoom(kitchen,kitchen_Item);
        dining_hall = addItemsToRoom(dining_hall,dining_hall_Item);
        bathroom = addItemsToRoom(bathroom,bathroom_Item);
        game_room = addItemsToRoom(game_room,game_room_Item);
        lounge = addItemsToRoom(lounge,lounge_Item);
        gym = addItemsToRoom(gym,gym_Item);
        coffee_shop = addItemsToRoom(coffee_shop,coffee_shop_Item);
        field = addItemsToRoom(field,field_Item);
        parking_lot = addItemsToRoom(parking_lot,parking_lot_Item);
        
        
        
        
        
        // initialise room exits
        outside.setExit("east", theater);
        outside.setExit("south", lab);
        outside.setExit("west", pub);

        theater.setExit("west", outside);

        pub.setExit("east", outside);

        lab.setExit("north", outside);
        
        lab.setExit("east", office);

        office.setExit("west", lab);
        
        kitchen.setExit("east", dining_hall);
        
        dining_hall.setExit("north", field);
        
        bathroom.setExit("west", coffee_shop);
        
        game_room.setExit("south",lounge);
        
        lounge.setExit("south", parking_lot);
        
        gym.setExit("east", parking_lot);
        
        coffee_shop.setExit("south", outside);
        
        field.setExit("north", parking_lot);
        
        parking_lot.setExit("west", outside);
        
        currentRoom = outside;  // start game outside
        
        previousRoom= null; // initializes the previous room
    }
    
   
    /**
     * 
     * Add the items to the room
     */
    private Room addItemsToRoom(Room room, Items items[]){
     for(int i =0;i<items.length; ++i){
         room.addItems(items[i]);
         
        }
       return room; 
        
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
        System.out.println("Thank you for playing.  Good bye.");
    }

    /**
     * Print out the opening message for the player.
     */
    private void printWelcome()
    {
        System.out.println();
        System.out.println("Welcome to the World of Zuul!");
        System.out.println("World of Zuul is a new, incredibly boring adventure game.");
        System.out.println("Type '" + CommandWord.HELP + "' if you need help.");
        System.out.println();
        System.out.println(currentRoom.getLongDescription());
    }

    /**
     * Given a command, process (that is: execute) the command.
     * @param command The command to be processed.
     * @return true If the command ends the game, false otherwise.
     */
    private boolean processCommand(Command command) 
    {
        boolean wantToQuit = false;

        CommandWord commandWord = command.getCommandWord();

        switch (commandWord) {
            case UNKNOWN:
                System.out.println("I don't know what you mean...");
                break;

            case HELP:
                printHelp();
                break;

            case GO:
                goRoom(command);
                break;

            case QUIT:
                wantToQuit = quit(command);
                break;
                
            case LOOK:
                look();
                break;
                
            case EAT:
                eat();
                break;
                
            case BACK:
                backRoom();
                break;
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
        System.out.println("You are lost. You are alone. You wander");
        System.out.println("around at the university.");
        System.out.println();
        System.out.println("Your command words are:");
        parser.showCommands();
    }

    /** 
     * Try to go in one direction. If there is an exit, enter the new
     * room, otherwise print an error message.
     */
    private void goRoom(Command command) 
    {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("Go where?");
            return;
        }

        String direction = command.getSecondWord();

        // Try to leave current room.
        Room nextRoom = currentRoom.getExit(direction);

        if (nextRoom == null) {
            System.out.println("There is no door!");
        }
        else {
            previousRoom =currentRoom;
            currentRoom = nextRoom;
            System.out.println(currentRoom.getLongDescription());
        }
    }
    
    
    private void backRoom()
    {
        currentRoom=previousRoom;
        
        
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
    
    /**
     * Look will print the description of the room.
     * 
     */
    private void look(){
    System.out.println(currentRoom.getLongDescription());    
        
        
    }
    
    /**
     * Look will print the description of the room.
     * 
     */
    private void eat(){
    System.out.println("I'm hungry, is there a Chick-fil-A nearby?");    
    System.out.println("(NOm-NOm-NOm)");    
    System.out.println("It wasn't Chick-fil-A but it was pretty good.");    
    }
}
