package gui;

import java.awt.EventQueue;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

import logica.CostosDeDefensa;
import logica.FabricaDeDefensa;
import logica.Juego;
import logica.Puntaje;

import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.Insets;

import javax.swing.JTextField;

import javax.swing.JOptionPane;
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

public class Ventana implements Runnable{

private JFrame frame;
	
	private Juego juego;
	
	private final int ALTO = 8;
	private final int ANCHO = 16;
	private final int ALTO_IMG = 50; // antes 32
	private final int ANCHO_IMG = 50; // antes 32
	private final int NIVELCELDA = 3;
	private final int NIVELDEFENSA = 1;
	private final int NIVELENEMIGO = 2;
	private final int NIVELPREMIO = 0;
	private boolean clickCampo = false;
	
	private Puntaje p;
	private FabricaDeDefensa fabricaDeDefensa = FabricaDeDefensa.getInstancia();
	private CostosDeDefensa costosDeDefensa = CostosDeDefensa.getInstancia();
	
	private boolean aEliminar=false;
	private boolean seUso=false;
	private JButton botonPremioBomba;
	private JButton botonPremioBarricada;
	private JButton botonPremioOro;
	private JButton botonPremioCuracion;
	private JButton botonPremioDanioDoble;
	private JButton botonPremioCampoProtector;
	
	private List<ObjetoMovil> moviles;
	private List<ObjetoMovil> aBorrar;
	private List<ObjetoMovil> aAgregar;
	private JLayeredPane panelMapa;
	private JPanel panelEnemigos;
	private JPanel panelCeldas;
	private JPanel panelDefensa;
	private JPanel panelCeldaPremios;
	private JPanel panelPersonajes;
	private JPanel panelObstaculos;
	
	
	private JButton botonYgritte;
	private JButton botonMountain;
	private JButton botonDragon;
	private JButton botonInmaculado;
	private JButton botonBronn;
	private JButton botonGendry;
	private JButton botonBarricada;
	private JButton botonCampo;
	private JButton botonBomba;
	private JLabel lblPuntaje;
	private JLabel lblMonedas;
	private JLabel lblMonedas_1;
	private JButton botonRoca;
	private JButton botonTrinchera;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Ventana window = new Ventana();
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
	public Ventana() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame("NIGHT KING DEFENSE");
		frame.setBounds(new Rectangle(0, 0, 1000, 600));
		frame.getContentPane().setBounds(new Rectangle(0, 0, 1000,600));
		frame.setBounds(100, 100, 1000, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		moviles= new LinkedList<ObjetoMovil>();
		aBorrar= new LinkedList<ObjetoMovil>();
		aAgregar= new LinkedList<ObjetoMovil>();
		
		panelMapa = new JLayeredPane();
		panelMapa.setBounds(180, 150, 800, 400);
		frame.getContentPane().add(panelMapa);
				
		panelDefensa = new JPanel();
		panelDefensa.setOpaque(false);
		panelDefensa.setBounds(0, 0, 800, 400);
		panelDefensa.setLayout(null);
		panelMapa.add(panelDefensa);
		
		panelEnemigos = new JPanel();
		panelEnemigos.setOpaque(false);
		panelEnemigos.setBounds(0, 0, 800, 400);
		panelEnemigos.setLayout(null);
		panelMapa.add(panelEnemigos);
		
		panelCeldaPremios = new JPanel();
		panelCeldaPremios.setOpaque(false);
		panelCeldaPremios.setBounds(0, 0, 800, 400);
		panelCeldaPremios.setLayout(null);
		panelMapa.add(panelCeldaPremios);
		
		panelCeldas = new JPanel();
		panelCeldas.setOpaque(false);
		panelCeldas.setBounds(0, 0, 800, 400);
		panelCeldas.setLayout(null);
		panelMapa.add(panelCeldas);
		
		//-----------------
		panelPersonajes = new JPanel();
		panelPersonajes.setOpaque(false);
		panelPersonajes.setBounds(0, 0, 100, 562);
		frame.getContentPane().add(panelPersonajes);
		panelPersonajes.setLayout(null);
		
		botonYgritte = new JButton("ygritte");
		botonYgritte.setBounds(0, 0, 100, 93);
		botonYgritte.setIcon(new ImageIcon("res/imagenes/juego/botonYgritte.png"));
		panelPersonajes.add(botonYgritte);
		
		botonMountain = new JButton("mount");
		botonMountain.setBounds(0, 93, 100, 93);
		botonMountain.setIcon(new ImageIcon("res/imagenes/juego/botonMountain.png"));
		panelPersonajes.add(botonMountain);
		
		botonDragon = new JButton("dragon");
		botonDragon.setBounds(0, 186, 100, 96);
		botonDragon.setIcon(new ImageIcon("res/imagenes/juego/botonDragon.png"));
		panelPersonajes.add(botonDragon);
		
		botonInmaculado = new JButton("inmac");
		botonInmaculado.setBounds(0, 279, 100, 94);
		botonInmaculado.setIcon(new ImageIcon("res/imagenes/juego/botonInmaculado.png"));
		panelPersonajes.add(botonInmaculado);
		
		botonBronn = new JButton("bronn");
		botonBronn.setBounds(0, 373, 100, 94);
		botonBronn.setIcon(new ImageIcon("res/imagenes/juego/botonBronn.png"));
		panelPersonajes.add(botonBronn);
		
		botonGendry = new JButton("gendry");
		botonGendry.setBounds(0, 468, 100, 94);
		botonGendry.setIcon(new ImageIcon("res/imagenes/juego/botonGendry.png"));
		panelPersonajes.add(botonGendry);
		
		
		botonBarricada = new JButton("barricada");
		botonBarricada.setBounds(262, 6, 80, 80);
		botonBarricada.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0) 
			{
				FabricaDeDefensa.getInstancia().construirBarricada();
				juego.restarBarricada();	
				if (!juego.hayBarricadas())
					botonBarricada.setEnabled(false);
			}
		});
		botonBarricada.setIcon(new ImageIcon("res/imagenes/obstaculos/barricada.png"));
		frame.getContentPane().add(botonBarricada);
		
		lblMonedas_1 = new JLabel("Monedas: 0");
		lblMonedas_1.setBounds(110, 11, 139, 31);
		frame.getContentPane().add(lblMonedas_1);
		lblMonedas_1.setOpaque(true);
		lblMonedas_1.setFont(new Font("Century", Font.PLAIN, 17));
		
		lblPuntaje = new JLabel("Puntaje: 0");
		lblPuntaje.setBounds(110, 53, 139, 31);
		frame.getContentPane().add(lblPuntaje);
		lblPuntaje.setForeground(Color.BLACK);
		lblPuntaje.setBorder(null);
		lblPuntaje.setOpaque(true);
		lblPuntaje.setFont(new Font("Century", Font.PLAIN, 17));
		
		botonBomba = new JButton("bomba");
		botonBomba.setEnabled(false);
		botonBomba.setBounds(803, 6, 80, 80);
		botonBomba.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0) 
			{
				//FabricaDeDefensa.getInstancia().construirBomba();
				juego.restarBomba();
				if(!juego.hayBombas()) {
					botonBomba.setEnabled(false);
				}
			}
		});
	    botonBomba.setIcon(new ImageIcon("res/imagenes/premios/iconoBomba.png"));
		frame.getContentPane().add(botonBomba);
		
		botonCampo = new JButton("Campo");
		botonCampo.setEnabled(false);
		botonCampo.setBounds(894, 4, 80, 80);
		frame.getContentPane().add(botonCampo);
		
		JButton botonMuro = new JButton("muro");
		botonMuro.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				FabricaDeDefensa.getInstancia().construirMuro();
			}
		});
		botonMuro.setBounds(353, 6, 80, 80);
		botonMuro.setIcon(new ImageIcon("res/imagenes/obstaculos/muro.png"));
		frame.getContentPane().add(botonMuro);
		
		botonRoca = new JButton("roca");
		botonRoca.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FabricaDeDefensa.getInstancia().construirRoca();
			}
		});
		botonRoca.setBounds(444, 6, 80, 80);
		botonRoca.setIcon(new ImageIcon("res/imagenes/obstaculos/roca.png"));
		frame.getContentPane().add(botonRoca);
		
		botonTrinchera = new JButton("trinchera");
		botonTrinchera.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				FabricaDeDefensa.getInstancia().construirTrinchera();
			}
		});
		botonTrinchera.setBounds(534, 6, 80, 80);
		botonTrinchera.setIcon(new ImageIcon("res/imagenes/obstaculos/trinchera.png"));
		frame.getContentPane().add(botonTrinchera);
		
		
		botonCampo.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0) 
			{
				seUso=true;
			}
		});
		
		JLabel labelFondo = new JLabel("");
		labelFondo.setBackground(Color.ORANGE);
		labelFondo.setForeground(Color.ORANGE);
		labelFondo.setIcon(new ImageIcon("res/imagenes/juego/fondo.jpg"));
		labelFondo.setBounds(0, 0, 1000, 562);
		frame.getContentPane().add(labelFondo);
		
		///--- ACCIONES DE LOS BOTONES ----
		botonYgritte.addActionListener(new ActionListener() 
 		{
 			public void actionPerformed(ActionEvent arg0) 
 			{
 				FabricaDeDefensa.getInstancia().construirYgritte(); 
 				juego.restarOro(costosDeDefensa.costoYgritte());	
 					
 			}
 		});
		
		botonMountain.addActionListener(new ActionListener() 
 		{
 			public void actionPerformed(ActionEvent e) 
 			{
 				FabricaDeDefensa.getInstancia().construirMountain();
 				juego.restarOro(costosDeDefensa.costoMountain());
 			}
 		});
		
		botonDragon.addActionListener(new ActionListener() 
 		{
 			public void actionPerformed(ActionEvent e) 
 			{
 				FabricaDeDefensa.getInstancia().construirDragon();
 				juego.restarOro(costosDeDefensa.costoDragon());
 					
 			}
 		});
		
 		botonInmaculado.addActionListener(new ActionListener()
 		{
 			public void actionPerformed(ActionEvent e)
 			{
 				FabricaDeDefensa.getInstancia().construirInmaculado();
 				juego.restarOro(costosDeDefensa.costoInmaculado());
 					
 			}
 		});
		
 		botonGendry.addActionListener(new ActionListener() 
 		{
 			public void actionPerformed(ActionEvent e) 
 			{
 				FabricaDeDefensa.getInstancia().construirGendry();
 				juego.restarOro(costosDeDefensa.costoGendry());
 			}
 		});
 		
 		botonBronn.addActionListener(new ActionListener()
 		{
 			public void actionPerformed(ActionEvent arg0) 
 			{
 				FabricaDeDefensa.getInstancia().construirBronn();
 				juego.restarOro(costosDeDefensa.costoBronn());
 			}
 		});
		
		///-----------BOTONES PREMIOS
		botonPremioBomba = new JButton("Bomba");
		botonPremioBomba.setMargin(new Insets(0, 0, 0, 0));
 		botonPremioBomba.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				juego.agregarBomba();
				botonBomba.setEnabled(true);
				panelCeldaPremios.remove(botonPremioBomba);
			}
 		});
 		
 		botonPremioBarricada = new JButton("Barr");
 		botonPremioBarricada.setMargin(new Insets(0, 0, 0, 0));
 		botonPremioBarricada.addActionListener(new ActionListener() {
 			public void actionPerformed(ActionEvent arg0) {
 				juego.agregarBarricada();
 				botonBarricada.setEnabled(true);
 				panelCeldaPremios.remove(botonPremioBarricada);
 			}
 		});
 		
 		botonPremioOro = new JButton("Oro");
 		botonPremioOro.setMargin(new Insets(0, 0, 0, 0));
 		botonPremioOro.addActionListener(new ActionListener() {
 			public void actionPerformed(ActionEvent arg0) {
 				juego.sumarOro(1000);
 				panelCeldaPremios.remove(botonPremioOro);
 			}
 		});
 		
 		botonPremioCuracion = new JButton("+++");
 		botonPremioCuracion.setMargin(new Insets(0, 0, 0, 0));
 		botonPremioCuracion.addActionListener(new ActionListener() {
 			public void actionPerformed(ActionEvent arg0) {
 				juego.curarDefensas();
 				panelCeldaPremios.remove(botonPremioCuracion);
 			}
 		});
 		
 		botonPremioDanioDoble = new JButton("DD");
 		botonPremioDanioDoble.setMargin(new Insets(0, 0, 0, 0));
 		botonPremioDanioDoble.addActionListener(new ActionListener() {
 			public void actionPerformed(ActionEvent arg0) {
 				juego.danioDoble();
 				panelCeldaPremios.remove(botonPremioDanioDoble);
 			}
 		});
 		
 		botonPremioCampoProtector = new JButton("CPro");
 		botonPremioCampoProtector.setMargin(new Insets(0, 0, 0, 0));
 		botonPremioCampoProtector.addActionListener(new ActionListener() {
 			public void actionPerformed(ActionEvent arg0) {
 				
 			}
 		});
		

		///---------------
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
	}
	
	public void puntaje(int puntaje){
		
		lblPuntaje.setText("Puntaje: "+puntaje);
	}
	
	public void agregarObjetoMovil(int x, int y, GameObject obj) {
		
		JLabel labelEnemigo = new JLabel();
		obj.getGrafico().setLabel(labelEnemigo);
		ImageIcon icono = obj.getGrafico().getImagen();
		labelEnemigo.setIcon(obj.getGrafico().getImagen());
		labelEnemigo.setBounds(x*ANCHO_IMG, y*ALTO_IMG,icono.getIconHeight(),icono.getIconWidth());
		panelEnemigos.add(labelEnemigo);
		repintar();
	}

	public void moverGrafico(ObjetoMovil o) {
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
			
			for(ObjetoMovil o : aBorrar){
				moviles.remove(o);
			}
			aBorrar.clear();
			
			for(ObjetoMovil o : moviles) {
				if(!o.getGrafico().moverGrafico(o.getVelocidad())){
					aBorrar.add(o);
					o.getGrafico().Lock(false);
				}
			}
			repintar();
		}
	}
	
	private void repintar() {
		panelMapa.repaint();

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
	
	public void dibujarDefensa(int x, int y, GameObject obj) {
		JLabel labelCelda = buscarLabel(x, y, panelCeldas);
		JLabel labelNuevo = new JLabel(obj.getGrafico().getImagen());
		ImageIcon icono = obj.getGrafico().getImagen();
		obj.getGrafico().setLabel(labelNuevo);
		labelNuevo.setBounds(labelCelda.getX(), labelCelda.getY(), icono.getIconHeight(), icono.getIconWidth());
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
		lblMonedas_1.setText("Monedas: "+oro);
		//btnJorgito.setEnabled((oro < costosDeDefensa.costoJorgito()) ? false : true);
		
		botonYgritte.setEnabled((oro < costosDeDefensa.costoYgritte()) ? false : true);
		
		botonMountain.setEnabled((oro < costosDeDefensa.costoMountain()) ? false : true); 
		
		botonDragon.setEnabled((oro < costosDeDefensa.costoDragon()) ? false : true);
		
		botonInmaculado.setEnabled((oro < costosDeDefensa.costoInmaculado()) ? false : true);
		
		botonGendry.setEnabled((oro < costosDeDefensa.costoGendry()) ? false : true);
		
		botonBronn.setEnabled((oro < costosDeDefensa.costoBronn()) ? false : true);
		
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
						ImageIcon imagen = defensa.getGrafico().getImagen();
						JLabel labelNuevo = new JLabel(imagen);
						defensa.getGrafico().setLabel(labelNuevo);
						labelNuevo.setBounds(labelCelda.getBounds().x,labelCelda.getBounds().y,imagen.getIconWidth(),imagen.getIconHeight());
						panelDefensa.add(labelNuevo);
						juego.agregarDefensa(x,y);
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
	
	public void habilitarBomba() {
		botonBomba.setEnabled(true);
	}
	
	public MouseListener listenBomba() {
		return new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				JLabel labelCelda = (JLabel) e.getComponent();
				labelCelda.setIcon ( new ImageIcon("/res/imagenes/premios/objetosPreciosos/iconoBomba.png"));
				panelMapa.add(labelCelda);
				int x= labelCelda.getBounds().x / ANCHO_IMG;
				int y= labelCelda.getBounds().y / ALTO_IMG;
				juego.detonarBomba(x,y);
				repintar();
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



	public void agregarPremioBomba(int x, int y) {
		botonPremioBomba.setBounds(x*ANCHO_IMG, y*ALTO_IMG, ANCHO_IMG, ALTO_IMG);
		panelCeldaPremios.add(botonPremioBomba);
	}

	public void agregarPremioBarricada(int x, int y) {
		botonPremioBarricada.setBounds(x*ANCHO_IMG, y*ALTO_IMG, ANCHO_IMG, ALTO_IMG);
		panelCeldaPremios.add(botonPremioCuracion);
	}
	
	public void agregarPremioCuracion(int x, int y) {
		botonPremioCuracion.setBounds(x*ANCHO_IMG, y*ALTO_IMG, ANCHO_IMG, ALTO_IMG);
		panelCeldaPremios.add(botonPremioCuracion);
		
	}

	public void agregarPremioOro(int x, int y) {
		botonPremioOro.setBounds(x*ANCHO_IMG, y*ALTO_IMG, ANCHO_IMG, ALTO_IMG);
		panelCeldaPremios.add(botonPremioOro);
		
	}

	public void agregarPremioDanioDoble(int x, int y) {
		botonPremioDanioDoble.setBounds(x*ANCHO_IMG, y*ALTO_IMG, ANCHO_IMG, ALTO_IMG);
		panelCeldaPremios.add(botonPremioDanioDoble);
	}

	public void agregarPremioCampoProtector(int x, int y) {
		// TODO Auto-generated method stub
		
	}
}