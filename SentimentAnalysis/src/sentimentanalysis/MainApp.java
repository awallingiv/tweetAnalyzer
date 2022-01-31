package sentimentanalysis;

import java.io.File;
import java.io.FileReader;
import java.util.HashSet;
import java.util.Scanner;

/**
 * MainApp interface for tweet classificaiton based on counting
 *
 */
public class MainApp{

  /**
    * This method will begin the user interface console.
    * It reads positive and negative word files with user's input of the path.
    * Then it reads and process the tweets file.
    *
    * Finally it outputs the number of correctly and wrongly classified tweets.
    *
    * @param args This program expects no command line arguments.
    * @throws Exception Exceptions may happen when reading from the file. They should have been handled though.
    */
    public static void main(String[] args) throws Exception {

      Scanner in = new Scanner(System.in);

      //first pass
      System.out.println("Please input absolute or relative path to positive words file.");
      System.out.println("(Example: Data/positive-words.txt)");
      String posPath = in.nextLine();
      File posFile = new File(posPath);

      System.out.println("Please input absolute or relative path to negative words file.");
      System.out.println("(Example: Data/negative-words.txt)");
      String negPath = in.nextLine();
      File negFile = new File(negPath);

      System.out.println("Please input absolute or relative path path to tweet csv file.");
      System.out.println("(Example: Data/testdata.manual.2009.06.14.csv)");
      String twPath = in.nextLine();
      File tweetFile = new File(twPath);

      System.out.println("Do you want a detailed print out of tweets and classifiers");
      System.out.println("yes: true   no: false");
      String prList = in.nextLine();
      boolean longList = false;
      if (prList.equalsIgnoreCase("true") || prList.equalsIgnoreCase("false")) {
        longList = Boolean.valueOf(prList);
      } else {
        // throw some exception
        System.out.println("Value not recognized, supressing detailed printing");
      }

      //in case something goes wrong, give the user more chances
      int counter = 0;
      while ((!posFile.exists()) && (counter <3)) {
        System.out.println("Positive file path is wrong, try again");
        System.out.println("(Example: C:\\Doc\\positive-words.txt)");
        posPath = in.nextLine();
        posFile = new File(posPath);
        counter++;
      }

      counter = 0;
      while ((!negFile.exists()) && (counter <3)) {
        System.out.println("Negative file path is wrong, try again");
        System.out.println("(Example: C:\\Doc\\negative-words.txt)");
        negPath = in.nextLine();
        negFile = new File(negPath);
        counter++;
      }

      counter = 0;
      while ((!tweetFile.exists()) && (counter <3)) {
        System.out.println("CSV file not found, please try again");
        twPath = in.nextLine();
        tweetFile = new File(twPath);
        counter++;
      }

      //if all files needed exist, proceed with processing
      if (posFile.exists()&&negFile.exists()&&tweetFile.exists()){

        //initialize classifier with positive and negative files
        TweetClassifier tweetClassifier = new TweetClassifier(posPath,negPath);
        //classify input tweets using TweetClassifier
        tweetClassifier.classifyFile(twPath);
        tweetClassifier.printResults(longList);
        
      } else if (!posFile.exists()){
        System.out.println("Positive file not found, exiting");
      } else if (!negFile.exists()){
        System.out.println("Negative file not found, exiting");
      } else if (!tweetFile.exists()){
        System.out.println("Tweet file not found, exiting");
      }

  }//end mail
}//end MainApp
