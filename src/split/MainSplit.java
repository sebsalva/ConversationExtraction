package split;
import java.io.File;




import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;

import objetsconversations.ConversationSet;
import objetsconversations.Event;
import parralelisation.*;
import metrique.*;
import main.*;

/**
 * @author Blot Elliott
 *
 */

public class MainSplit {

	public static String log;
	public static String output;
	public static boolean timerMode;
	public static String mode;

	public static HashMap<String, Double> means;
	public static Regex regex;
	public static Trace logOrigin;
	public static MetriquePersonnel metrique;
	public static double interval = 5000.0;//in milliseconds 
	//public static double fact = 10.0;

	/** 
	public static Dependency Dep;
	*/
	public static void main(String[] args, sauvegarde sauv) {
		//parse the log
		final long timebuildingTraces1 = System.currentTimeMillis();
		means = new HashMap<String, Double>();
		boolean reg = true;
		
		if (Arrays.stream(args).anyMatch("-r"::equals)) {
			try {
				MapperOptions.setOptions(args);
			} catch (Exception e) {
				System.err.println("pb option");
				System.exit(3);
			}
		}
		else {
			
			try {
				MapperOptions.setOptionsNoRegex(args);
				reg=false;
			} catch (Exception e) {
				System.err.println("pb option");
				System.exit(3);
			}
		}
		
		
		//coupeurfichiertest.coupeurfichier(new File(log));
		
		
		
		final long timebuildingTraces2 = System.currentTimeMillis();

		ConversationSet ensemble = new ConversationSet();

        ThreadExecutor threadpool= new ThreadExecutor();
        if(reg) {
			Trace trace = new Trace(new File(log),regex);
			Task tache=new Task("u",ensemble,0,false,trace, threadpool,regex, sauv);
			threadpool.SubmitTask(tache);
		}
		else {
			Trace trace = new Trace(new File(log));
			Task tache=new Task("u",ensemble,0,false,trace, threadpool,new Regex(), sauv);
			threadpool.SubmitTask(tache);
		}

        final long TimeTask1 = System.currentTimeMillis();
        
        try {
    	    Thread.sleep(1000);
    	} catch (InterruptedException ie) {
    		System.err.println("erreur sleep");
    	}
        while ((threadpool.getqueue().size()!=0) ) {
        	try {
        	    Thread.sleep(1000);
        	} catch (InterruptedException ie) {
        		System.err.println("erreur sleep");
        	}
        }
        final long TimeTask2 = System.currentTimeMillis();
        threadpool.threadpool.shutdown();
        System.out.println(threadpool.threadpool);
        System.out.println(threadpool.numberThread);
        System.out.println(TimeTask2-TimeTask1);
	}

}