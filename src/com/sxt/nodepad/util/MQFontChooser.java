package com.sxt.nodepad.util;

import org.yaml.snakeyaml.Yaml;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.PlainDocument;


public class MQFontChooser extends JDialog {
/**
 * read from yaml
*/

    /**
     * Select the return value of the cancel button
     */
    public static final int CANCEL_OPTION = 0;
    /**
     * Select the return value of the determine button
     */
    public static final int APPROVE_OPTION = 1;
    /**
     * Chinese preview character string
     */
    private static final String CHINA_STRING = "All are nothing!";
    /**
     * English preview character string
     */
    private static final String ENGLISH_STRING = "Hello Kitty！";
    /**
     * Number preview character string
     */
    private static final String NUMBER_STRING = "0123456789";
    // The default font, which is the font to be returned in the future
    private Font font;
    // Font selector component container
    private Box box = null;
    // font text box
    private JTextField fontText = null;
    // style text box
    private JTextField styleText = null;
    // text size text box
    private JTextField sizeText = null;
    // preview text box
    private JTextField previewText = null;
    // Chinese preview
    private JRadioButton chinaButton = null;
    // English preview
    private JRadioButton englishButton = null;
    // number preview
    private JRadioButton numberButton = null;
    // font selection box
    private JList fontList = null;
    // style selection box
    private JList styleList = null;
    // font size selection box
    private JList sizeList = null;
    // Confirm button
    private JButton approveButton = null;
    // Cancel button
    private JButton cancelButton = null;
    // All font
    private String [] fontArray = null;
    // All pattern
    private String [] styleArray = {"regular", "bold", "italic", "bold italic"};
    // All preset font sizes
    private String [] sizeArray = {"8", "9", "10", "11", "12", "14", "16", "18", "20", "22", "24", "26", "28", "36", "48", "初号", "小初", "一号", "小一", "二号", "小二", "三号", "小三", "四号", "小四", "五号", "小五", "六号", "小六", "七号", "八号"};
    // The corresponding font size in the above array
    private int [] sizeIntArray = {8, 9, 10, 11, 12, 14, 16, 18, 20, 22, 24, 26, 28, 36, 48, 42, 36, 26, 24, 22, 18, 16, 15, 14, 12, 10, 9, 8, 7, 6, 5};
    private int returnValue = CANCEL_OPTION;
    /**
     * build a font chooser
     */
//    public MQFontChooser() {
//        this(new Font("font song", Font.PLAIN, 12));
//    }
    /**
     * build a font
     * @param font style
     */
    public MQFontChooser(Font font) throws FileNotFoundException {
        Yaml Fontchooseryaml = new Yaml();
        FileReader yamlreader = new FileReader("org.yaml.snakeyaml.Yaml.yml");
        BufferedReader yamlbuffer = new BufferedReader(yamlreader);
        Map<String, Object> map = (Map<String, Object>) Fontchooseryaml.load(yamlbuffer);
        int FontSize = Integer.parseInt(String.valueOf(map.get("FontSize")));


        setTitle("Font selector");
        this.font = font;
        // Initialize UI components
        init();
        // add listener
        addListener();
        // Display in default font
        setup();
        // basic set up
        setModal(true);
        setResizable(false);
        // adaptive size
        pack();
    }
    /**
     * Initialization module
     */
    private void init(){
        // get system font
        GraphicsEnvironment eq = GraphicsEnvironment.getLocalGraphicsEnvironment();
        fontArray = eq.getAvailableFontFamilyNames();
        // main container
        box = Box.createVerticalBox();
        box.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
        fontText = new JTextField();
        fontText.setEditable(false);
        fontText.setBackground(Color.WHITE);
        styleText = new JTextField();
        styleText.setEditable(false);
        styleText.setBackground(Color.WHITE);
        sizeText = new JTextField("12");
        // For the Document document used by the text size text box, some rules for inputting characters are formulated
        Document doc = new PlainDocument(){
            public void insertString(int offs, String str, AttributeSet a)
                    throws BadLocationException {
                if (str == null) {
                    return;
                }
                if (getLength() >= 3) {
                    return;
                }
                if (!str.matches("[0-9]+") && !str.equals("初号") && !str.equals("小初") && !str.equals("一号") && !str.equals("小一") && !str.equals("二号") && !str.equals("小二") && !str.equals("三号") && !str.equals("小三") && !str.equals("四号") && !str.equals("小四") && !str.equals("五号") && !str.equals("小五") && !str.equals("六号") && !str.equals("小六") && !str.equals("七号") && !str.equals("八号")) {
                    return;
                }
                super.insertString(offs, str, a);
                sizeList.setSelectedValue(sizeText.getText(), true);
            }
        };
        sizeText.setDocument(doc);
        previewText = new JTextField(20);
        previewText.setHorizontalAlignment(JTextField.CENTER);
        previewText.setEditable(false);
        previewText.setBackground(Color.WHITE);
        chinaButton = new JRadioButton("Chinese preview", true);
        englishButton = new JRadioButton("English preview");
        numberButton = new JRadioButton("Num preview");
        ButtonGroup bg = new ButtonGroup();
        bg.add(chinaButton);
        bg.add(englishButton);
        bg.add(numberButton);
        fontList = new JList(fontArray);
        styleList = new JList(styleArray);
        sizeList = new JList(sizeArray);
        approveButton = new JButton("confirm");
        cancelButton = new JButton("cancel");
        Box box1 = Box.createHorizontalBox();
        JLabel l1 = new JLabel("Font size:");
        JLabel l2 = new JLabel("Font glyph:");
        JLabel l3 = new JLabel("Size:");
        l1.setPreferredSize(new Dimension(165, 14));
        l1.setMaximumSize(new Dimension(165, 14));
        l1.setMinimumSize(new Dimension(165, 14));
        l2.setPreferredSize(new Dimension(95, 14));
        l2.setMaximumSize(new Dimension(95, 14));
        l2.setMinimumSize(new Dimension(95, 14));
        l3.setPreferredSize(new Dimension(80, 14));
        l3.setMaximumSize(new Dimension(80, 14));
        l3.setMinimumSize(new Dimension(80, 14));
        box1.add(l1);
        box1.add(l2);
        box1.add(l3);
        Box box2 = Box.createHorizontalBox();
        fontText.setPreferredSize(new Dimension(160, 20));
        fontText.setMaximumSize(new Dimension(160, 20));
        fontText.setMinimumSize(new Dimension(160, 20));
        box2.add(fontText);
        box2.add(Box.createHorizontalStrut(5));
        styleText.setPreferredSize(new Dimension(90, 20));
        styleText.setMaximumSize(new Dimension(90, 20));
        styleText.setMinimumSize(new Dimension(90, 20));
        box2.add(styleText);
        box2.add(Box.createHorizontalStrut(5));
        sizeText.setPreferredSize(new Dimension(80, 20));
        sizeText.setMaximumSize(new Dimension(80, 20));
        sizeText.setMinimumSize(new Dimension(80, 20));
        box2.add(sizeText);
        Box box3 = Box.createHorizontalBox();
        JScrollPane sp1 = new JScrollPane(fontList);
        sp1.setPreferredSize(new Dimension(160, 100));
        sp1.setMaximumSize(new Dimension(160, 100));
        sp1.setMaximumSize(new Dimension(160, 100));
        box3.add(sp1);
        box3.add(Box.createHorizontalStrut(5));
        JScrollPane sp2 = new JScrollPane(styleList);
        sp2.setPreferredSize(new Dimension(90, 100));
        sp2.setMaximumSize(new Dimension(90, 100));
        sp2.setMinimumSize(new Dimension(90, 100));
        box3.add(sp2);
        box3.add(Box.createHorizontalStrut(5));
        JScrollPane sp3 = new JScrollPane(sizeList);
        sp3.setPreferredSize(new Dimension(80, 100));
        sp3.setMaximumSize(new Dimension(80, 100));
        sp3.setMinimumSize(new Dimension(80, 100));
        box3.add(sp3);
        Box box4 = Box.createHorizontalBox();
        Box box5 = Box.createVerticalBox();
        JPanel box6 = new JPanel(new BorderLayout());
        box5.setBorder(BorderFactory.createTitledBorder("Char"));
        box6.setBorder(BorderFactory.createTitledBorder("Example"));
        box5.add(chinaButton);
        box5.add(englishButton);
        box5.add(numberButton);
        box5.setPreferredSize(new Dimension(90, 95));
        box5.setMaximumSize(new Dimension(90, 95));
        box5.setMinimumSize(new Dimension(90, 95));
        box6.add(previewText);
        box6.setPreferredSize(new Dimension(250, 95));
        box6.setMaximumSize(new Dimension(250, 95));
        box6.setMinimumSize(new Dimension(250, 95));
        box4.add(box5);
        box4.add(Box.createHorizontalStrut(4));
        box4.add(box6);
        Box box7 = Box.createHorizontalBox();
        box7.add(Box.createHorizontalGlue());
        box7.add(approveButton);
        box7.add(Box.createHorizontalStrut(5));
        box7.add(cancelButton);
        box.add(box1);
        box.add(box2);
        box.add(box3);
        box.add(Box.createVerticalStrut(5));
        box.add(box4);
        box.add(Box.createVerticalStrut(5));
        box.add(box7);
        getContentPane().add(box);
    }
    /**
     * Display in default font
     */
    private void setup() {
        String fontName = font.getFamily();
        int fontStyle = font.getStyle();
        int fontSize = font.getSize();
        /*
         * If the preset text size is in the selection list, set the value by selecting an item in the list, otherwise directly write the preset text size into the text box
         */
        boolean b = false;
        for (int i = 0; i < sizeArray.length; i++) {
            if (sizeArray[i].equals(String.valueOf(fontSize))) {
                b = true;
                break;
            }
        }
        if(b){
            // Select an item from the text size list
            sizeList.setSelectedValue(String.valueOf(fontSize), true);
        }else{
            sizeText.setText(String.valueOf(fontSize));
        }
        // Select an item in the font list
        fontList.setSelectedValue(fontName, true);
        // Select an item in the style list
        styleList.setSelectedIndex(fontStyle);
        // The preview displays Chinese characters by default
        chinaButton.doClick();
        // show preview
        setPreview();
    }
    /**
     * Add the required event listeners
     */
    private void addListener() {
        sizeText.addFocusListener(new FocusListener() {
            public void focusLost(FocusEvent e) {
                setPreview();
            }
            public void focusGained(FocusEvent e) {
                sizeText.selectAll();
            }
        });
        // Listener for font list selection event
        fontList.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    fontText.setText(String.valueOf(fontList.getSelectedValue()));
                    // set preview
                    setPreview();
                }
            }
        });
        styleList.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    styleText.setText(String.valueOf(styleList.getSelectedValue()));
                    // set preview
                    setPreview();
                }
            }
        });
        sizeList.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    if(!sizeText.isFocusOwner()){
                        sizeText.setText(String.valueOf(sizeList.getSelectedValue()));
                    }
                    // set preview
                    setPreview();
                }
            }
        });
        // Encoding listener
        EncodeAction ea = new EncodeAction();
        chinaButton.addActionListener(ea);
        englishButton.addActionListener(ea);
        numberButton.addActionListener(ea);
        // Event listener for OK button
        approveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Combining fonts
                font = groupFont();
                // Set return num
                returnValue = APPROVE_OPTION;
                // 关闭窗口
                disposeDialog();
            }
        });
        // 取消按钮事件监听
        cancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                disposeDialog();
            }
        });
    }
    /**
     * 显示字体选择器
     * @param owner 上层所有者
     * @return 该整形返回值表示用户点击了字体选择器的确定按钮或取消按钮，参考本类常量字段APPROVE_OPTION和CANCEL_OPTION
     */
    public final int showFontDialog(JFrame owner) {
        setLocationRelativeTo(owner);
        setVisible(true);
        return returnValue;
    }
    /**
     * 返回选择的字体对象
     * @return 字体对象
     */
    public final Font getSelectFont() {
        return font;
    }
    /**
     * 关闭窗口
     */
    private void disposeDialog() {
        MQFontChooser.this.removeAll();
        MQFontChooser.this.dispose();
    }

    /**
     * 显示错误消息
     * @param errorMessage 错误消息
     */
    private void showErrorDialog(String errorMessage) {
        JOptionPane.showMessageDialog(this, errorMessage, "错误", JOptionPane.ERROR_MESSAGE);
    }
    /**
     * 设置预览
     */
    private void setPreview() {
        Font f = groupFont();
        previewText.setFont(f);
    }
    /**
     * 按照选择组合字体
     * @return 字体
     */
    private Font groupFont() {
        String fontName = fontText.getText();
        int fontStyle = styleList.getSelectedIndex();
        String sizeStr = sizeText.getText().trim();
        // 如果没有输入
        if(sizeStr.length() == 0) {
            showErrorDialog("字体（大小）必须是有效“数值！");
            return null;
        }
        int fontSize = 0;
        // 通过循环对比文字大小输入是否在现有列表内
        for (int i = 0; i < sizeArray.length; i++) {
            if(sizeStr.equals(sizeArray[i])){
                fontSize = sizeIntArray[i];
                break;
            }
        }
        // 没有在列表内
        if (fontSize == 0) {
            try{
                fontSize = Integer.parseInt(sizeStr);
                if(fontSize < 1){
                    showErrorDialog("字体（大小）必须是有效“数值”！");
                    return null;
                }
            }catch (NumberFormatException nfe) {
                showErrorDialog("字体（大小）必须是有效“数值”！");
                return null;
            }
        }
        return new Font(fontName, fontStyle, fontSize);
    }

    /**
     * 编码选择事件的监听动作
     *
     */
    class EncodeAction implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (e.getSource().equals(chinaButton)) {
                previewText.setText(CHINA_STRING);
            } else if (e.getSource().equals(englishButton)) {
                previewText.setText(ENGLISH_STRING);
            } else {
                previewText.setText(NUMBER_STRING);
            }
        }
    }
}