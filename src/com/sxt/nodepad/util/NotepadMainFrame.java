package com.sxt.nodepad.util;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.undo.UndoManager;
import java.awt.*;
import java.awt.datatransfer.*;
import java.awt.event.*;
import java.awt.print.PageFormat;
import java.awt.print.PrinterJob;
import java.io.*;
import java.util.Calendar;
import java.util.GregorianCalendar;

import static javafx.application.Platform.exit;


public class NotepadMainFrame extends JFrame implements ActionListener {

    /**
     * num
     */
    private static final long serialVersionUID = 8585210209467333480L;
    private JPanel contentPane;
    private JTextArea textArea;
    private JMenuItem itemOpen;
    private JMenuItem itemSave;

    //1：new
    //2：A modified
    //3：saved
    int flag=0;

    //filename
    String currentFileName=null;

    PrintJob p=null;
    Graphics  g=null;


    String currentPath=null;


    JColorChooser jcc1=null;
    Color color=Color.BLACK;


    int linenum = 1;
    int columnnum = 1;


    public UndoManager undoMgr = new UndoManager();


    public Clipboard clipboard = new Clipboard("System shear plate");

    private JMenuItem itemSaveAs;
    private JMenuItem itemNew;
    private JMenuItem itemPage;
    private JSeparator separator;
    private JMenuItem itemPrint;
    private JMenuItem itemExit;
    private JSeparator separator_1;
    private JMenu itemEdit;
    private JMenu itFormat;
    private JMenu itemCheck;
    private JMenu itemHelp;
    private JMenuItem itemSearchForHelp;
    private JMenuItem itemAboutNotepad;
    private JMenuItem itemUndo;
    private JMenuItem itemCut;
    private JMenuItem itemCopy;
    private JMenuItem itemPaste;
    private JMenuItem itemDelete;
    private JMenuItem itemFind;
    private JMenuItem itemFindNext;
    private JMenuItem itemReplace;
    private JMenuItem itemTurnTo;
    private JMenuItem itemSelectAll;
    private JMenuItem itemTime;
    private JMenuItem itemFont;
    private JMenuItem itemColor;
    private JMenuItem itemFontColor;
    private JCheckBoxMenuItem itemNextLine;
    private JScrollPane scrollPane;
    private JCheckBoxMenuItem itemStatement;
    private JToolBar toolState;
    public static JLabel label1;
    private JLabel label2;
    private JLabel label3;
    int length=0;
    int sum=0;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    NotepadMainFrame frame = new NotepadMainFrame();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    GregorianCalendar c=new GregorianCalendar();
    int hour=c.get(Calendar.HOUR_OF_DAY);
    int min=c.get(Calendar.MINUTE);
    int second=c.get(Calendar.SECOND);
    private JPopupMenu popupMenu;
    private JMenuItem popM_Undo;
    private JMenuItem popM_Cut;
    private JMenuItem popM_Copy;
    private JMenuItem popM_Paste;
    private JMenuItem popM_Delete;
    private JMenuItem popM_SelectAll;
    private JMenuItem popM_toLeft;
    private JMenuItem popM_showUnicode;
    private JMenuItem popM_closeIMe;
    private JMenuItem popM_InsertUnicode;
    private JMenuItem popM_RestartSelect;
    private JSeparator separator_2;
    private JSeparator separator_3;
    private JSeparator separator_4;
    private JSeparator separator_5;
    private JMenuItem itemRedo;
    private JSeparator separator_6;
    private JSeparator separator_7;
    private JSeparator separator_8;
    private JMenuItem popM_Redo;

    /**
     * Create the frame.
     */
    public NotepadMainFrame() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException e1) {
            e1.printStackTrace();
        } catch (InstantiationException e1) {
            e1.printStackTrace();
        } catch (IllegalAccessException e1) {
            e1.printStackTrace();
        } catch (UnsupportedLookAndFeelException e1) {
            e1.printStackTrace();
        }
        setTitle("Text Editor");
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        setBounds(100, 100, 800, 800);

        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        JMenu itemFile = new JMenu("File(F)");
        itemFile.setMnemonic('F');
        menuBar.add(itemFile);

        itemNew = new JMenuItem("New(N)",'N');
        itemNew.setAccelerator(KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_N,
                java.awt.Event.CTRL_MASK));
        itemNew.addActionListener(this);
        itemFile.add(itemNew);

        itemOpen = new JMenuItem("Open(O)",'O');
        itemOpen.setAccelerator(KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_O,
                java.awt.Event.CTRL_MASK));
        itemOpen.addActionListener(this);
        itemFile.add(itemOpen);

        itemSave = new JMenuItem("Save(S)");
        itemSave.setAccelerator(KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S,
                java.awt.Event.CTRL_MASK));
        itemSave.addActionListener(this);
        itemFile.add(itemSave);

        itemSaveAs = new JMenuItem("Save As(A)");
        itemSaveAs.addActionListener(this);
        itemFile.add(itemSaveAs);

        separator = new JSeparator();
        itemFile.add(separator);

        itemPage = new JMenuItem("Page setup(U)",'U');
        itemPage.addActionListener(this);
        itemFile.add(itemPage);

        itemPrint = new JMenuItem("Print(P)...",'P');
        itemPrint.setAccelerator(KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_P,
                java.awt.Event.CTRL_MASK));
        itemPrint.addActionListener(this);
        itemFile.add(itemPrint);

        separator_1 = new JSeparator();
        itemFile.add(separator_1);

        itemExit = new JMenuItem("Exit(X)",'X');
        itemExit.addActionListener(this);
        itemFile.add(itemExit);

        itemEdit = new JMenu("Edit(E)");
        itemEdit.setMnemonic('E');
        menuBar.add(itemEdit);

        itemUndo = new JMenuItem("Undo(U)",'U');//撤销
        itemUndo.setAccelerator(KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_Z,
                java.awt.Event.CTRL_MASK));
        itemUndo.addActionListener(this);
        itemEdit.add(itemUndo);

        itemRedo = new JMenuItem("Recover(R)");
        itemRedo.setAccelerator(KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_R,
                java.awt.Event.CTRL_MASK));
        itemRedo.addActionListener(this);
        itemEdit.add(itemRedo);

        itemCut = new JMenuItem("Cut(T)",'T');
        itemCut.setAccelerator(KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_X,
                java.awt.Event.CTRL_MASK));
        itemCut.addActionListener(this);

        separator_6 = new JSeparator();
        itemEdit.add(separator_6);
        itemEdit.add(itemCut);

        itemCopy = new JMenuItem("Copy(C)",'C');
        itemCopy.addActionListener(this);
        itemCopy.setAccelerator(KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_C,
                java.awt.Event.CTRL_MASK));
        itemEdit.add(itemCopy);

        itemPaste = new JMenuItem("Paste(P)",'P');
        itemPaste.addActionListener(this);
        itemPaste.setAccelerator(KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_V,
                java.awt.Event.CTRL_MASK));
        itemEdit.add(itemPaste);

        itemDelete = new JMenuItem("Delete(L)",'L');
        itemDelete.addActionListener(this);
        itemDelete.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_D,
                InputEvent.CTRL_MASK));
        itemEdit.add(itemDelete);

        separator_7 = new JSeparator();
        itemEdit.add(separator_7);

        itemFind = new JMenuItem("Find(F)",'F');
        itemFind.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F,
                Event.CTRL_MASK));
        itemFind.addActionListener(this);
        itemEdit.add(itemFind);

        itemFindNext = new JMenuItem("Find next(N)",'N');
        itemFindNext.setAccelerator(KeyStroke.getKeyStroke("F3"));
        itemFindNext.addActionListener(this);
        itemEdit.add(itemFindNext);

        itemReplace = new JMenuItem("Replace(R)",'R');
        itemReplace.addActionListener(this);
        itemReplace.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_H,
                Event.CTRL_MASK));
        itemEdit.add(itemReplace);

        itemTurnTo = new JMenuItem("Go to(G)",'G');
        itemTurnTo.addActionListener(this);
        itemTurnTo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_G,
                Event.CTRL_MASK));
        itemEdit.add(itemTurnTo);

        itemSelectAll = new JMenuItem("Select All(A)",'A');
        itemSelectAll.setAccelerator(KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_A,
                java.awt.Event.CTRL_MASK));
        itemSelectAll.addActionListener(this);

        separator_8 = new JSeparator();
        itemEdit.add(separator_8);
        itemEdit.add(itemSelectAll);

        itemTime = new JMenuItem("Date(D)",'D');
        itemTime.addActionListener(this);
        itemTime.setAccelerator(KeyStroke.getKeyStroke("F5"));
        itemEdit.add(itemTime);

        itFormat = new JMenu("Format(O)");
        itFormat.setMnemonic('O');
        menuBar.add(itFormat);

        itemNextLine = new JCheckBoxMenuItem("Word Wrap(W)");
        itemNextLine.addActionListener(this);
        itFormat.add(itemNextLine);

        itemFont = new JMenuItem("Font size(F)...");
        itemFont.addActionListener(this);
        itFormat.add(itemFont);

        itemColor = new JMenuItem("Background Color(C)...");
        itemColor.addActionListener(this);
        itFormat.add(itemColor);

        itemFontColor = new JMenuItem("Font Color(FC)...");
        itemFontColor.addActionListener(this);
        itFormat.add(itemFontColor);

        itemCheck = new JMenu("View(V)");
        itemCheck.setMnemonic('V');
        menuBar.add(itemCheck);

        itemStatement = new JCheckBoxMenuItem("Status(S)");
        itemStatement.addActionListener(this);
        itemCheck.add(itemStatement);

        itemHelp = new JMenu("Help(H)");
        itemHelp.setMnemonic('H');
        menuBar.add(itemHelp);

        itemSearchForHelp = new JMenuItem("Help(H)",'H');
        itemSearchForHelp.addActionListener(this);
        itemHelp.add(itemSearchForHelp);

        itemAboutNotepad = new JMenuItem("About(A)",'A');
        itemAboutNotepad.addActionListener(this);
        itemHelp.add(itemAboutNotepad);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        //设置边框布局
        contentPane.setLayout(new BorderLayout(0, 0));
        setContentPane(contentPane);

        textArea = new JTextArea();

        //VERTICAL   HORIZONTAL
        scrollPane = new JScrollPane(textArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        //contentPane2=new JPanel();
        //contentPane2.setSize(10,textArea.getSize().height);
        //contentPane.add(contentPane2, BorderLayout.WEST);
        TestLine view = new TestLine();
        scrollPane.setRowHeaderView(view);

        popupMenu = new JPopupMenu();
        addPopup(textArea, popupMenu);

        popM_Undo = new JMenuItem("Undo(U)");//撤销
        popM_Undo.addActionListener(this);
        popupMenu.add(popM_Undo);

        popM_Redo = new JMenuItem("Recover(R)");
        popM_Redo.addActionListener(this);
        popupMenu.add(popM_Redo);

        separator_2 = new JSeparator();
        popupMenu.add(separator_2);

        popM_Cut = new JMenuItem("Cut(T)");
        popM_Cut.addActionListener(this);
        popupMenu.add(popM_Cut);

        popM_Copy = new JMenuItem("Copy(C)");
        popM_Copy.addActionListener(this);
        popupMenu.add(popM_Copy);

        popM_Paste = new JMenuItem("Paste(P)");
        popM_Paste.addActionListener(this);
        popupMenu.add(popM_Paste);

        popM_Delete = new JMenuItem("Delete(D)");
        popM_Delete.addActionListener(this);
        popupMenu.add(popM_Delete);

        separator_3 = new JSeparator();
        popupMenu.add(separator_3);

        popM_SelectAll = new JMenuItem("Select All(A)");
        popM_SelectAll.addActionListener(this);
        popupMenu.add(popM_SelectAll);

        separator_4 = new JSeparator();
        popupMenu.add(separator_4);

        popM_toLeft = new JMenuItem("Reading order from right to left(R)");
        popM_toLeft.addActionListener(this);
        popupMenu.add(popM_toLeft);

        popM_showUnicode = new JMenuItem("Displays Unicode control characters(S)");
        popM_showUnicode.addActionListener(this);
        popupMenu.add(popM_showUnicode);

        popM_InsertUnicode = new JMenuItem("Insert Unicode control characters(I)");
        popM_InsertUnicode.addActionListener(this);
        popupMenu.add(popM_InsertUnicode);

        separator_5 = new JSeparator();
        popupMenu.add(separator_5);

        popM_closeIMe = new JMenuItem("Shut down IME(L)");
        popM_closeIMe.addActionListener(this);
        popupMenu.add(popM_closeIMe);

        popM_RestartSelect = new JMenuItem("Re-election(R)");
        popM_RestartSelect.addActionListener(this);
        popupMenu.add(popM_RestartSelect);
        //添加到面板中
        contentPane.add(scrollPane, BorderLayout.CENTER);

        //添加撤销管理器
        textArea.getDocument().addUndoableEditListener(undoMgr);


        toolState = new JToolBar();
        toolState.setSize(textArea.getSize().width, 10);//toolState.setLayout(new FlowLayout(FlowLayout.LEFT));
        label1 = new JLabel("Current system time:" + hour + ":" + min + ":" + second+" ");
        toolState.add(label1);
        toolState.addSeparator();
        label2 = new JLabel(" The " + linenum + " line, The " + columnnum+" column  ");
        toolState.add(label2);
        toolState.addSeparator();

        label3 = new JLabel(" A total of " +length+" words  ");
        toolState.add(label3);
        textArea.addCaretListener(new CaretListener() {        //记录行数和列数
            public void caretUpdate(CaretEvent e) {
                //sum=0;
                JTextArea editArea = (JTextArea)e.getSource();

                try {
                    int caretpos = editArea.getCaretPosition();
                    linenum = editArea.getLineOfOffset(caretpos);
                    columnnum = caretpos - textArea.getLineStartOffset(linenum);
                    linenum += 1;
                    label2.setText(" The " + linenum + " Line, The " + (columnnum+1)+" column  ");
                    //sum+=columnnum+1;
                    //length+=sum;
                    length=NotepadMainFrame.this.textArea.getText().toString().length();
                    label3.setText(" A total of " +length+" words ");
                }
                catch(Exception ex) { }
            }});

        contentPane.add(toolState, BorderLayout.SOUTH);
        toolState.setVisible(false);
        toolState.setFloatable(false);
        Clock clock=new Clock();
        clock.start();




        final JPopupMenu jp=new JPopupMenu();    //
        textArea.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(e.getButton()==MouseEvent.BUTTON3)//
                {
                    jp.show(e.getComponent(),e.getX(),e.getY());//
                }
            }
        });

        isChanged();

        this.MainFrameWidowListener();
    }


    /*===============================1====================================*/

    private void isChanged() {
        textArea.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                //Here I make a judgment about using a shortcut key but not entering a character without changing the content of the textArea
                Character c=e.getKeyChar();
                if(c != null && !textArea.getText().toString().equals("")){
                    flag=2;
                }
            }
        });
    }
    /*===================================================================*/


    /*===============================2====================================*/
    /**
     *A new or saved exit has only two options
     */
    private void MainFrameWidowListener() {
        this.addWindowListener(new WindowAdapter(){
            @Override
            public void windowClosing(WindowEvent e) {
                if(flag==2 && currentPath==null){
                    //This is the little popup window
                    //(The notepad is 0 when you just start, and the newly created document is 1) after modification
                    int result=JOptionPane.showConfirmDialog(NotepadMainFrame.this, "Whether to save the changes to Text Editor?", "Notepad", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
                    if(result==JOptionPane.OK_OPTION){
                        NotepadMainFrame.this.saveAs();
                    }else if(result==JOptionPane.NO_OPTION){
                        NotepadMainFrame.this.dispose();
                        NotepadMainFrame.this.setDefaultCloseOperation(EXIT_ON_CLOSE);
                    }
                }else if(flag==2 && currentPath!=null){
                    //This is the little popup window
                    //(The notepad is 0 when you just start, and the newly created document is 1) after modification
                    int result=JOptionPane.showConfirmDialog(NotepadMainFrame.this, "Whether to save the changes to " + currentPath + "?", "Notepad", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
                    if(result==JOptionPane.OK_OPTION){
                        NotepadMainFrame.this.save();
                    }else if(result==JOptionPane.NO_OPTION){
                        NotepadMainFrame.this.dispose();
                        NotepadMainFrame.this.setDefaultCloseOperation(EXIT_ON_CLOSE);
                    }
                }else{
                    //
                    int result=JOptionPane.showConfirmDialog(NotepadMainFrame.this, "Determine to closed？", "The system prompt", JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE);
                    if(result==JOptionPane.OK_OPTION){
                        NotepadMainFrame.this.dispose();
                        NotepadMainFrame.this.setDefaultCloseOperation(EXIT_ON_CLOSE);
                    }
                }
            }
        });
    }
    /*===================================================================*/


    /*==============================3=====================================*/
    /**
     *
     */
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==itemOpen){            //open
            openFile();
        }else if(e.getSource()==itemSave){        //save
            save();
        }else if(e.getSource()==itemSaveAs){    //save as
            saveAs();
        }else if(e.getSource()==itemNew){        //new
            newFile();
        }else if(e.getSource()==itemExit){        //exit
            exit();
        }else if(e.getSource()==itemPage){        //page set up
            PageFormat pf = new PageFormat();
            PrinterJob.getPrinterJob().pageDialog(pf);
        }else if(e.getSource()==itemPrint){        //print
            Print();
        }else if(e.getSource()==itemUndo || e.getSource()==popM_Undo){        //Undo
            if(undoMgr.canUndo()){
                undoMgr.undo();
            }
        }else if(e.getSource()==itemRedo || e.getSource()==popM_Redo){        //Redo
            if(undoMgr.canRedo()){
                undoMgr.redo();
            }
        }else if(e.getSource()==itemCut || e.getSource()==popM_Cut){        //cut
            cut();
        }else if(e.getSource()==itemCopy || e.getSource()==popM_Copy){        //copy
            copy();
        }else if(e.getSource()==itemPaste || e.getSource()==popM_Paste){    //paste
            paste();
        }else if(e.getSource()==itemDelete || e.getSource()==popM_Delete){    //delete
            String tem=textArea.getText().toString();
            textArea.setText(tem.substring(0,textArea.getSelectionStart()));
        }else if(e.getSource()==itemFind){        //find
            mySearch();
        }else if(e.getSource()==itemFindNext){    //find next
            mySearch();
        }else if(e.getSource()==itemReplace){    //replace
            mySearch();
        }else if(e.getSource()==itemTurnTo){    //turn to
            turnTo();
        }else if(e.getSource()==itemSelectAll || e.getSource()==popM_SelectAll){    //Select all
            textArea.selectAll();
        }else if(e.getSource()==itemTime){        //Time/date
            textArea.append(hour+":"+min+" "+c.get(Calendar.YEAR)+"/"+(c.get(Calendar.MONTH)+1)+"/"+c.get(Calendar.DAY_OF_MONTH));
        }else if(e.getSource()==itemNextLine){    //next line
            //Sets the newline policy for the text area. If set to true, a line will be wrapped when the length of the line is greater than the allocated width. This property defaults to false.
            if(itemNextLine.isSelected()){
                textArea.setLineWrap(true);
            }else{
                textArea.setLineWrap(false);
            }
        }else if(e.getSource()==itemFont){        //font size
            MQFontChooser fontChooser = new MQFontChooser(textArea.getFont());
            fontChooser.showFontDialog(this);
            Font font = fontChooser.getSelectFont();
            //JTextArea
            textArea.setFont(font);
        }else if(e.getSource()==itemColor){        //background color
            jcc1 = new JColorChooser();
            JOptionPane.showMessageDialog(this, jcc1,"Select the background color color",-1);
            color = jcc1.getColor();
            textArea.setBackground(color);
        }else if(e.getSource()==itemFontColor){    //font color
            jcc1=new JColorChooser();
            JOptionPane.showMessageDialog(this, jcc1, "Select the font color", -1);
            color = jcc1.getColor();
            //String string=textArea.getSelectedText();
            textArea.setForeground(color);
        }else if(e.getSource()==itemStatement){    //set status
            if(itemStatement.isSelected()){
                //scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
                toolState.setVisible(true);
            }else{
                //scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
                toolState.setVisible(false);
            }
        }
    }
    /*===================================================================*/


    private void turnTo() {
        final JDialog gotoDialog = new JDialog(this, "turn to next line");
        JLabel gotoLabel = new JLabel("Line_num(L):");
        final JTextField linenum = new JTextField(5);
        linenum.setText("1");
        linenum.selectAll();

        JButton okButton = new JButton("Sure");
        okButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                int totalLine = textArea.getLineCount();
                int[] lineNumber = new int[totalLine + 1];
                String s = textArea.getText();
                int pos = 0, t = 0;

                while (true) {
                    pos = s.indexOf('\12', pos);
                    // System.out.println("pos:"+pos);
                    if (pos == -1)
                        break;
                    lineNumber[t++] = pos++;
                }

                int gt = 1;
                try {
                    gt = Integer.parseInt(linenum.getText());
                } catch (NumberFormatException efe) {
                    JOptionPane.showMessageDialog(null, "Please put line-num!", "tips", JOptionPane.WARNING_MESSAGE);
                    linenum.requestFocus(true);
                    return;
                }

                if (gt < 2 || gt >= totalLine) {
                    if (gt < 2)
                        textArea.setCaretPosition(0);
                    else
                        textArea.setCaretPosition(s.length());
                } else
                    textArea.setCaretPosition(lineNumber[gt - 2] + 1);

                gotoDialog.dispose();
            }

        });

        JButton cancelButton = new JButton("cancel");
        cancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                gotoDialog.dispose();
            }
        });

        Container con = gotoDialog.getContentPane();
        con.setLayout(new FlowLayout());
        con.add(gotoLabel);
        con.add(linenum);
        con.add(okButton);
        con.add(cancelButton);

        gotoDialog.setSize(200, 100);
        gotoDialog.setResizable(false);
        gotoDialog.setLocation(300, 280);
        gotoDialog.setVisible(true);
    }



    /*===============================4====================================*/
    /**
     * new file
     */
    private void newFile() {
        if(flag==0 || flag==1){        //When you start Notepad, it's 0. When you create a new document, it's 1
            return;
        }else if(flag==2 && this.currentPath==null){        //changed
            //1、When you start Notepad, it's 0. When you create a new document, it's 1
            int result=JOptionPane.showConfirmDialog(NotepadMainFrame.this, "Whether to save changes to untitled\n" +
                    "\n?", "Notepad", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
            if(result==JOptionPane.OK_OPTION){
                this.saveAs();        //save as
            }else if(result==JOptionPane.NO_OPTION){
                this.textArea.setText("");
                this.setTitle("Text Editor");
                flag=1;
            }
            return;
        }else if(flag==2 && this.currentPath!=null ){
            //2、
            int result=JOptionPane.showConfirmDialog(NotepadMainFrame.this, "Whether to save the changes to"+this.currentPath+"?", "Notepad", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
            if(result==JOptionPane.OK_OPTION){
                this.save();        //save straight
            }else if(result==JOptionPane.NO_OPTION){
                this.textArea.setText("");
                this.setTitle("Text Editor");
                flag=1;
            }
        }else if(flag==3){        //file saved
            this.textArea.setText("");
            flag=1;
            this.setTitle("Text Editor");
        }
    }
    /*===================================================================*/


    /*===============================5====================================*/
    /**
     * save as
     */
    private void saveAs() {
        //open save place
        JFileChooser choose=new JFileChooser();
        //chose file
        int result=choose.showSaveDialog(this);
        if(result==JFileChooser.APPROVE_OPTION){
            //Get the selected file [the name of the file is your own input]
            File file=choose.getSelectedFile();
            FileWriter fw=null;
            //save
            try {
                fw=new FileWriter(file);
                fw.write(textArea.getText());
                currentFileName=file.getName();
                currentPath=file.getAbsolutePath();
                //If it's less, you need to write
                fw.flush();
                this.flag=3;
                this.setTitle(currentPath);
            } catch (IOException e1) {
                e1.printStackTrace();
            }finally{
                try {
                    if(fw!=null) fw.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }
    /*===================================================================*/


    /*===============================6====================================*/
    /**
     * save
     */
    private void save() {
        if(this.currentPath==null){
            this.saveAs();
            if(this.currentPath==null){
                return;
            }
        }
        FileWriter fw=null;
        //save
        try {
            fw=new FileWriter(new  File(currentPath));
            fw.write(textArea.getText());
            fw.flush();
            flag=3;
            this.setTitle(this.currentPath);
        } catch (IOException e1) {
            e1.printStackTrace();
        }finally{
            try {
                if(fw!=null) fw.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }
    /*===================================================================*/


    /*================================7===================================*/
    /**
     * save
     */
    private void openFile() {
        if(flag==2 && this.currentPath==null){
            //1,When you start Notepad, it's 0. When you create a new document, it's 1
            int result=JOptionPane.showConfirmDialog(NotepadMainFrame.this, "Whether save to Text Editor?", "Notepad", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
            if(result==JOptionPane.OK_OPTION){
                this.saveAs();
            }
        }else if(flag==2 && this.currentPath!=null){
            //2、open 2 save 3
            int result=JOptionPane.showConfirmDialog(NotepadMainFrame.this, "Whether save to"+this.currentPath+"?", "Notepad", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
            if(result==JOptionPane.OK_OPTION){
                this.save();
            }
        }
        JFileChooser choose=new JFileChooser();
        //choose file
        int result=choose.showOpenDialog(this);
        if(result==JFileChooser.APPROVE_OPTION){
            File file=choose.getSelectedFile();
            currentFileName=file.getName();
            currentPath=file.getAbsolutePath();
            flag=3;
            this.setTitle(this.currentPath);
            BufferedReader br=null;
            try {
                InputStreamReader isr=new InputStreamReader(new FileInputStream(file),"GBK");
                br=new BufferedReader(isr);
                StringBuffer sb=new StringBuffer();
                String line=null;
                while((line=br.readLine())!=null){
                    sb.append(line+SystemParam.LINE_SEPARATOR);
                }
                textArea.setText(sb.toString());
            } catch (FileNotFoundException e1) {
                e1.printStackTrace();
            } catch (IOException e1) {
                e1.printStackTrace();
            } finally{
                try {
                    if(br!=null) br.close();
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        }
    }
    /*================================================================*/


    /*=============================9===================================*/
    public void Print()
    {
        try{
            p = getToolkit().getPrintJob(this,"ok",null);//new a Printfjob  p
            g = p.getGraphics();//print
            //g.translate(120,200);//change the position
            this.textArea.printAll(g);
            p.end();//release g
        }
        catch(Exception a){

        }
    }
    /*================================================================*/


    private static void addPopup(Component component, final JPopupMenu popup) {
        component.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                if (e.isPopupTrigger()) {
                    showMenu(e);
                }
            }
            public void mouseReleased(MouseEvent e) {
                if (e.isPopupTrigger()) {
                    showMenu(e);
                }
            }
            private void showMenu(MouseEvent e) {
                popup.show(e.getComponent(), e.getX(), e.getY());
            }
        });
    }

    public void cut(){
        copy();
        //
        int start = this.textArea.getSelectionStart();
        //
        int end = this.textArea.getSelectionEnd();
        //
        this.textArea.replaceRange("", start, end);

    }

    public void copy(){
        String temp = this.textArea.getSelectedText();
        StringSelection text = new StringSelection(temp);
        this.clipboard.setContents(text, null);
    }

    public void paste(){
        Transferable contents = this.clipboard.getContents(this);
        //DataFalvor
        DataFlavor flavor = DataFlavor.stringFlavor;
        //if it can translate
        if(contents.isDataFlavorSupported(flavor)){
            String str;
            try {//translate
                str=(String)contents.getTransferData(flavor);
                //if paste
                if(this.textArea.getSelectedText()!=null){
                    int start = this.textArea.getSelectionStart();
                    int end = this.textArea.getSelectionEnd();
                    this.textArea.replaceRange(str, start, end);
                }else{
                    int mouse = this.textArea.getCaretPosition();
                    this.textArea.insert(str, mouse);
                }
            } catch(UnsupportedFlavorException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch(IllegalArgumentException e){
                e.printStackTrace();
            }
        }
    }

    public void mySearch() {
        final JDialog findDialog = new JDialog(this, "Find and replace", true);
        Container con = findDialog.getContentPane();
        con.setLayout(new FlowLayout(FlowLayout.LEFT));
        JLabel searchContentLabel = new JLabel("Find content(N) :");
        JLabel replaceContentLabel = new JLabel("Place(P)　 :");
        final JTextField findText = new JTextField(22);
        final JTextField replaceText = new JTextField(22);
        final JCheckBox matchcase = new JCheckBox("Case sensitive");
        ButtonGroup bGroup = new ButtonGroup();
        final JRadioButton up = new JRadioButton("Up(U)");
        final JRadioButton down = new JRadioButton("Down(D)");
        down.setSelected(true);
        bGroup.add(up);
        bGroup.add(down);
        JButton searchNext = new JButton("Find next(F)");
        JButton replace = new JButton("Replace(R)");
        final JButton replaceAll = new JButton("Replace All(A)");
        searchNext.setPreferredSize(new Dimension(110, 22));
        replace.setPreferredSize(new Dimension(110, 22));
        replaceAll.setPreferredSize(new Dimension(110, 22));
        //replace
        replace.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                if (replaceText.getText().length() == 0 && textArea.getSelectedText() != null)
                    textArea.replaceSelection("");
                if (replaceText.getText().length() > 0 && textArea.getSelectedText() != null)
                    textArea.replaceSelection(replaceText.getText());
            }
        });

        //replace all
        replaceAll.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                textArea.setCaretPosition(0);
                int a = 0, b = 0, replaceCount = 0;

                if (findText.getText().length() == 0) {
                    JOptionPane.showMessageDialog(findDialog, "Please fill in the search content!", "tips", JOptionPane.WARNING_MESSAGE);
                    findText.requestFocus(true);
                    return;
                }
                while (a > -1) {

                    int FindStartPos = textArea.getCaretPosition();
                    String str1, str2, str3, str4, strA, strB;
                    str1 = textArea.getText();
                    str2 = str1.toLowerCase();
                    str3 = findText.getText();
                    str4 = str3.toLowerCase();

                    if (matchcase.isSelected()) {
                        strA = str1;
                        strB = str3;
                    } else {
                        strA = str2;
                        strB = str4;
                    }

                    if (up.isSelected()) {
                        if (textArea.getSelectedText() == null) {
                            a = strA.lastIndexOf(strB, FindStartPos - 1);
                        } else {
                            a = strA.lastIndexOf(strB, FindStartPos - findText.getText().length() - 1);
                        }
                    } else if (down.isSelected()) {
                        if (textArea.getSelectedText() == null) {
                            a = strA.indexOf(strB, FindStartPos);
                        } else {
                            a = strA.indexOf(strB, FindStartPos - findText.getText().length() + 1);
                        }

                    }

                    if (a > -1) {
                        if (up.isSelected()) {
                            textArea.setCaretPosition(a);
                            b = findText.getText().length();
                            textArea.select(a, a + b);
                        } else if (down.isSelected()) {
                            textArea.setCaretPosition(a);
                            b = findText.getText().length();
                            textArea.select(a, a + b);
                        }
                    } else {
                        if (replaceCount == 0) {
                            JOptionPane.showMessageDialog(findDialog, "Cant find!", "Notepad", JOptionPane.INFORMATION_MESSAGE);
                        } else {
                            JOptionPane.showMessageDialog(findDialog, "Successful replacement" + replaceCount + "num of match content!", "替换成功", JOptionPane.INFORMATION_MESSAGE);
                        }
                    }
                    if (replaceText.getText().length() == 0 && textArea.getSelectedText() != null) {
                        textArea.replaceSelection("");
                        replaceCount++;
                    }
                    if (replaceText.getText().length() > 0 && textArea.getSelectedText() != null) {
                        textArea.replaceSelection(replaceText.getText());
                        replaceCount++;
                    }
                }// end while
            }
        });

        // find next
        searchNext.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                int a = 0, b = 0;
                int FindStartPos = textArea.getCaretPosition();
                String str1, str2, str3, str4, strA, strB;
                str1 = textArea.getText();
                str2 = str1.toLowerCase();
                str3 = findText.getText();
                str4 = str3.toLowerCase();
                // The "case-sensitive" CheckBox is selected
                if (matchcase.isSelected()) {
                    strA = str1;
                    strB = str3;
                } else {
                    strA = str2;
                    strB = str4;
                }

                if (up.isSelected()) {
                    if (textArea.getSelectedText() == null) {
                        a = strA.lastIndexOf(strB, FindStartPos - 1);
                    } else {
                        a = strA.lastIndexOf(strB, FindStartPos - findText.getText().length() - 1);
                    }
                } else if (down.isSelected()) {
                    if (textArea.getSelectedText() == null) {
                        a = strA.indexOf(strB, FindStartPos);
                    } else {
                        a = strA.indexOf(strB, FindStartPos - findText.getText().length() + 1);
                    }

                }
                if (a > -1) {
                    if (up.isSelected()) {
                        textArea.setCaretPosition(a);
                        b = findText.getText().length();
                        textArea.select(a, a + b);
                    } else if (down.isSelected()) {
                        textArea.setCaretPosition(a);
                        b = findText.getText().length();
                        textArea.select(a, a + b);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "can't find the content you find!", "Notepad", JOptionPane.INFORMATION_MESSAGE);
                }

            }
        });/* find next over */
        // cancel
        JButton cancel = new JButton("cancel");
        cancel.setPreferredSize(new Dimension(110, 22));
        cancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                findDialog.dispose();
            }
        });

        JPanel bottomPanel = new JPanel();
        JPanel centerPanel = new JPanel();
        JPanel topPanel = new JPanel();

        JPanel direction = new JPanel();
        direction.setBorder(BorderFactory.createTitledBorder("direction "));
        direction.add(up);
        direction.add(down);
        direction.setPreferredSize(new Dimension(170, 60));
        JPanel replacePanel = new JPanel();
        replacePanel.setLayout(new GridLayout(2, 1));
        replacePanel.add(replace);
        replacePanel.add(replaceAll);

        topPanel.add(searchContentLabel);
        topPanel.add(findText);
        topPanel.add(searchNext);
        centerPanel.add(replaceContentLabel);
        centerPanel.add(replaceText);
        centerPanel.add(replacePanel);
        bottomPanel.add(matchcase);
        bottomPanel.add(direction);
        bottomPanel.add(cancel);

        con.add(topPanel);
        con.add(centerPanel);
        con.add(bottomPanel);

        findDialog.setSize(410, 210);
        findDialog.setResizable(false);
        findDialog.setLocation(230, 280);
        findDialog.setVisible(true);
    }

}