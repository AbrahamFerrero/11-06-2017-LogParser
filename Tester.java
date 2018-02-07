
/**
 *
 * This class has been given by the exercise. I just completed the testLogAnalyzer() method
 * 
 * @author (Abraham Ferrero) 
 * @version (06/NOV/2017)
 */

import java.util.*;

public class Tester
{
    public void testLogEntry() {
        LogEntry le = new LogEntry("1.2.3.4", new Date(), "example request", 200, 500);
        System.out.println(le);
        LogEntry le2 = new LogEntry("1.2.100.4", new Date(), "example request 2", 300, 400);
        System.out.println(le2);
    }
    
    public void testLogAnalyzer() {
        LogAnalyzer read = new LogAnalyzer();
        read.readFile("weblog3-short_log");
        read.printAll();
    }
    
    public void testUniqueIP(){
        LogAnalyzer read = new LogAnalyzer();
        read.readFile("weblog2_log");
        System.out.println("There are " + read.countUniqueIPs()+ " different IPs");
    }
    
    public void testStatusCodeHigherThanNum(){
        LogAnalyzer read = new LogAnalyzer();
        read.readFile("weblog1_log");
        read.printAllHigherThanNum(400);
    }
    
    public void testUniqueIPVisitsOnDay(){
        LogAnalyzer read = new LogAnalyzer();
        read.readFile("weblog2_log");
        read.countUniqueIPs();
        ArrayList a = read.uniqueIPVisitsOnDay("Sep 24");
        System.out.println(a.size());
    }
    
    public void testCountUniqueIPsInRange(){
        LogAnalyzer read = new LogAnalyzer();
        read.readFile("weblog2_log");
        System.out.println(read.countUniqueIPsInRange(200,299));
    }
    
    //Assignment 3 testers:
    public void testAssignment3(){
        LogAnalyzer read = new LogAnalyzer();
        read.readFile("weblog2_log");
        HashMap<String,Integer> counts = read.countVisitsPerIP();
        System.out.println(counts);
        System.out.println("The ip with more visits visited the site " + read.mostNumberVisitsByIP(counts)+ " times.");
        System.out.print("These are the ip's with more visits: ");
        System.out.println(read.iPsMostVisits(counts));
        HashMap<String,ArrayList<String>> daysIps = read.iPsForDays();
        System.out.println(daysIps);
        HashMap<String,ArrayList<String>> datesIpMap = read.dayCountHash();
        System.out.println("Day with most different IP visits: " + read.dayWithMostIPVisits(datesIpMap));
        System.out.println(read.iPsWithMostVisitsOnDay(datesIpMap, "Sep 30"));
    }
}
