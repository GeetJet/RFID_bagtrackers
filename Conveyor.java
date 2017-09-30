/*
 * Between frame no 0 to 100 the cube moves straight
 * line
 * Between frame no 100 to 200 the cube moves in curve
 * Between frame no 200 to 300 the cube moves in straight line
 * Between frame no 300 to 400 the cube moves in curve
 */


import java.nio.FloatBuffer;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.util.gl2.GLUT;
import com.jogamp.opengl.util.FPSAnimator; 
import javax.swing.JFrame;
public class Conveyor implements GLEventListener {

	float angle=0;
	GLUT glut;
	int fno1=0,fno2=300;
	double x=0,z=0,x1=0,z1=10;
	FloatBuffer position;
	float[] pos={0,0,1,1};
	private void square(GL2 gl2, double r, double g, double b) {
		gl2.glColor3d(r,g,b);
		gl2.glBegin(GL2.GL_TRIANGLE_FAN);
		gl2.glVertex3d(-0.5, -0.5, 0.5);
		gl2.glVertex3d(0.5, -0.5, 0.5);
		gl2.glVertex3d(0.5, 0.5, 0.5);
		gl2.glVertex3d(-0.5, 0.5, 0.5);
		gl2.glEnd();
	}

	private void cube(GL2 gl2, double size) {
		gl2.glPushMatrix();
		gl2.glScaled(size,size,size); // scale unit cube to desired size

		square(gl2,1, 0, 0); // red front face

		gl2.glPushMatrix();
		gl2.glRotated(90, 0, 1, 0);
		square(gl2,0, 1, 0); // green right face
		gl2.glPopMatrix();

		gl2.glPushMatrix();
		gl2.glRotated(-90, 1, 0, 0);
		square(gl2,0, 0, 1); // blue top face
		gl2.glPopMatrix();

		gl2.glPushMatrix();
		gl2.glRotated(180, 0, 1, 0);
		square(gl2,0, 1, 1); // cyan back face
		gl2.glPopMatrix();

		gl2.glPushMatrix();
		gl2.glRotated(-90, 0, 1, 0);
		square(gl2,1, 0, 1); // magenta left face
		gl2.glPopMatrix();

		gl2.glPushMatrix();
		gl2.glRotated(90, 1, 0, 0);
		square(gl2,1, 1, 0); // yellow bottom face
		gl2.glPopMatrix();

		gl2.glPopMatrix(); // Restore matrix to its state before cube() was called.
	}
	double rotateX = 15;    // rotations of the cube about the axes
	double rotateY = 15;
	double rotateZ = 0;
	@Override
	public void display(GLAutoDrawable arg0) {

		glut=new GLUT();
		
		GL2 gl = arg0.getGL().getGL2();
		gl.glMatrixMode(GL2.GL_MODELVIEW);
		gl.glLoadIdentity();
		glu.gluLookAt(50,  50, 50, 0, 0,  0, 0,  1,  0);
		gl.glClear(GL2.GL_COLOR_BUFFER_BIT|GL2.GL_DEPTH_BUFFER_BIT);
		gl.glPushMatrix();
	if(fno1>=0&&fno1<=100)
		x+=0.1f;
	if(fno1>100&&fno1<200)
	{
		double angle1=90-((fno1-100)*180)/100;
		
		x=10+5*Math.cos((angle1*3.141f)/180);
		z=5-5*Math.sin((angle1*3.141f)/180);
		
	}
		
	if(fno1>=200&&fno1<300) x-=0.1f;;
	if(fno1>=300&&fno1<400)
	{
double angle1=270-((fno1-100)*180)/100;
		
		x=5*Math.cos((angle1*3.141f)/180);
		z=5-5*Math.sin((angle1*3.141f)/180);
	}
	if(fno2>=0&&fno2<=100)
		x1+=0.1f;
	if(fno2>100&&fno2<200)
	{
		double angle1=90-((fno2-100)*180)/100;
		
		x1=10+5*Math.cos((angle1*3.141f)/180);
		z1=5-5*Math.sin((angle1*3.141f)/180);
		
	}
		
	if(fno2>=200&&fno2<300) x1-=0.1f;;
	if(fno2>=300&&fno2<400)
	{
double angle1=270-((fno2-100)*180)/100;
		
		x1=5*Math.cos((angle1*3.141f)/180);
		z1=5-5*Math.sin((angle1*3.141f)/180);
	}	
	gl.glTranslated(x,0,z);
		cube(gl,2.0f);
		
		gl.glPopMatrix();
		gl.glPushMatrix();
		gl.glTranslated(x1,0,z1);
		cube(gl,2.0f);
		gl.glFlush();
		fno1++;
		if(fno1>400) fno1=0;
		fno2++;
		if(fno2>400) fno2=0;
	}

	@Override
	public void dispose(GLAutoDrawable arg0) {
		//method body
	}
	private GLU glu;
	@Override
	public void init(GLAutoDrawable arg0) {
		GL2 gl = arg0.getGL().getGL2();
		float[] material={0f,1f,0,1}; 
		glu = new GLU();

		gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
		/*gl.glEnable(GL2.GL_LIGHTING);
		gl.glEnable(GL2.GL_LIGHT0);

		gl.glMaterialfv(GL2.GL_FRONT_AND_BACK,GL2.GL_AMBIENT_AND_DIFFUSE,material,0);
		gl.glMaterialfv(GL2.GL_FRONT_AND_BACK,GL2.GL_SPECULAR,new float[] {1.5f,0f,0f,1},0);


		gl.glLightModeli(GL2.GL_LIGHT_MODEL_TWO_SIDE,GL2.GL_TRUE);
		gl.glEnable(GL2.GL_NORMALIZE);*/
		gl.glEnable(GL2.GL_DEPTH_TEST);


		gl.glMatrixMode(GL2.GL_PROJECTION);
		gl.glLoadIdentity();
		GLU glu = GLU.createGLU();
		glu.gluPerspective(45.0f, 1, 0.1f,100.0f);
		gl.glMatrixMode(GL2.GL_MODELVIEW);
		gl.glLoadIdentity();


	}

	@Override
	public void reshape(GLAutoDrawable arg0, int arg1, int arg2, int w, int h) {
		// method body
		GL2 gl=arg0.getGL().getGL2();
		gl.glViewport(0, 0,  w,  h);
		/*gl.glMatrixMode(GL2.GL_PROJECTION);
		gl.glLoadIdentity();
		if (w <= h)
			gl.glOrtho(-100, 100, -100*(float)h/(float)w,
					100*(float)h/(float)w, -100.0, 100.0);
		else
			gl.glOrtho(-100*(float)w/(float)h,
					100*(float)w/(float)h, -100, 100, -100.0, 100.0);
		gl.glMatrixMode(GL2.GL_MODELVIEW);
		gl.glLoadIdentity();*/
	}

	public static void main(String[] args) {

		//getting the capabilities object of GL2 profile
		final GLProfile profile = GLProfile.get(GLProfile.GL2);
		GLCapabilities capabilities = new GLCapabilities(profile);

		// The canvas
		final GLCanvas glcanvas = new GLCanvas(capabilities);
		Conveyor b = new Conveyor();
		glcanvas.addGLEventListener(b);        
		glcanvas.setSize( 1300, 768 );

		//creating frame
		final JFrame frame = new JFrame (" Basic Frame");

		//adding canvas to frame
		frame.getContentPane().add( glcanvas ); 
		frame.setSize(frame.getContentPane().getPreferredSize());                 
		frame.setVisible( true ); 
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		final FPSAnimator fps=new FPSAnimator(glcanvas,50,true);
		fps.start();
	}

}


