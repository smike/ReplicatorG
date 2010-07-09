package replicatorg.app.ui.modeling;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;
import replicatorg.app.Base;
import replicatorg.app.ui.modeling.PreviewPanel.DragMode;

public class RotationTool extends Tool implements MouseMotionListener, MouseListener, MouseWheelListener {
	public RotationTool(ToolPanel parent) {
		super(parent);
	}
	
	@Override
	Icon getButtonIcon() {
		return null;
	}

	@Override
	String getButtonName() {
		return "Rotate";
	}

	@Override
	JPanel getControls() {
		JPanel p = new JPanel(new MigLayout("fillx,filly"));
		JButton b;

		b = createToolButton("Z+","images/center-object.png");
		b.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				parent.getModel().rotateObject(Math.PI/2, 0d);
			}
		});
		p.add(b,"growx");

		b = createToolButton("Z-","images/center-object.png");
		b.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				parent.getModel().rotateObject(-Math.PI/2, 0d);
			}
		});
		p.add(b,"growx,wrap");

		b = createToolButton("X+","images/center-object.png");
		b.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				parent.getModel().rotateObject(0d, Math.PI/2);
			}
		});
		p.add(b,"growx");

		b = createToolButton("X-","images/center-object.png");
		b.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				parent.getModel().rotateObject(0d, -Math.PI/2);
			}
		});
		p.add(b,"growx,wrap");

		b = createToolButton("Y+","images/center-object.png");
		b.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				parent.getModel().rotateObject(Math.PI/2, Math.PI/2);
			}
		});
		p.add(b,"growx");

		b = createToolButton("Y-","images/center-object.png");
		b.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				parent.getModel().rotateObject(-Math.PI/2, Math.PI/2);
			}
		});
		p.add(b,"growx,wrap");

		b = createToolButton("Lay flat","images/center-object.png");
		b.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				parent.getModel().rotateObject(-Math.PI/2, Math.PI/2);
			}
		});
		p.add(b,"growx,spanx,wrap");

		return p;
	}

	@Override
	String getInstructions() {
		return Base.isMacOS()?
				"<html><body>Drag to rotate<br>Shift-drag to pan view<br>Mouse wheel to zoom</body></html>":
				"<html><body>Left button drag to rotate object<br>Right button drag to pan view<br>Mouse wheel to zoom</body></html>";
	}

	@Override
	String getTitle() {
		return "Rotate Object";
	}


	Point startPoint = null;
	int button = 0;
	
	public void mouseDragged(MouseEvent e) {
		if (startPoint == null) return;
		Point p = e.getPoint();
		DragMode mode = DragMode.ROTATE_VIEW; 
		if (Base.isMacOS()) {
			if (button == MouseEvent.BUTTON1 && !e.isShiftDown()) { mode = DragMode.ROTATE_OBJECT; }
			else if (button == MouseEvent.BUTTON1 && e.isShiftDown()) { mode = DragMode.TRANSLATE_VIEW; }
		} else {
			if (button == MouseEvent.BUTTON1) { mode = DragMode.ROTATE_OBJECT; }
			else if (button == MouseEvent.BUTTON3) { mode = DragMode.TRANSLATE_VIEW; }
		}
		double xd = (double)(p.x - startPoint.x);
		double yd = -(double)(p.y - startPoint.y);
		switch (mode) {
		case ROTATE_OBJECT:
			parent.getModel().rotateObject(0.05*xd, -0.05*yd);
			break;
		case TRANSLATE_VIEW:
			parent.preview.adjustViewTranslation(-0.5 * xd, 0.5 * yd);
			break;
		}
		startPoint = p;
	}
	public void mouseMoved(MouseEvent e) {
	}
	public void mouseClicked(MouseEvent e) {
	}
	public void mouseEntered(MouseEvent e) {
	}
	public void mouseExited(MouseEvent e) {
	}
	
	double objectDistance;
	
	public void mousePressed(MouseEvent e) {
		startPoint = e.getPoint();
		button = e.getButton();
	}
	public void mouseReleased(MouseEvent e) {
		startPoint = null;
	}
	public void mouseWheelMoved(MouseWheelEvent e) {
		int notches = e.getWheelRotation();
		parent.preview.adjustZoom(10 * notches);
	}

}