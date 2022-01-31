package sentimentanalysis;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Tweet class dfor storing tweet information
 */
public class Tweet extends AbstractTweet{
    public Tweet ()
    {

    }
  /**
   * This constructor will take a line of csv file as input, separate the data fields use them to set the parameters.
   * For optimization purpose, we proceed the data on the fly.
   * It calls other methods to make decision about positive/negative polarity, and output the result for each tweet.
   *
   * @param info [String] each line string read from csv file
   */
  public Tweet (String info) {
      // CSV splits data with comma but it is also possible that there are commas in the text.
      // Thus we need to use (",") as the separator, not only (,)
      String[] info_Fields = info.split("\",\"");

      // Each data field in csv is surrounded with "", but most of them have been removed.
      // Now we need to deal with the first and last field which are special:
      // The first field has a redundant " as the first character, and the last field has one at the end.
      for (int i = 0; i < info_Fields.length; i++) {
          if (i == 0) {
              // Remove the first character of the first field.
              info_Fields[i] = info_Fields[i].substring(1, info_Fields[i].length());
          } else if (i == 5) {
              // Remove the last character of the last field.
              info_Fields[i] = info_Fields[i].substring(0, info_Fields[i].length()-1);
          } else {
              // Do not need to do anything for other fields.
              continue;
          }
      }


      target = Integer.parseInt(info_Fields[0]);
      id = Integer.parseInt(info_Fields[1]);
      DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEE MMM dd HH:mm:ss zzz yyyy");
      date = LocalDate.parse(info_Fields[2], formatter);
      flag = info_Fields[3];
      user = info_Fields[4];
      text = info_Fields[5];
    }

  /**
   *
   * @param target
   * @param id
   * @param date
   * @param flag
   * @param user
   * @param text
   */
  public Tweet(int target, int id, LocalDate date, String flag, String user, String text) {
      this.target = target;
      this.id = id;
      this.date = date;
      this.flag = flag;
      this.user = user;
      this.text = text;
      this.predictedPolarity = 2;
  }

  /**
   *
   * @return
   */
  public int getTarget() {
      return target;
  }

  /**
   *
   * @return
   */
  public int getId() {
      return id;
  }

  /**
   *
   * @return
   */

  public LocalDate getDate() {
      return date;
  }

  /**
   *
   * @return
   */
  public String getFlag() {
      return flag;
  }

  /**
   *
   * @return
   */
  public String getUser() {
      return user;
  }

  /**
   *
   * @return
   */
  public String getText() {
      return text;
  }

  /**
   *
   * @return
   */
  public int getPredictedPolarity() {
      return predictedPolarity;
  }

  /**
   *
   * @param predictedPolarity
   */
  public void setPredictedPolarity(int predictedPolarity) {
      this.predictedPolarity = predictedPolarity;
  }

  /**
   *
   * @return boolean value if the prediction matches the ground truth
   */
  public boolean truePrediction(){
    return this.target == this.predictedPolarity;
  }

  /**
   * @return formated output
   */
   public String toString() {
      String myEol = System.getProperty("line.separator");
      String myTweet = "Tweet: " + this.text + myEol;
      String myLabel = "Real label: " + this.target + "Predicted label: " + this.predictedPolarity + myEol;
      return String.format( myTweet + myLabel);
  }

    /**
     * The polarity of the tweet (0 = negative, 2 = neutral, 4 = positive).
     */
    private final int target;
    /**
     * The id of the tweet (e.g. 2087).
     */
    private final int id;
    /**
     *  The LocalDate of the tweet (Sat May 16 23:58:44 UTC 2009).
     */
    private final LocalDate date;

    /**
     *  Flag
     */
    private final String flag;
    /**
     *  The user that tweeted (robotickilldozr).
     */
    private final String user;
    /**
     *  The text of the tweet.
     */
    private final String text;

    /**
     * The predicted polarity of the tweet (0 = negative, 2 = neutral (default value), 4 = positive).
     */
    private int predictedPolarity;
  }
