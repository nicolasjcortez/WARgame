package game;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class GoalDialog extends JDialog
{
	    private final JButton _closeButton = new JButton("Fechar");
	    private Image _objImage;
	    private String _objMsg;

	    public GoalDialog(JFrame parent) {
	        super(parent);
	        setTitle("Objetivo do " + MainFrame.getInstance().getCurrentPlayerName());
	        JPanel mainPanel = new JPanel(new BorderLayout());
	        loadImage();
	        _objMsg = GameData.getInstance().getCurrentPlayerGoalMsg();
	        JPanel objPanel = 
	        		new JPanel(){    
			        	public void paintComponent(Graphics g) {
				            super.paintComponent(g);
				            //final int diceSize = 80;
				            //Dimension ptDim = getParent().getSize();		            
				            g.drawImage(_objImage, 2,2, _objImage.getWidth(null), _objImage.getHeight(null), null);
				             
				            g.drawString(_objMsg.substring(0, 27), 30 , _objImage.getWidth(null)-(_objImage.getWidth(null)/10));
				            g.drawString(_objMsg.substring(27, _objMsg.length()), 30 , _objImage.getWidth(null)-(_objImage.getWidth(null)/10)+20);
			        }
	        };
	        mainPanel.add(objPanel, BorderLayout.CENTER);
	        mainPanel.add(_closeButton, BorderLayout.PAGE_END);


	        _closeButton.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					setVisible(false);
				}
			});
	        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
	        setResizable(false);
	        setModal(true);
			setBounds(0,0, _objImage.getWidth(null)+2 ,_objImage.getHeight(null)+50);
	        setLocationRelativeTo(parent.getRootPane());      
	        setSize(new Dimension(_objImage.getWidth(null) ,_objImage.getHeight(null)  + 50));
	        setContentPane(mainPanel);
	    }
	    
	    public void loadImage()
	    {
            try 
            {
                _objImage = ImageIO.read(new File("images/war_carta_objetivo_grande.png"));
            } catch (IOException e)
            {
                System.out.println(e.getMessage());
                System.exit(1);
            }       
	    }
}
