package ui;

import MidiIO.MidiReader;
import MidiIO.MidiWriter;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.midi.InvalidMidiDataException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.TitledBorder;
import core.SongMaker;

public class SongInterface implements ActionListener{
    public SongInterface()
    {
        top = new JFrame("Random Song");
        songHolder = new JPanel();
        ta_song = new JTextArea();
        sp_scroll = new JScrollPane(ta_song);
        b_buildSong = new JButton("Build Song");
        b_buildBinarySong = new JButton("Build Binary Song");
        b_buildFugalExpo = new JButton("Build Fugal Exposition");
        b_playSong = new JButton("Play Song");
        gridBag = new GridBagLayout();
        gridConstraints = new GridBagConstraints();
        
        initComponents();
    }
   
   ////Students will design/code this function when designing a user inteface
    private void initComponents()
    {
        //Set properties of JTextArea, JScrollPane, and related
        ta_song.setEditable(false);
        ta_song.setFont(new Font(Font.MONOSPACED, Font.LAYOUT_LEFT_TO_RIGHT, 14));
        sp_scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        sp_scroll.setVisible(true);
        songHolder.setBorder(new TitledBorder("Song notes will be displayed here"));
        
        //Set sizes
        top.setBounds(150, 125, 350, 374);
        sp_scroll.setPreferredSize(new Dimension(300, 100));
        
        //Add listeners
        b_buildSong.addActionListener(this);
        b_buildBinarySong.addActionListener(this);
        b_buildFugalExpo.addActionListener(this);
        b_playSong.addActionListener(this);
        
        //Disable the play song button. Enable it after a song has been built
        b_playSong.setEnabled(false);
        
        
        //Set close
        top.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        //Set layout
        top.setLayout(gridBag);
        //Set grid
        gridBag.setConstraints(top, gridConstraints);
        
        //Add components
        //Build song button
        gridConstraints.gridx = 0;
        gridConstraints.gridy = 5;
        gridConstraints.weightx = 0.5;
        top.add(b_buildSong, gridConstraints);
        //Build binary song button
        gridConstraints.gridx = 2;
        gridConstraints.gridy = 5;
        gridConstraints.weightx = 0.5;
        top.add(b_buildBinarySong, gridConstraints);
        //Build fugal exposition button
        gridConstraints.gridx = 3;
        gridConstraints.gridy = 5;
        gridConstraints.weightx = 0.5;
        top.add(b_buildFugalExpo, gridConstraints);
        //Play song button
        gridConstraints.gridx = 2;
        gridConstraints.gridy = 6;
        gridConstraints.weightx = 0.5;
        top.add(b_playSong, gridConstraints);
        //Song holder, scroll pane, and text area
        gridConstraints.gridx = 0;
        gridConstraints.gridy = 0;
        gridConstraints.weightx = 0.0;
        gridConstraints.gridwidth = 4;
        gridConstraints.ipady = 50;
        gridConstraints.weightx = 0;
        gridConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridConstraints.anchor = GridBagConstraints.NORTH;
        songHolder.add(sp_scroll);
        top.add(songHolder, gridConstraints);
        
        top.setVisible(true);
        
        //Pack in the components on the JFrame (aesthetic)
        top.pack();
        
    }
    
    //Member variables
    //Containers
    private final JFrame top;
    private final JPanel songHolder;
    //Visuals
    private final JButton b_buildSong;
    private final JButton b_buildBinarySong;
    private final JButton b_buildFugalExpo;
    private final JButton b_playSong;
    private final JTextArea ta_song;
    private final JScrollPane sp_scroll;
    //Layout
    private final GridBagLayout gridBag;
    private final GridBagConstraints gridConstraints;
    //Behind the scenes
    private SongMaker song = null;
    
    
    
    
    
    //Methods overridden from ActionListener
    @Override
    public void actionPerformed(ActionEvent action)
    {
        int general;
        String temp;
        if(action.getActionCommand().equals("Build Song"))
        {
            //Prompt user for number of notes
            temp = JOptionPane.showInputDialog("How many notes are in this song?", "");
            
            if(temp == null)
                return;
            if(temp.equals(""))
            {
                JOptionPane.showMessageDialog(top, "You must enter a number of notes");
                return;
            }
            
            general = Integer.parseInt(temp);
            
            //Build the RandomSong;
            if(song == null)
                song = new SongMaker(general);
            else
                song.buildSong(general, true, true);
            
            //Display the notes of the song in JTextArea ta_song
            ta_song.setText(song.toString());
            
            //Enable the play song button
            b_playSong.setEnabled(true);
        }
        else if(action.getActionCommand().equals("Build Binary Song"))
        {
            //Prompt user for number of notes
            temp = JOptionPane.showInputDialog("How many notes are in each section?", "");
            
            if(temp == null)
                return;
            if(temp.equals(""))
            {
                JOptionPane.showMessageDialog(top, "You must enter a number of notes");
                return;
            }
            
            general = Integer.parseInt(temp);
            
            //Prompt user for number of repetitions
            temp = JOptionPane.showInputDialog("How many times should the sections repeat?", "");
            
            if(song == null)
                song = new SongMaker();
            song.buildBinarySong(general, Integer.parseInt(temp));
            
            //Display the notes of the song in JTextArea ta_song
            ta_song.setText(song.toString());
            
            //Enable the play song button
            b_playSong.setEnabled(true);
        }
        else if(action.getActionCommand().equals("Build Fugal Exposition"))
        {
            //Prompt user for number of notes
            temp = JOptionPane.showInputDialog("How many notes are in the exposition?", "");
            
            if(temp == null)
                return;
            if(temp.equals(""))
            {
                JOptionPane.showMessageDialog(top, "You must enter a number of notes");
                return;
            }
            
            general = Integer.parseInt(temp);
            
            //Prompt user for delay (in notes) between melody and harmony
            temp = JOptionPane.showInputDialog("How much of a delay (in notes)?", "");
            
            if(song == null)
                song = new SongMaker();
            song.buildFugualExpo(general, Integer.parseInt(temp));
            
            //Display the ontes of the song in JTextArea ta_song
            ta_song.setText(song.toString());
            
            //Enable the play song button
            b_playSong.setEnabled(true);
        }
        else if(action.getActionCommand().equals("Play Song"))
        {
            try {
                MidiWriter.writeMidiFile(song);
                MidiReader.playSong(song);
            } catch (InvalidMidiDataException | IOException ex) {
                System.out.println("In actionListener: Caught exception "+ex.getMessage());
                Logger.getLogger(SongInterface.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
    