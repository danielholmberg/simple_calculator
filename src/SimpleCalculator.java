import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class SimpleCalculator {

  static CalculatorGraph graph = new CalculatorGraph();
  static boolean quit = false;
  static String invalidCommandStr = "Invalid command, please try again\n";
  static String nonAlphaNumericStr = "Input needs to be Alphanumeric";
  final static int invalidChoice = 0;
  final static int commandLineChoice = 1;
  final static int fileChoice = 2;
  final static int quitChoice = 3;

  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);

    int choice;
    do {
      choice = menu(scanner);
    } while(choice == invalidChoice);

    if(choice == commandLineChoice) {
      System.out.println(
              " _______VALID INPUT FORMAT_______" + "\n" +
              "* <register> <operation> <value> *" + "\n" +
              "* print <register>               *" + "\n" +
              "* quit                           *" + "\n" +
              " --------------------------------"
      );
    }

    //
    do {
      if(choice == commandLineChoice) {
        System.out.print(">>> ");
        handleStandardInput(scanner.nextLine());
      } else if(choice == fileChoice) {
        handleFileInput(scanner);
      } else {
        quit = true;
      }
    } while (!quit);

    scanner.close();
  }

  /**
   * Shows start-up menu for interactive selection of input option.
   *
   * @param scanner
   * @return valid choice integer
   */
  private static int menu(Scanner scanner) {
    int choice;

    do {
      System.out.println("-------------------------");
      System.out.println("Simple Calculator (by Daniel Holmberg)");
      System.out.println("-------------------------");
      System.out.println("1 - Use command-line");
      System.out.println("2 - Use a file");
      System.out.println("3 - Quit");
      System.out.print(">>> ");

      while(!scanner.hasNextInt()) {
        scanner.nextLine(); // Skip any non-integer input
      }

      choice = scanner.nextInt();
      scanner.nextLine(); // Skip '\n' Enter key
    } while(choice != commandLineChoice && choice != fileChoice && choice != quitChoice);

    return choice;
  }

  /**
   * Parses the file content located at the file-path provided by the user,
   * and performs corresponding action for each line in that file.
   *
   * @param scanner
   */
  private static void handleFileInput(Scanner scanner) {
    boolean existingFile = false;
    do {
      System.out.println("Enter file-path (e.g. test/test.txt)");
      System.out.print(">>> ");

      String filePath = scanner.nextLine();
      File file = new File(filePath);
      try {
        Scanner fileScanner = new Scanner(file);

        while (fileScanner.hasNextLine()) {
          handleStandardInput(fileScanner.nextLine());
        }
        existingFile = true;

        fileScanner.close();
        quit = true;
      }
      catch (FileNotFoundException e) {
        if(filePath.toLowerCase().equals("quit")) quit = true;
        else System.out.println("'" + filePath + "'" + " doesn't point to an existing file!");
      }

    } while (!existingFile && !quit);
  }

  /**
   * Parses the input command and performs actions based on valid input types:
   * 1. <register> <operation> <value>
   * 2. print <register>
   * 3. quit
   *
   * @param input
   */
  private static void handleStandardInput(String input) {
    // Greedy matcher for whitespaces and tabs.
    // Adding .trim() to 'input' String to handle leading and trailing whitespaces.
    String whitespaceRegex = "\\s+";
    String[] inputArgs = input.trim().toLowerCase().split(whitespaceRegex);

    // RegEx to only allow Alphanumeric characters (and negative numbers "-?")
    String regex = "^[a-zA-Z0-9]+$";
    boolean isAlphanumeric = input.replaceAll(" ", "").matches(regex);

    if(isAlphanumeric) {
      int inputItemsLength = inputArgs.length;

      switch (inputItemsLength) {
        case 1:
          quit = inputArgs[0].equals("quit");
          if(!quit) System.out.println("Did you mean 'quit'?" + "\n");
          break;
        case 2:
          boolean print = inputArgs[0].equals("print");
          if(print) handlePrintRegister(inputArgs); // print <register>
          else System.out.println("Did you mean 'print <register>'?" + "\n");
          break;
        case 3:
          handleRegisterOperationValue(inputArgs); // <register> <operation> <value>
          break;
        default:
          System.out.println(invalidCommandStr);
      }
    } else {
      System.out.println(nonAlphaNumericStr);
    }
  }

  /**
   * Parses the <operation> parameter and performs the following actions if it is a supported operation:
   * 1. <register> <operation> <value>
   *     Adding an <OperationEdge> with a <Weight> of <operation> & <value> pointing at the <register>
   * 2. <register> <operation> <otherRegister>
   *     Adding an <OperationEge> with a <Weight> of <operation> pointing at the <otherRegister>
   *
   * @param inputArgs - Consisting of [<register>, <operation>, <value> or <otherRegister>]
   */
  public static void handleRegisterOperationValue(String[] inputArgs) {
    String registerParam = inputArgs[0];
    String operationParam = inputArgs[1];
    String valueParam = inputArgs[2];

    //System.out.println("input: " + Arrays.toString(inputArgs));

    OperationType type = graph.parseOperationType(operationParam);

    boolean valueInputIsNumeric = valueParam.matches("-?\\d+(\\.\\d+)?");

    if(!type.equals(OperationType.UNDEFINED)) {
      RegisterNode registerNode = graph.getRegisterNode(registerParam, true);

      if(valueInputIsNumeric) {
        Weight weight = new Weight(type, Double.parseDouble(valueParam));
        graph.addEdge(registerNode, registerNode, weight);
      } else {
        Weight weight = new Weight(type);
        RegisterNode otherRegisterNode = graph.getRegisterNode(valueParam, true);

        if(!graph.hasEdge(otherRegisterNode, registerNode)) {
          graph.addEdge(registerNode, otherRegisterNode, weight);
        } else {
          System.out.println(
                  "(" + valueParam + ")" +
                  " already has a relationship <edge> to " +
                  "(" + registerParam + ")"
          );
        }
      }
    } else {
      System.out.println("<" + operationParam + ">" + " is not a supported <operation>!\n");
    }
  }

  /**
   * Lazy evaluates the incoming <register> value and prints it to console.
   *
   * @param inputArgs - Consisting of [print, <register>];
   */
  public static void handlePrintRegister(String[] inputArgs) {
    String registerParam = inputArgs[1];
    RegisterNode registerNode = graph.getRegisterNode(registerParam, false);

    if(registerNode != null) {
      System.out.println(graph.calculateValue(registerNode));
      graph.resetDepthFirstSearch();
    } else {
      System.out.println("(" + registerParam + ")" + " is not yet a register!");
    }
  }
}