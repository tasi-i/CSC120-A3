import java.util.*;

class Conversation implements ConversationRequirements {

  // Attributes
  // Responses possible weeee
  private static final String[] cannedResponses = {
      "Please tell me more.",
      "Intresting go on.",
      "I see.",
      "I'm not sure I understand you fully.",
      "What does that suggest to you?",
      "Do you feel strongly about discussing such things?"
  };

  // Map for mirroring
  private static final Map<String, String> mirrorMap = new HashMap<>();
  static {
    mirrorMap.put("I", "you");
    mirrorMap.put("me", "you");
    mirrorMap.put("am", "are");
    mirrorMap.put("you", "I");
    mirrorMap.put("your", "my");
    mirrorMap.put("my", "your");
  }
  // trnascript
  private final List<String> transcript = new ArrayList<>();

  // number of rounds
  private int rounds = 0;

  /**
   * Constructor
   */
  Conversation() {

  }

  /**
   * Starts and runs the conversation with the user
   */
  public void chat() {
    Scanner sc = new Scanner(System.in);

    System.out.print("How many rounds? ");
    try {
      rounds = Integer.parseInt(sc.nextLine());
    } catch (NumberFormatException e) {
      rounds = 1;
      System.out.println("Invalid input. Defaulting to 1 round.");
    }

    System.out.println("Hi there! What's on your mind?");
    transcript.add("Chatbot: Hi there! What's on your mind?");

    for (

        int i = 0; i < rounds; i++) {
      System.out.print("You: ");
      String userInput = sc.nextLine();
      transcript.add("User: " + userInput);
      String response = respond(userInput);
      System.out.println("Chatbot: " + response);
      transcript.add("Chatbot: " + response);
    }
    System.out.println("Chatbot: Goodbye!");
    transcript.add("Chatbot: Goodbye!");
  }

  /**
   * Prints transcript of conversation
   */
  public void printTranscript() {
    System.out.println("\n--- Conversation Transcript ---");
    for (String line : transcript) {
      System.out.println(line);
    }
    System.out.println("--- End of Transcript ---");
  }

  /**
   * Gives response (mirrored or canned) to user input
   * 
   * @param inputString the users last line of input
   * @return mirrored or canned response to user input
   */
  public String respond(String inputString) {
    String[] words = inputString.split("\\s+");
    boolean mirrored = false;

    for (int i = 0; i < words.length; i++) {
      String word = words[i];
      String cleanWord = word.replaceAll("[^a-zA-Z]", ""); // remove punctuation
      String replacement = null;

      // Case-insensitive check in mirrorMap
      for (String key : mirrorMap.keySet()) {
        if (key.equalsIgnoreCase(cleanWord)) {
          replacement = mirrorMap.get(key);
          mirrored = true;
          break;
        }
      }

      if (replacement != null) {
        // preserve capitalization if first letter was uppercase
        if (Character.isUpperCase(word.charAt(0))) {
          replacement = replacement.substring(0, 1).toUpperCase() + replacement.substring(1);
        }
        words[i] = replacement;
      }
    }

    if (mirrored) {
      return String.join(" ", words);
    } else {
      Random rand = new Random();
      return cannedResponses[rand.nextInt(cannedResponses.length)];
    }
  }

  // main methods to run converstaionn

  public static void main(String[] arguments) {

    Conversation myConversation = new Conversation();
    myConversation.chat();
    myConversation.printTranscript();

  }
}
