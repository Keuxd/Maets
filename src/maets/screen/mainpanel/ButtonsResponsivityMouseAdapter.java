package maets.screen.mainpanel;

import java.awt.Color;
import java.awt.Image;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.UIManager;
import javax.swing.border.Border;

public class ButtonsResponsivityMouseAdapter extends MouseAdapter {

	protected JComponent button;
	protected ImageIcon originalImageIcon;
	private Border defaultButtonBorder;
	
	public ButtonsResponsivityMouseAdapter(JComponent button) {
		this.button = button;
		this.originalImageIcon = convertIconToImageIcon(getIconInComponent());
		
		this.defaultButtonBorder = UIManager.getBorder("Button.border");
		button.setBorder(this.defaultButtonBorder);
	}
	
	// Should be implemented by it's child class
	public void fixedMouseClicked(MouseEvent e) {}
	
	@Override
	public void mouseEntered(MouseEvent e) {
		button.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 3));
	}

	@Override
	public void mouseExited(MouseEvent e) {
		button.setBorder(defaultButtonBorder);
	}
	
	@Override
	public void mouseReleased(MouseEvent e) {
		setIconInComponent(originalImageIcon);
		button.setFont(button.getFont().deriveFont((float)button.getFont().getSize() * 2));

		if(isClickValid()) {
			fixedMouseClicked(e);
		}
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		int imageSize = originalImageIcon.getIconHeight() / 2;
		Image scaledImage = originalImageIcon.getImage().getScaledInstance(imageSize, imageSize, Image.SCALE_SMOOTH);
		setIconInComponent(new ImageIcon(scaledImage));

		button.setFont(button.getFont().deriveFont((float) button.getFont().getSize() / 2));
	}

	private boolean isClickValid() {
		try {
		    Point mousePos = MouseInfo.getPointerInfo().getLocation();
		    Rectangle bounds = button.getBounds();
		    bounds.setLocation(button.getLocationOnScreen());
		    return bounds.contains(mousePos);
		} catch(Exception e) {
			return false;
		}
	}
	
	protected void setIconInComponent(ImageIcon ic) {
		if(button instanceof JButton) {
			((JButton) button).setIcon(ic);
		} else {
			((JLabel) button).setIcon(ic);
		}
	}
	
	protected Icon getIconInComponent() {
		Icon componentIcon;
		
		if(button instanceof JButton) {
			componentIcon = ((JButton) button).getIcon();
		} else {
			componentIcon = ((JLabel) button).getIcon();
		}
		
		return componentIcon;
	}
	
	protected ImageIcon convertIconToImageIcon(Icon icon) {
		BufferedImage bi = new BufferedImage(icon.getIconWidth(), icon.getIconHeight(), BufferedImage.TYPE_INT_ARGB);
		icon.paintIcon(new JCheckBox(), bi.getGraphics(), 0, 0);
		return new ImageIcon(bi);
	}
}