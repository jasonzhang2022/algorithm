[reference](https://www.topcoder.com/community/data-science/data-science-tutorials/geometry-concepts-basic-concepts/)
#Vector
Vector A->B is a **directed line**. The **direction** is from A to B, The **magnitude** is |AB| is the length of the segment from A->B. Distance for A->B segment is sqrt( (x2-x1)^2 + (y2-y1)^2)

**How to express a line**
1. A line can be defined as two points. 
2. A line can be expressed as y=Slope*x+b. Where slope will be (point1.y-point2.y)/(point1.x-point2.x). b=point1.y-Slope*point1.x
3. A line can be expressed as Ax+By=C, the perpendicular line is -Bx+Ay=D. This expression is better since we don't need treating vertical line as a special case.  The slope will be -A/B.

 We only have two points, but we have three variables here. So we have a infinite set of (A, B, C) combination. We can fix 
+ A = y2-y1
+ B = x1-x2. 
+ C = A*x1+B*y1
If A==0, We have a vertical line
If B==0, we have horizontal line

#Relation between two segments.
## Dot production:
A->B*B->C=(Xb-Xa)*(Xc-Xb)+(Yb-Ya)*(Yc-Yb)=|AB||BC|Cos(ridian).
A->B*B->C > 0  <=>  radian is between 1 and 0
A->B*B->C = 0  <=>  radian is 0
A->B*B->C < 0  <=>  radian is between 0 and -1

**Have a Cos(Radian) curve in mind.

See DotProductProperty Class
Line perpendicular to Ax + By=C can be expressed as -Bx + Ay=D

##Cross product.
A->BxB->C=((Xb-Xa)*(Yc-Yb)-(Xc-Xb)*(Yb-Ya). 
Three practical interpretation: judge **orientation.**

value is positive, A->B to B->C is counter closewise.
value is zeo, A->B to B->C is collinear.
value is negative, A->B to B->C is closewise.

**value=|AB||BC|sin(radian)**

It is the **area** of the parallelogram or twice of the triangular formed by point A, B, C. This is can be used to calculate the area of simple ploygon. It is also a easy way to calculate triangular area giving its vertices.



##Distance between point and line/segment.
Between point to line. (A->BxB->C)/|AB|
Between point to segments: see reference

#Line intersection


#parallel line and perpendicular line
[reference](http://www.purplemath.com/modules/strtlneq3.htm)
Line1: y=Ax+B. A is slope.
parallel line: Y=Ax+C
perpendicular line: Y=(-1/A)x+C










