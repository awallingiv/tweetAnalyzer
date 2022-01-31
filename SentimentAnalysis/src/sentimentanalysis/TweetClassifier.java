package sentimentanalysis;

import java.io.File;
import java.io.FileReader;
import java.util.HashSet;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.FileNotFoundException;

/**
 * Class that supports Tweet Classification Mechanism
 * Dictionary of positive words, negative words and all tweets are kept in this class.
 */
public class TweetClassifier{

  /**
    * constructor
    * @param posPath path to file containing positive words
    * @param negPath path to file containing negative words
    */
  public TweetClassifier(String posPath, String negPath){

    String readInLine;
    FileReader fileReader;
    Scanner inFile;

      try{

        fileReader= new FileReader(new File(posPath));
        inFile = new Scanner(fileReader);
        int counter = 0;
        while (inFile.hasNextLine()) {
          readInLine = inFile.nextLine();
          if (!readInLine.startsWith(";") && !readInLine.isEmpty()) {
            positiveWords.add(readInLine);
            counter++;
          }
        }
        System.out.println("Done. " + counter + " positive words imported.\n");
        inFile.close();
      } catch (FileNotFoundException e){
        System.out.println("positive file not found, exiting ..\n");
      } //done with positiveWords

      try{

        fileReader = new FileReader(new File(negPath));
        inFile = new Scanner(fileReader);
        int counter = 0;
        while (inFile.hasNextLine()) {
          readInLine = inFile.nextLine();
          if (!readInLine.startsWith(";") && !readInLine.isEmpty()) {
            negativeWords.add(readInLine);
            counter++;
          }
        }
        System.out.println("Done. " + counter + " negative words imported.\n");
        inFile.close();
      } catch (FileNotFoundException e){
        System.out.println("positive file not found, exiting ..\n");
      }

        //object analysis board set to 0
        this.counter = 0;
        this.correct = 0;
        this.incorrect = 0;
        this.precision = 0;
     } //end constructor

     /**
       * @param twPath string path to twitter file to be classified 
       */
     public void classifyFile(String twPath){

       this.correct = 0;
       this.incorrect = 0;
       FileReader fileReader;
       Scanner inFile;
       String readInLine;

       try{
         fileReader = new FileReader(new File(twPath));
         inFile = new Scanner(fileReader);
         int counter = 0;
         while (inFile.hasNextLine()) {
           readInLine = inFile.nextLine();
           Tweet tweetTemp = new Tweet(readInLine);
           int score = this.polarityClassify(tweetTemp.getText());
           tweetTemp.setPredictedPolarity(score);
           //System.out.println(tweetTemp);
           this.tweetList.add(tweetTemp);
           if (tweetTemp.truePrediction()){
           this.correct ++;
         } else {
           this.incorrect ++;
         }
         counter++;
       }
       this.precision = (double) correct / ((double) incorrect + (double) correct) * 100;
       inFile.close();
     } catch (FileNotFoundException e){
       System.out.println("Corruped Tweet File, exiting ...");
     }
   }//end method

   public void printResults(boolean longList){
      if (longList) {
         for(Tweet t: this.tweetList){
            System.out.println(t);
          }
        }
        System.out.println("Correctly classified tweets: " + this.correct);
        System.out.println("Wrongly classified tweets: " + this.incorrect);
        System.out.println("Correct prediction rate: " + String.format("%.2f", this.precision) + "%");

      }//end printResults


    /**
     * This method check each word of the text and make judgement whether it is positive or negative based on
     * the dictionary. It counts the overall number of all positive, negative and neutral words in the tweet, and
     * then classify the tweet based on the three counters. Then it update the result in MainApp.
     * @return integer value of polarity (0,2,4)
     */
    public int polarityClassify(String inText) {

        int counterNegative = 0;
        int counterPositive = 0;
        int counterNeutral = 0;

        // Remove special characters in text and change it into lower cases.
        String text_Processed = inText.replaceAll("\\p{Punct}","").toLowerCase();
        // Split the text into word units.
        String[] words = text_Processed.split(" ");

        // Make judgement whether each word is positive, negative or neutral, and update the counter accordingly.
        for (String word : words) {
            if (positiveWords.contains(word)) {
                counterPositive++;
            } else if (negativeWords.contains(word)) {
                counterNegative++;
            } else {
                counterNeutral++;
            }
        }

        // TODO: current classification is based on the requirement. We may need to change it in the future.
        if (counterNegative == 0 && counterPositive == 0) {
            // Redundant code block: Neutral(2) is the default value.
            // Still I put it here in case we need to change the way of classification in the future.
            return 2; // Neutral
        } else if (counterPositive > counterNegative) {
            return 4; //Positive
        } else {
            return 0; //Negative
        }

    } //end polarity_Classify

    private int counter = 0;
    private int correct = 0;
    private int incorrect = 0;
    private double precision = 0.0;

    protected ArrayList<Tweet> tweetList = new ArrayList<Tweet>();
    /**
     * Positive word dictionary. Import from file.
     */
    protected HashSet<String> positiveWords =  new HashSet<String>();
    /**
     * Negative word dictionary. Import from file.
     */
    protected HashSet<String> negativeWords = new HashSet<String>();
}
