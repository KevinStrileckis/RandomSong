//Lab in Progress
///Random Song
/// by Kevin Strileckis

//Some notes
//Have advanced and one for entire class
    //Entire includes UI made by students
//Semester/year-long project
    //Can show Audacity, other audio editing software

package core;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
//Midi
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Sequencer;

public class SongMaker {
   //Constructors
    public SongMaker()
    {
        //Staves 1 and 2 are initialized in method BuildSong()
        numOfNotes = 0;
        tones = Note.values();
        delay = 0;
        
        try {
            seq = MidiSystem.getSequencer();
        }
        catch (MidiUnavailableException ex) {
            System.out.println("In actionListener: Caught exception "+ex.getMessage());
            Logger.getLogger(SongMaker.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public SongMaker(int notes)
    {
        //Staves 1 and 2 are initialized in method BuildSong()
        tones = Note.values();
        delay = 0;
        
        buildSong(notes, true, true);
        
        try {
            //WARNING: Could not open/create prefs root node Software\JavaSoft\Prefs at
                // root 0x80000002. Windows RegCreateKeyEx(...) returned error code 5.
            seq = MidiSystem.getSequencer();
        }
        catch (MidiUnavailableException ex) {
            System.out.println("In actionListener: Caught exception "+ex.getMessage());
            Logger.getLogger(SongMaker.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
   //Member methods
    
   ////Students will design/code this function
    //Function will print out the song
    public void printSong()
    {
        int i;
        
        //Check if there are no notes in the song. If so, then print "Song is empty" and
            // exit the method
        if(numOfNotes == 0)
        {
            System.out.print("Song is empty");
            return;
        }
        
        String durationLine = "";
        
        //Print out the melody
        System.out.print("Melody:\t\t");
        for(i=0; i<numOfNotes; ++i)
        {
            System.out.print(tones[staff1.get(i).tone]+" ");
            durationLine += staff1.get(i).getDuration() + " ";
        }
        System.out.print("\n");
        
        System.out.println("\t\t\t"+durationLine);
        
        //Print out the harmony
        System.out.print("Harmony:\t");
        
        //Print out any delay between harmony and melody
        for(i=0; i<delay; ++i)
            System.out.print("  ");
        
        for(i=0; i<numOfNotes; ++i)
        {
            System.out.print(tones[staff2.get(i).getTone()]+" ");
        }
        System.out.print("\n");
    }
    
  ////Students will design/code this function
    //Function will return a text version of the song
    @Override
    public String toString()
    {
        int temp;
        int delayTemp = delay;
        String melodyLine;
        String durationLine;
        String harmonyLine;
        
        //Check if there are no notes in the song. If so, then return "Song is empty"
        if(numOfNotes == 0)
            return "Song is empty";
        
        melodyLine = "Melody:\t\t";
        durationLine = "Duration:\t";
        harmonyLine = "Harmony:\t";
        
        //Fill melody, duration, and harmony lines
        for(int i=0; i<numOfNotes; ++i)
        {
            melodyLine += (tones[staff1.get(i).tone]+" ");
            temp = staff1.get(i).getDuration();
            switch(temp)
            {
                case 0:
                    //Note: the ⅛ symbol is too wide, even with MONOSPACED as our font. So, we will
                        // use the character 'e' to represent an eigth note, instead.
                    durationLine += "e ";
                    break;
                case 1:
                    durationLine += "¼ ";
                    break;
                case 2:
                    durationLine += "½ ";
                    break;
                case 3:
                    durationLine += "W ";
            }
             
            for(int j=0; delayTemp > 0; ++ j)
            {
                harmonyLine += "  ";
                
                switch(staff1.get(j).getDuration())
                {
                    case 0:
                        delayTemp -= 24;
                        break;
                    case 1:
                        delayTemp -= 48;
                        break;
                    case 2:
                        delayTemp -= 96;
                        break;
                    default:
                        delayTemp -= 192;
                }
            }
            harmonyLine += (tones[staff2.get(i).getTone()]+" ");
        }
        
        //Return a concatenation of the three lines
        return melodyLine + "\n" + durationLine + "\n" + harmonyLine;
    }
    
    
   ////Students will design/code this function
    //Given some number of notes, function will produce a RandomSong
    //Given a song about n notes
    // Each note goes up, goes down, stays the same
    public void buildSong(int notes, boolean overWriteOldSong, boolean writeToBothStaves)
    {
        int newTone, newDuration;
        int tonic;
        
        //Check that int notes is not less than zero
        if(notes < 0)
        {
            numOfNotes = 0;
            return;
        }
        
        numOfNotes = notes;
        
        if(staff1 == null || overWriteOldSong)
        {
            staff1 = new ArrayList<>();
            staff2 = new ArrayList<>();
        }
        
        
        //Add the first note. Use this tone for the final note as well
        newTone = (int) (Math.random() * 7);
        tonic = newTone;
        newDuration = (int) (Math.random() * 4);
        staff1.add(new MusicalNote(newTone, newDuration));
        
        //If we are to write both sections, then write the first note of the second
            // staff.
        if(writeToBothStaves)
        {
            //The note of the harmony will be based on the note of the melody
            if((int)(Math.random() * 2) == 0)
                staff2.add(new MusicalNote((newTone+2) % 7, 
                                            newDuration));
            else
                staff2.add(new MusicalNote((newTone+4) % 7, 
                                            newDuration));
        }
        notes--;
        
        
        //Write all but the last note
        while(notes > 1)
        {
            //Add all other notes, using the previous note to decide this one
            //Go upwards
            if((int)(Math.random() * 2) == 0)
            {
                //Three is the maximum for stepping, zero the minimum
                newTone += (int)(Math.random() * 4);
                if(newTone > 6)
                    newTone = 6;
            }
            else
            {
                newTone -= ((int)(Math.random() * 3 + 1));
                if(newTone < 0)
                    newTone = 0;
            }
            
            if(staff1.size() > 2 &&
                    new MusicalNote(newTone, newDuration) == staff1.get(staff1.size()-1)
                    &&
                    new MusicalNote(newTone, newDuration) == staff1.get(staff1.size()-2)
                    )
            {
                if(newTone + 1 > 6)
                    newTone--;
                else
                    newTone++;
            }
            
            newDuration = (int)(Math.random() * 4);
            staff1.add(new MusicalNote(newTone, newDuration));
            
            if(writeToBothStaves)
            {
                //The new note of the harmony will be based on the note of the melody
                if((int)(Math.random() * 2) == 0)
                    staff2.add(new MusicalNote((newTone+2) % 7, 
                                                newDuration));
                else
                    staff2.add(new MusicalNote((newTone+4) % 7, 
                                                newDuration));
            }
            notes--;
        }
        
        
        //Write the last note. Use the tonic for the melody's tone
        newDuration = (int) (Math.random() * 4);
        staff1.add(new MusicalNote(tonic, newDuration));
        
        //If we are to write both sections, then write the first note of the second
            // staff.
        if(writeToBothStaves)
        {
            //The note of the harmony will be based on the note of the melody
            if((int)(Math.random() * 2) == 0)
                staff2.add(new MusicalNote((tonic+2) % 7, 
                                            newDuration));
            else
                staff2.add(new MusicalNote((tonic+4) % 7, 
                                            newDuration));
        }
    }
    
  ////Students will design/code this function
    //Function will construct a RandomSong with two distinct sections, each repeated
        // a certain number of times
    public void buildBinarySong(int sectionLength, int timesToRepeat)
    {
        if(timesToRepeat < 0)
            return;
        
        buildSong(sectionLength, true, true);
        buildSong(sectionLength, false, true);
        
        sectionLength *= 2;
        
        for(int i=0; i<timesToRepeat; ++i)
        {
            for(int j=0; j<sectionLength; ++j)
            {
                staff1.add(staff1.get(j));
                staff2.add(staff2.get(j));
            }
        }
        
        numOfNotes = sectionLength * timesToRepeat;
    }
    
  ////Students will design/code this function
    //Function will construct a RandomSong with two distinct sections, each repeated
        // a certain number of times. Section lengths are distinct as well.
    public void buildBinarySong(int section1Length, int section2Length, int timesToRepeat)
    {
        if(timesToRepeat < 0)
            return;
        
        buildSong(section1Length, true, true);
        buildSong(section2Length, false, true);
        
        section1Length = (section1Length * 2) + (section2Length * 2);
        
        for(int i=0; i<timesToRepeat; ++i)
        {
            for(int j=0; j<section1Length; ++j)
            {
                staff1.add(staff1.get(j));
                staff2.add(staff2.get(j));
            }
        }
        
        numOfNotes = section1Length * timesToRepeat;
    }
    
  ////Studetns will design/code this function
    public void buildFugualExpo(int notes, int delay)
    {
        notes -= 2;
        //Construct the majority of the tune, but leave the last two notes to make the 
            // cadence. Build the new melody (well, "exposition") notes-2 long, overwrie 
            // the previous song and write to only one staff
        buildSong(notes, true, false);
        
        //Set the delay
        this.delay = 0;
        
        for(int i=0; i<delay; ++i)
        {
            switch(staff1.get(i).getDuration())
            {
                case 0:
                    this.delay += 24;
                    break;
                case 1:
                    this.delay += 48;
                    break;
                case 2:
                    this.delay += 96;
                    break;
                default:
                    this.delay += 192;
            }
        }
        
        
        for(int i=0; i<notes; ++i)
            staff2.add(staff1.get(i));
        
        //Add the cadence (takes up two notes)
    }
        
    
    
    public Sequencer getSequencer()
    {
        return seq;
    }
    
    public int getNumOfNotes()
    {
        return numOfNotes;
    }
    public int getDelay()
    {
        return delay;
    }
    public ArrayList<MusicalNote> getStaff1()
    {
        return staff1;
    }
    public ArrayList<MusicalNote> getStaff2()
    {
        return staff2;
    }
    
   //Member variables - provided for students
    private ArrayList<MusicalNote> staff1, staff2;
    private int numOfNotes;
    private int delay;
    private Sequencer seq;
    private Note[] tones;
}
