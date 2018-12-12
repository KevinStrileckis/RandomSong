package core;


public class MusicalNote {
    //Constructors
    public MusicalNote(int mnTone, int mnDur)
    {
        tone = mnTone;
        duration = mnDur;
    }
    
    //Member functions
    public int getTone()
    {
        return tone;
    }
    public int getDuration()
    {
        return duration;
    }
    
    ////Students will design/code this function
    //Function checks two notes and returns true if they are within one step of each other
    public static boolean areDissonant(MusicalNote n1, MusicalNote n2)
    {
        if(n1.getTone() - n2.getTone() == 1 || n2.getTone() - n1.getTone() == 1)
            return true;
        return false;
    }
    
    
    //Member variables
    int tone, duration;
}
