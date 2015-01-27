import org.apache.commons.cli.*;

public class KeyPass {
    
    private Options options;
    private CommandLine line;
    
    public KeyPass() {
        options = new Options();
        options.addOption(OptionBuilder.withLongOpt("add").withDescription("Add password to storage").create());
        
        options.addOption(OptionBuilder.withLongOpt("get").withDescription("Get password from storage").create());
        
        options.addOption(OptionBuilder.withLongOpt("update").withDescription("Update password in storage").create());
        
        options.addOption(OptionBuilder.withLongOpt("remove").withDescription("Remove password from storage").create());
        
        options.addOption(OptionBuilder.withLongOpt("show").withDescription("Show passwords in storage").create());
        
        options.addOption(OptionBuilder.withLongOpt("help").withDescription("Print Help").create("h"));
    }
    
    public void parse(String[] args) throws ParseException {
 
        CommandLineParser parser = new PosixParser();
        line = parser.parse(options, args);
        //System.out.println(line);
        if (line.hasOption("add")) {
            ConsolePasswordManager cpm = new ConsolePasswordManager();
            cpm.init();
            cpm.add();
        } else if (line.hasOption("get")) {
            ConsolePasswordManager cpm = new ConsolePasswordManager();
            cpm.init();
            cpm.get();
        } else if (line.hasOption("update")) {
            ConsolePasswordManager cpm = new ConsolePasswordManager();
            cpm.init();
            cpm.update();
        } else if (line.hasOption("remove")) {
            ConsolePasswordManager cpm = new ConsolePasswordManager();
            cpm.init();
            cpm.remove();
        } else if (line.hasOption("show")) {
            ConsolePasswordManager cpm = new ConsolePasswordManager();
            cpm.init();
            cpm.showNames();
        } else {
            this.printKeyPassHelp();
        }
    }
    
    public void printKeyPassHelp() {
        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp("KeyPass", "Read following instructions for using KeyPass", options, "Developed by Nikelangelo");
    }
    
    public static void main(String[] args) {
        KeyPass keyPass = new KeyPass();
        try {
            keyPass.parse(args);
        } catch (ParseException e) {
            System.out.println("Wrong parsing of command line");
        }
    }
}
