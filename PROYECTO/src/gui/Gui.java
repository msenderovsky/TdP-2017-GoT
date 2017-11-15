package gui;

import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import defensa.Defensa;
import enemigos.Enemigo;
import logica.*;
import objetos.*;
import premio.*;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import javax.swing.border.LineBorder;
import java.awt.SystemColor;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;

public class Gui implements Runnable
{

	private JFrame frame;
	
	private Juego juego;
	
	private final int ALTO = 8;
	private final int ANCHO = 16;
	private final int ALTO_IMG = 50; // antes 32
	private final int ANCHO_IMG = 50; // antes 32
	private final int NIVELCELDA = 2;
	private final int NIVELDEFENSA = 0;
	private final int NIVELENEMIGO = 1;
	
	private Puntaje p;
	private FabricaDeDefensa fabricaDeDefensa = FabricaDeDefensa.getInstancia();
	private CostosDeDefensa costosDeDefensa = CostosDeDefensa.getInstncia();
	
	private JLabel labelPuntaje;
	
	private boolean aEliminar=false;
	
	
	private JLayeredPane panelMapa;
	
	private JPanel panelPersonajes;
	private JPanel panelCeldas;
	private JPanel panelDefensa;
	private JPanel panelEnemigos;
	private JPanel panelControl;
	private JPanel panelPremios;
	
	private JButton botonBomba;
	private JButton botonMina;
	private JButton botonCampo;
	private JButton botonDanio;
	private JButton botonCuracion;
	private JButton botonYgritte;
	private JButton botonMountain;
	private JButton botonDragon; 
	private JButton botonInmaculado;
	private JButton botonGendry;
	private JButton botonBronn;
	
	private JLabel lblMonedas;
	
	
	
	private List<ObjetoMovil> moviles;
	private List<ObjetoMovil> aBorrar;
	private List<ObjetoMovil> aAgregar;

	private JLabel fondo;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Gui window = new Gui();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Gui() 
	{
	
		frame = new JFrame("NIGHT KING DEFENSE");
		frame.setBounds(100, 100, 1000, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		frame.getContentPane().setLayout(new BorderLayout(0, 0));
		initialize();
		
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() 
	{
		moviles= new LinkedList<ObjetoMovil>();
		aBorrar= new LinkedList<ObjetoMovil>();
		aAgregar= new LinkedList<ObjetoMovil>();
		panelMapa = new JLayeredPane();
		panelMapa.setBorder(new LineBorder(new Color(0, 0, 0)));
		panelMapa.setBounds(0,0,ANCHO*ANCHO_IMG,ALTO*ALTO_IMG);
		panelMapa.setLocation(200,100);
		panelMapa.setBackground(Color.GRAY);
		
		
		panelCeldas = new JPanel();
		panelCeldas.setLayout(null);
		panelCeldas.setBounds(panelMapa.getBounds());
		panelCeldas.setBackground(new Color(0,0,0,0));
		panelMapa.add(panelCeldas, NIVELCELDA);
		
		panelDefensa = new JPanel();
		panelDefensa.setLayout(null);
		panelDefensa.setBounds(panelMapa.getBounds());
		panelDefensa.setBackground(new Color(0,0,0,0));
		panelMapa.add(panelDefensa, NIVELDEFENSA);
		
		panelEnemigos = new JPanel();
		panelEnemigos.setLayout(null);
		panelEnemigos.setBounds(panelMapa.getBounds());
		panelEnemigos.setBackground(new Color(0,0,0,0));
		panelMapa.add(panelEnemigos, NIVELENEMIGO);
		
		frame.getContentPane().repaint();
		frame.getContentPane().add(panelMapa, BorderLayout.CENTER);
		panelMapa.setLayout(null);
		
		
		
		p=new Puntaje();
		
		
		//--------- PANEL CONTROL
		
		panelControl = new JPanel();
		panelControl.setBounds(122, 21, 213, 48);
		panelMapa.add(panelControl);
		panelControl.setLayout(null);
		
		labelPuntaje = new JLabel("Puntaje: 0");
		labelPuntaje.setBounds(10, 28, 70, 14);
		panelControl.add(labelPuntaje);
	

		lblMonedas = new JLabel("Monedas:  0");
		lblMonedas.setBounds(108, 28, 95, 14);
		panelControl.add(lblMonedas);
		
		//-----------PANEL PREMIOS
		panelPremios = new JPanel();
		panelPremios.setBorder(new LineBorder(new Color(0, 0, 0)));
		panelPremios.setBounds(353, 11, 302, 81);
		panelMapa.add(panelPremios);
		panelPremios.setLayout(null);
		
		
		
		botonBomba = new JButton("");
		botonBomba.setEnabled(false);
		botonBomba.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0) 
			{
				FabricaDeDefensa.getInstancia().construirBomba();
				juego.restarBomba();	
				if (!juego.hayBombas())
					botonBomba.setEnabled(false);
			}
		});
		botonBomba.setIcon(new ImageIcon("/res/imagenes/premios/objetosPreciosos/iconoBomba.png"));
		botonBomba.setBounds(172, 11, 92, 59);
		panelPremios.add(botonBomba);
		
		botonMina = new JButton("");
		botonMina.setEnabled(false);
		botonMina.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0) 
			{
				FabricaDeDefensa.getInstancia().construirMina();
				juego.restarMina();	
				if (!juego.hayMinas())
					botonMina.setEnabled(false);
			}
		});
		botonMina.setIcon(new ImageIcon("/res/imagenes/premios/objetosPreciosos/iconoMina.png"));
		botonMina.setBounds(10, 11, 92, 59);
		panelPremios.add(botonMina);	
		
		botonCampo = new JButton("");
		botonCampo.setEnabled(false);
		botonCampo.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0) 
			{
				FabricaDeDefensa.getInstancia().construirCampo();
				juego.restarCampo();	
				if (!juego.hayCampos())
					botonCampo.setEnabled(false);
			}
		});
		botonCampo.setIcon(new ImageIcon("/res/imagenes/premios/objetosPreciosos/iconoCampo.png"));
		botonCampo.setBounds(10, 11, 92, 59);
		panelPremios.add(botonCampo);	
		
		botonDanio = new JButton("");
		botonDanio.setEnabled(false);
		botonDanio.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0) 
			{
				FabricaDeDefensa.getInstancia().construirDanio();
				juego.restarDanio();	
				if (!juego.hayDanio())
					botonDanio.setEnabled(false);
			}
		});
		botonDanio.setIcon(new ImageIcon("/res/imagenes/premios/objetosPreciosos/iconoDanio.png"));
		botonDanio.setBounds(10, 11, 92, 59);
		panelPremios.add(botonDanio);
		
		
		botonCuracion = new JButton("");
		botonCuracion.setEnabled(false);
		botonCuracion.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0) 
			{
				FabricaDeDefensa.getInstancia().construirCuracion();
				juego.restarCuracion();	
				if (!juego.hayCuracion())
					botonCuracion.setEnabled(false);
			}
		});
		botonCuracion.setIcon(new ImageIcon("/res/imagenes/premios/objetosPreciosos/iconoCuracion.png"));
		botonCuracion.setBounds(10, 11, 92, 59);
		panelPremios.add(botonCuracion);
		
	

		
		
	
		//----------------- PANEL PERSONAJE - SBOTONES PERSONAJES
			
		panelPersonajes = new JPanel();
		panelPersonajes.setBounds(10, 11, 102, 539);
		panelMapa.add(panelPersonajes);
		panelPersonajes.setBackground(null);
		panelPersonajes.setLayout(null);
		
		// BOTON YGRITTE
		botonYgritte = new JButton("Ygritte");
		botonYgritte.setBounds(10, 0, 80, 80);
		panelPersonajes.add(botonYgritte);
		botonYgritte.addActionListener(new ActionListener() 
 		{
 			public void actionPerformed(ActionEvent arg0) 
 			{
 				FabricaDeDefensa.getInstancia().construirYgritte(); 
 				p.restarOro(600);	
 				if (p.getOro()<600)
 					botonYgritte.setEnabled(false);
 					
 			}
 		});
		botonYgritte.setIcon(new ImageIcon("res/imagenes/juego/botonYgritte.png"));
		
		// BOTON MOUNTAIN
		botonMountain = new JButton("Mountain");
		botonMountain.setBounds(10, 91, 80, 80);
		panelPersonajes.add(botonMountain);
		botonMountain.addActionListener(new ActionListener() 
 		{
 			public void actionPerformed(ActionEvent e) 
 			{
 				FabricaDeDefensa.getInstancia().construirMountain();
 				p.restarOro(500);	
 				if (p.getOro()<500)
 					botonMountain.setEnabled(false);
 			}
 		});
		botonMountain.setIcon(new ImageIcon("res/imagenes/juego/botonMountain.png"));
		
		// BOTON DRAGON
 		botonDragon = new JButton("Dragon");
 		botonDragon.setBounds(10, 182, 80, 80);
 		panelPersonajes.add(botonDragon);
 		botonDragon.addActionListener(new ActionListener() 
 		{
 			public void actionPerformed(ActionEvent e) 
 			{
 				FabricaDeDefensa.getInstancia().construirDragon();
 				p.restarOro(1000);	
 				if (p.getOro()<1000)
 					botonDragon.setEnabled(false);
 					
 			}
 		});
 		botonDragon.setIcon( new ImageIcon("res/imagenes/juego/botonDragon.png"));
 		
 		// BOTON INMACULADO
 		botonInmaculado = new JButton("Inmaculado");
 		botonInmaculado.setBounds(10, 273, 80, 80);
 		panelPersonajes.add(botonInmaculado);
 		botonInmaculado.addActionListener(new ActionListener()
 		{
 			public void actionPerformed(ActionEvent e)
 			{
 				FabricaDeDefensa.getInstancia().construirInmaculado();
 				p.restarOro(200);	
 				if (p.getOro()<200)
 					botonInmaculado.setEnabled(false);
 					
 			}
 		});
 		botonInmaculado.setIcon(new ImageIcon("res/imagenes/juego/botonInmaculado.png"));
 		
 		// BOTON GENDRY
 		botonGendry = new JButton("Gendry");
 		botonGendry.setBounds(10, 455, 80, 80);
 		panelPersonajes.add(botonGendry);
 		botonGendry.addActionListener(new ActionListener() 
 		{
 			public void actionPerformed(ActionEvent e) 
 			{
 				FabricaDeDefensa.getInstancia().construirGendry();
 				p.restarOro(400);	
 				if (p.getOro()<400)
 					botonGendry.setEnabled(false);
 			}
 		});
 		botonGendry.setIcon(new ImageIcon("res/imagenes/juego/botonGendry.png"));
 		
 		// BOTON BRONN
 		botonBronn = new JButton("Bronn");
 		botonBronn.setBounds(10, 364, 80, 80);
 		panelPersonajes.add(botonBronn);
 		botonBronn.addActionListener(new ActionListener()
 		{
 			public void actionPerformed(ActionEvent arg0) 
 			{
 				FabricaDeDefensa.getInstancia().construirBronn();
 				p.restarOro(300);	
 				if (p.getOro()<300)
 					botonBomba.setEnabled(false);
 			}
 		});
 		botonBronn.setIcon( new ImageIcon("res/imagenes/juego/botonBronn.png"));
 		
 		
 		
 		
 		
 		
 		//-------------- FONDO DE JUEGO 
 		
 		
 		fondo = new JLabel("FONDO_JUEGO");
 		fondo.setBounds(10, 11, 1000, 600);
 		fondo.setIcon(new ImageIcon("res/imagenes/juego/fondo.jpg"));
 		panelMapa.add(fondo);
 		
 		
 		
 	

		
		//---------------
		
		juego = new Juego(this, ALTO, ANCHO);
		GameObjectGrafico[][] graficos = juego.getCeldasGraficas();
		
		
		for(int i = 0; i < ANCHO; i++) {
			
			for (int j = 0; j < ALTO; j++) {
				
				ImageIcon imagen = graficos[i][j].getImagen();
				int ancho = imagen.getIconWidth();
				int alto = imagen.getIconHeight();
				int x = i*ancho;
				int y = j*alto;
				JLabel label = new JLabel();
				label.setBounds(x ,y,alto,ancho);
				label.setIcon(imagen);
				label.addMouseListener(getMouseListener());
				panelCeldas.add(label);
				
			}
		}
		
		juego.crearMuro();
	}
	
	public void puntaje(int puntaje){
		
		labelPuntaje.setText("Puntaje: "+puntaje);
	}

	public void agregarObjetoMovil(int x, int y, GameObject obj) {
		
		JLabel labelEnemigo = new JLabel();
		obj.getGrafico().setLabel(labelEnemigo);
		labelEnemigo.setIcon(obj.getGrafico().getImagen());
		labelEnemigo.setBounds(x*ANCHO_IMG, y*ALTO_IMG,50,50);
		panelEnemigos.add(labelEnemigo);
		repintar();
	}
	
	public void moverEnemigoGrafico(ObjetoMovil o) {
		
		aAgregar.add(o);
	}
	
	public void run(){
		boolean estado=true;
		while(estado){
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			for(ObjetoMovil o : aAgregar){
				moviles.add(o);
			}
			aAgregar.clear();
			
			for(ObjetoMovil o : moviles) {
				if(!o.getGrafico().moverGrafico(o.getVelocidad())){
					aBorrar.add(o);
					o.getGrafico().Lock(false);
				}
				repintar();
			}
			
			for(ObjetoMovil o : aBorrar){
				moviles.remove(o);
			}
			aBorrar.clear();
		}
	}
	
	public void eliminarMovil(int x, int y) {
		System.out.println("GUI Quiero eliminar en x "+x+" y "+y);
		JLabel aEliminar = buscarLabel(x, y, panelEnemigos);
		panelEnemigos.remove(aEliminar);
		repintar();
		double posibilidad=Math.random();
		if ((posibilidad%2)==0){
			double cual= Math.random();
			if (cual<0.2){
				JLabel LBomba = new JLabel();	
				LBomba.setIcon(FabricaObjetoGrafico.getInstancia().construirGraficoBomba().getImagen());
				LBomba.setBounds(x*ANCHO_IMG, y*ALTO_IMG,50,50);
				panelEnemigos.add(LBomba);
				LBomba.addMouseListener(listenBomba());
			}
			else if (cual<0.4){
				JLabel LMina = new JLabel();	
				LMina.setIcon(FabricaObjetoGrafico.getInstancia().construirGraficoMina().getImagen());
				LMina.setBounds(x*ANCHO_IMG, y*ALTO_IMG,50,50);
				panelEnemigos.add(LMina);
				LMina.addMouseListener(listenMina());
			}
			else if (cual<0.6){
				JLabel LCampo = new JLabel();	
				LCampo.setIcon(FabricaObjetoGrafico.getInstancia().construirGraficoCampoProtector().getImagen());
				LCampo.setBounds(x*ANCHO_IMG, y*ALTO_IMG,50,50);
				panelEnemigos.add(LCampo);
				LCampo.addMouseListener(listenCampo());
			}
			else if (cual<0.8){
				JLabel LDanio = new JLabel();	
				LDanio.setIcon(FabricaObjetoGrafico.getInstancia().construirGraficoDanio().getImagen());
				LDanio.setBounds(x*ANCHO_IMG, y*ALTO_IMG,50,50);
				panelEnemigos.add(LDanio);
				LDanio.addMouseListener(listenDanio());
			}
			else if (cual<1){
				JLabel LCuracion = new JLabel();	
				LCuracion.setIcon(FabricaObjetoGrafico.getInstancia().construirGraficoCuracion().getImagen());
				LCuracion.setBounds(x*ANCHO_IMG, y*ALTO_IMG,50,50);
				panelEnemigos.add(LCuracion);
				LCuracion.addMouseListener(listenCuracion());
			}
		}						
	}
		
	public MouseListener listenBomba() {
		return new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				
				juego.guardarBomba();
				botonBomba.setEnabled(true);
				repintar();
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
		};
	}
	
	public MouseListener listenMina() {
		return new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				
				juego.guardarMina();
				botonMina.setEnabled(true);
				repintar();
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
		};
	}
	
	public MouseListener listenCampo() {
		return new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				
				juego.guardarCampo();
				botonCampo.setEnabled(true);
				repintar();
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
		};
	}

	public MouseListener listenDanio() {
		return new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				
				juego.guardarDanio();
				botonDanio.setEnabled(true);
				repintar();
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
		};
	}
		
	public MouseListener listenCuracion() {
		return new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				
				juego.guardarCuracion();
				botonCuracion.setEnabled(true);
				repintar();
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
		};
	}
	
	private JLabel buscarLabel(int x, int y, JPanel panel) {
		Component[] arrComponents = panel.getComponents();
		JLabel label = null;
		boolean encontre = false;
		for(int i = 0; !encontre && i < arrComponents.length; i++ ) {
			Component comp = arrComponents[i];
			if(comp.getBounds().x == x*ANCHO_IMG && comp.getBounds().y == y*ALTO_IMG) {
				encontre = true;
				label = (JLabel) arrComponents[i];
			}
		}
		return label;
	}
	
	private void repintar() {
		///panelMapa.validate();
		panelMapa.repaint();

	}
	
	public MouseListener getMouseListener() {
		return new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				JLabel labelCelda = (JLabel) e.getComponent();
				int x= labelCelda.getBounds().x / ANCHO_IMG;
				int y= labelCelda.getBounds().y / ALTO_IMG;
				if(aEliminar)
				{
					
					JLabel remover = buscarLabel(x, y, panelDefensa);
					if(remover!=null){
						juego.eliminarDefensa(x, y);
					}
					aEliminar=false;
				}
				else {
					Defensa defensa = fabricaDeDefensa.getDefensa();
					if(defensa != null) 
					{
						juego.agregarDefensa(x,y);
						ImageIcon imagen = defensa.getGrafico().getImagen();
						JLabel labelNuevo = new JLabel(imagen);
						defensa.getGrafico().setLabel(labelNuevo);
						labelNuevo.setBounds(labelCelda.getBounds());
						panelDefensa.add(labelNuevo);
						repintar();
					 }
				   }
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
		};
	}
	
	public void dibujarDefensa(int x, int y, GameObject obj) {
		JLabel labelCelda = buscarLabel(x, y, panelCeldas);
		JLabel labelNuevo = new JLabel(obj.getGrafico().getImagen());
		obj.getGrafico().setLabel(labelNuevo);
		labelNuevo.setBounds(labelCelda.getBounds());
		panelDefensa.add(labelNuevo);
		repintar();
	}

	public void gameOver() {
		int reply = JOptionPane.showConfirmDialog(frame, "Has perdido!\nQuieres jugar de nuevo?", "Game Over", JOptionPane.YES_NO_OPTION);
		if (reply == JOptionPane.YES_OPTION) {
			frame.getContentPane().removeAll();
			frame.getContentPane().repaint();
			
			initialize();
		}
		else {
			JOptionPane.showMessageDialog(null, "Gracias por jugar!");
			System.exit(0);
		}
		
	}

	public void oroActual(int oro) {
		lblMonedas.setText("Monedas: "+oro);
		//btnJorgito.setEnabled((oro < costosDeDefensa.costoJorgito()) ? false : true);
		
		botonYgritte.setEnabled((oro < costosDeDefensa.costoYgritte()) ? false : true);
		
		botonMountain.setEnabled((oro < costosDeDefensa.costoMountain()) ? false : true); 
		
		botonDragon.setEnabled((oro < costosDeDefensa.costoDragon()) ? false : true);
		
		botonInmaculado.setEnabled((oro < costosDeDefensa.costoInmaculado()) ? false : true);
		
		botonGendry.setEnabled((oro < costosDeDefensa.costoGendry()) ? false : true);
		
		botonBronn.setEnabled((oro < costosDeDefensa.costoBronn()) ? false : true);
		
	}
}
