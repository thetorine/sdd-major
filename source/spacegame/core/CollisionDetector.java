package spacegame.core;

import org.newdawn.slick.Color;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Polygon;
import spacegame.entity.EntityBase;
import spacegame.gamestates.IngameState;
import spacegame.other.GameUtilities.Point;

import java.util.ArrayList;

public class CollisionDetector {

	//checks if there any entities who are colliding.
	public static void update(int delta) {
		ArrayList<EntityBase> ingameEntites = IngameState.getInstance().entityManager.getIngameEntities();
		for(EntityBase entityCheck : ingameEntites) {
			if(entityCheck instanceof ICollisionDetection) {
				Polygon p1 = entityCheck.collisionShape;
				for(EntityBase entity : ingameEntites) {
					if(!entity.equals(entityCheck)) {
						Polygon p2 = entity.collisionShape;
						double d1 = p1.getBoundingCircleRadius();
						double d2 = p2.getBoundingCircleRadius();
						//uses the squared distance because sqrt is extremely expensive
						double distance = distanceSquaredBetweenPolygon(p1, p2);
						if(distance <= Math.pow(d1+d2, 2)) {
							((ICollisionDetection) entityCheck).onCollision(entity);
						}
					}
				}
			}
		}
	} //intersect's very expensive, should try find a way to make it not expensive other, collision not very accurate
	
	//doesnt work for objects that are hollow--have transparent pixels within the object.
	//or the shape is just screwed up
	//basically just comparing every pixel to its surroundings to determine if the pixel forms
	//the border of the OBJECT in the image. 
	public static Polygon createPolygonFromImage(Image i) {
		Polygon p = new Polygon();
		int imageWidth = i.getWidth() - 1;
		int imageHeight = i.getHeight() - 1;
		ArrayList<Point> pointsList = new ArrayList<>();
		for (int x = 0; x <= imageWidth; x++) {
			for (int y = 0; y <= imageHeight; y++) {
				Color c = i.getColor(x, y);
				//a transparent pixel
				if (c.getAlpha() == 0) {
					//checks the pixel currently being tested isn't at the edge of the image.
					if (x > 0 && x < imageWidth && y > 0 && y < imageHeight) {
						boolean top = i.getColor(x, y - 1).getAlpha() != 0;
						boolean bottom = i.getColor(x, y + 1).getAlpha() != 0;
						boolean left = i.getColor(x - 1, y).getAlpha() != 0;
						boolean right = i.getColor(x + 1, y).getAlpha() != 0;
						//checks if the transparent pixel is near a colour pixel -- meaning it is a 
						//boundary pixel of the object 
						if (top || bottom || left || right) {
							Point pt = new Point(x, y);
							pointsList.add(pt);
						}
					}
				}

				if (x == 0 || y == 0 || x == imageWidth || y == imageHeight) {
					//check if this part of image is at the boundary of the IMAGE
					if (i.getColor(x, y).getAlpha() > 0) {
						Point pt = new Point(x, y);
						pointsList.add(pt);
					}
				}
			}
		}
		if(pointsList.size() > 0) {
			pointsList = stripSimilarPoints(sortPoints(pointsList));
			for(Point pt : pointsList) {
				p.addPoint(pt.x, pt.y);
			}
		}
		return p;
	}

	private static ArrayList<Point> sortPoints(ArrayList<Point> list) {
		//just sorting the points based on distances between the points.
		ArrayList<Point> newList = new ArrayList<>();
		int cX = (int) list.get(0).x;
		int cY = (int) list.get(0).y;
		while (list.size() > 0) {
			Point pt = findClosestPoint(cX, cY, list);
			list.remove(pt);
			newList.add(pt);
			cX = (int) pt.x;
			cY = (int) pt.y;
		}
		return newList;
	}
	
	//checks if 3 given points are collinear. If the threshhold between the distances is less than 0.1
	//then the points are almost collinear meaning the middle point can be removed as it has a 
	//minimal affect on the geometry of the polygon.
	private static ArrayList<Point> stripSimilarPoints(ArrayList<Point> list) {
		ArrayList<Point> ptsToRemove = new ArrayList<>();
		for(int i = 1; i < list.size()-1; i++) {
			Point prevPoint = list.get(i-1);
			Point currPoint = list.get(i);
			Point nextPoint = list.get(i+1);
			double straightPrevToNext = distanceBetween(prevPoint, nextPoint);
			double prevToNext = distanceBetween(prevPoint, currPoint) + distanceBetween(currPoint, nextPoint);
			if(Math.abs(straightPrevToNext-prevToNext) < 0.1) {
				ptsToRemove.add(currPoint);
			}
		}
		list.removeAll(ptsToRemove);
		return list;
	}

	private static double distanceBetween(Point p1, Point p2) {
		return Math.sqrt(Math.pow(p1.x - p2.x, 2) + Math.pow(p1.y - p2.y, 2));
	}
	
	private static double distanceSquaredBetweenPolygon(Polygon p1, Polygon p2) {
		return Math.pow(p1.getCenterX() - p2.getCenterX(), 2) + Math.pow(p1.getCenterY() - p2.getCenterY(), 2);
	}

	//used for sorting the points based on their distance from each other.
	private static Point findClosestPoint(int x, int y, ArrayList<Point> list) {
		double d = Double.MAX_VALUE;
		Point closestPt = null;
		for (Point pt : list) {
			double d1 = Math.sqrt(Math.pow(pt.x - x, 2) + Math.pow(pt.y - y, 2));
			if (d1 < d) {
				closestPt = pt;
				d = d1;
			}
		}
		return closestPt;
	}
	
	public static interface ICollisionDetection {
		public void onCollision(EntityBase collisionWith);
	}
}
