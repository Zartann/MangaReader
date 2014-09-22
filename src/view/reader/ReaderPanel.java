package view.reader;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import model.Chapter;
import model.Manga;
import model.Page;
import view.APanel;

public class ReaderPanel extends APanel implements KeyListener,
		MouseWheelListener, MouseListener {

	private static Color defaultBackgroundColor = Color.BLACK;

	private CurrentPage currentPage;
	private Color backgroundColor;

	public ReaderPanel(Manga manga, Chapter chapter, Page page, Color color) {
		backgroundColor = color;
		currentPage = CurrentPage.load(manga, chapter, page);
	}
	
	public void change(Manga manga, Chapter chapter, Page page){
		currentPage = CurrentPage.load(manga, chapter, page);
	}

	public void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setRenderingHints(rh);

		g.setColor(backgroundColor);
		g.clearRect(0, 0, getWidth(), getHeight());
		g.fillRect(0, 0, getWidth(), getHeight());

		int h = currentPage.getImageHeight();
		int w = currentPage.getImageWidth();
		double ratio = getHeight() / (double) h;

		int wr = (int) (w * ratio);

		int x1 = getWidth() / 2 - wr / 2;
		int x2 = getWidth() / 2 - w / 2;

		if (h > w)
			g.drawImage(currentPage.getImage(), x2, -currentPage.getYStart(),
					w, h, null);
		else {
			Image img = currentPage.getImage().getScaledInstance(wr,
					getHeight(), Image.SCALE_AREA_AVERAGING);
			g.drawImage(img, x1, 0, wr, getHeight(), null);
		}
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		if (arg0.getButton() == MouseEvent.BUTTON1)
			currentPage.nextPage(this);
	}

	@Override
	public void keyPressed(KeyEvent arg0) {
		if (arg0.getKeyCode() == 39)
			currentPage.nextPage(this);

		else if (arg0.getKeyCode() == 37)
			currentPage.previousPage(this);

		else if (image.getHeight() > getHeight() && arg0.getKeyCode() == 38)
			currentPage.scrollUp(this);

		else if (image.getHeight() > getHeight() && arg0.getKeyCode() == 40)
			currentPage.scrollDown(this);

	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent arg0) {

		int scroll = arg0.getWheelRotation();
		if (scroll > 10)
			scroll = 10;
		else if (scroll < -10)
			scroll = -10;

		currentPage.scroll(scroll, this);

	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

}
