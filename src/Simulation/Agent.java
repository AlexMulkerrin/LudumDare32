package Simulation;

/**
 *
 * @author Alex Mulkerrin
 */
public class Agent {
    public int x;
    public int y;
    public int oldx;
    public int oldy;
    public int population;
    public String name;
    
    public Boolean isNomadic;
    
    private Simulation sim;
    private Terrain map;

    
    public Agent(int placeX, int placeY, Terrain location, Simulation containingSim ) {
        x = placeX;
        y = placeY;
        oldx = x;
        oldy = y;
        map = location;
        sim = containingSim;
        name = randomTribeName();
        isNomadic=true;
 
        population=50;
        
    }
    
    public void update() {
        if (isNomadic) {
            // very slight chance to settle on their own
            if (map.getCityFertility(x, y)>40) {
                this.settle();
            } else {
                wander();
                switch (map.getFertility(x,y)) {
                    case 0: {
                        map.drainFertility(x,y);
                        population-=1;
                        break;
                    }
                    case 1: {
                        //unchanged;
                        map.drainFertility(x,y);
                        break;
                    }
                    case 2: {
                        population+=2;
                        map.drainFertility(x,y);
                        if (population>150) population=150;
                        break;
                    }
                }
            }
        } else {
            int food = 100*map.getCityFertility(x,y); 
            if (food>population) {
                population+=5;
            } else if (food==population) {
                //population unchanged;
            } else if (food<population) {
                population-=5;  
            }
            map.drainCityFertility(x,y,population/100);
        }
    }
    
    public void wander() {
        int nx=x;
        int ny=y;
        int choice = (int)(Math.random()*4);
        if (choice==0) ny--;
        if (choice==1) nx++;
        if (choice==2) ny++;
        if (choice==3) nx--;
        if (nx>0 && nx<map.getWidth() && ny>0 && ny<map.getHeight()) {
            if (map.checkValidMove(nx,ny)) {

                oldx=x;
                oldy=y;
                x=nx;
                y=ny;
                map.occupier[x][y] = this;
                
            }  
        }
    }
    
    public void settle() {
        isNomadic=false;
        sim.log.add(sim.turn+": Tribe "+name+" has settled.");
    }
    
    private String randomTribeName() {
        char[] vowels = new char[]{'a','e','i','o','u'};
        char[] consonants = new char[]{'p','t','k','m','n'};
        String text="", result="";


            text="";
            int wordLength= sim.random.nextInt(4)+4;
            int letterType = sim.random.nextInt(2);
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
            result += text;
        return result;
    }
    
    public char randomChoice(char[] choices) {
        int pick = sim.random.nextInt(choices.length);
        return choices[pick];
    }
    
    
}
