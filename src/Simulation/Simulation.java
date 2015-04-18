package Simulation;

import java.util.*;
/**
 *
 * @author Alex Mulkerrin
 */
public class Simulation {
    Random random;
    public int seed;
    public String name;
    public Terrain map;
    public ArrayList<Agent> unit;
    
    public int turn;
    
    public Simulation(int width, int height, int startingFactions) {
        
        seed = (int)(System.nanoTime());
        random = new Random();
        random.setSeed(seed);
        name = randomName();
        
        turn=0;
        
        map = new Terrain(width,height);
        map.generateWorld(random);
        
        unit = new ArrayList<>();
        for (int i=0; i<startingFactions; i++) {
            Agent toAdd=null;
            while (toAdd==null) {
                int x =random.nextInt(map.getWidth());
                int y =random.nextInt(map.getHeight());
                toAdd = map.createOccupier(x,y);
                
                
            }
            unit.add(toAdd);
        }
    }
    
    public void update() {
        turn++;
//        for (int i=0; i<unit.size(); i++) {
//            unit.get(i).wander();
//        }
    }
    
    private String randomName() {
        char[] vowels = new char[]{'a','e','i','o','u'};
        char[] consonants = new char[]{'p','t','k','m','n'};
        String text="", result="";
        int numWords = random.nextInt(3)+1;
        for (int i=0; i<numWords; i++) {
            text="";
            int wordLength= random.nextInt(4)+4;
            int letterType = random.nextInt(2);
            for (int j=0; j<wordLength; j++) {
                if (letterType==0) {
                    text += randomChoice(consonants);
                    letterType++;
                } else {
                    text += randomChoice(vowels);
                    letterType=0;
                }
                if (j==0) text = text.toUpperCase();
            }
            result += text+" ";
        }
        return result;
    }
    
    public char randomChoice(char[] choices) {
        int pick = random.nextInt(choices.length);
        return choices[pick];
    }
}
