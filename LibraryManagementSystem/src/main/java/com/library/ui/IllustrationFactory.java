package com.library.ui;

import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;

public class IllustrationFactory {

    public static Group getBrandLogo() {
        Group g = new Group();
        
        Rectangle r1 = new Rectangle(0, 0, 40, 40);
        r1.setArcWidth(18); r1.setArcHeight(18);
        r1.setFill(Color.web("#2b2510"));
        
        Rectangle r2 = new Rectangle(8, 10, 6, 20);
        r2.setArcWidth(4); r2.setArcHeight(4);
        r2.setFill(Color.web("#c8a44a"));
        
        Rectangle r3 = new Rectangle(16, 13, 6, 17);
        r3.setArcWidth(4); r3.setArcHeight(4);
        r3.setFill(Color.web("#e2c06a"));
        
        Rectangle r4 = new Rectangle(24, 11, 7, 19);
        r4.setArcWidth(4); r4.setArcHeight(4);
        r4.setFill(Color.web("#9a7830"));
        
        Rectangle r5 = new Rectangle(8, 29, 23, 2);
        r5.setArcWidth(2); r5.setArcHeight(2);
        r5.setFill(Color.web("#c8a44a"));
        r5.setOpacity(0.4);
        
        g.getChildren().addAll(r1, r2, r3, r4, r5);
        return g;
    }

    public static Group getBooksIllustration() {
        Group g = new Group();
        
        Rectangle r1 = new Rectangle(2, 8, 14, 44); r1.setArcWidth(4); r1.setArcHeight(4); r1.setFill(Color.web("#7a6030"));
        Rectangle r2 = new Rectangle(4, 10, 10, 40); r2.setArcWidth(2); r2.setArcHeight(2); r2.setFill(Color.web("#9a8040"));
        Line l1 = new Line(5, 20, 13, 20); l1.setStroke(Color.web("#c8a44a")); l1.setStrokeWidth(1); l1.setOpacity(0.5);
        Line l2 = new Line(5, 25, 11, 25); l2.setStroke(Color.web("#c8a44a")); l2.setStrokeWidth(1); l2.setOpacity(0.4);
        
        Rectangle r3 = new Rectangle(18, 4, 16, 48); r3.setArcWidth(4); r3.setArcHeight(4); r3.setFill(Color.web("#c8a44a"));
        Rectangle r4 = new Rectangle(20, 6, 12, 44); r4.setArcWidth(2); r4.setArcHeight(2); r4.setFill(Color.web("#e2c06a"));
        Line l3 = new Line(21, 16, 31, 16); l3.setStroke(Color.web("#7a6030")); l3.setStrokeWidth(1.2); l3.setOpacity(0.6);
        Line l4 = new Line(21, 21, 29, 21); l4.setStroke(Color.web("#7a6030")); l4.setStrokeWidth(1); l4.setOpacity(0.5);
        
        Rectangle r5 = new Rectangle(36, 12, 12, 40); r5.setArcWidth(4); r5.setArcHeight(4); r5.setFill(Color.web("#514830"));
        Rectangle r6 = new Rectangle(38, 14, 8, 36); r6.setArcWidth(2); r6.setArcHeight(2); r6.setFill(Color.web("#6a6040"));
        
        Rectangle r7 = new Rectangle(50, 6, 18, 46); r7.setArcWidth(4); r7.setArcHeight(4); r7.setFill(Color.web("#8a5020"));
        Rectangle r8 = new Rectangle(52, 8, 14, 42); r8.setArcWidth(2); r8.setArcHeight(2); r8.setFill(Color.web("#b06828"));
        Line l5 = new Line(53, 18, 64, 18); l5.setStroke(Color.web("#e2c06a")); l5.setStrokeWidth(1); l5.setOpacity(0.4);
        
        Rectangle r9 = new Rectangle(70, 14, 8, 38); r9.setArcWidth(4); r9.setArcHeight(4); r9.setFill(Color.web("#c8a44a")); r9.setOpacity(0.5);
        Rectangle base = new Rectangle(2, 51, 76, 3); base.setArcWidth(2); base.setArcHeight(2); base.setFill(Color.web("#c8a44a")); base.setOpacity(0.3);
        
        g.getChildren().addAll(r1, r2, l1, l2, r3, r4, l3, l4, r5, r6, r7, r8, l5, r9, base);
        return g;
    }

    public static Group getMembersIllustration() {
        Group g = new Group();
        
        // Person 1
        Circle c1 = new Circle(18, 18, 9, Color.web("#2b2510")); c1.setStroke(Color.web("#c8a44a")); c1.setStrokeWidth(1.5);
        Circle c2 = new Circle(18, 15, 4, Color.web("#c8a44a")); c2.setOpacity(0.7);
        SVGPath p1 = new SVGPath(); p1.setContent("M9 28c0-5 4-8 9-8s9 3 9 8"); p1.setFill(Color.web("#c8a44a")); p1.setOpacity(0.4);
        
        // Person 2
        Circle c3 = new Circle(40, 18, 9, Color.web("#2b2510")); c3.setStroke(Color.web("#4a7ec8")); c3.setStrokeWidth(1.5);
        Circle c4 = new Circle(40, 15, 4, Color.web("#4a7ec8")); c4.setOpacity(0.7);
        SVGPath p2 = new SVGPath(); p2.setContent("M31 28c0-5 4-8 9-8s9 3 9 8"); p2.setFill(Color.web("#4a7ec8")); p2.setOpacity(0.4);
        
        // Person 3
        Circle c5 = new Circle(62, 18, 9, Color.web("#2b2510")); c5.setStroke(Color.web("#4fa86a")); c5.setStrokeWidth(1.5);
        Circle c6 = new Circle(62, 15, 4, Color.web("#4fa86a")); c6.setOpacity(0.7);
        SVGPath p3 = new SVGPath(); p3.setContent("M53 28c0-5 4-8 9-8s9 3 9 8"); p3.setFill(Color.web("#4fa86a")); p3.setOpacity(0.4);
        
        // ID Cards
        Rectangle r1 = new Rectangle(4, 38, 28, 16); r1.setArcWidth(6); r1.setArcHeight(6); r1.setFill(Color.web("#1d1b15")); r1.setStroke(Color.web("#2b281f"));
        Rectangle r2 = new Rectangle(6, 41, 8, 6); r2.setArcWidth(2); r2.setArcHeight(2); r2.setFill(Color.web("#c8a44a")); r2.setOpacity(0.3);
        Rectangle r3 = new Rectangle(17, 42, 12, 1.5); r3.setArcWidth(1.5); r3.setArcHeight(1.5); r3.setFill(Color.web("#514d3f"));
        Rectangle r4 = new Rectangle(17, 46, 8, 1.5); r4.setArcWidth(1.5); r4.setArcHeight(1.5); r4.setFill(Color.web("#3a3626"));
        
        Rectangle r5 = new Rectangle(26, 38, 28, 16); r5.setArcWidth(6); r5.setArcHeight(6); r5.setFill(Color.web("#1d1b15")); r5.setStroke(Color.web("#2b281f"));
        Rectangle r6 = new Rectangle(28, 41, 8, 6); r6.setArcWidth(2); r6.setArcHeight(2); r6.setFill(Color.web("#4a7ec8")); r6.setOpacity(0.3);
        Rectangle r7 = new Rectangle(39, 42, 12, 1.5); r7.setArcWidth(1.5); r7.setArcHeight(1.5); r7.setFill(Color.web("#514d3f"));
        Rectangle r8 = new Rectangle(39, 46, 8, 1.5); r8.setArcWidth(1.5); r8.setArcHeight(1.5); r8.setFill(Color.web("#3a3626"));
        
        Rectangle r9 = new Rectangle(48, 38, 28, 16); r9.setArcWidth(6); r9.setArcHeight(6); r9.setFill(Color.web("#1d1b15")); r9.setStroke(Color.web("#2b281f"));
        Rectangle r10 = new Rectangle(50, 41, 8, 6); r10.setArcWidth(2); r10.setArcHeight(2); r10.setFill(Color.web("#4fa86a")); r10.setOpacity(0.3);
        Rectangle r11 = new Rectangle(61, 42, 12, 1.5); r11.setArcWidth(1.5); r11.setArcHeight(1.5); r11.setFill(Color.web("#514d3f"));
        Rectangle r12 = new Rectangle(61, 46, 8, 1.5); r12.setArcWidth(1.5); r12.setArcHeight(1.5); r12.setFill(Color.web("#3a3626"));
        
        g.getChildren().addAll(c1, c2, p1, c3, c4, p2, c5, c6, p3, r1, r2, r3, r4, r5, r6, r7, r8, r9, r10, r11, r12);
        return g;
    }

    public static Group getIssueReturnIllustration() {
        Group g = new Group();
        
        Rectangle r1 = new Rectangle(4, 10, 28, 34); r1.setArcWidth(6); r1.setArcHeight(6); r1.setFill(Color.web("#1d1b15")); r1.setStroke(Color.web("#c8a44a")); r1.setStrokeWidth(1.2);
        Rectangle r2 = new Rectangle(6, 12, 24, 30); r2.setArcWidth(4); r2.setArcHeight(4); r2.setFill(Color.web("#2b2510"));
        Line l1 = new Line(8, 20, 28, 20); l1.setStroke(Color.web("#c8a44a")); l1.setOpacity(0.5);
        Line l2 = new Line(8, 25, 24, 25); l2.setStroke(Color.web("#c8a44a")); l2.setOpacity(0.4);
        Line l3 = new Line(8, 30, 26, 30); l3.setStroke(Color.web("#c8a44a")); l3.setOpacity(0.3);
        
        SVGPath p1 = new SVGPath(); p1.setContent("M36 20 L44 20 M41 16 L45 20 L41 24");
        p1.setStroke(Color.web("#4a7ec8")); p1.setStrokeWidth(1.5);
        p1.setStrokeLineCap(StrokeLineCap.ROUND); p1.setStrokeLineJoin(StrokeLineJoin.ROUND);
        
        Circle c1 = new Circle(60, 20, 8, Color.web("#2b2510")); c1.setStroke(Color.web("#4a7ec8")); c1.setStrokeWidth(1.2);
        Circle c2 = new Circle(60, 18, 3.5, Color.web("#4a7ec8")); c2.setOpacity(0.6);
        SVGPath p2 = new SVGPath(); p2.setContent("M52 30c0-4.5 3.5-7 8-7s8 2.5 8 7"); p2.setFill(Color.web("#4a7ec8")); p2.setOpacity(0.3);
        
        SVGPath p3 = new SVGPath(); p3.setContent("M44 40 L36 40 M39 36 L35 40 L39 44");
        p3.setStroke(Color.web("#4fa86a")); p3.setStrokeWidth(1.5);
        p3.setStrokeLineCap(StrokeLineCap.ROUND); p3.setStrokeLineJoin(StrokeLineJoin.ROUND);
        
        g.getChildren().addAll(r1, r2, l1, l2, l3, p1, c1, c2, p2, p3);
        return g;
    }

    public static Group getHeroBookshelf() {
        Group g = new Group();
        
        Rectangle shelf = new Rectangle(0, 130, 320, 6); shelf.setArcWidth(4); shelf.setArcHeight(4); shelf.setFill(Color.web("#c8a44a"));
        
        Rectangle b1 = new Rectangle(20, 60, 22, 70); b1.setArcWidth(6); b1.setArcHeight(6); b1.setFill(Color.web("#7a6030"));
        Rectangle b1a = new Rectangle(22, 65, 18, 60); b1a.setArcWidth(4); b1a.setArcHeight(4); b1a.setFill(Color.web("#9a7830"));
        
        Rectangle b2 = new Rectangle(44, 50, 18, 80); b2.setArcWidth(6); b2.setArcHeight(6); b2.setFill(Color.web("#c8a44a"));
        Rectangle b2a = new Rectangle(46, 55, 14, 70); b2a.setArcWidth(4); b2a.setArcHeight(4); b2a.setFill(Color.web("#e2c06a"));
        
        Rectangle b3 = new Rectangle(64, 70, 20, 60); b3.setArcWidth(6); b3.setArcHeight(6); b3.setFill(Color.web("#514830"));
        Rectangle b3a = new Rectangle(66, 74, 16, 52); b3a.setArcWidth(4); b3a.setArcHeight(4); b3a.setFill(Color.web("#6a6040"));
        
        Rectangle b4 = new Rectangle(86, 45, 24, 85); b4.setArcWidth(6); b4.setArcHeight(6); b4.setFill(Color.web("#8a5020"));
        Rectangle b4a = new Rectangle(88, 50, 20, 75); b4a.setArcWidth(4); b4a.setArcHeight(4); b4a.setFill(Color.web("#b06828"));
        
        Rectangle b5 = new Rectangle(112, 65, 16, 65); b5.setArcWidth(6); b5.setArcHeight(6); b5.setFill(Color.web("#c8a44a")); b5.setOpacity(0.6);
        Rectangle b5a = new Rectangle(114, 68, 12, 57); b5a.setArcWidth(4); b5a.setArcHeight(4); b5a.setFill(Color.web("#e2c06a")); b5a.setOpacity(0.5);
        
        Rectangle b6 = new Rectangle(130, 55, 20, 75); b6.setArcWidth(6); b6.setArcHeight(6); b6.setFill(Color.web("#4a4030"));
        Rectangle b6a = new Rectangle(132, 58, 16, 67); b6a.setArcWidth(4); b6a.setArcHeight(4); b6a.setFill(Color.web("#5a5040"));
        
        Rectangle b7 = new Rectangle(152, 40, 26, 90); b7.setArcWidth(6); b7.setArcHeight(6); b7.setFill(Color.web("#7a6030"));
        Rectangle b7a = new Rectangle(154, 45, 22, 80); b7a.setArcWidth(4); b7a.setArcHeight(4); b7a.setFill(Color.web("#c8a44a")); b7a.setOpacity(0.8);
        
        Rectangle b8 = new Rectangle(180, 68, 18, 62); b8.setArcWidth(6); b8.setArcHeight(6); b8.setFill(Color.web("#9a4020"));
        Rectangle b8a = new Rectangle(182, 72, 14, 54); b8a.setArcWidth(4); b8a.setArcHeight(4); b8a.setFill(Color.web("#c05028"));
        
        Rectangle b9 = new Rectangle(200, 58, 22, 72); b9.setArcWidth(6); b9.setArcHeight(6); b9.setFill(Color.web("#404030"));
        Rectangle b9a = new Rectangle(202, 62, 18, 64); b9a.setArcWidth(4); b9a.setArcHeight(4); b9a.setFill(Color.web("#505040"));
        
        Rectangle b10 = new Rectangle(224, 48, 20, 82); b10.setArcWidth(6); b10.setArcHeight(6); b10.setFill(Color.web("#c8a44a")); b10.setOpacity(0.4);
        Rectangle b10a = new Rectangle(226, 52, 16, 74); b10a.setArcWidth(4); b10a.setArcHeight(4); b10a.setFill(Color.web("#e2c06a")); b10a.setOpacity(0.3);
        
        Rectangle b11 = new Rectangle(246, 62, 24, 68); b11.setArcWidth(6); b11.setArcHeight(6); b11.setFill(Color.web("#6a5028"));
        Rectangle b11a = new Rectangle(248, 66, 20, 60); b11a.setArcWidth(4); b11a.setArcHeight(4); b11a.setFill(Color.web("#8a6838"));
        
        Rectangle b12 = new Rectangle(272, 52, 18, 78); b12.setArcWidth(6); b12.setArcHeight(6); b12.setFill(Color.web("#7a6030"));
        Rectangle b12a = new Rectangle(274, 56, 14, 70); b12a.setArcWidth(4); b12a.setArcHeight(4); b12a.setFill(Color.web("#a08040"));
        
        Line l1 = new Line(46, 78, 60, 78); l1.setStroke(Color.web("#c8a44a")); l1.setOpacity(0.4);
        Line l2 = new Line(46, 84, 58, 84); l2.setStroke(Color.web("#c8a44a")); l2.setOpacity(0.3);
        Line l3 = new Line(154, 65, 174, 65); l3.setStroke(Color.web("#0d0c09")); l3.setOpacity(0.4);
        Line l4 = new Line(154, 72, 170, 72); l4.setStroke(Color.web("#0d0c09")); l4.setOpacity(0.3);
        
        g.getChildren().addAll(shelf, b1, b1a, b2, b2a, b3, b3a, b4, b4a, b5, b5a, b6, b6a, b7, b7a, b8, b8a, b9, b9a, b10, b10a, b11, b11a, b12, b12a, l1, l2, l3, l4);
        return g;
    }
}
