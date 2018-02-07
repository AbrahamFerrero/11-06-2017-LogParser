
/**
 * This is the class which we are working on. 
 */

import java.util.*;
import edu.duke.*;

public class LogAnalyzer
{
     private ArrayList<LogEntry> records;
     //private HashMap<String,ArrayList<String>> datesIpMap;
     int index;
     
     public LogAnalyzer() {
         records = new ArrayList<LogEntry>();
         //datesIpMap = new HashMap<String,ArrayList<String>>();
     }
        
     public void readFile(String filename) {
         FileResource f = new FileResource(filename);
        for (String s : f.lines()){
            LogEntry entry = WebLogParser.parseEntry(s);
            records.add(entry);
        }
     }
        
     public void printAll() {
         for (LogEntry le : records) {
             System.out.println(le);
         }
     }
     
     public int countUniqueIPs(){
         ArrayList<String> uniqueIPs = new ArrayList<String>();
         for (LogEntry le : records){
             //Check every entry, if the ip is not in our new array, add it, then return the size.
             String ipAddr = le.getIpAddress();
             if (!uniqueIPs.contains(ipAddr)){
                 uniqueIPs.add(ipAddr);
             }
         }
         return uniqueIPs.size();
     }
     
     public void printAllHigherThanNum(int num){
         for (LogEntry le : records){
             //Check the entries, if the code is bigger than the num as a parameter, print it. Very simple.
             int statusCode = le.getStatusCode();
             if (statusCode > num){
                System.out.println(le); 
             }
         }
     }
     
     public ArrayList uniqueIPVisitsOnDay(String someday){
        /* This method returns a new ArrayList with a number of items as long as the date included as a para
         *  meter matches the date in the logEntry, and it does not include the logEntry if the ip has already
         *  been included that day (if the ip user already visited the webpage that date). For mor info, please
         *  read the readme.txt file.
         * It may result easier to call somehow the countUniqueIPs method instead of copypasting code
         * but as we are not interested in the int variable returned on that method, we just started 
         * the arrayList again and created a new one for matching dates and unique IPs.
         * I am also sure that we could have used a HashMap, but as we see here, it is not necessary at all.
         */
        ArrayList<String> uniqueIPs = new ArrayList<String>(); 
        ArrayList<String> uniqueIPsDates = new ArrayList<String>(); 
         for (LogEntry le : records){
             Date d = le.getAccessTime();
             String str = d.toString();
             String subStr = str.substring(4,10); 
             String ipAddr = le.getIpAddress();
             if(subStr.equals(someday) && !uniqueIPs.contains(ipAddr)){
                 uniqueIPs.add(ipAddr);
                 uniqueIPsDates.add(subStr);
                 //it works
             }
        }
        return uniqueIPs;
    }
    
    public int countUniqueIPsInRange(int low, int high){
        ArrayList<String> uniqueIPs = new ArrayList<String>(); 
        ArrayList<Integer> uniqueIPsStatus = new ArrayList<Integer>();
        for (LogEntry le : records){
           int status = le.getStatusCode();
           String ipAddr = le.getIpAddress();
           if (status >= low && status <= high && !uniqueIPs.contains(ipAddr)){
               uniqueIPs.add(ipAddr);
               uniqueIPsStatus.add(status);
            }
        }
        return uniqueIPsStatus.size();
    }
    //Assignment 3 exercises start here:
    public HashMap countVisitsPerIP(){
        HashMap<String,Integer> counts = new HashMap<String,Integer>();
        for(LogEntry le: records){
            String ip = le.getIpAddress();
            if(!counts.containsKey(ip)){
                counts.put(ip,1);
            }
            else {
                counts.put(ip,counts.get(ip)+1);
            }
        }
        return counts;
    }
    
    public int mostNumberVisitsByIP(HashMap<String, Integer> counts){
        index = 0;
        for (int num : counts.values()){
            if (index < num){
                index = num;
            }
        }
        return index;
    }
    
    public ArrayList<String> iPsMostVisits(HashMap<String, Integer> counts){
        //index gets the value from the last method in the tester:
        ArrayList<String> maxIPs = new ArrayList<String>();
        for (String s : counts.keySet()){
            if (counts.get(s) == index){
                maxIPs.add(s);
            }
        }
        return maxIPs;
    }
    
    public HashMap<String, ArrayList<String>> iPsForDays(){
        HashMap<String,ArrayList<String>> daysIps = new HashMap<String,ArrayList<String>>();
        
        for(LogEntry le: records){
            //We will create a substring of date to be represented in a string and in the format demanded:
            ArrayList<String> date = new ArrayList<String>();
            String ip = le.getIpAddress();
            Date d = le.getAccessTime();
            String str = d.toString();
            String dateStr = str.substring(4,10); 
            //If the ip is not in the hashmap, add it, and add the date to the arraylist that is the value to the key::
            if(!daysIps.containsKey(ip)){
                date.add(dateStr);
                daysIps.put(ip,date);
                
            }
            //If the ip is in the hashmap, and date is not in our array value, add the date to that arraylist
            else{
                //To add the date properly, we locate it in the array and add it from there:
                date = daysIps.get(ip);
                if (!date.contains(dateStr)){
                    date.add(dateStr);
                }
            }
        }
        return daysIps;
    }
    
    public HashMap<String, ArrayList<String>> dayCountHash(){
        /*This hashmap will be use for the following two methods, so it is important to
         * develop it separately:*/
         HashMap<String,ArrayList<String>> datesIpMap = new HashMap<String,ArrayList<String>>();
        for(LogEntry le: records){
            ArrayList<String>ipArray = new ArrayList<String>();
            String ip = le.getIpAddress();
            Date d = le.getAccessTime();
            String str = d.toString();
            String dateStr = str.substring(4,10); 
            if(!datesIpMap.containsKey(dateStr)){
                ipArray.add(ip);
                datesIpMap.put(dateStr,ipArray);
            }
            else{
                ipArray = datesIpMap.get(dateStr);
                if (!ipArray.contains(ip)){
                    ipArray.add(ip);
                }
            }
        }
        return datesIpMap;
    }
    
    public String dayWithMostIPVisits(HashMap<String, ArrayList<String>> map){
        /*I am sure there is a better way to do this, but this one works anyway:
         * First we calculate the bigger arraylist, the one with the biggest number of ips,
         * and then, we return the first key of which Arraylist is as big as the maximum index
         * and that gives you the biggest. If there is a tie, it returns the first one found, which
         * is valid according to the exercise. 
         */
        int indexMap = 0;
        
        for (ArrayList s: map.values()){
            if(indexMap < s.size()){
                indexMap = s.size();
            }
        }
        for(String s: map.keySet()){
            ArrayList ips = map.get(s);
            if(indexMap == ips.size()){
                return s;
            }
        }
        return null; 
    }
    
    public ArrayList<String> iPsWithMostVisitsOnDay(HashMap<String, ArrayList<String>> map, String day){
        /*This method takes the hashmap from dayCountHash() method, iterates over the keys
         * and if the key is the string we insert in the tester method, it returns the arraylist
         * of ips that visited the site that day. 
         */
        System.out.println("The " + day + ", this IPs visited our website: ");
        for (String s: map.keySet()){
            if (s.contains(day)){
                return map.get(s);
            }
        }
        return null;
    }
}
