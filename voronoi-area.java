import ij.IJ;
import ij.ImagePlus;
import ij.gui.GenericDialog;
import ij.gui.ImageCanvas;
import ij.gui.ImageWindow;
import ij.gui.PointRoi;
import ij.gui.PolygonRoi;
import ij.gui.Roi;
import ij.gui.ShapeRoi;
import ij.gui.StackWindow;
import ij.macro.Interpreter;
import ij.measure.Calibration;
import ij.measure.ResultsTable;
import ij.plugin.PlugIn;
import ij.plugin.frame.Roimanager;
import ij.plugin.filter.Analyzer;
import ij.process.ImageProcessor;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.geom.GeneralPath;
import java.util.Iterator;
import java.util.TreeMap;

public class voronoi_area implements PlugIn {
    @Override
    public void run(String arg){
	ImagePlus imp = IJ.getImage();
	if (imp == null) // Return if no image is opened.
	    return;
	GenericDialog gd = new GenericDialog("Area of Voronoi Cells");
	gd.addCheckbox("interactive", !Interpreter.isBatchMode());
	gd.addCheckbox("make Delaunay ROI", false);
	gd.addCheckbox("showMeanDistance", false);
	ResultsTable results = Analyzer.getResultsTable();
	gd.addCheckbox("inferSelectionFromParticles",
		       imp.getRoi() == null && results != null
		       && results.getColumnIndex("XM")
		       != ResultsTable.COLUMN_NOT_FOUND);
	gd.addCheckbox("export into Results", false);
	gd.showDialog();
	if (gd.wasCanceled())
	    return;

	Roi roi = imp.getRoi();
	if (roi == null) return;

	if (roi instanceof PointRoi){
	    xpoints = roi.getPolygon().xpoints;
	    ypoints = roi.getPolygon().ypoints;

	    pointType = roi.getCounters();
	
    }
}
